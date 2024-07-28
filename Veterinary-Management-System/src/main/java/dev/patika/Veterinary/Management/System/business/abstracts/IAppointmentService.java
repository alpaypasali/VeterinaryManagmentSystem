package dev.patika.Veterinary.Management.System.business.abstracts;


import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.appointment.CreateAppointmentRequest;
import dev.patika.Veterinary.Management.System.dto.requests.appointment.UpdateAppointmentRequest;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.AppointmentResponse;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.GetAllAppointmentResponse;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.GetAppointmentResponse;
import dev.patika.Veterinary.Management.System.entities.Animal;
import dev.patika.Veterinary.Management.System.entities.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    DataResult<List<GetAllAppointmentResponse>> getAll();
    Result create(CreateAppointmentRequest createAppointmentRequest);
    DataResult<AppointmentResponse> update(UpdateAppointmentRequest updateAppointmentRequest);
    Result delete(int id);
    DataResult<GetAppointmentResponse> getAppointmentById(int id);
    DataResult<List<GetAllAppointmentResponse>> getByDoctorIdAndDateRange(Doctor doctor, LocalDateTime startDate, LocalDateTime endDate);
    DataResult<List<GetAllAppointmentResponse>> getByAnimalIdAndDateRange(Animal animal, LocalDateTime startDate, LocalDateTime endDate);
}
