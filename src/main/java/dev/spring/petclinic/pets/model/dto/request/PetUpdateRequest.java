package dev.spring.petclinic.pets.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateRequest {
    private Integer id;

    @NotBlank(message = "Pet name is required")
    @Size(min = 2, max = 30, message = "Pet name must be between 2 and 30 characters")
    private String name;

    @NotNull(message = "Birth date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Pet type is required")
    private Integer typeId;

    public boolean isNew() {
        return false;
    }
}