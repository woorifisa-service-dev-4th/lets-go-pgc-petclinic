package dev.spring.petclinic.owners.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.spring.petclinic.owners.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    @Query("SELECT DISTINCT owner FROM Owner owner LEFT JOIN FETCH owner.pets WHERE owner.lastName LIKE :lastName%")
    List<Owner> findByLastNameStartingWith(@Param("lastName") String lastName);

    Page<Owner> findByLastNameStartingWith(String lastName, Pageable pageable);

    // Used for pagination of all owners
    Page<Owner> findAll(Pageable pageable);

    @Query("SELECT owner FROM Owner owner LEFT JOIN FETCH owner.pets WHERE owner.id = :id")
    Owner findByIdWithPets(@Param("id") Integer id);
}
