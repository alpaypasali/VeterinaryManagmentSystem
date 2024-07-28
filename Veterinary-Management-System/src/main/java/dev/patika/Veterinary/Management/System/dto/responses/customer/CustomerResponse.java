package dev.patika.Veterinary.Management.System.dto.responses.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private int customerId;

    private String name;

    private String phone;

    private String mail;

    private String address;

    private String city;
}
