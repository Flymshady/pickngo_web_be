package cz.uhk.fim.bs.pickngo_web_be.Employee;

import cz.uhk.fim.bs.pickngo_web_be.EmployeeRole.EmployeeRole;
import cz.uhk.fim.bs.pickngo_web_be.EmployeeRole.EmployeeRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock private EmployeeRepository employeeRepository;

    @Mock private EmployeeRoleRepository employeeRoleRepository;
    @Mock private PasswordEncoder passwordEncoder;
    private EmployeeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new EmployeeService(employeeRepository, passwordEncoder, employeeRoleRepository);
    }

    @Test
    void canGetEmployees() {
        //when
        underTest.getEmployees();
        //then
        verify(employeeRepository).findAll();
}

    @Test
    void canAddNewEmployee() {
        //given
        Employee employee = new Employee(
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );
        EmployeeRole employeeRole = new EmployeeRole("Employee");

        //when
        given(employeeRoleRepository.findByName("Employee")).willReturn(Optional.of(employeeRole));
        underTest.addNewEmployee(employee);
        //then
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee capturedEmp = employeeArgumentCaptor.getValue();

        assertThat(capturedEmp).isEqualTo(employee);
    }

    @Test
    void willThrowWhenLoginIsTakenInAddNewEmployee() {
        //given
        Employee employee = new Employee(
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );

        given(employeeRepository.findEmployeeByLogin(employee.getLogin())).willReturn(Optional.of(employee));

        assertThatThrownBy(() ->underTest.addNewEmployee(employee))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("Uživatelské jméno je již obsazeno");

        verify(employeeRepository, never()).save(any());

    }

    @Test
    void canAddNewAdmin() {
        //given
        Employee employee = new Employee(
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );
        EmployeeRole employeeRole = new EmployeeRole("Admin");

        //when
        given(employeeRoleRepository.findByName("Admin")).willReturn(Optional.of(employeeRole));
        //when
        underTest.addNewAdmin(employee);
        //then
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee capturedEmp = employeeArgumentCaptor.getValue();

        assertThat(capturedEmp).isEqualTo(employee);
    }

    @Test
    void willThrowWhenLoginIsTakenInAddNewAdmin() {
        //given
        Employee employee = new Employee(
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );

        given(employeeRepository.findEmployeeByLogin(employee.getLogin())).willReturn(Optional.of(employee));

        assertThatThrownBy(() ->underTest.addNewAdmin(employee))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("Uživatelské jméno je již obsazeno");

        verify(employeeRepository, never()).save(any());

    }

    @Test
    void canDeleteEmployee() {
        //given
        Employee employee = new Employee(
                1L,
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );

        given(employeeRepository.existsById(employee.getId())).willReturn(true);
        //when
        underTest.deleteEmployee(employee.getId());
        //then
        verify(employeeRepository).deleteById(employee.getId());
    }


    @Test
    void willThrowWhenDoesntExistInDeleteEmployee() {
        //given
        Employee employee = new Employee(
                1L,
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );

        given(employeeRepository.existsById(employee.getId())).willReturn(false);
        assertThatThrownBy(() ->underTest.deleteEmployee(employee.getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("employee with id " + employee.getId() + " doesnt exist");

        verify(employeeRepository, never()).deleteById(any());
    }

    @Test
    void canUpdateEmployee() {

        Long roleId = 42L;
        EmployeeRole employeeRole = new EmployeeRole(roleId, "role");

        Employee employee = new Employee(
                1L,
                "jmeno",
                "prijmeni",
                "login",
                "pw",
                employeeRole
        );

        Employee employee2 = new Employee(
                1L,
                "jmeno2",
                "prijmeni2",
                "login2",
                "pw2",
                employeeRole
        );
        given(employeeRepository.findById(employee2.getId())).willReturn(Optional.of(employee));
        given(employeeRepository.findEmployeeByLogin(employee2.getLogin())).willReturn(Optional.empty());
        given(employeeRoleRepository.findById(roleId)).willReturn(Optional.of(employeeRole));
        String pwe = passwordEncoder.encode(employee2.getPassword());
        //when
        underTest.updateEmployee(employee2.getId(), employee2.getFirstname(), employee2.getLastname(),employee2.getLogin(), pwe, roleId);

    }

    @Test
    void willThrowWhenByIdDoesntExistInUpdateEmployee() {

        Long roleId = 42L;
        EmployeeRole employeeRole = new EmployeeRole(roleId, "role");

        Employee employee = new Employee(
                1L,
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );

        Employee employee2 = new Employee(
                1L,
                "jmeno2",
                "prijmeni2",
                "login2",
                "pw2"
        );

        given(employeeRepository.findById(employee.getId())).willReturn(Optional.empty());
        assertThatThrownBy(() ->underTest.updateEmployee(employee2.getId(), employee2.getFirstname(), employee2.getLastname(),employee2.getLogin(), employee2.getPassword(), roleId))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("employee with id " + employee.getId() + " doesnt exist");

        verify(employeeRepository, never()).save(any());

    }
    @Test
    void willThrowWhenLoginExistsInUpdateEmployee() {

        Long roleId = 42L;
        EmployeeRole employeeRole = new EmployeeRole(roleId, "role");

        Employee employee = new Employee(
                1L,
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );

        Employee employee2 = new Employee(
                1L,
                "jmeno2",
                "prijmeni2",
                "login3",
                "pw2"
        );

        Employee employee3 = new Employee(
                2L,
                "jmeno3",
                "prijmeni3",
                "login3",
                "pw3"
        );

        assertThatThrownBy(() ->underTest.updateEmployee(employee2.getId(), employee2.getFirstname(), employee2.getLastname(),employee2.getLogin(), employee2.getPassword(), roleId))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("login taken");

        verify(employeeRepository, never()).save(any());

    }

    @Test
    void willThrowWhenRoleDoesntExistInUpdateEmployee() {

        Long roleId = 42L;
        EmployeeRole employeeRole = new EmployeeRole(roleId, "role");

        Employee employee = new Employee(
                1L,
                "jmeno",
                "prijmeni",
                "login",
                "pw"
        );

        Employee employee2 = new Employee(
                1L,
                "jmeno2",
                "prijmeni2",
                "login3",
                "pw2"
        );

        Employee employee3 = new Employee(
                2L,
                "jmeno3",
                "prijmeni3",
                "login3",
                "pw3"
        );

        assertThatThrownBy(() ->underTest.updateEmployee(employee2.getId(), employee2.getFirstname(), employee2.getLastname(),employee2.getLogin(), employee2.getPassword(), employeeRole.getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("role with id "+ roleId + " doesnt exists");

        verify(employeeRepository, never()).save(any());

    }




    @Test
    void getEmployee() {
        Long id = 1L;
        //when
        underTest.getEmployee(id);
        //then
        verify(employeeRepository).findById(id);
    }
}