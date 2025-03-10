package dev.spring.petclinic.pets.model;

import dev.spring.petclinic.global.common.BaseTimeEntity;
import dev.spring.petclinic.owners.model.Owner;
// import dev.spring.petclinic.visits.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private PetType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @Builder.Default
    // private List<Visit> visits = new ArrayList<>();
    //
    // public void addVisit(Visit visit) {
    //     visits.add(visit);
    //     visit.setPet(this);
    // }

    public void update(String name, LocalDate birthDate, PetType type) {
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }


}