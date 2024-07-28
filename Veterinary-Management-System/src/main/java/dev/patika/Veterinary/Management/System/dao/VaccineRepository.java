package dev.patika.Veterinary.Management.System.dao;

import dev.patika.Veterinary.Management.System.dto.responses.vaccine.GetAllVaccineResponse;
import dev.patika.Veterinary.Management.System.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VaccineRepository extends JpaRepository<Vaccine , Integer> {
    List<Vaccine> findByAnimal_AnimalId(int animalId);
    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);
    boolean existsByCodeAndProtectionFinishDateAfter(String code, LocalDate date);
    @Query("SELECT COUNT(v) > 0 FROM Vaccine v WHERE v.animal.animalId = :animalId AND v.code = :code AND v.protectionFinishDate > :currentDate")
    boolean existsActiveVaccineByAnimalIdAndCode(@Param("animalId") int animalId, @Param("code") String code, @Param("currentDate") LocalDate currentDate);

}
