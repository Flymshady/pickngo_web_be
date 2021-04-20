package cz.uhk.fim.bs.pickngo_web_be.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void registerNewUser(@RequestBody Customer customer){
        customerService.addNewUser(customer);
    }

    @RequestMapping(value = "/remove/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("userId") Long userId){
        customerService.deleteUser(userId);
    }

    @RequestMapping(value = "/update/{userId}", method = RequestMethod.PUT)
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        customerService.updateUser(userId, name, email);
    }

}
