package dev.patika.Veterinary.Management.System.dto.responses.customer;

import dev.patika.Veterinary.Management.System.dto.responses.animal.AnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.VaccineResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerResponse {
    private int customerId;

    private String name;

    private String phone;

    private String mail;

    private String address;

    private String city;
    private List<AnimalResponse> animals;

}
