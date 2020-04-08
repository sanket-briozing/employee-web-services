package com.briozing.employees.api;

import com.briozing.employees.models.EmployeeRequestVO;
import com.briozing.employees.models.EmployeeResponseVO;
import com.briozing.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeApi {
    EmployeeService employeeService;

    public EmployeeApi(@Autowired EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping(value = "/addEmployee",consumes= MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseVO> addEmployee(@RequestBody EmployeeRequestVO employeeRequestVO){
        EmployeeResponseVO employeeResponseVO= employeeService.addEmployeeService(employeeRequestVO);
        return new ResponseEntity<>(employeeResponseVO, HttpStatus.OK);
    }

    @GetMapping(value="/getAll",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponseVO>> getAllEmployees(){
        List<EmployeeResponseVO> employeeResponseVOList=null;
        HttpStatus status= HttpStatus.OK;
        try{
            employeeResponseVOList=employeeService.getAllEmployeesService();
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
            employeeResponseVO = employeeService.updateEmployeeService(employeeRequestVO, id);
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
            long deletedId=employeeService.deleteEmployeeService(id);
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
}
