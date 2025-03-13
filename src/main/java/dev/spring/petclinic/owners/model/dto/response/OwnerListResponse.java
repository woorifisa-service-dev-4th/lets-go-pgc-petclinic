package dev.spring.petclinic.owners.model.dto.response;

import dev.spring.petclinic.owners.model.Owner;
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
public class OwnerListResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private List<String> pets;

    public static OwnerListResponse from(Owner owner) {
        return OwnerListResponse.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .address(owner.getAddress())
                .city(owner.getCity())
                .telephone(owner.getTelephone())
                .pets(owner.getPets().stream()
                        .map(pet -> pet.getName())
                        .collect(Collectors.toList()))
                .build();
    }

    public static List<OwnerListResponse> from(List<Owner> owners) {
        return owners.stream()
                .map(OwnerListResponse::from)
                .collect(Collectors.toList());
    }

}