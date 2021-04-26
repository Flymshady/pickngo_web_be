package cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder;

import cz.uhk.fim.bs.pickngo_web_be.Customer.Customer;
import cz.uhk.fim.bs.pickngo_web_be.Customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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

        //when
        underTest.getBaguetteOrdersByCustomer(c.getId());
        //then
        verify(baguetteOrderRepository).findAllByCustomer_Id(c.getId());

    }

    @Test
    void getBaguetteOrderById() {
        Long id = 84L;
        BaguetteOrder baguetteOrder = new BaguetteOrder(id, new Customer(), 2.0,new Date(), 0,"note");
        given(baguetteOrderRepository.findById(id)).willReturn(Optional.of(baguetteOrder));
        underTest.getBaguetteOrderById(id);
        verify(baguetteOrderRepository).findById(id);
    }

    @Test
    void WontGetBaguetteOrderById() {
        Long id = 84L;
        assertThatThrownBy(() ->underTest.getBaguetteOrderById(id))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("baguette order with id "+ id + " doesnt exist");
    }

    @Test
    void updateBaguetteOrder() {
        Long id = 88L;
        Customer customer = new Customer();
        Date date = new Date();
        BaguetteOrder baguetteOrder = new BaguetteOrder(id, customer, 2.0, date, 0,"note");
        BaguetteOrder baguetteOrder2 = new BaguetteOrder(id, customer, 2.0,date, 1,"note");

        given(baguetteOrderRepository.findById(baguetteOrder2.getId())).willReturn(Optional.of(baguetteOrder));

        underTest.updateBaguetteOrder(baguetteOrder2.getId(), baguetteOrder2.getState());
    }
    @Test
    void wontUpdateBaguetteOrder() {
        Long id = 88L;
        Customer customer = new Customer();
        Date date = new Date();
        BaguetteOrder baguetteOrder = new BaguetteOrder(id, customer, 2.0, date, 0,"note");
        BaguetteOrder baguetteOrder2 = new BaguetteOrder(id, customer, 2.0,date, 1,"note");

        given(baguetteOrderRepository.findById(baguetteOrder2.getId())).willReturn(Optional.empty());
        assertThatThrownBy(() ->underTest.updateBaguetteOrder(baguetteOrder2.getId(), baguetteOrder2.getState()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("employee with id " + baguetteOrder2.getId() + " doesnt exist");

        verify(baguetteOrderRepository, never()).save(any());
    }

    @Test
    void getBaguetteOrdersByState() {
        int state = 5;
        List<BaguetteOrder> list = new ArrayList<>();
        BaguetteOrder baguetteOrder = new BaguetteOrder(new Customer(), 2.0,new Date(), state,"note");
        given(baguetteOrderRepository.findAllByState(5)).willReturn(Optional.of(list));
        underTest.getBaguetteOrdersByState(state);
        verify(baguetteOrderRepository).findAllByState(state);

    }
}