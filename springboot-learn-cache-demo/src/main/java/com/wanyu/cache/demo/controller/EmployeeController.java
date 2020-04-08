package com.wanyu.cache.demo.controller;



import com.wanyu.cache.demo.entity.Employee;
import com.wanyu.cache.demo.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {


    @Autowired
    IEmployeeService employeeService;

    @GetMapping(value = "/emp/{id}")
    public Employee getEmpById(@PathVariable("id")Long id){
       return employeeService.getEmpById(id);
    }

    @PostMapping(value = "/emp")
    public Employee updateEmployee(@RequestBody Employee employee){
        return employeeService.updateEmp(employee);
    }

    @DeleteMapping(value = "/emp/{id}")
    public Boolean delete(@PathVariable("id") Long id){
        return employeeService.deleteEmp(id);
    }
}


