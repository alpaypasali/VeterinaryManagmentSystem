package dev.patika.Veterinary.Management.System.business.rules;

import dev.patika.Veterinary.Management.System.core.utilities.exceptions.BusinessException;
import dev.patika.Veterinary.Management.System.dao.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerBusinessRules {
    private final CustomerRepository customerRepository;
    public void checkIfCustomerIdNotFind(int id) {
        if (!customerRepository.existsById(id)) {
            throw new BusinessException("Customer with ID " + id + " does not find.");
        }
    }
}
