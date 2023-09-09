package com.example.cruddemo.rest;

import com.example.cruddemo.dao.EmployeeDAO;
import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

  private EmployeeService employeeService;

  // quick solution: inject EmployeeDAO (constructor injection)
  public EmployeeRestController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // expose "/employees" and return a list of employees
  @GetMapping("/employees")
  public List<Employee> findAll() {
    return employeeService.findAll();
  }

  // expose "/employees/{employeeId}" and return employee
  @GetMapping("/employees/{employeeId}")
  public Employee getEmployee(@PathVariable int employeeId) {
    Employee employee = employeeService.findById(employeeId);
    if (employee == null) {
      throw new RuntimeException("Employee id not found - " + employeeId);
    }
    return employee;
  }

  // add mapping for POSt /employees - add new employee
  @PostMapping("/employees")
  public Employee addEmployee(@RequestBody Employee employee) {
    // force save of a new item if an id is posted
    employee.setId(0);
    return employeeService.save(employee);
  }

  // add mapping for PUT /employees - update existing employee
  @PutMapping("/employees")
  public Employee updateEmployee(@RequestBody Employee employee) {
    return employeeService.save(employee);
  }

  // add mapping for DELTE /employees - delete existing employee by id
  @DeleteMapping("/employees/{employeeId}")
  public String deleteEmployee(@PathVariable int employeeId) {
    Employee tempEmployee = employeeService.findById(employeeId);

    // throw exception if null
    if (tempEmployee == null) {
      throw new RuntimeException("Employee id not found - " + employeeId);
    }

    employeeService.deleteById(employeeId);
    return "Deleted Employee with id " + employeeId;
  }
}
