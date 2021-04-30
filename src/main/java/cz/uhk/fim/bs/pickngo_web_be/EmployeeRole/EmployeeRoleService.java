package cz.uhk.fim.bs.pickngo_web_be.EmployeeRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRoleService {

    private final EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    public EmployeeRoleService(EmployeeRoleRepository employeeRoleRepository){
        this.employeeRoleRepository =employeeRoleRepository;
    }

    public List<EmployeeRole> getEmployeeRoles() {
        return employeeRoleRepository.findAll();
    }


    public void addNewEmployeeRole(EmployeeRole employeeRole) {
        Optional<EmployeeRole> employeeRoleOptional = employeeRoleRepository.findByName(employeeRole.getName());
        if (employeeRoleOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "name taken");
        }
        employeeRoleRepository.save(employeeRole);
    }

    public Optional<EmployeeRole> getEmployeeRole(Long employeeRoleId) { return employeeRoleRepository.findById(employeeRoleId);
    }
}
