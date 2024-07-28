package dev.patika.Veterinary.Management.System.dto.requests.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnimalRequest {
    @NotBlank(message = "Please provide a animal name")
    @Size(min = 2, message = "Animal name must be at least 3 characters long")
    private String name;
    private String species;
    private String breed;
    @NotBlank(message = "Please provide the gender of the animal")
    private String gender;
    private String colour;
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    private int customerId;
}
