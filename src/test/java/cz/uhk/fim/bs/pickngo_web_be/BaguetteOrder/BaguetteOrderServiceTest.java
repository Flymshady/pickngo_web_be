package cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder;

import cz.uhk.fim.bs.pickngo_web_be.Customer.Customer;
import cz.uhk.fim.bs.pickngo_web_be.Customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class BaguetteOrderServiceTest {

    @Mock private BaguetteOrderRepository baguetteOrderRepository;
    @Mock private CustomerRepository customerRepository;
    private BaguetteOrderService underTest;
    @BeforeEach
    void setUp() {
        underTest = new BaguetteOrderService(baguetteOrderRepository, customerRepository);
    }

    @Test
    void getBaguetteOrders() {
        //when
        underTest.getBaguetteOrders();
        //then
        verify(baguetteOrderRepository).findAll();
    }

    @Test
    void getBaguetteOrdersByCustomer() {
        Customer c = new Customer(1L, "name", "email", "eshort");
/*
        //when
        underTest.getBaguetteOrdersByCustomer(c.getId());
        //then
        verify(baguetteOrderRepository).findAllByCustomer(c.getId());

 */
    }

    @Test
    void getBaguetteOrderById() {
    }

    @Test
    void updateBaguetteOrder() {
    }

    @Test
    void getBaguetteOrdersByState() {
    }
}