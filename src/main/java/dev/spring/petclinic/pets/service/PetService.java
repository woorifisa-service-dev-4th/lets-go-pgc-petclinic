package dev.spring.petclinic.pets.service;

import dev.spring.petclinic.global.exception.ResourceNotFoundException;
import dev.spring.petclinic.owners.model.Owner;
import dev.spring.petclinic.owners.repository.OwnerRepository;
import dev.spring.petclinic.pets.model.dto.request.PetCreateRequest;
import dev.spring.petclinic.pets.model.dto.response.PetDetailResponse;
import dev.spring.petclinic.pets.model.dto.request.PetUpdateRequest;
import dev.spring.petclinic.pets.model.Pet;
import dev.spring.petclinic.pets.model.PetType;
import dev.spring.petclinic.pets.repository.PetRepository;
import dev.spring.petclinic.pets.repository.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final OwnerRepository ownerRepository;

    public List<PetType> findPetTypes() {
        return petTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PetDetailResponse findPetById(Integer id) {
        // Pet pet = petRepository.findByIdWithVisits(id);
        Optional<Pet> pet = petRepository.findById(id);
        if (pet == null) {
            throw new ResourceNotFoundException("Pet", "id", id);
        }
        Pet notNullPet = pet.get();
        return PetDetailResponse.from(notNullPet);
    }

    @Transactional
    public Integer createPet(PetCreateRequest request) {
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner", "id", request.getOwnerId()));
        
        PetType petType = petTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("PetType", "id", request.getTypeId()));
        
        Pet pet = Pet.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .type(petType)
                .owner(owner)
                .build();
        
        owner.addPet(pet);
        Pet savedPet = petRepository.save(pet);
        return savedPet.getId();
    }

    @Transactional
    public void updatePet(Integer id, PetUpdateRequest request) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", "id", id));
        
        PetType petType = petTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("PetType", "id", request.getTypeId()));
        
        pet.update(request.getName(), request.getBirthDate(), petType);
    }
}