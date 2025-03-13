package dev.spring.petclinic.owners.controller;

import dev.spring.petclinic.global.common.PageResponse;
import dev.spring.petclinic.owners.model.dto.request.OwnerCreateRequest;
import dev.spring.petclinic.owners.model.dto.request.OwnerUpdateRequest;
import dev.spring.petclinic.owners.model.dto.response.OwnerDetailResponse;
import dev.spring.petclinic.owners.model.dto.response.OwnerListResponse;
import dev.spring.petclinic.owners.service.OwnerGetService;
import dev.spring.petclinic.owners.service.OwnerInsertService;
import dev.spring.petclinic.owners.service.OwnerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OwnerWebController {

    private final OwnerGetService ownerGetService;
    private final OwnerInsertService ownerInsertService;
    private final OwnerUpdateService ownerUpdateService;

    @GetMapping("/owners/find")
    public String initFindForm(Model model) {
        model.addAttribute("owner", new OwnerCreateRequest());
        return "owners/findOwners";
    }

    @GetMapping("/owners")
    public String processFindForm(OwnerCreateRequest owner, BindingResult result, Model model,
                                  @RequestParam(defaultValue = "1") int page) {
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        String lastName = owner.getLastName();
        List<OwnerListResponse> results = ownerGetService.findOwnersByLastName(lastName);

        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        }
        else if (results.size() == 1) {
            // 1 owner found
            return "redirect:/owners/" + results.get(0).getId();
        }
        else {
            // multiple owners found
            Pageable pageable = PageRequest.of(page - 1, 10);
            PageResponse<OwnerListResponse> pageResponse = ownerGetService.findOwners(lastName, pageable);
            
            model.addAttribute("listOwners", pageResponse.getContent());
            model.addAttribute("currentPage", pageResponse.getCurrentPage());
            model.addAttribute("totalPages", pageResponse.getTotalPages());
            return "owners/ownersList";
        }
    }

    @GetMapping("/owners/{ownerId}")
    public String showOwner(@PathVariable("ownerId") int ownerId, Model model) {
        OwnerDetailResponse owner = ownerGetService.findOwnerById(ownerId);
        model.addAttribute("owner", owner);
        return "owners/ownerDetails";
    }

    @GetMapping("/owners/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", new OwnerCreateRequest());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/owners/new")
    public String processCreationForm(@Valid @ModelAttribute("owner") OwnerCreateRequest owner, BindingResult result) {
        if (result.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }
        
        Integer id = ownerInsertService.createOwner(owner);
        return "redirect:/owners/" + id;
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
        OwnerDetailResponse owner = ownerGetService.findOwnerById(ownerId);
        
        // Convert to OwnerUpdateRequest
        OwnerUpdateRequest updateRequest = OwnerUpdateRequest.builder()
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .address(owner.getAddress())
                .city(owner.getCity())
                .telephone(owner.getTelephone())
                .build();
        
        model.addAttribute("owner", updateRequest);
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid OwnerUpdateRequest owner, BindingResult result,
                                         @PathVariable("ownerId") int ownerId) {
        if (result.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }
        
        ownerUpdateService.updateOwner(ownerId, owner);
        return "redirect:/owners/" + ownerId;
    }
}