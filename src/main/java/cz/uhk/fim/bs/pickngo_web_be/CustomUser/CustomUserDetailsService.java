package cz.uhk.fim.bs.pickngo_web_be.CustomUser;

import cz.uhk.fim.bs.pickngo_web_be.Employee.Employee;
import cz.uhk.fim.bs.pickngo_web_be.Employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private EmployeeRepository employeeRepository;
    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByLogin(login);
        employeeOptional.orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return employeeOptional.map(employee -> new CustomUserDetails(employee)).get();
    }
}
