package dev.patika.Veterinary.Management.System.business.concretes;

import dev.patika.Veterinary.Management.System.business.abstracts.IAvailableDateService;
import dev.patika.Veterinary.Management.System.business.abstracts.IDoctorService;
import dev.patika.Veterinary.Management.System.business.rules.AvailableDateBusinessRules;
import dev.patika.Veterinary.Management.System.core.utilities.mappers.ModelMapperService;
import dev.patika.Veterinary.Management.System.core.utilities.results.*;
import dev.patika.Veterinary.Management.System.dao.AvailableDateRepository;
import dev.patika.Veterinary.Management.System.dao.DoctorRepository;
import dev.patika.Veterinary.Management.System.dto.requests.availableDate.CreateAvailableDateRequest;
import dev.patika.Veterinary.Management.System.dto.requests.availableDate.UpdateAvailableDateRequest;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.AvailableDateResponse;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.GetAllAvailableDateResponse;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.GetAvailableDateResponse;
import dev.patika.Veterinary.Management.System.entities.AvailableDate;
import dev.patika.Veterinary.Management.System.entities.Doctor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AvailableDateManager implements IAvailableDateService {
    private final AvailableDateRepository availableDateRepository;
    private final ModelMapperService modelMapperService;
    private final DoctorRepository doctorService;
    private final AvailableDateBusinessRules rules;
    @Override
    public DataResult<List<GetAllAvailableDateResponse>> getAll() {
        List<AvailableDate> availableDates = availableDateRepository.findAll();

        List<GetAllAvailableDateResponse> getAllAuthorResponses = availableDates.stream()
                .map(availableDate -> this.modelMapperService.forResponse().map(availableDate, GetAllAvailableDateResponse.class))
                .toList();

        return new SuccessDataResult<>(getAllAuthorResponses);
    }

    @Override
    public Result create(CreateAvailableDateRequest createAvailableDateRequest) {
        this.rules.checkIfDoctorIdNotFind(createAvailableDateRequest.getDoctorId());
        this.rules.checkIfDoctorAlreadyHaveThisDate(createAvailableDateRequest.getDoctorId(),createAvailableDateRequest.getAvailableDate());
        Doctor doctor = this.doctorService.getById(createAvailableDateRequest.getDoctorId());
        createAvailableDateRequest.setDoctorId(0);


        AvailableDate availableDate = this.modelMapperService.forRequest()
                .map(createAvailableDateRequest, AvailableDate.class);
        availableDate.setDoctor(doctor);
        this.availableDateRepository.save(availableDate);
        return new SuccessResult("AvailableDate added");
    }


    @Override
    public DataResult<AvailableDateResponse> update(UpdateAvailableDateRequest updateAvailableDateRequest) {
        this.rules.checkIfDoctorIdNotFind(updateAvailableDateRequest.getDoctorId());
        this.rules.checkIfDoctorAlreadyHaveThisDate(updateAvailableDateRequest.getDoctorId(),updateAvailableDateRequest.getAvailableDate());

        AvailableDate existingAvailableDate = this.availableDateRepository.getById(updateAvailableDateRequest.getAvailableId());
        this.modelMapperService.forRequest().map(updateAvailableDateRequest, existingAvailableDate);

        this.availableDateRepository.save(existingAvailableDate);
        AvailableDateResponse response = this.modelMapperService.forResponse().map(existingAvailableDate, AvailableDateResponse.class);
        return new SuccessDataResult<>(response, "AvailableDate updated successfully");
    }

    @Override
    public Result delete(int id) {
        this.rules.checkIfAvailableDateIdNotFind(id);
        AvailableDate deletedAvailableDate = this.availableDateRepository.getById(id);
        this.availableDateRepository.delete(deletedAvailableDate);
        return new SuccessResult("AvailableDate deleted");
    }

    @Override
    public DataResult<GetAvailableDateResponse> getAvailableDateById(int id) {
        this.rules.checkIfAvailableDateIdNotFind(id);
        AvailableDate existingAvailable = this.availableDateRepository.getById(id);
        GetAvailableDateResponse response = this.modelMapperService.forResponse().map(existingAvailable, GetAvailableDateResponse.class);
        return new SuccessDataResult<>(response);
    }
    @Override
    public DataResult<List<GetAllAvailableDateResponse>> getByDoctorId(int doctorId) {
        List<AvailableDate> availableDates = availableDateRepository.findByDoctor_DoctorId(doctorId);
        List<GetAllAvailableDateResponse> responses = availableDates.stream()
                .map(date -> modelMapperService.forResponse().map(date, GetAllAvailableDateResponse.class))
                .toList();
        return new SuccessDataResult<>(responses);
    }

    @Override
    public DataResult<List<GetAllAvailableDateResponse>> getByDateRange(LocalDate startDate, LocalDate endDate) {
        List<AvailableDate> availableDates = availableDateRepository.findByAvailableDateBetween(startDate, endDate);
        List<GetAllAvailableDateResponse> responses = availableDates.stream()
                .map(date -> modelMapperService.forResponse().map(date, GetAllAvailableDateResponse.class))
                .toList();
        return new SuccessDataResult<>(responses);
    }
}
