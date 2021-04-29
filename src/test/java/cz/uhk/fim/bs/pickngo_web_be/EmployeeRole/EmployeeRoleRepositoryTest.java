package cz.uhk.fim.bs.pickngo_web_be.EmployeeRole;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class EmployeeRoleRepositoryTest {

    @Autowired
    private EmployeeRoleRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }


    @Test
    void shouldFindByName() {
        String name = "testrolename";

        EmployeeRole employeeRole = new EmployeeRole(name);

        underTest.save(employeeRole);
        //when
        EmployeeRole result = underTest.findByName(name);
        //then
        assertThat(result).isEqualTo(employeeRole);
    }
}