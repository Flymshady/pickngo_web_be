package cz.uhk.fim.bs.pickngo_web_be.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.uhk.fim.bs.pickngo_web_be.Employee.Employee;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Role {

    @Id
    @SequenceGenerator(name="role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    private Long id;
    private String name;
    @JsonIgnore
    private List<Employee> employees;

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, List<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(){}

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
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }
}
