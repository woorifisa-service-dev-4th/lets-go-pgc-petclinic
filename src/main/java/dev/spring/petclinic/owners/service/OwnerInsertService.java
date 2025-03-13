package dev.spring.petclinic.owners.service;

import dev.spring.petclinic.owners.model.dto.request.OwnerCreateRequest;
import dev.spring.petclinic.owners.model.Owner;
import dev.spring.petclinic.owners.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerInsertService {

    private final OwnerRepository ownerRepository;

    @Transactional
    public Integer createOwner(OwnerCreateRequest request) {
        Owner owner = request.toEntity();
        Owner savedOwner = ownerRepository.save(owner);
        return savedOwner.getId();
    }
}