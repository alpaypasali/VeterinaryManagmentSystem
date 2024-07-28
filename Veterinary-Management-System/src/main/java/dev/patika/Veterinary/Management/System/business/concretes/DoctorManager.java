package dev.patika.Veterinary.Management.System.business.concretes;

import dev.patika.Veterinary.Management.System.business.abstracts.IDoctorService;
import dev.patika.Veterinary.Management.System.business.rules.DoctorBusinessRules;
import dev.patika.Veterinary.Management.System.core.utilities.mappers.ModelMapperService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessDataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessResult;
import dev.patika.Veterinary.Management.System.dao.DoctorRepository;
import dev.patika.Veterinary.Management.System.dto.requests.doctor.CreateDoctorRequest;
import dev.patika.Veterinary.Management.System.dto.requests.doctor.UpdateDoctorRequest;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.DoctorResponse;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.GetAllDoctorResponse;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.GetDoctorResponse;
import dev.patika.Veterinary.Management.System.entities.Doctor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
@Service
@AllArgsConstructor
public class DoctorManager implements IDoctorService {
    private final DoctorRepository doctorRepository;
    private final ModelMapperService modelMapperService;
    private  final DoctorBusinessRules rules;
    @Override
    public DataResult<List<GetAllDoctorResponse>> getAll() {
        List<Doctor> doctors = doctorRepository.findAll();

        List<GetAllDoctorResponse> getAllDoctorResponses = doctors.stream()
                .map(doctor -> this.modelMapperService.forResponse().map(doctor, GetAllDoctorResponse.class))
                .toList();

        return new SuccessDataResult<>(getAllDoctorResponses);
    }

    @Override
    public Result create(CreateDoctorRequest createDoctorRequest) {
        Doctor doctor = this.modelMapperService.forRequest()
                .map(createDoctorRequest , Doctor.class);

        this.doctorRepository.save(doctor);
        return new SuccessResult("Doctor added");
    }

    @Override
    public DataResult<DoctorResponse> update(UpdateDoctorRequest updateDoctorRequest) {
        this.rules.checkIfDoctorIdNotFind(updateDoctorRequest.getDoctorId());
        Doctor existingDoctor = this.doctorRepository.getById(updateDoctorRequest.getDoctorId());
        this.modelMapperService.forRequest().map(updateDoctorRequest, existingDoctor);

        this.doctorRepository.save(existingDoctor);
        DoctorResponse response = this.modelMapperService.forResponse().map(existingDoctor, DoctorResponse.class);
        return new SuccessDataResult<>(response, "Doctor updated successfully");
    }

    @Override
    public Result delete(int id) {
        this.rules.checkIfDoctorIdNotFind(id);
        Doctor deletedDoctor = this.doctorRepository.getById(id);
        this.doctorRepository.delete(deletedDoctor);
        return new SuccessResult("Doctor deleted");
    }

    @Override
    public DataResult<GetDoctorResponse> getDoctorById(int id) {
        this.rules.checkIfDoctorIdNotFind(id);
        Doctor existingDoctor = this.doctorRepository.getById(id);
        GetDoctorResponse response = this.modelMapperService.forResponse().map(existingDoctor, GetDoctorResponse.class);
        return new SuccessDataResult<>(response);
    }
}
