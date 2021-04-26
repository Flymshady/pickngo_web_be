package cz.uhk.fim.bs.pickngo_web_be.Customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {

        //given
        String email = "email@email.cz";
        Customer c = new Customer("jmeno", email , "emailshort");

        underTest.save(c);
        //when
        Optional<Customer> optionalCustomer = underTest.findUserByEmail(email);
        //then
        assertThat(optionalCustomer).isPresent();
    }


    @Test
    void itShouldNotFindEmployeeByLogin() {
        //given
        String email = "email@email.cz";
        //when
        Optional<Customer> optionalCustomer = underTest.findUserByEmail(email);
        //then
        assertThat(optionalCustomer).isNotPresent();
    }
}