package dev.patika.Veterinary.Management.System.dto.requests.vaccine;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVaccineRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters long")
    private String name;

    @NotBlank(message = "Code cannot be blank")
    @Size(min = 1, max = 50, message = "Code must be between 1 and 50 characters long")
    private String code;


    @FutureOrPresent(message = "Protection start date must be today or in the future")
    private LocalDate protectionStartDate;


    @FutureOrPresent(message = "Protection finish date must be today or in the future")
    private LocalDate protectionFinishDate;


    private int animalId;
}
