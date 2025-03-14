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

    /**
     * 반려동물 종류 목록 조회
     *
     * @return 반려동물 종류 리스트 (200 OK)
     */
    @Operation(
            summary = "반려동물 종류 조회",
            description = "등록된 반려동물의 종류 목록을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "반려동물 종류 조회 성공"),
            @ApiResponse(responseCode = "404", description = "반려동물을 찾을 수 없음")
    })
    @GetMapping("/pets/types")
    public ResponseEntity<List<PetType>> getPetTypes() {
        List<PetType> types = petService.findPetTypes();
        return ResponseEntity.ok(types);
    }

    /**
     * 반려동물 상세 정보 조회
     *
     * @param id 반려동물 ID
     * @return 반려동물 상세 정보 (200 OK)
     */
    @Operation(
            summary = "반려동물 상세 조회",
            description = "주어진 ID에 해당하는 반려동물의 상세 정보를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "반려동물 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "반려동물을 찾을 수 없음")
    })
    @GetMapping("/pets/{id}")
    public ResponseEntity<PetDetailResponse> getPet(@PathVariable Integer id) {
        PetDetailResponse pet = petService.findPetById(id);
        return ResponseEntity.ok(pet);
    }

    /**
     * 반려동물 등록
     *
     * @param ownerId 소유자 ID
     * @param request 반려동물 생성 요청 객체
     * @return 생성된 반려동물(201 Created)
     */
    @Operation(
            summary = "반려동물 등록",
            description = "새로운 반려동물을 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "반려동물 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
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

    /**
     * 반려동물 정보 업데이트
     *
     * @param id      반려동물 ID
     * @param request 반려동물 업데이트 요청 객체
     * @return 응답 없음 (204 No Content)
     */
    @Operation(
            summary = "반려동물 정보 수정",
            description = "주어진 ID의 반려동물 정보를 업데이트합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "반려동물 정보 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "반려동물을 찾을 수 없음")
    })
    @PutMapping("/pets/{id}")
    public ResponseEntity<Void> updatePet(
            @PathVariable Integer id,
            @Valid @RequestBody PetUpdateRequest request) {

        petService.updatePet(id, request);
        return ResponseEntity.noContent().build();
    }
}