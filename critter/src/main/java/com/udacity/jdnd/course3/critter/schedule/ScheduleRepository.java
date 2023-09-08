package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> findByEmployeesContaining(Employee e1);
    List<Schedule> findByPetsContaining (Pet pet);


}
