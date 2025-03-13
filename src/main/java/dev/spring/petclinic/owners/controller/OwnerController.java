package dev.spring.petclinic.owners.controller;

import dev.spring.petclinic.global.common.PageResponse;
import dev.spring.petclinic.owners.model.dto.request.OwnerCreateRequest;
import dev.spring.petclinic.owners.model.dto.response.OwnerDetailResponse;
import dev.spring.petclinic.owners.model.dto.response.OwnerListResponse;
import dev.spring.petclinic.owners.model.dto.request.OwnerUpdateRequest;
import dev.spring.petclinic.owners.service.OwnerGetService;
import dev.spring.petclinic.owners.service.OwnerInsertService;
import dev.spring.petclinic.owners.service.OwnerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerGetService ownerGetService;
    private final OwnerInsertService ownerInsertService;
    private final OwnerUpdateService ownerUpdateService;

    @GetMapping
    public ResponseEntity<PageResponse<OwnerListResponse>> findOwners(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<OwnerListResponse> response = ownerGetService.findOwners(lastName, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OwnerListResponse>> findOwnersByLastName(
            @RequestParam String lastName) {
        List<OwnerListResponse> owners = ownerGetService.findOwnersByLastName(lastName);
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDetailResponse> findOwner(@PathVariable Integer id) {
        OwnerDetailResponse owner = ownerGetService.findOwnerById(id);
        return ResponseEntity.ok(owner);
    }

    @PostMapping
    public ResponseEntity<Void> createOwner(@Valid @RequestBody OwnerCreateRequest request) {
        Integer id = ownerInsertService.createOwner(request);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOwner(
            @PathVariable Integer id,
            @Valid @RequestBody OwnerUpdateRequest request) {
        
        ownerUpdateService.updateOwner(id, request);
        return ResponseEntity.noContent().build();
    }
}