package dev.patika.Veterinary.Management.System.dto.responses.appointmentDate;

import dev.patika.Veterinary.Management.System.dto.responses.animal.AnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.DoctorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAppointmentResponse {
    private int appointmentId;

    private AnimalResponse animalResponse;


    private DoctorResponse doctorResponse;

    private LocalDateTime appointmentDate;
}
