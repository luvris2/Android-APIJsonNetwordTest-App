package com.luvris2.apidatagettestapp.Model;

import java.io.Serializable;

public class Employee implements Serializable {

    public String employee_name;
    public int employee_id, employee_salary, employee_age;

    public Employee(int employee_id,String employee_name, int employee_age, int employee_salary) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
    }
}
