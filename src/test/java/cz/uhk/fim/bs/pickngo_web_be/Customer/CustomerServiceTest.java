package cz.uhk.fim.bs.pickngo_web_be.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock private CustomerRepository customerRepository;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerRepository);
    }


    @Test
    void getUsers() {

        //when
        underTest.getUsers();
        //then
        verify(customerRepository).findAll();
    }

    @Test
    void getUser() {

        Long id = 1L;

        //when
        underTest.getUser(id);
        //then
        verify(customerRepository).findById(id);
    }


}