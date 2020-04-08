package com.wanyu.cache.demo.service;



import com.wanyu.cache.demo.entity.Employee;

import java.util.List;

public interface IEmployeeService {


    Employee getEmpById(Long id);

    Employee updateEmp(Employee employee);

    Boolean deleteEmp(Long id);

    List<Employee> list();

    Employee insertEmp(Employee employee);
}
