package dev.spring.petclinic.owners.service;

import dev.spring.petclinic.global.exception.ResourceNotFoundException;
import dev.spring.petclinic.owners.model.dto.request.OwnerUpdateRequest;
import dev.spring.petclinic.owners.model.Owner;
import dev.spring.petclinic.owners.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerUpdateService {

    private final OwnerRepository ownerRepository;

    @Transactional
    public void updateOwner(Integer id, OwnerUpdateRequest request) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner", "id", id));
        
        owner.update(
                request.getFirstName(),
                request.getLastName(),
                request.getAddress(),
                request.getCity(),
                request.getTelephone()
        );
    }
}