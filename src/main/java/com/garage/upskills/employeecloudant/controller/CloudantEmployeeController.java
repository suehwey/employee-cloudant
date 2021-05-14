package com.garage.upskills.employeecloudant.controller;

import com.garage.upskills.employeecloudant.domain.Employee;
import com.garage.upskills.employeecloudant.service.CloudantEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CloudantEmployeeController {

    @Autowired
    private CloudantEmployeeService service;

    @GetMapping("/listDatabases")
    public List<String> listDB() {
        return service.getAllDB();
    }

    @GetMapping("/getEmployees")
    public List<Employee> getEmployees() {
        return service.getEmployees();
    }

    @GetMapping("/getEmployeeById/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
        return service.getEmployeeById(id);
    }

    @GetMapping("/getEmployeeByRole/{role}")
    public List<Employee> getEmployeeByRole(@PathVariable String role) {
        return service.getEmployeeByRole(role);
    }

    @GetMapping("/getEmployeeByCity/{city}")
    public List<Employee> getEmployeeByCity(@PathVariable String city) {
        return service.getEmployeeByCity(city);
    }

    @PostMapping("/addEmployee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return service.saveEmployee(employee);
    }

    @PostMapping("/bulkAddEmployees")
    public List<Employee> saveEmployees(@RequestBody List<Employee> employees) {
        return service.saveEmployees(employees);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployeeById(@PathVariable String id) {
        return service.deleteEmployeeById(id);
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@RequestBody Employee employee) {
        return service.updateEmployee(employee);
    }
}
