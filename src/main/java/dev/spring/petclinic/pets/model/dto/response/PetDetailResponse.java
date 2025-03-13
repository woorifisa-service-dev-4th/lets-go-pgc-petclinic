package dev.spring.petclinic.pets.model.dto.response;

import dev.spring.petclinic.pets.model.Pet;
// import dev.spring.petclinic.visits.model.dto.VisitResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDetailResponse {

    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String type;
    
    // private List<VisitResponse> visits;
    
    // .visits(pet.getVisits().stream()
    // .map(VisitResponse::from)
    // .collect(Collectors.toList()))

    public static PetDetailResponse from(Pet pet) {
        return PetDetailResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .birthDate(pet.getBirthDate())
                .type(pet.getType().getName())
                .build();
    }

    public boolean isNew() {
        return this.id == null;
    }
}