package cz.uhk.fim.bs.pickngo_web_be.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }


    public void addNewEmployee(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByLogin(employee.getLogin());
        if (employeeOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists){
            throw new IllegalStateException("employee with id "+ employeeId + " doesnt exist");
        }
        employeeRepository.deleteById(employeeId);
    }
    @Transactional
    public void updateEmployee(Long  employeeId, String firstname, String lastname, String login, String password){
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
                throw new IllegalStateException("login taken");
            }
            employee.setLogin(login);
        }
    }

    public Optional<Employee> getEmployee(Long employeeId) { return employeeRepository.findById(employeeId);
    }
}
