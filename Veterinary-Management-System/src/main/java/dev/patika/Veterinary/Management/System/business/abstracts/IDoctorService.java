package dev.patika.Veterinary.Management.System.business.abstracts;

import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.doctor.CreateDoctorRequest;
import dev.patika.Veterinary.Management.System.dto.requests.doctor.UpdateDoctorRequest;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.DoctorResponse;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.GetAllDoctorResponse;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.GetDoctorResponse;

import java.util.List;

public interface IDoctorService {
    DataResult<List<GetAllDoctorResponse>> getAll();
    Result create(CreateDoctorRequest createDoctorRequest);
    DataResult<DoctorResponse> update(UpdateDoctorRequest updateDoctorRequest);
    Result delete(int id);
    DataResult<GetDoctorResponse> getDoctorById(int id);
}
