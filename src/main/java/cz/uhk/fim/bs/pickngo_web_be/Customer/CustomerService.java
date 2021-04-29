package cz.uhk.fim.bs.pickngo_web_be.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public List<Customer> getUsers() {
        return customerRepository.findAll();
    }


    public  Optional<Customer> getUser(Long userId) {
        Optional<Customer> customer = customerRepository.findById(userId);
        return customer;
    }
}
