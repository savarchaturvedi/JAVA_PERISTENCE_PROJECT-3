package com.udacity.jdnd.course3.critter.user;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.util.Set;


@Entity
public class Employee extends User{

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "daysAvailable=" + daysAvailable +
                ", skills=" + skills +
                "} " + super.toString();
    }


}
