package com.tietoevry.bookorabackend.bootstrap;

import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Role;
import com.tietoevry.bookorabackend.model.RoleEnum;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RoleRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final ZoneRepository zoneRepository;

    public DataLoader(EmployeeRepository employeeRepository, RoleRepository roleRepository, ZoneRepository zoneRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        LoadEmployees();
        loadRoles();
    }

    private void loadZones() {
        zoneRepository.save(new Zone(1, 'A', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(1, 'B', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(1, 'C', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(1, 'D', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(1, 'E', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(1, 'F', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(2, 'A', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(2, 'B', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(2, 'C', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(2, 'D', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(2, 'E', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(2, 'F', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(3, 'A', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(3, 'B', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(3, 'C', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(3, 'D', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(3, 'E', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(3, 'F', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(4, 'A', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(4, 'B', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(4, 'C', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(4, 'D', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(4, 'E', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(4, 'F', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(5, 'A', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(5, 'B', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(5, 'C', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(5, 'D', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(5, 'E', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(5, 'F', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(6, 'A', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(6, 'B', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(6, 'C', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(6, 'D', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(6, 'E', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(6, 'F', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(7, 'A', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(7, 'B', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(7, 'C', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(7, 'D', Boolean.TRUE, 100));
        zoneRepository.save(new Zone(7, 'E', Boolean.FALSE, 100));
        zoneRepository.save(new Zone(7, 'F', Boolean.FALSE, 100));



    }

    private void LoadEmployees() {
        Employee employee1 = new Employee("Per", "Peterson", "per.peterson@tietoevry.com","111");
        Employee employee2 = new Employee("John", "Johnson", "oslomet7@tietoevry.com","222");
        Employee employee3 = new Employee("Kari", "Hansen", "oslomet6@gmail.com","333");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
    }

    private void loadRoles() {
        Role role1 = new Role();
        role1.setName(RoleEnum.ROLE_USER);
        roleRepository.save(role1);

        Role role2 = new Role();
        role2.setName(RoleEnum.ROLE_ADMIN);
        roleRepository.save(role2);
    }

}
