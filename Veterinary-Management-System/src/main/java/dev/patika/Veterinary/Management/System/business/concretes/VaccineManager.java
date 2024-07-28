package dev.patika.Veterinary.Management.System.business.concretes;

import dev.patika.Veterinary.Management.System.business.abstracts.IVaccineService;
import dev.patika.Veterinary.Management.System.business.rules.VaccineBusinessRules;
import dev.patika.Veterinary.Management.System.core.utilities.mappers.ModelMapperService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessDataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessResult;
import dev.patika.Veterinary.Management.System.dao.AnimalRepository;
import dev.patika.Veterinary.Management.System.dao.VaccineRepository;
import dev.patika.Veterinary.Management.System.dto.requests.vaccine.CreateVaccineRequest;
import dev.patika.Veterinary.Management.System.dto.requests.vaccine.UpdateVaccineRequest;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.GetAllVaccineResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.GetVaccineResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.VaccineResponse;
import dev.patika.Veterinary.Management.System.entities.Animal;
import dev.patika.Veterinary.Management.System.entities.Vaccine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VaccineManager implements IVaccineService {
    private final VaccineRepository vaccineRepository;
    private final ModelMapperService modelMapperService;
    private final AnimalRepository animalRepository;
    private final VaccineBusinessRules rules;
    @Override
    public DataResult<List<GetAllVaccineResponse>> getAll() {
        List<Vaccine> vaccines = vaccineRepository.findAll();

        List<GetAllVaccineResponse> getAllVaccineResponses = vaccines.stream()
                .map(vaccine -> this.modelMapperService.forResponse().map(vaccine, GetAllVaccineResponse.class))
                .toList();

        return new SuccessDataResult<>(getAllVaccineResponses);
    }

    @Override
    public Result create(CreateVaccineRequest createVaccineRequest) {
        rules.checkIfActiveVaccineExists(createVaccineRequest.getAnimalId(), createVaccineRequest.getCode(), LocalDate.now());
        rules.checkIfAnimalIdNotFind(createVaccineRequest.getAnimalId());
        Animal animal = animalRepository.getById(createVaccineRequest.getAnimalId());
        createVaccineRequest.setAnimalId(0);
        Vaccine vaccine = this.modelMapperService.forRequest()
                .map(createVaccineRequest , Vaccine.class);
        vaccine.setAnimal(animal);
        this.vaccineRepository.save(vaccine);
        return new SuccessResult("Vaccine added");
    }

    @Override
    public DataResult<VaccineResponse> update(UpdateVaccineRequest updateVaccineRequest) {
       rules.checkIfVaccineIdNotFind(updateVaccineRequest.getVaccineId());

        Vaccine existingVaccine = this.vaccineRepository.getById(updateVaccineRequest.getVaccineId());
        this.modelMapperService.forRequest().map(updateVaccineRequest, existingVaccine);

        this.vaccineRepository.save(existingVaccine);
        VaccineResponse response = this.modelMapperService.forResponse().map(existingVaccine, VaccineResponse.class);
        return new SuccessDataResult<>(response, "Vaccine updated successfully");
    }

    @Override
    public Result delete(int id) {
        this.rules.checkIfVaccineIdNotFind(id);
        Vaccine deletedVaccine = this.vaccineRepository.getById(id);
        this.vaccineRepository.delete(deletedVaccine);
        return new SuccessResult("Vaccine deleted");
    }

    @Override
    public DataResult<GetVaccineResponse> getVaccineById(int id) {
        this.rules.checkIfVaccineIdNotFind(id);
        Vaccine existingVaccine = this.vaccineRepository.getById(id);
        GetVaccineResponse response = this.modelMapperService.forResponse().map(existingVaccine, GetVaccineResponse.class);
        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<List<GetAllVaccineResponse>> getByAnimalId(int animalId) {
        List<Vaccine> vaccines = vaccineRepository.findByAnimal_AnimalId(animalId);
        List<GetAllVaccineResponse> responses = vaccines.stream()
                .map(vaccine -> modelMapperService.forResponse().map(vaccine, GetAllVaccineResponse.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(responses);
    }

    @Override
    public DataResult<List<GetAllVaccineResponse>> getByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate) {
        List<Vaccine> vaccines = vaccineRepository.findByProtectionFinishDateBetween(startDate, endDate);
        List<GetAllVaccineResponse> responses = vaccines.stream()
                .map(vaccine -> modelMapperService.forResponse().map(vaccine, GetAllVaccineResponse.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(responses);
    }
}
