package dev.patika.Veterinary.Management.System.dao;

import dev.patika.Veterinary.Management.System.dto.responses.customer.GetAllCustomerResponse;
import dev.patika.Veterinary.Management.System.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer , Integer> {
    List<Customer> findByNameContaining(String name);
}
