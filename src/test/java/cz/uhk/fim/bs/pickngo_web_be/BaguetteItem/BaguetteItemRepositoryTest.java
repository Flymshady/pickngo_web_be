package cz.uhk.fim.bs.pickngo_web_be.BaguetteItem;

import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrderRepository;
import cz.uhk.fim.bs.pickngo_web_be.Customer.Customer;
import cz.uhk.fim.bs.pickngo_web_be.Customer.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BaguetteItemRepositoryTest {

    @Autowired
    private BaguetteItemRepository underTest;
    @Autowired
    private BaguetteOrderRepository baguetteOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown(){
        customerRepository.deleteAll();
        baguetteOrderRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void findAllByBaguetteOrder_Id() {

        Customer customer = new Customer("name", "email", "eshort");
        customerRepository.save(customer);
        BaguetteOrder baguetteOrder = new BaguetteOrder(customer, 2.0, new Date(), 0, "note");
        baguetteOrderRepository.save(baguetteOrder);
        BaguetteItem baguetteItem = new BaguetteItem(baguetteOrder, 2.0);
        underTest.save(baguetteItem);
        List<BaguetteItem> list = underTest.findAllByBaguetteOrder_Id(baguetteOrder.getId());
        assertThat(list).containsExactly(baguetteItem);


    }
}