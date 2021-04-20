package cz.uhk.fim.bs.pickngo_web_be.Employee;

import cz.uhk.fim.bs.pickngo_web_be.EmployeeRole.EmployeeRole;
import cz.uhk.fim.bs.pickngo_web_be.EmployeeRole.EmployeeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }


    @RequestMapping(value = "/detail/{employeeId}", method = RequestMethod.GET)
    public Optional<Employee> getEmployee(
            @PathVariable("employeeId") Long employeeId) {
        return  employeeService.getEmployee(employeeId);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void registerNewEmployee(@RequestBody Employee employee){
        employeeService.addNewEmployee(employee);
    }

    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public void registerNewAdmin(@RequestBody Employee employee){
        employeeService.addNewAdmin(employee);
    }


    @RequestMapping(value = "/remove/{employeeId}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }


    @RequestMapping(value = "/update/{employeeId}", method = RequestMethod.PUT)
    public void updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String password) {
        employeeService.updateEmployee(employeeId, firstname, lastname, login, password);
    }
}
