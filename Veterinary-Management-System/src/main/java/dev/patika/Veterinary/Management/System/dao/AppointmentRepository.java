package dev.patika.Veterinary.Management.System.dao;

import dev.patika.Veterinary.Management.System.entities.Animal;
import dev.patika.Veterinary.Management.System.entities.Appointment;
import dev.patika.Veterinary.Management.System.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDoctor_DoctorIdAndAppointmentDate(int doctorId, LocalDateTime appointmentDate);
    boolean existsByDoctor_DoctorId(int id);
    boolean existsByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDate, LocalDateTime endDate, Animal animal);
    List<Appointment> findByAppointmentDateBetweenAndDoctor(LocalDateTime startDate, LocalDateTime endDate, Doctor doctor);




}

