package com.rest.employee.service;

import com.rest.employee.dao.EmployeeDAO;
import com.rest.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public Employee findById(int id) {
        return employeeDAO.findById(id);
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        employeeDAO.deleteById(id);
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        Employee oldEmployee = employeeDAO.findById(employee.getId());
        if(oldEmployee == null){
            throw new RuntimeException("Employee not found");
        }
        oldEmployee.setEmail(employee.getEmail());
        oldEmployee.setFirstName(employee.getFirstName());
        oldEmployee.setLastName(employee.getLastName());
        return employeeDAO.update(oldEmployee);
    }
}
