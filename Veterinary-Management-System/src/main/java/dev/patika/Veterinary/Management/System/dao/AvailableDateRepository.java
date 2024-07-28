package dev.patika.Veterinary.Management.System.dao;

import dev.patika.Veterinary.Management.System.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailableDateRepository extends JpaRepository<AvailableDate , Integer> {
    List<AvailableDate> findByDoctor_DoctorId(int doctorId);

    @Query("select case when count(a) > 0 then true else false end " +
            "from AvailableDate a where a.availableDate = :date and a.doctor.doctorId = :doctorId")
    boolean existsByDateAndDoctorId(@Param("date") LocalDate date, @Param("doctorId") int doctorId);

    @Query("select a.availableId from AvailableDate a where a.availableDate = :endDate and a.doctor.doctorId = :doctorId")
    Optional<Integer> findByAvailableIdInEndDateAndDoctorId(@Param("endDate") LocalDate endDate, @Param("doctorId") int doctorId);



    List<AvailableDate> findByAvailableDateBetween(LocalDate startDate, LocalDate endDate);



}
