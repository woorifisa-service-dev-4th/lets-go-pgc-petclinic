package dev.spring.petclinic.owners.model.dto.response;

import dev.spring.petclinic.owners.model.Owner;
import dev.spring.petclinic.pets.model.dto.response.PetDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDetailResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private List<PetDetailResponse> pets;

    public static OwnerDetailResponse from(Owner owner) {
        return OwnerDetailResponse.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .address(owner.getAddress())
                .city(owner.getCity())
                .telephone(owner.getTelephone())
                .pets(owner.getPets().stream()
                        .map(PetDetailResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}