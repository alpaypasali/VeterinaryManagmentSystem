package dev.patika.Veterinary.Management.System.business.concretes;

import dev.patika.Veterinary.Management.System.business.abstracts.IAnimalService;
import dev.patika.Veterinary.Management.System.business.rules.AnimalBusinessRules;
import dev.patika.Veterinary.Management.System.core.utilities.mappers.ModelMapperService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessDataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessResult;
import dev.patika.Veterinary.Management.System.dao.AnimalRepository;
import dev.patika.Veterinary.Management.System.dao.CustomerRepository;
import dev.patika.Veterinary.Management.System.dto.requests.animal.CreateAnimalRequest;
import dev.patika.Veterinary.Management.System.dto.requests.animal.UpdateAnimalRequest;
import dev.patika.Veterinary.Management.System.dto.responses.animal.AnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.animal.GetAllAnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.animal.GetAnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.CustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.VaccineResponse;
import dev.patika.Veterinary.Management.System.entities.Animal;
import dev.patika.Veterinary.Management.System.entities.Customer;
import dev.patika.Veterinary.Management.System.entities.Doctor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalManager implements IAnimalService {
    private final AnimalRepository animalRepository;
    private final ModelMapperService modelMapperService;
    private final CustomerRepository customerRepository;
    private final AnimalBusinessRules rules;
    @Override
    public DataResult<List<GetAllAnimalResponse>> getAll() {
        List<Animal> animals = animalRepository.findAll();

        List<GetAllAnimalResponse> animalResponses = animals.stream()
                .map(animal -> {
                    CustomerResponse customerDto = modelMapperService.forResponse().map(animal.getCustomer(), CustomerResponse.class);
                    List<VaccineResponse> vaccineResponses = animal.getVaccines() != null ?
                            animal.getVaccines().stream()
                                    .map(vaccine -> modelMapperService.forResponse().map(vaccine, VaccineResponse.class))
                                    .collect(Collectors.toList())
                            : Collections.emptyList();

                    return new GetAllAnimalResponse(
                            animal.getAnimalId(),
                            animal.getName(),
                            animal.getSpecies(),
                            animal.getBreed(),
                            animal.getGender(),
                            animal.getColour(),
                            animal.getDateOfBirth(),
                            customerDto,


                            vaccineResponses
                    );
                })
                .collect(Collectors.toList());

        return new SuccessDataResult<>(animalResponses);
    }
    @Override
    public Result create(CreateAnimalRequest createAnimalRequest) {
        this.rules.checkIfCustomerIdNotFind(createAnimalRequest.getCustomerId());
        Customer customer = this.customerRepository.getById(createAnimalRequest.getCustomerId());
        createAnimalRequest.setCustomerId(0);
        Animal animal = this.modelMapperService.forRequest()
                .map(createAnimalRequest , Animal.class);
        animal.setCustomer(customer);
        this.animalRepository.save(animal);
        return new SuccessResult("Animal added");
    }

    @Override
    public DataResult<AnimalResponse> update(UpdateAnimalRequest updateAnimalRequest) {
        this.rules.checkIfAnimalIdNotFind(updateAnimalRequest.getAnimalId());
        this.rules.checkIfCustomerIdNotFind(updateAnimalRequest.getCustomerId());
        Animal exitingAnimal = this.animalRepository.getById(updateAnimalRequest.getAnimalId());
        this.modelMapperService.forRequest().map(updateAnimalRequest, exitingAnimal);

        this.animalRepository.save(exitingAnimal);
        AnimalResponse response = this.modelMapperService.forResponse().map(exitingAnimal, AnimalResponse.class);
        return new SuccessDataResult<>(response, "Animal updated successfully");
    }

    @Override
    public Result delete(int id) {
        this.rules.checkIfAnimalIdNotFind(id);
        Animal deletedAnimal = this.animalRepository.getById(id);
        this.animalRepository.delete(deletedAnimal);
        return new SuccessResult("Animal deleted");
    }

    @Override
    public DataResult<GetAnimalResponse> getAnimalById(int id) {
        this.rules.checkIfAnimalIdNotFind(id);
        Animal existingAnimal = this.animalRepository.getById(id);
        GetAnimalResponse response = this.modelMapperService.forResponse().map(existingAnimal, GetAnimalResponse.class);
        return new SuccessDataResult<>(response);
    }
}
