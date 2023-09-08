package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)
    {
        Customer c1=convertFromDTO(customerDTO);
        c1=customerService.save(c1);
        return(convertToDTO(c1));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> c_list=customerService.getall();
        List<CustomerDTO> t=convertListToDTO(c_list);
        return(t);

    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getbypetId(petId);
        return convertToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee e1=convertEmployeeFromDTO(employeeDTO);
        Employee e2=employeeService.save(e1);
        return(convertEmployeeToDTO(e2));

    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        Employee e1=employeeService.getEmployeeById(employeeId);
        return(convertEmployeeToDTO(e1));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.set_days_available(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.get_employees_with_skills_and_date(employeeDTO.getSkills(),employeeDTO.getDate());
        return(convertEmployeeListToDTO(employees));
    }


    private Customer convertFromDTO(CustomerDTO customerDTO)
    {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        List<Pet> pet_list=converter_petsids_to_pets(customerDTO.getPetIds());
        customer.setPets(pet_list);
        return(customer);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setPetIds(getIdsFromPets(customer.getPets()));
        return customerDTO;

    }

    List<Long> getIdsFromPets(List<Pet> pets) {
        if(pets == null) return null;
        List<Long> ids = new ArrayList<>();
        for( Pet pet: pets)
            ids.add(pet.getId());
        return ids;
    }
    private List<Pet> converter_petsids_to_pets(List<Long> petids)
    {
        if(petids == null) {
            return null;
        }
        List<Pet> petList=new ArrayList<>();
        for(Long p_id:petids)
        {
            petList.add(petService.getPetbyId(p_id));
        }
        return(petList);

    }
    private List<CustomerDTO> convertListToDTO(List<Customer> customers) {
        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers)
            customerDTOs.add(convertToDTO(customer));
        return customerDTOs;
    }


    private Employee convertEmployeeFromDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private List<EmployeeDTO> convertEmployeeListToDTO(List<Employee> employees) {
        ArrayList<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees)
            employeeDTOs.add(convertEmployeeToDTO(employee));
        return employeeDTOs;
    }

}
