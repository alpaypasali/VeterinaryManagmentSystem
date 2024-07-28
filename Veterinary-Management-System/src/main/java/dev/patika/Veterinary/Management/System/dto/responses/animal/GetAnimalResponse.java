package dev.patika.Veterinary.Management.System.dto.responses.animal;

import dev.patika.Veterinary.Management.System.dto.responses.customer.CustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.VaccineResponse;
import dev.patika.Veterinary.Management.System.entities.Customer;
import dev.patika.Veterinary.Management.System.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAnimalResponse {
    private int animalId;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
  private CustomerResponse customer;
    private List<VaccineResponse> vaccines;
}
