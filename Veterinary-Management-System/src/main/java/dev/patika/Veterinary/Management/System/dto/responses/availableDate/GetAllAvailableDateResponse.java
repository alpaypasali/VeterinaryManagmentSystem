package dev.patika.Veterinary.Management.System.dto.responses.availableDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAvailableDateResponse {
    private int availableId;
    private int doctorId;

    private LocalDate availableDate;
}
