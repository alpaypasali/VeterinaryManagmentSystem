package dev.patika.Veterinary.Management.System.dto.requests.appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentRequest {
    private int appointmentId;
    @NotNull(message = "Animal ID cannot be null")
    private int animalId;

    @NotNull(message = "Doctor ID cannot be null")
    private int doctorId;

    @NotNull(message = "Appointment date cannot be null")
    @FutureOrPresent(message = "Appointment date must be in the future or present")
    private LocalDateTime appointmentDate;
}
