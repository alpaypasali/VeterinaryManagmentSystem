package dev.patika.Veterinary.Management.System.business.rules;

import dev.patika.Veterinary.Management.System.core.utilities.exceptions.BusinessException;
import dev.patika.Veterinary.Management.System.dao.AnimalRepository;
import dev.patika.Veterinary.Management.System.dao.CustomerRepository;
import dev.patika.Veterinary.Management.System.dao.VaccineRepository;
import lombok.AllArgsConstructor;
import org.hibernate.usertype.BaseUserTypeSupport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class VaccineBusinessRules {
    private final VaccineRepository vaccineRepository;
    private final AnimalRepository animalRepository;
    public void checkIfAnimalIdNotFind(int id) {
        if (!animalRepository.existsById(id)) {
            throw new BusinessException("Animal with ID " + id + " does not find.");
        }
    }
    public void checkIfVaccineIdNotFind(int id) {
        if (!vaccineRepository.existsById(id)) {
            throw new BusinessException("Vaccine with ID " + id + " does not find.");
        }
    }

    public void existsByCodeAndProtectionFinishDateAfter(String code, LocalDate date){
        if(vaccineRepository.existsByCodeAndProtectionFinishDateAfter(code,date)){

            throw  new BusinessException("A vaccine with the same code is already active");

        }
    }
    public void checkIfActiveVaccineExists(int animalId, String code, LocalDate currentDate){
        boolean existsActiveVaccine = vaccineRepository.existsActiveVaccineByAnimalIdAndCode(animalId, code, currentDate);
        if(existsActiveVaccine){
            throw new BusinessException("A vaccine with the same code is already active for this animal.");
        }
    }
}
