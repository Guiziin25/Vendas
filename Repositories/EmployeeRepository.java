package Repositories;

import Person.Employee;

public class EmployeeRepository {
    private Employee[] employees = new Employee[100];
    private int count = 0;

    public void add(Employee employee) {
        if (count < employees.length) {
            employees[count++] = employee;
        }
    }

    public Employee getById(Long id) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getId().equals(id)) {
                return employees[i];
            }
        }
        return null;
    }

    public int size() {
        return count;
    }
}