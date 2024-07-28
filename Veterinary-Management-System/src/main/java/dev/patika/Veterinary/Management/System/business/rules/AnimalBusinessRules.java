package dev.patika.Veterinary.Management.System.business.rules;

import dev.patika.Veterinary.Management.System.core.utilities.exceptions.BusinessException;
import dev.patika.Veterinary.Management.System.dao.AnimalRepository;
import dev.patika.Veterinary.Management.System.dao.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AnimalBusinessRules {
    private final AnimalRepository animalRepository;
    private final CustomerRepository customerRepository;
    public void checkIfCustomerIdNotFind(int id) {
        if (!customerRepository.existsById(id)) {
            throw new BusinessException("Customer with ID " + id + " does not find.");
        }
    }
    public void checkIfAnimalIdNotFind(int id) {
        if (!animalRepository.existsById(id)) {
            throw new BusinessException("Animal with ID " + id + " does not find.");
        }
    }


}
