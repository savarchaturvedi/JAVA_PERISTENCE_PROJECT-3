package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule s1=convertfromDTO(scheduleDTO);
        Schedule s2=scheduleService.create_schedule(s1);
        return(convertToDTO(s2));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> schedules = scheduleService.get_list();
        return convertListToDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getSchedulesForPet(petId);
        return convertListToDTO(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.get_schedule_for_emp(employeeId);
        return convertListToDTO(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return convertListToDTO(schedules);

    }
    private Schedule convertfromDTO(ScheduleDTO dto_schedule)
    {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto_schedule, schedule);
        schedule.setEmployees(getEmployeesFromIds(dto_schedule.getEmployeeIds()));
        schedule.setPets(getPetsFromIds(dto_schedule.getPetIds()));
        return schedule;
    }

    private List<Employee> getEmployeesFromIds(List<Long> empIds) {
        if(empIds == null) return null;
        List<Employee> employees = new ArrayList<>();
        for (Long id : empIds)
            employees.add(employeeService.getEmployeeById(id));
        return employees;
    }
    private List<Pet> getPetsFromIds(List<Long> petIds) {
        if(petIds == null) return null;
        List<Pet> pets = new ArrayList<>();
        for (Long id : petIds)
            pets.add(petService.getPetbyId(id));
        return pets;
    }

    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(getIdsFromEmp(schedule.getEmployees()));
        scheduleDTO.setPetIds(getIdsFromPets(schedule.getPets()));
        return scheduleDTO;
    }

    List<Long> getIdsFromEmp(List<Employee> employees) {
        if(employees == null) return null;
        List<Long> ids = new ArrayList<>();
        for( Employee employee: employees)
            ids.add(employee.getId());
        return ids;
    }


    List<Long> getIdsFromPets(List<Pet> pets) {
        if(pets == null) return null;
        List<Long> ids = new ArrayList<>();
        for( Pet pet: pets)
            ids.add(pet.getId());
        return ids;
    }
    private List<ScheduleDTO> convertListToDTO(List<Schedule> schedules) {
        ArrayList<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules)
            scheduleDTOs.add(convertToDTO(schedule));
        return scheduleDTOs;
    }



}
