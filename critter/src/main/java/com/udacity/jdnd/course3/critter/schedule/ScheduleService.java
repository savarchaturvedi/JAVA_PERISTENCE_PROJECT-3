package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    public Schedule create_schedule(Schedule s1)
    {
        Schedule s2=scheduleRepository.save(s1);
        return(s2);
    }

    public List<Schedule> get_list()
    {
        return(scheduleRepository.findAll());
    }

    public List<Schedule> get_schedule_for_emp(Long empid)
    {
       Employee e1= employeeService.getEmployeeById(empid);
       return(scheduleRepository.findByEmployeesContaining(e1));
    }

    public List<Schedule> getSchedulesForPet (Long petId) {
        Pet pet = petService.getPetbyId(petId);
        return scheduleRepository.findByPetsContaining(pet);
    }

    public List<Schedule> getScheduleForCustomer(Long customerid)
    {
        List<Pet> pets = petService.getPetsByOwnerId(customerid);
        HashSet<Long> schIds = new HashSet<>();
        ArrayList<Schedule> schedules = new ArrayList<>();
        for (Pet pet : pets) {
            List<Schedule> curSch = scheduleRepository.findByPetsContaining(pet);
            for (Schedule schedule : curSch) {
                if (!schIds.contains(schedule.getId())) {
                    schIds.add(schedule.getId());
                    schedules.add(schedule);
                }
            }
        }
        return schedules;

    }

}
