package org.kainos.ea.api;

import org.kainos.ea.cli.Contractor;
import org.kainos.ea.cli.Employee;
import org.kainos.ea.cli.SalesEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    public List<Employee> getEmployees(){
        Employee employee = new Employee(1, "Shawn", 20000);

        SalesEmployee salesEmployee = new SalesEmployee(1, "Shaun", 2000,
                1000, 0.01f);
        Contractor contractor = new Contractor("Shaun", 1000, 10);


        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        employees.add(salesEmployee);
        employees.add(contractor);

        for(Employee e : employees){
            System.out.println(e);
        }

        return employees;
    }
}
