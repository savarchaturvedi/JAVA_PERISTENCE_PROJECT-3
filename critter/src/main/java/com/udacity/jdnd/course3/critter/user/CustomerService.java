package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getall()
    {
        return(customerRepository.findAll());
    }
    public Customer getById(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent())
            return customer.get();
        else
            throw new UserNotFoundException("There is no customer with this ID !");
    }

    public Customer getbypetId(Long petid)
    {
        Optional<Pet> p1=petRepository.findById(petid);
        if(p1.isPresent())
        {
            Pet p2=p1.get();
            Customer c1=customerRepository.findByPetsContaining(p2);
            return(c1);
        }
        throw new PetNotFoundException("There is no pet with this ID !");
    }


    //check this function as this may lead to errors
    public void addPetToCustomer(Pet p1,Customer c1)
    {
        List<Pet> petlist=c1.getPets();
        if (petlist != null)
            petlist.add(p1);
        else {
            petlist = new ArrayList<Pet>();
            petlist.add(p1);
        }
        c1.setPets(petlist);
        customerRepository.save(c1);
        //saving is it crucial
    }



}
