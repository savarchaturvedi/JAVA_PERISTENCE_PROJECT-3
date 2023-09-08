package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;


    public Pet save(Pet p1)
    {
        p1=petRepository.save(p1);
        customerService.addPetToCustomer(p1, p1.getOwner());
        return(p1);
    }

    public Pet getPetbyId(Long id)
    {
        Optional<Pet> p2=petRepository.findById(id);
        if(p2.isPresent())
        {
            return(p2.get());
        }
        else
        {
            throw new PetNotFoundException("There is no pet with this ID !");
        }
    }

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }

}
