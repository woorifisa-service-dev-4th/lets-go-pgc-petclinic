package dev.spring.petclinic.owners.service;

import dev.spring.petclinic.global.common.PageResponse;
import dev.spring.petclinic.global.exception.ResourceNotFoundException;
import dev.spring.petclinic.owners.model.dto.response.OwnerDetailResponse;
import dev.spring.petclinic.owners.model.dto.response.OwnerListResponse;
import dev.spring.petclinic.owners.model.Owner;
import dev.spring.petclinic.owners.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerGetService {

    private final OwnerRepository ownerRepository;

    public PageResponse<OwnerListResponse> findOwners(String lastName, Pageable pageable) {
        Page<Owner> owners;
        
        if (lastName == null || lastName.isEmpty()) {
            owners = ownerRepository.findAll(pageable);
        } else {
            owners = ownerRepository.findByLastNameStartingWith(lastName, pageable);
        }
        
        Page<OwnerListResponse> ownerResponses = owners.map(OwnerListResponse::from);
        return new PageResponse<>(ownerResponses);
    }

    public List<OwnerListResponse> findOwnersByLastName(String lastName) {
        List<Owner> owners = ownerRepository.findByLastNameStartingWith(lastName);
        return OwnerListResponse.from(owners);
    }

    public OwnerDetailResponse findOwnerById(Integer id) {
        Owner owner = ownerRepository.findByIdWithPets(id);
        if (owner == null) {
            throw new ResourceNotFoundException("Owner", "id", id);
        }
        return OwnerDetailResponse.from(owner);
    }
}