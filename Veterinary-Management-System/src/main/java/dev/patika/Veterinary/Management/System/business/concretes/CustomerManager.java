package dev.patika.Veterinary.Management.System.business.concretes;

import dev.patika.Veterinary.Management.System.business.abstracts.ICustomerService;
import dev.patika.Veterinary.Management.System.business.rules.CustomerBusinessRules;
import dev.patika.Veterinary.Management.System.core.utilities.mappers.ModelMapperService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessDataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessResult;
import dev.patika.Veterinary.Management.System.dao.CustomerRepository;
import dev.patika.Veterinary.Management.System.dto.requests.customer.CreateCustomerRequest;
import dev.patika.Veterinary.Management.System.dto.requests.customer.UpdateCustomerRequest;
import dev.patika.Veterinary.Management.System.dto.responses.animal.AnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.animal.GetAllAnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.CustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.GetAllCustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.GetCustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.VaccineResponse;
import dev.patika.Veterinary.Management.System.entities.Animal;
import dev.patika.Veterinary.Management.System.entities.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerManager implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapperService modelMapperService;
    private final CustomerBusinessRules rules;
    @Override
    public DataResult<List<GetAllCustomerResponse>> getAll() {
        List<Customer> customers = customerRepository.findAll();

        List<GetAllCustomerResponse> customerResponses = customers.stream()
                .map(customer -> {
                    List<AnimalResponse> animalResponses = customer.getAnimals() != null ?
                            customer.getAnimals().stream()
                                    .map(animal -> modelMapperService.forResponse().map(animal, AnimalResponse.class))
                                    .collect(Collectors.toList())
                            : Collections.emptyList();

                    return new GetAllCustomerResponse(
                            customer.getCustomerId(),
                            customer.getName(),
                            customer.getPhone(),
                            customer.getMail(),
                            customer.getAddress(),
                            customer.getCity(),
                            animalResponses
                    );
                })
                .collect(Collectors.toList());

        return new SuccessDataResult<>(customerResponses);
    }
    @Override
    public Result create(CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.modelMapperService.forRequest()
                .map(createCustomerRequest , Customer.class);

        this.customerRepository.save(customer);
        return new SuccessResult("Customer added");
    }
    @Override
    public DataResult<CustomerResponse> update(UpdateCustomerRequest updateCustomerRequest) {
        this.rules.checkIfCustomerIdNotFind(updateCustomerRequest.getCustomerId());
        Customer existingCustomer = this.customerRepository.getById(updateCustomerRequest.getCustomerId());
        this.modelMapperService.forRequest().map(updateCustomerRequest, existingCustomer);

        this.customerRepository.save(existingCustomer);
        CustomerResponse response = this.modelMapperService.forResponse().map(existingCustomer, CustomerResponse.class);
        return new SuccessDataResult<>(response, "Customer updated successfully");
    }
    @Override
    public Result delete(int id) {
        this.rules.checkIfCustomerIdNotFind(id);
        Customer deletedCustomer = this.customerRepository.getById(id);
        this.customerRepository.delete(deletedCustomer);
        return new SuccessResult("Customer deleted");
    }
    @Override
    public DataResult<GetCustomerResponse> getCustomerById(int id) {
        rules.checkIfCustomerIdNotFind(id);
        Customer existingCustomer= this.customerRepository.getById(id);
        GetCustomerResponse response = this.modelMapperService.forResponse().map(existingCustomer, GetCustomerResponse.class);
        return new SuccessDataResult<>(response);
    }
    @Override
    public DataResult<List<GetAllCustomerResponse>> getByName(String name) {
        List<Customer> customers = customerRepository.findByNameContaining(name);
        if (customers.isEmpty()) {
            return new SuccessDataResult<>(Collections.emptyList(), "Not found");
        }
        List<GetAllCustomerResponse> customerResponses = customers.stream()
                .map(customer -> {
                    List<AnimalResponse> animalResponses = customer.getAnimals() != null ?
                            customer.getAnimals().stream()
                                    .map(animal -> modelMapperService.forResponse().map(animal, AnimalResponse.class))
                                    .collect(Collectors.toList())
                            : Collections.emptyList();

                    return new GetAllCustomerResponse(
                            customer.getCustomerId(),
                            customer.getName(),
                            customer.getPhone(),
                            customer.getMail(),
                            customer.getAddress(),
                            customer.getCity(),
                            animalResponses
                    );
                })
                .collect(Collectors.toList());
        return new SuccessDataResult<>(customerResponses);
    }
}
