package cz.uhk.fim.bs.pickngo_web_be.EmployeeRole;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.uhk.fim.bs.pickngo_web_be.Employee.Employee;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class EmployeeRole {

    @Id
    @SequenceGenerator(name="employee_role_sequence", sequenceName = "employee_role_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_role_sequence")
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "employeeRole")
    private List<Employee> employees;

    public EmployeeRole(String name) {
        this.name = name;
    }

    public EmployeeRole(String name, List<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }

    public EmployeeRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EmployeeRole(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "EmployeeRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }
}
