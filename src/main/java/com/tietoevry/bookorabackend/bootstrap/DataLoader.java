package com.tietoevry.bookorabackend.bootstrap;

import com.tietoevry.bookorabackend.model.*;
import com.tietoevry.bookorabackend.repositories.BookingRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RoleRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class DataLoader implements CommandLineRunner{

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final ZoneRepository zoneRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder encoder;

    //

    public DataLoader(EmployeeRepository employeeRepository, RoleRepository roleRepository, ZoneRepository zoneRepository, BookingRepository bookingRepository, PasswordEncoder encoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.zoneRepository = zoneRepository;
        this.bookingRepository = bookingRepository;
        this.encoder = encoder;
        //

    }

    int numberOfBookings = 300;
    int numberOfEmployees = 50;

    @Override
    public void run(String... args) throws Exception {

        loadRoles();
        LoadEmployees();
        loadZones();

        for(int i = 0; i<numberOfBookings; i++)
        bookingRepository.save(new Booking(getOneRandomDayInComingWeek(), getOneRandomEmployee(), getOneRandomActivatedZone()));

    }

    private void loadRoles() {

        Role role1 = new Role();
        role1.setName(RoleEnum.ROLE_USER);
        roleRepository.save(role1);

        Role role2 = new Role();
        role2.setName(RoleEnum.ROLE_ADMIN);
        roleRepository.save(role2);
    }

    private void LoadEmployees() {

        Role user = new Role(1L, RoleEnum.ROLE_USER);
        Role admin = new Role(2L,RoleEnum.ROLE_ADMIN);

        HashSet<Role> allRoles = new HashSet<>();
        allRoles.add(user);
        allRoles.add(admin);

        Employee employee1 = new Employee("root", "root", "root@tietoevry.com","123456aB@");
        Employee employee2 = new Employee("John", "Johnson", "john@tietoevry.com","123456aB@");
        Employee employee3 = new Employee("Kari", "Hansen", "kari@gmail.com","123456aB@");
        employee1.setEnabled(true);
        employee2.setEnabled(true);
        employee3.setEnabled(true);
        employee1.setPassword(encoder.encode(employee1.getPassword()));
        employee2.setPassword(encoder.encode(employee2.getPassword()));
        employee3.setPassword(encoder.encode(employee3.getPassword()));
        employee1.setRoles(allRoles);
        employee2.setRoles(allRoles);
        employee3.setRoles(allRoles);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        for(int i = 0; i<numberOfEmployees; i++){
            Employee employee = new Employee("employee"+i, "employee"+i, "employee"+i+"@tietoevry.com","123456aB@");
            employee.setEnabled(true);
            employee.setPassword(encoder.encode(employee.getPassword()));
            employee.setRoles(allRoles);
            employeeRepository.save(employee);
        }
    }

    private void loadZones() {
        Zone a1= zoneRepository.save(new Zone(1, 'A', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(1, 'B', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(1, 'C', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(1, 'D', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(1, 'E', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(1, 'F', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(1, 'G', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(2, 'A', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(2, 'B', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(2, 'C', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(2, 'D', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(2, 'E', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(2, 'F', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(2, 'G', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(3, 'A', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(3, 'B', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(3, 'C', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(3, 'D', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(3, 'E', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(3, 'F', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(3, 'G', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(4, 'A', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(4, 'B', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(4, 'C', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(4, 'D', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(4, 'E', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(4, 'F', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(4, 'G', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(5, 'A', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(5, 'B', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(5, 'C', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(5, 'D', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(5, 'E', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(5, 'F', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(5, 'G', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(6, 'A', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(6, 'B', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(6, 'C', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(6, 'D', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(6, 'E', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(6, 'F', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(6, 'G', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(7, 'A', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(7, 'B', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(7, 'C', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(7, 'D', Boolean.TRUE, 10));
        zoneRepository.save(new Zone(7, 'E', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(7, 'F', Boolean.FALSE, 10));
        zoneRepository.save(new Zone(7, 'G', Boolean.FALSE, 10));
    }

    private LocalDate getOneRandomDayInComingWeek(){
        int randomExtraDays = ThreadLocalRandom.current().nextInt(0,6);
        return LocalDate.now().plusDays(randomExtraDays);
    }

    private Employee getOneRandomEmployee(){
        int max = (int) employeeRepository.count();
        Long randomId = (long) ThreadLocalRandom.current().nextInt(1, max+1);
        return employeeRepository.getOne(randomId);
    }

    private Zone getOneRandomActivatedZone(){
        List<Zone> zoneList = zoneRepository.findByActivated(true);

        int max = zoneList.size();
        int randomId = ThreadLocalRandom.current().nextInt(0, max);
        Zone zone = zoneList.get(randomId);

        return zone;
    }

}
