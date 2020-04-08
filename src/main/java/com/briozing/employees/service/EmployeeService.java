package com.briozing.employees.service;

import com.briozing.employees.models.EmployeeRequestVO;
import com.briozing.employees.models.EmployeeResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class EmployeeService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeService(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    public EmployeeResponseVO addEmployeeService(EmployeeRequestVO employeeRequestVO){
        String query="insert into employees (name,email) values ('"+employeeRequestVO.getName()+"','"+employeeRequestVO.getEmail()+"');";
        int newId = jdbcTemplate.update(query);
        EmployeeResponseVO employeeResponseVO = new EmployeeResponseVO();
        employeeResponseVO.setId(newId);
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
}
