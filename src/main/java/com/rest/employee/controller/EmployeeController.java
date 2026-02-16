package com.rest.employee.controller;

import com.rest.employee.dao.EmployeeDAOJpaImpl;
import com.rest.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeDAOJpaImpl employeeDAOJpa;

    @GetMapping("/employees")
    public List<Employee> allEmployees(){
        return employeeDAOJpa.findAll();
    }
}
