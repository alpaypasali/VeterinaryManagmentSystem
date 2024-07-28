package dev.patika.Veterinary.Management.System.dto.requests.availableDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAvailableDateRequest {

    @NotNull(message = "Doctor ID cannot be null")
    private int doctorId;

    @NotNull(message = "Available date cannot be null")
    @FutureOrPresent(message = "Available date must be today or in the future")
    private LocalDate availableDate;
}
