package dev.spring.petclinic.pets.controller;

import dev.spring.petclinic.owners.model.dto.response.OwnerDetailResponse;
import dev.spring.petclinic.owners.service.OwnerGetService;
import dev.spring.petclinic.pets.model.PetType;
import dev.spring.petclinic.pets.model.dto.request.PetCreateRequest;
import dev.spring.petclinic.pets.model.dto.request.PetUpdateRequest;
import dev.spring.petclinic.pets.model.dto.response.PetDetailResponse;
import dev.spring.petclinic.pets.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class PetWebController {

    private final PetService petService;
    private final OwnerGetService ownerGetService;

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return this.petService.findPetTypes();
    }

    @GetMapping("/owners/{ownerId}/pets/new")
    public String initCreationForm(@PathVariable("ownerId") int ownerId, Model model) {
        OwnerDetailResponse owner = ownerGetService.findOwnerById(ownerId);
        PetCreateRequest pet = new PetCreateRequest();
        
        model.addAttribute("owner", owner);
        model.addAttribute("pet", pet);
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/owners/{ownerId}/pets/new")
    public String processCreationForm(@PathVariable("ownerId") int ownerId, 
                                      @Valid @ModelAttribute("pet") PetCreateRequest pet, 
                                      BindingResult result, 
                                      Model model) {
        if (result.hasErrors()) {
            OwnerDetailResponse owner = ownerGetService.findOwnerById(ownerId);
            model.addAttribute("owner", owner);
            return "pets/createOrUpdatePetForm";
        }
        
        pet.setOwnerId(ownerId);
        petService.createPet(pet);
        return "redirect:/owners/" + ownerId;
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("ownerId") int ownerId, 
                                 @PathVariable("petId") int petId, 
                                 Model model) {
        PetDetailResponse pet = petService.findPetById(petId);
        OwnerDetailResponse owner = ownerGetService.findOwnerById(ownerId);
        
        // Convert to PetUpdateRequest
        PetUpdateRequest updateRequest = PetUpdateRequest.builder()
                .name(pet.getName())
                .birthDate(pet.getBirthDate())
                .typeId(getTypeIdFromName(pet.getType()))
                .build();
        
        model.addAttribute("pet", updateRequest);
        model.addAttribute("owner", owner);
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/edit")
    public String processUpdateForm(@PathVariable("ownerId") int ownerId, 
                                   @PathVariable("petId") int petId, 
                                   @Valid @ModelAttribute("pet") PetUpdateRequest pet, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            OwnerDetailResponse owner = ownerGetService.findOwnerById(ownerId);
            model.addAttribute("owner", owner);
            return "pets/createOrUpdatePetForm";
        }
        
        petService.updatePet(petId, pet);
        return "redirect:/owners/" + ownerId;
    }
    
    // Helper method to get type ID from type name
    private Integer getTypeIdFromName(String typeName) {
        // Find pet type by name
        Collection<PetType> types = petService.findPetTypes();
        for (PetType type : types) {
            if (type.getName().equals(typeName)) {
                return type.getId();
            }
        }
        return 1; // Default to first type if not found
    }
}