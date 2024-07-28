package dev.patika.Veterinary.Management.System.business.abstracts;

import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.availableDate.CreateAvailableDateRequest;
import dev.patika.Veterinary.Management.System.dto.requests.availableDate.UpdateAvailableDateRequest;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.AvailableDateResponse;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.GetAllAvailableDateResponse;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.GetAvailableDateResponse;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {
    DataResult<List<GetAllAvailableDateResponse>> getAll();
    Result create(CreateAvailableDateRequest createAvailableDateRequest);
    DataResult<AvailableDateResponse> update(UpdateAvailableDateRequest updateAvailableDateRequest);
    Result delete(int id);
    DataResult<GetAvailableDateResponse> getAvailableDateById(int id);
    DataResult<List<GetAllAvailableDateResponse>> getByDoctorId(int doctorId);
    DataResult<List<GetAllAvailableDateResponse>> getByDateRange(LocalDate startDate, LocalDate endDate);
}
