package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee e1)
    {
        return(employeeRepository.save(e1));
    }

    public Employee getEmployeeById(Long eid)
    {
        Optional<Employee> o_eid=employeeRepository.findById(eid);
        if(o_eid.isPresent())
        {
            return(o_eid.get());
        }
        throw new UserNotFoundException(" the employee with this EId is not present");
    }

    public void set_days_available(Set<DayOfWeek> a_days,Long empId)
    {
        Employee e1=getEmployeeById(empId);
        e1.setDaysAvailable(a_days);
    }

    public List<Employee> get_employees_with_skills_and_date(Set<EmployeeSkill> skills,LocalDate date)
    {
        DayOfWeek d1=date.getDayOfWeek();
        List<Employee> list_of_employees_available_on_the_day=employeeRepository.findByDaysAvailableContaining(d1);
        HashSet<Long> poss_ids=getIdSet(list_of_employees_available_on_the_day);
        for (EmployeeSkill skill:skills)
        {
            List<Employee> list_of_employees_with_specific_skill=employeeRepository.findBySkillsContaining(skill);
            HashSet<Long> new_list=new HashSet<>();
            for(Employee e1:list_of_employees_with_specific_skill)
            {
                if(poss_ids.contains(e1.getId()))
                {
                    new_list.add(e1.getId());
                }
            }
            poss_ids=new_list;
        }
        List<Employee> result=new ArrayList<>();
        for(Long id:poss_ids)
        {
            result.add(getEmployeeById(id));
        }
        return(result);
    }

    HashSet<Long> getIdSet(List<Employee> employees) {
        HashSet<Long> set = new HashSet<>();
        for (Employee employee : employees) {
            set.add(employee.getId());
        }
        return set;
    }
}
