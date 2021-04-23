package cz.uhk.fim.bs.pickngo_web_be.Employee;

import cz.uhk.fim.bs.pickngo_web_be.EmployeeRole.EmployeeRole;
import cz.uhk.fim.bs.pickngo_web_be.EmployeeRole.EmployeeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, EmployeeRoleRepository employeeRoleRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRoleRepository = employeeRoleRepository;
    }


    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }


    public void addNewEmployee(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByLogin(employee.getLogin());
        if (employeeOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uživatelské jméno je již obsazeno");
        }
        String password = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(password);
        EmployeeRole employeeRole = employeeRoleRepository.findByName("Employee");
        employee.setEmployeeRole(employeeRole);
        employeeRepository.save(employee);
    }

    public void addNewAdmin(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByLogin(employee.getLogin());
        if (employeeOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uživatelské jméno je již obsazeno");
        }
        String password = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(password);
        EmployeeRole employeeRole = employeeRoleRepository.findByName("Admin");
        employee.setEmployeeRole(employeeRole);
        employeeRepository.save(employee);
    }


    public void deleteEmployee(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "employee with id "+ employeeId + " doesnt exist");
        }
        employeeRepository.deleteById(employeeId);
    }
    @Transactional
    public void updateEmployee(Long  employeeId, String firstname, String lastname, String login, String password, Long roleId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()-> new IllegalStateException("employee with id "+ employeeId+ "doesnt exist"));
        if (firstname !=null && firstname.length() > 0 && !Objects.equals(employee.getFirstname(), firstname)){
            employee.setFirstname(firstname);
        }

        if (lastname !=null && lastname.length() > 0 && !Objects.equals(employee.getLastname(), lastname)){
            employee.setLastname(lastname);
        }

        if (password !=null && password.length() > 0 && !Objects.equals(employee.getPassword(), password)){
            employee.setPassword(password);
        }

        if (login !=null && login.length() > 0 && !Objects.equals(employee.getLogin(), login)){
            Optional<Employee> employeeOptional = employeeRepository.findEmployeeByLogin(login);
            if (employeeOptional.isPresent()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "login taken");
            }
            employee.setLogin(login);
        }

        if (roleId != null) {
            Optional<EmployeeRole> employeeRoleOptional = employeeRoleRepository.findById(roleId);
            employeeRoleOptional.ifPresent(employee::setEmployeeRole);
        }
        employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployee(Long employeeId) { return employeeRepository.findById(employeeId);
    }
}
