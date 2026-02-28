package com.rest.employee.dao;

import com.rest.employee.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

//    // define field for entityManager
//    private EntityManager entityManager;
//
//    // set up constructor injection
//    public EmployeeDAOJpaImpl(EntityManager theEntityManager){
//        entityManager = theEntityManager;
//    }

//    or

    // define field for entityManager
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Employee> findAll(){

        // create query
        TypedQuery<Employee> query = entityManager.createQuery("from Employee", Employee.class);

        // execute query and get list
        List<Employee> result = query.getResultList();

        // return list
        return result;
    }

    @Override
    public Employee findById(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee;
    }

    @Override
    public Employee save(Employee employee) {
        return entityManager.merge(employee); // merge will add if there is no data with same id and update if the data with same id exists
    }

    @Override
    public void deleteById(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }
}
