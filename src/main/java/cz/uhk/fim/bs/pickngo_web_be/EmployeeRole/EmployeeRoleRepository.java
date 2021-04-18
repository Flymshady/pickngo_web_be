package cz.uhk.fim.bs.pickngo_web_be.EmployeeRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {

    EmployeeRole findByName(String name);
}
