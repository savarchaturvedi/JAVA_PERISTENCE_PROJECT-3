package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByDaysAvailableContaining (DayOfWeek dayOfWeek);

    List<Employee> findBySkillsContaining (EmployeeSkill skill);

}
