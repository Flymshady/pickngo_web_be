package cz.uhk.fim.bs.pickngo_web_be.security;

import cz.uhk.fim.bs.pickngo_web_be.CustomUser.CustomUserDetailsService;
import cz.uhk.fim.bs.pickngo_web_be.Employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService){
        this.userDetailsService=customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**","/employee/**", "/baguetteOrder/**", "/baguetteItem/**", "/ingredient/**", "/item/**", "/specialOrder/**").authenticated()

                .antMatchers("/employee/admin/create/**").hasAuthority("ROLE_Admin")
                .antMatchers("/employee/all","/employee/create/**","/employee/remove/**", "/employee/update/**" ).hasAuthority("ROLE_Admin")
                .antMatchers("/role/all", "/role/detail/**", "/role/create", "/role/update/**", "/role/remove/**").hasAuthority("ROLE_Admin")
                .antMatchers("/ingredient/create", "/ingredient/update/**", "/ingredient/remove/**").hasAuthority("ROLE_Admin")
                .antMatchers("/ingredientType/create", "/ingredientType/update/**", "/ingredientType/remove/**").hasAuthority("ROLE_Admin")
                .anyRequest().permitAll()
                .and()
                .httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)  {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
