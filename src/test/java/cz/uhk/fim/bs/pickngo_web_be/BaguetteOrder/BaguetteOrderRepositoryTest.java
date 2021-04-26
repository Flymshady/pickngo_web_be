package cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder;

import cz.uhk.fim.bs.pickngo_web_be.Customer.Customer;
import cz.uhk.fim.bs.pickngo_web_be.Customer.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BaguetteOrderRepositoryTest {

    @Autowired
    private BaguetteOrderRepository underTest;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown(){
        customerRepository.deleteAll();
        underTest.deleteAll();
    }


    @Test
    void findAllByCustomer() {
        Customer customer = new Customer("name", "email", "emailshort");
        customerRepository.save(customer);

        BaguetteOrder baguetteOrder = new BaguetteOrder(customer, 1.0, new Date(),0, "note");

        underTest.save(baguetteOrder);
        //
        Optional<List<BaguetteOrder>> list = underTest.findAllByCustomer(Optional.of(customer));

        //then
        assertThat(list).isPresent();
    }

    @Test
    void findAllByState(){
        Customer customer = new Customer();
        customerRepository.save(customer);
        BaguetteOrder baguetteOrder = new BaguetteOrder(customer, 1.0, new Date(),0, "note");

        underTest.save(baguetteOrder);
        //
        Optional<List<BaguetteOrder>> list = underTest.findAllByState(baguetteOrder.getState());

        //then
        assertThat(list).isPresent();
    }
}