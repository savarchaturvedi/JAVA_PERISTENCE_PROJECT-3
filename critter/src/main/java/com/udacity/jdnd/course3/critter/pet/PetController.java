package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService ownerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet p1=convertFromDTO(petDTO);
        p1=petService.save(p1);
        return(convertToDTO(p1));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        Pet p1=petService.getPetbyId(petId);
        PetDTO petDTO=convertToDTO(p1);
        return(petDTO);
    }

    @GetMapping
    public List<PetDTO> getPets(){

        List<Pet> petList=petService.getAll();
        return(convertlistofpetstopetdto(petList));

    }


    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> p_list=petService.getPetsByOwnerId(ownerId);
        return(convertlistofpetstopetdto(p_list));
    }

    private Pet convertFromDTO (PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setOwner(ownerService.getById(petDTO.getOwnerId()));
        return pet;
    }

    private PetDTO convertToDTO (Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    private List<PetDTO> convertlistofpetstopetdto(List<Pet> pets)
    {
        ArrayList<PetDTO> p2=new ArrayList<>();
        for(Pet p : pets)
        {
            p2.add(convertToDTO(p));
        }
        return(p2);
    }

}
