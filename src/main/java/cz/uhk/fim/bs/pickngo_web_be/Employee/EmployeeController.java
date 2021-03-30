package cz.uhk.fim.bs.pickngo_web_be.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @PostMapping
    public void registerNewEmployee(@RequestBody Employee employee){
        employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path = "{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping(path = "{employeeId}")
    public void updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String password) {
        employeeService.updateEmployee(employeeId, firstname, lastname, login, password);
    }
}
