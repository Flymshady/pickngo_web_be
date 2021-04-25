package cz.uhk.fim.bs.pickngo_web_be.Employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindEmployeeByLogin() {
        //given
        String login = "testlogin";
        Employee employee = new Employee(
                "jmeno",
                "prijmeni",
                login,
                "pw"
        );
        underTest.save(employee);
        //when
        Optional<Employee> optionalEmployee = underTest.findEmployeeByLogin(login);
        //then
        assertThat(optionalEmployee).isPresent();
    }


    @Test
    void itShouldNotFindEmployeeByLogin() {
        //given
        String login = "testlogin";
        //when
        Optional<Employee> optionalEmployee = underTest.findEmployeeByLogin(login);
        //then
        assertThat(optionalEmployee).isNotPresent();
    }
}