package dev.patika.Veterinary.Management.System.business.rules;

import dev.patika.Veterinary.Management.System.core.utilities.exceptions.BusinessException;

import dev.patika.Veterinary.Management.System.dao.AnimalRepository;
import dev.patika.Veterinary.Management.System.dao.AppointmentRepository;
import dev.patika.Veterinary.Management.System.dao.AvailableDateRepository;
import dev.patika.Veterinary.Management.System.dao.DoctorRepository;
import dev.patika.Veterinary.Management.System.entities.Appointment;
import dev.patika.Veterinary.Management.System.entities.AvailableDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmnentBusinessRules {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final AnimalRepository animalRepository;
    private final AvailableDateRepository availableRepository;

    public void checkIfAppointmentIdNotFind(int id) {
        if (!appointmentRepository.existsById(id)) {
            throw new BusinessException("Appointment with ID " + id + " does not find.");
        }
    }
    public void checkIfDoctorWorkingThisDay(LocalDate date, int doctorId) {
        Optional<Integer> availableId = availableRepository.findByAvailableIdInEndDateAndDoctorId(date, doctorId);
        if (!availableId.isPresent()) {
            throw new BusinessException("The doctor is not working on this date.");
        }
    }

    public void checkDoctorAvailability(int doctorId, LocalDateTime appointmentDate) {
        // Check if doctor exists
        if (!doctorRepository.existsById(doctorId)) {
            throw new BusinessException("Doctor with ID " + doctorId + " does not exist.");
        }

        // Check for existing appointments
        List<Appointment> existingAppointments = appointmentRepository.findByDoctor_DoctorIdAndAppointmentDate(doctorId, appointmentDate);
        if (!existingAppointments.isEmpty()) {
            throw new BusinessException("Doctor already has an appointment at this time.");
        }
    }

    public void checkIfDoctorAvailableOnDate(LocalDate date, int doctorId) {
        boolean isAvailable = availableRepository.existsByDateAndDoctorId(date, doctorId);
        for (int i = 0; i < appointmentRepository.findAll().size(); i++) {
            if (appointmentRepository.existsByDoctor_DoctorId(doctorId)) {
                if (Duration.between(date, appointmentRepository.findAll().get(i).getAppointmentDate()).toHours() == 0) { // Section 18 - Save an appointment
                    throw new BusinessException("Another appointment is available at the entered time.");


                }
            }
        }
    }

    public void checkAnimalExists(int animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new BusinessException("Animal with ID " + animalId + " does not exist.");
        }
    }
    public void checkIfDoctorIdNotFind(int id) {
        if (!doctorRepository.existsById(id)) {
            throw new BusinessException("Doctor with ID " + id + " does not find.");
        }
    }

    public void existsByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (!appointmentRepository.existsByAppointmentDateBetween(startDate, endDate)) {
            throw new BusinessException("No appointments found in the specified range.");
        }
    }



}

