package cz.uhk.fim.bs.pickngo_web_be.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(path="/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Customer> getUsers() {
        return customerService.getUsers();
    }

    @RequestMapping(value = "/detail/{userId}", method = RequestMethod.GET)
    public Optional<Customer> getUser(@PathVariable("userId") Long userId) {
        return customerService.getUser(userId);
    }


}
