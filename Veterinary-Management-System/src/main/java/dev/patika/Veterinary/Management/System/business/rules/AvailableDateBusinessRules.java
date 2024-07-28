package dev.patika.Veterinary.Management.System.business.rules;

import dev.patika.Veterinary.Management.System.core.utilities.exceptions.BusinessException;
import dev.patika.Veterinary.Management.System.dao.AvailableDateRepository;
import dev.patika.Veterinary.Management.System.dao.DoctorRepository;
import dev.patika.Veterinary.Management.System.entities.AvailableDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AvailableDateBusinessRules {
    private final AvailableDateRepository availableDateRepository;
    private final DoctorRepository doctorRepository;



    public void checkIfAvailableDateIdNotFind(int id) {
        if (!availableDateRepository.existsById(id)) {
            throw new BusinessException("Available date with ID " + id + " does not find.");
        }
    }



    public void checkIfDoctorIdNotFind(int id) {
        if (!doctorRepository.existsById(id)) {
            throw new BusinessException("Doctor with ID " + id + " does not find.");
        }
    }

    public void checkIfDoctorAlreadyHaveThisDate(int doctorId, LocalDate availableDate) {
        List<AvailableDate> availableDateList = availableDateRepository.findByDoctor_DoctorId(doctorId);

        boolean dateExists = availableDateList.stream()
                .anyMatch(date -> date.getAvailableDate().isEqual(availableDate));

        if (dateExists) {
            throw new BusinessException("Available date already exists for this doctor");
        }
    }
}





