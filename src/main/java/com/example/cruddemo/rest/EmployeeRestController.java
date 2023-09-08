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

  // add mapping for post /employees - add new employee
  @PostMapping("employees")
  public Employee addEmployee(@RequestBody Employee employee) {
    // force save of a new item if an id is posted
    employee.setId(0);
    Employee dbEmployee = employeeService.save(employee);
    return dbEmployee;
  }
}
