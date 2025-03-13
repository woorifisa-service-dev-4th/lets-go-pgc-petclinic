package dev.spring.petclinic.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.spring.petclinic.pets.model.PetType;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {
}