package dev.patika.Veterinary.Management.System.business.abstracts;

import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.vaccine.CreateVaccineRequest;
import dev.patika.Veterinary.Management.System.dto.requests.vaccine.UpdateVaccineRequest;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.GetAllVaccineResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.GetVaccineResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.VaccineResponse;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    DataResult<List<GetAllVaccineResponse>> getAll();
    Result create(CreateVaccineRequest createVaccineRequest);
    DataResult<VaccineResponse> update(UpdateVaccineRequest updateVaccineRequest);
    Result delete(int id);
    DataResult<GetVaccineResponse> getVaccineById(int id);
    DataResult<List<GetAllVaccineResponse>> getByAnimalId(int animalId);
    DataResult<List<GetAllVaccineResponse>> getByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);
}
