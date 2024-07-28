package dev.patika.Veterinary.Management.System.dto.responses.appointmentDate;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private int appointmentId;

    private int animalId;


    private int doctorId;

    private LocalDateTime appointmentDate;
}
