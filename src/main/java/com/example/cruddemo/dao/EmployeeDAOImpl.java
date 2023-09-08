package com.example.cruddemo.dao;

import com.example.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

  // define field for EntityManager
  private EntityManager entityManager;

  // set up constructor injection
  @Autowired
  public EmployeeDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Employee> findAll() {
    // create a query
    TypedQuery<Employee> myQuery = entityManager.createQuery("FROM Employee", Employee.class);

    // execute query and get result list
    List<Employee> employees = myQuery.getResultList();

    // return the results
    return employees;
  }

  @Override
  public Employee findById(int id) {
    //get employee
    Employee employee = entityManager.find(Employee.class, id);

    //return employee
    return employee;
  }

  @Override
  public Employee save(Employee employee) {
    // save if id == 0, else it updates the existing employee
    Employee dbEmployee = entityManager.merge(employee);

    // return dbEmployee (from DB)
    return dbEmployee;
  }

  @Override
  public void deleteById(int id) {
    // find employee
    Employee employee = entityManager.find(Employee.class, id);
    // remove employee
    entityManager.remove(employee);
  }
}
