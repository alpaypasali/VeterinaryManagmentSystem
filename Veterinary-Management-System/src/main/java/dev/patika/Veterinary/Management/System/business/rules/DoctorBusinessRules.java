package dev.patika.Veterinary.Management.System.business.rules;

import dev.patika.Veterinary.Management.System.core.utilities.exceptions.BusinessException;
import dev.patika.Veterinary.Management.System.dao.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorBusinessRules {
    private final DoctorRepository doctorRepository;
    public void checkIfDoctorIdNotFind(int id) {
        if (!doctorRepository.existsById(id)) {
            throw new BusinessException("Doctor with ID " + id + " does not find.");
        }
    }
}
