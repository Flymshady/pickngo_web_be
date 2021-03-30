package cz.uhk.fim.bs.pickngo_web_be.Employee;

import cz.uhk.fim.bs.pickngo_web_be.Role.Role;

import javax.persistence.*;

@Entity
@Table
public class Employee {

    @Id
    @SequenceGenerator(name="employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    private Long id;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private Role role;

    public Employee(String firstname, String lastname, String login, String password, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Employee(Long id,String firstname, String lastname, String login, String password, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Employee(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
