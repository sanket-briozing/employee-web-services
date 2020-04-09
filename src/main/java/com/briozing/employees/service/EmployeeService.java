package com.briozing.employees.service;

import com.briozing.employees.models.EmployeeRequestVO;
import com.briozing.employees.models.EmployeeResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private DataSource dataSource;

    @Autowired
    public EmployeeService(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
        this.dataSource=dataSource;
    }

    public EmployeeResponseVO addEmployee1Service(EmployeeRequestVO employeeRequestVO){
        String query="insert into employees (name,email) values ('"+employeeRequestVO.getName()+"','"+employeeRequestVO.getEmail()+"');";
        int id=jdbcTemplate.update(query);
        EmployeeResponseVO employeeResponseVO = new EmployeeResponseVO();
        employeeResponseVO.setName(employeeRequestVO.getName());
        employeeResponseVO.setEmail(employeeRequestVO.getEmail());
        System.out.println(employeeRequestVO);
        return employeeResponseVO;
    }

    public EmployeeResponseVO addEmployeeService(EmployeeRequestVO employeeRequestVO){
        simpleJdbcInsert=new SimpleJdbcInsert(dataSource)
                .withTableName("employees")
                .usingGeneratedKeyColumns("id");

        Map<String,String> requestMap= new HashMap<>();
        requestMap.put("name",employeeRequestVO.getName());
        requestMap.put("email",employeeRequestVO.getEmail());

        Number id=simpleJdbcInsert.executeAndReturnKey(requestMap);
        System.out.println("New record id is :- "+id);
        EmployeeResponseVO employeeResponseVO = new EmployeeResponseVO();
        employeeResponseVO.setId(id.longValue());
        employeeResponseVO.setName(employeeRequestVO.getName());
        employeeResponseVO.setEmail(employeeRequestVO.getEmail());
        System.out.println(employeeRequestVO);
        return employeeResponseVO;
    }

    public List<EmployeeResponseVO> getAllEmployeesService(){
        String query="select * from employees;";
        System.out.println("Query :- "+query);
        return jdbcTemplate.query(query,(rs,rowNum)-> new EmployeeResponseVO(rs.getLong("id"),rs.getString("name"),rs.getString("email")));
    }

    public EmployeeResponseVO updateEmployeeService(EmployeeRequestVO employeeRequestVO,Long id){
        String query="UPDATE employees SET name='"+employeeRequestVO.getName()+"',email='"+employeeRequestVO.getEmail()+"' WHERE id='"+id+"'";
        int updatedId=jdbcTemplate.update(query);
        EmployeeResponseVO employeeResponseVO = null;
        if(updatedId!=0) {
            employeeResponseVO= new EmployeeResponseVO();
            employeeResponseVO.setId(id);
            employeeResponseVO.setName(employeeRequestVO.getName());
            employeeResponseVO.setEmail(employeeRequestVO.getEmail());
        }
        System.out.println(employeeResponseVO);
        return employeeResponseVO;
    }

    public long deleteEmployeeService(long id){
        String query="DELETE from employees WHERE id='"+id+"'";
        long deletedId= jdbcTemplate.update(query);
        return deletedId;
    }

    public EmployeeResponseVO getEmployeeByIdService(Long id){
        String query="SELECT * FROM employees WHERE id='"+id+"'";
        System.out.println(query);
        Map<String, Object> map = jdbcTemplate.queryForMap(query);
        EmployeeResponseVO employeeResponseVO=new EmployeeResponseVO();
        employeeResponseVO.setId(Long.parseLong(String.valueOf(map.get("id"))));
        employeeResponseVO.setName(String.valueOf(map.get("name")));
        employeeResponseVO.setEmail(String.valueOf(map.get("email")));
        System.out.println(employeeResponseVO);
        return employeeResponseVO;
    }

    public EmployeeResponseVO getEmployeeByNameService(String name){
        String query="SELECT * FROM employees WHERE name='"+name+"'";
        System.out.println(query);
        Map<String, Object> map=jdbcTemplate.queryForMap(query);
        EmployeeResponseVO employeeResponseVO=new EmployeeResponseVO();
        employeeResponseVO.setId(Long.parseLong(String.valueOf(map.get("id"))));
        employeeResponseVO.setName(String.valueOf(map.get("name")));
        employeeResponseVO.setEmail(String.valueOf(map.get("email")));
        System.out.println(employeeResponseVO);
        return employeeResponseVO;
    }
}
