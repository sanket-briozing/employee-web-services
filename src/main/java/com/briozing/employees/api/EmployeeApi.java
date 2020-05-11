package com.briozing.employees.api;

import com.briozing.employees.models.EmployeeRequestVO;
import com.briozing.employees.models.EmployeeResponseVO;
import com.briozing.employees.service.CountryService;
import com.briozing.employees.service.EmployeeService;
import com.briozing.employees.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeApi {

    EmployeeService employeeService;
    RestService restService;
    CountryService countryService;

    @Autowired
    public EmployeeApi(EmployeeService employeeService, RestService restService, CountryService countryService){
        this.employeeService=employeeService;
        this.restService = restService;
        this.countryService=countryService;
    }

    @PostMapping(value = "/addEmployee",consumes= MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> addEmployee(@RequestBody EmployeeRequestVO employeeRequestVO){
        EmployeeResponseVO employeeResponseVO = null;
        HttpStatus httpStatus = countryService.FindByName(employeeRequestVO.getCountry());
        if (httpStatus.is2xxSuccessful()) {
            employeeResponseVO = employeeService.addEmployee(employeeRequestVO);
        }
        else {
//          httpStatus = HttpStatus.BAD_REQUEST;
            employeeResponseVO =new EmployeeResponseVO();
            employeeResponseVO.setName(employeeRequestVO.getName());
            employeeResponseVO.setEmail(employeeRequestVO.getEmail());
            employeeResponseVO.setCountry(employeeRequestVO.getCountry());
            List<String> errors= new ArrayList<>();
            errors.add("Invalid Country");
            employeeResponseVO.setErrors(errors);
        }
        return ResponseEntity.status(httpStatus).body(employeeResponseVO);
    }
//
    @GetMapping(value="/getAll",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponseVO>> getAllEmployees(){
        System.out.println("Hello World");
        List<EmployeeResponseVO> employeeResponseVOList=null;
        HttpStatus status= HttpStatus.OK;
        try{
            employeeResponseVOList=employeeService.getAllEmployees();
        }catch (Exception e){
            status=HttpStatus.NOT_FOUND;
            employeeResponseVOList=null;
        }
        return new ResponseEntity<>(employeeResponseVOList,status);
    }

    @PutMapping(value = "/updateEmployee/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> updateEmployee(@RequestBody EmployeeRequestVO employeeRequestVO,@PathVariable Long id){
        HttpStatus status= HttpStatus.OK;
        EmployeeResponseVO employeeResponseVO=null;
        try {
            employeeResponseVO = employeeService.updateEmployee(employeeRequestVO, id);
            if(employeeResponseVO==null){
                status=HttpStatus.NOT_FOUND;
            }
        }catch (Exception e){
            status=HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(employeeResponseVO,status);
    }

    @DeleteMapping(value="/deleteEmployee/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        HttpStatus status= HttpStatus.CREATED;
        String message="Employee deleted successfully";
        try{
            long deletedId=employeeService.deleteEmployee(id);
            if(deletedId==0){
                status= HttpStatus.NOT_FOUND;
                message="Employee record not found";
            }
        }catch (Exception e)
        {
            status= HttpStatus.NOT_FOUND;
            message="Employee record not found";
        }
        return new ResponseEntity<>(message,status);
    }

    @GetMapping(value = "/id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> getEmployeeById(@PathVariable long id){
        EmployeeResponseVO employeeResponseVO=null;
        HttpStatus status=HttpStatus.OK;
        try{
            employeeResponseVO=employeeService.getEmployeeById(id);

            //call country api
            //res= localhost:8888/country/employeeResponseVO.getCountry
            //if res is !=200
        }catch(Exception e){
            status=HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(employeeResponseVO,status);
    }

    @GetMapping(value = "/name/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> getEmployeeById(@PathVariable String name){
        EmployeeResponseVO employeeResponseVO=null;
        HttpStatus status=HttpStatus.OK;
        try{
            employeeResponseVO=employeeService.getEmployeeByName(name);
        }catch(Exception e){
            status=HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(employeeResponseVO,status);
    }

    @GetMapping
    public String getHello(){

        return "Hello";
    }
}
