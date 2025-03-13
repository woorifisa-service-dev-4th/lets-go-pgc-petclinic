package dev.spring.petclinic.pets.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.spring.petclinic.pets.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByOwnerId(Integer ownerId);

    // @Query("SELECT pet FROM Pet pet LEFT JOIN FETCH pet.type LEFT JOIN FETCH pet.visits WHERE pet.id = :id")
    // Pet findByIdWithVisits(@Param("id") Integer id);

    Optional<Pet> findById(Integer id);
}
