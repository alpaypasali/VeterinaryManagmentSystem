package dev.patika.Veterinary.Management.System.api;

import dev.patika.Veterinary.Management.System.business.abstracts.IAppointmentService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.appointment.CreateAppointmentRequest;
import dev.patika.Veterinary.Management.System.dto.requests.appointment.UpdateAppointmentRequest;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.AppointmentResponse;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.GetAllAppointmentResponse;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.GetAppointmentResponse;
import dev.patika.Veterinary.Management.System.entities.Animal;
import dev.patika.Veterinary.Management.System.entities.Doctor;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
@AllArgsConstructor
public class AppointmentController {
    private final IAppointmentService appointmentService;
    @GetMapping()
    public DataResult<List<GetAllAppointmentResponse>> getAll(){
        return this.appointmentService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetAppointmentResponse> getAppointment(@PathVariable("id") int id){
        return appointmentService.getAppointmentById(id);


    }

    @PostMapping()
    public ResponseEntity<Result> create(@Valid @RequestBody CreateAppointmentRequest createAppointmentRequest){
        Result result = appointmentService.create(createAppointmentRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping()
    public DataResult<AppointmentResponse> update(@RequestBody UpdateAppointmentRequest updateAppointmentRequest){
        return appointmentService.update(updateAppointmentRequest);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable("id") int id){
        Result result = appointmentService.delete(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/doctor")
    public ResponseEntity<DataResult<List<GetAllAppointmentResponse>>> getByDoctorIdAndDateRange(
            @RequestParam("doctorId") int doctorId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);

        DataResult<List<GetAllAppointmentResponse>> result = appointmentService.getByDoctorIdAndDateRange(doctor, startDate, endDate);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/animal")
    public ResponseEntity<DataResult<List<GetAllAppointmentResponse>>> getByAnimalIdAndDateRange(
            @RequestParam("animalId") int animalId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Animal animal = new Animal();
        animal.setAnimalId(animalId);

        DataResult<List<GetAllAppointmentResponse>> result = appointmentService.getByAnimalIdAndDateRange(animal, startDate, endDate);

        return ResponseEntity.ok(result);
    }

}
