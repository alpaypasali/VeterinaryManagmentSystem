package dev.patika.Veterinary.Management.System.dao;

import dev.patika.Veterinary.Management.System.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal , Integer> {
}
