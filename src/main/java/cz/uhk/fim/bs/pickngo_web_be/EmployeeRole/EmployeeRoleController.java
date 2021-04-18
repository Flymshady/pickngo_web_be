package cz.uhk.fim.bs.pickngo_web_be.EmployeeRole;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/employeeRole")
public class EmployeeRoleController {

    private final EmployeeRoleService employeeRoleService;

    @Autowired
    public EmployeeRoleController(EmployeeRoleService employeeRoleService) {
        this.employeeRoleService = employeeRoleService;
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<EmployeeRole> getEmployeeRoles() {
        return employeeRoleService.getEmployeeRoles();
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/detail/{employeeRoleId}", method = RequestMethod.GET)
    public Optional<EmployeeRole> getEmployeeRole(
            @PathVariable("employeeRoleId") Long employeeId) {
        return  employeeRoleService.getEmployeeRole(employeeId);
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void registerNewEmployeeRole(@RequestBody EmployeeRole employeeRole){
        employeeRoleService.addNewEmployeeRole(employeeRole);
    }
}
