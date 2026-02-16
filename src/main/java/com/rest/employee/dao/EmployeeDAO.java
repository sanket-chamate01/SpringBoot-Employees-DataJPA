package com.rest.employee.dao;

import com.rest.employee.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> findAll();
}
