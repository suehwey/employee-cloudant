package com.garage.upskills.employeecloudant.service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.Expression;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.garage.upskills.employeecloudant.domain.Employee;
import com.garage.upskills.employeecloudant.exceptions.BadEmployeeDataException;
import com.garage.upskills.employeecloudant.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CloudantEmployeeService {

    @Autowired
    private CloudantClient client;

    @Autowired
    private Database db;

    public List<String> getAllDB() {
        return client.getAllDbs();
    }

    public List<Employee> getEmployees() {
        try {
            List<Employee> employeeList = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Employee.class);
            return employeeList;
        } catch (Exception e) {
            throw new EmployeeNotFoundException("No Employee Data");
        }

    }

    public Employee getEmployeeById(String id) {
        try {
            return db.find(Employee.class, id);
        } catch (NoDocumentException e) {
            throw new EmployeeNotFoundException("Employee ID: " + id + " doesn't exist.");
        }
    }

    public List<Employee> getEmployeeByRole(String role) {
        List<Employee> employeeList = queryByField("role", role);

        if (employeeList.size() == 0)
            throw new EmployeeNotFoundException("No Employee of role - " + role);

        return employeeList;
    }

    public List<Employee> getEmployeeByCity(String city) {
        List<Employee> employeeList = queryByField("city", city);

        if (employeeList.size() == 0)
            throw new EmployeeNotFoundException("No Employee lives in city - " + city);

        return employeeList;
    }

    public Employee saveEmployee(Employee employee) {
        Response response = db.save(employee);
        return db.find(Employee.class, response.getId());
    }

    public List<Employee> saveEmployees(List<Employee> employees) {
        List<Response> responseList = db.bulk(employees);

        return responseList.stream()
                .map(response -> db.find(Employee.class, response.getId()))
                .collect(Collectors.toList())
                ;
    }

    public String deleteEmployeeById(String id) {
        Employee employee = this.getEmployeeById(id);  // will throw exception if employee id doesn't exist

        db.remove(employee);
        return employee + " has been deleted.";
    }

    public String updateEmployee(Employee employee) throws EmployeeNotFoundException{
        String empId = employee.getId();
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();

        this.getEmployeeById(empId);
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0)
            throw new BadEmployeeDataException("Employee First Name and Last Name can't be null.\n" + employee);

        db.update(employee);
        return "Employee ID: " + empId + " has been updated:\n" + employee;
    }

    private List<Employee> queryByField(String field, String value) {
        String query = new QueryBuilder(Expression.eq(field, value)).build();
        return db.query(query, Employee.class).getDocs();
    }

}
