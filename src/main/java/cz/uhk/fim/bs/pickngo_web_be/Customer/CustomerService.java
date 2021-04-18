package cz.uhk.fim.bs.pickngo_web_be.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
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


    public void addNewUser(Customer customer) {
        Optional<Customer> userOptional = customerRepository.findUserByEmail(customer.getEmail());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email taken");
        }
        customerRepository.save(customer);
    }

    public void deleteUser(Long userId) {
        boolean exists = customerRepository.existsById(userId);
        if(!exists){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "user with id "+ userId + " doesnt exist");
        }
        customerRepository.deleteById(userId);
    }
    @Transactional
    public void updateUser(Long userId, String name, String email){
        Customer customer = customerRepository.findById(userId).orElseThrow(()-> new IllegalStateException("customer with id "+ userId+ "doesnt exist"));
        if (name !=null && name.length() > 0 && !Objects.equals(customer.getName(), name)){
            customer.setName(name);
        }
        if (email !=null && email.length() > 0 && !Objects.equals(customer.getEmail(), email)){
            Optional<Customer> userOptional = customerRepository.findUserByEmail(email);
            if (userOptional.isPresent()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "email taken");
            }
            customer.setEmail(email);
        }
    }
}
