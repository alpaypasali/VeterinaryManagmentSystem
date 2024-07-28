package dev.patika.Veterinary.Management.System.dto.responses.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllDoctorResponse {
    private int doctorId;
    private String name;

    private String phone;

    private String mail;

    private String address;

    private String city;
}
