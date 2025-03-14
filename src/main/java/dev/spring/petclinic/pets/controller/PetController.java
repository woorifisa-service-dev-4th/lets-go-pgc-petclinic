package dev.spring.petclinic.pets.controller;

import dev.spring.petclinic.pets.model.dto.request.PetCreateRequest;
import dev.spring.petclinic.pets.model.dto.response.PetDetailResponse;
import dev.spring.petclinic.pets.model.dto.request.PetUpdateRequest;
import dev.spring.petclinic.pets.model.PetType;
import dev.spring.petclinic.pets.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping("/pets/types")
    public ResponseEntity<List<PetType>> getPetTypes() {
        List<PetType> types = petService.findPetTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<PetDetailResponse> getPet(@PathVariable Integer id) {
        PetDetailResponse pet = petService.findPetById(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping("/owners/{ownerId}/pets")
    public ResponseEntity<Void> createPet(
            @PathVariable Integer ownerId,
            @Valid @RequestBody PetCreateRequest request) {
        
        // Ensure the ownerId in path matches the one in request
        if (!ownerId.equals(request.getOwnerId())) {
            return ResponseEntity.badRequest().build();
        }
        
        Integer id = petService.createPet(request);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/pets/{id}")
                .buildAndExpand(id)
                .toUri();
        
        return ResponseEntity.created(location).build();
    }

    @Operation(summary="Update a pet", description="Update a pet with the given ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PutMapping("/pets/{id}")
    public ResponseEntity<Void> updatePet(
            @PathVariable Integer id,
            @Valid @RequestBody PetUpdateRequest request) {
        
        petService.updatePet(id, request);
        return ResponseEntity.noContent().build();
    }
}