package cz.uhk.fim.bs.pickngo_web_be.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/user")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getUsers() {
        return customerService.getUsers();
    }

    @PostMapping
    public void registerNewUser(@RequestBody Customer customer){
        customerService.addNewUser(customer);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        customerService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        customerService.updateUser(userId, name, email);
    }

}
