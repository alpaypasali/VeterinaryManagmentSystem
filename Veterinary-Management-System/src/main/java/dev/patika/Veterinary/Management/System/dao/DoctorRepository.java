package dev.patika.Veterinary.Management.System.dao;

import dev.patika.Veterinary.Management.System.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor , Integer> {
}
