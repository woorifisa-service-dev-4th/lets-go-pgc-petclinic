package dev.spring.petclinic.owners.controller;

import dev.spring.petclinic.global.common.PageResponse;
import dev.spring.petclinic.owners.model.dto.request.OwnerCreateRequest;
import dev.spring.petclinic.owners.model.dto.response.OwnerDetailResponse;
import dev.spring.petclinic.owners.model.dto.response.OwnerListResponse;
import dev.spring.petclinic.owners.model.dto.request.OwnerUpdateRequest;
import dev.spring.petclinic.owners.service.OwnerGetService;
import dev.spring.petclinic.owners.service.OwnerInsertService;
import dev.spring.petclinic.owners.service.OwnerUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping
    @Operation(summary = "owner 생성", description = "새로운 owner를 생성합니다.")
    public ResponseEntity<Void> createOwner(
            @Parameter(name = "request", description = "owner 생성 요청", example = "")
            @Valid
            @RequestBody
            OwnerCreateRequest request) {
        Integer id = ownerInsertService.createOwner(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "성을 통한 owners 조회 (페이징 적용)", description = "페이징을 적용하여 특정 성을 가진 owner 리스트를 조회합니다.")
    public ResponseEntity<PageResponse<OwnerListResponse>> findOwners(
            @Parameter(description = "성", example = "Franklin") @RequestParam(required = false) String lastName,
            @Parameter(name="page", description="현재 페이지") @RequestParam(defaultValue = "0") int page,
            @Parameter(name="page", description="총 페이지 수") @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageResponse<OwnerListResponse> response = ownerGetService.findOwners(lastName, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "성을 통한 owners 조회", description = "특정 성을 가진 owner 리스트를 조회합니다.")
    public ResponseEntity<List<OwnerListResponse>> findOwnersByLastName(
            @Parameter(description = "성", example = "Franklin") @RequestParam String lastName) {
        List<OwnerListResponse> owners = ownerGetService.findOwnersByLastName(lastName);
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/{id}")
    @Operation(summary = "id를 통한 owner 조회", description = "id를 통해 owner를 조회합니다. ")
    public ResponseEntity<OwnerDetailResponse> findOwner(
            @Parameter(description = "id", example = "1")
            @PathVariable
            Integer id) {
        OwnerDetailResponse owner = ownerGetService.findOwnerById(id);
        return ResponseEntity.ok(owner);
    }


    @PutMapping("/{id}")
    @Operation(summary = "owner 업데이트", description = "owner 의 수정 사항을 업데이트 합니다")
    public ResponseEntity<Void> updateOwner(
            @Parameter(description = "업데이트 할 owner 의 id", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "업데이트 요청 객체")
            @Valid @RequestBody OwnerUpdateRequest request) {
        
        ownerUpdateService.updateOwner(id, request);
        return ResponseEntity.noContent().build();
    }
}