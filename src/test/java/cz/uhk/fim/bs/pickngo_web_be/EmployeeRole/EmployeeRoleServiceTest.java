package cz.uhk.fim.bs.pickngo_web_be.EmployeeRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeRoleServiceTest {
    @Mock
    private EmployeeRoleRepository employeeRoleRepository;
    private EmployeeRoleService underTest;

    @BeforeEach
    void setUp() {
        underTest = new EmployeeRoleService(employeeRoleRepository);
    }

    @Test
    void canGetEmployeeRoles() {

        //when
        underTest.getEmployeeRoles();
        //then
        verify(employeeRoleRepository).findAll();
    }

    @Test
    void canAddNewEmployeeRole() {

        //given
        EmployeeRole employeeRole = new EmployeeRole(
                "nazev"
        );
        //when
        underTest.addNewEmployeeRole(employeeRole);
        //then
        ArgumentCaptor<EmployeeRole> employeeRoleArgumentCaptor= ArgumentCaptor.forClass(EmployeeRole.class);
        verify(employeeRoleRepository).save(employeeRoleArgumentCaptor.capture());
        EmployeeRole capturedRole = employeeRoleArgumentCaptor.getValue();
        assertThat(capturedRole).isEqualTo(employeeRole);

    }

    @Test
    void willThrowWhenNameIsTakenInAddNewEmployeeRole() {
        //given
        EmployeeRole employeeRole = new EmployeeRole(
                "nazev"
        );

        given(employeeRoleRepository.findByName(employeeRole.getName())).willReturn(employeeRole);
        //when

        //then
        assertThatThrownBy(()-> underTest.addNewEmployeeRole(employeeRole))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("name taken");

        verify(employeeRoleRepository, never()).save(any());

    }


    @Test
    void canGetEmployeeRole() {
        Long id = 1L;
        //when
        underTest.getEmployeeRole(id);
        //then
        verify(employeeRoleRepository).findById(id);
    }
}