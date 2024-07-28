package dev.patika.Veterinary.Management.System.business.concretes;

import dev.patika.Veterinary.Management.System.business.abstracts.IAppointmentService;
import dev.patika.Veterinary.Management.System.business.rules.AppointmnentBusinessRules;
import dev.patika.Veterinary.Management.System.core.utilities.mappers.ModelMapperService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessDataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.SuccessResult;
import dev.patika.Veterinary.Management.System.dao.AnimalRepository;
import dev.patika.Veterinary.Management.System.dao.AppointmentRepository;
import dev.patika.Veterinary.Management.System.dao.AvailableDateRepository;
import dev.patika.Veterinary.Management.System.dao.DoctorRepository;
import dev.patika.Veterinary.Management.System.dto.requests.appointment.CreateAppointmentRequest;
import dev.patika.Veterinary.Management.System.dto.requests.appointment.UpdateAppointmentRequest;
import dev.patika.Veterinary.Management.System.dto.responses.animal.AnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.AppointmentResponse;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.GetAllAppointmentResponse;
import dev.patika.Veterinary.Management.System.dto.responses.appointmentDate.GetAppointmentResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.CustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.DoctorResponse;
import dev.patika.Veterinary.Management.System.entities.Animal;
import dev.patika.Veterinary.Management.System.entities.Appointment;
import dev.patika.Veterinary.Management.System.entities.AvailableDate;
import dev.patika.Veterinary.Management.System.entities.Doctor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ModelMapperService modelMapperService;
    private final AnimalRepository animalRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmnentBusinessRules rules;

    @Override
    public DataResult<List<GetAllAppointmentResponse>> getAll() {

        List<Appointment> appointments = appointmentRepository.findAll();

        List<GetAllAppointmentResponse> getAllAppointmentResponses = appointments.stream()
                .map(appointment -> {
                    GetAllAppointmentResponse response = modelMapperService.forResponse().map(appointment, GetAllAppointmentResponse.class);

                    AnimalResponse animalResponse = modelMapperService.forResponse().map(appointment.getAnimal(), AnimalResponse.class);
                    DoctorResponse doctorResponse = modelMapperService.forResponse().map(appointment.getDoctor(), DoctorResponse.class);

                    response.setAnimalResponse(animalResponse);
                    response.setDoctorResponse(doctorResponse);

                    return response;
                })
                .collect(Collectors.toList());

        return new SuccessDataResult<>(getAllAppointmentResponses);
    }
    @Override
    public Result create(CreateAppointmentRequest createAppointmentRequest) {
        // Check if the specified animal exists
        rules.checkAnimalExists(createAppointmentRequest.getAnimalId());
        rules.checkIfDoctorIdNotFind(createAppointmentRequest.getDoctorId());

        // Check doctor availability
        rules.checkDoctorAvailability(createAppointmentRequest.getDoctorId(), createAppointmentRequest.getAppointmentDate());

        rules.checkIfDoctorWorkingThisDay(createAppointmentRequest.getAppointmentDate().toLocalDate(),createAppointmentRequest.getDoctorId());

        rules.checkIfDoctorAvailableOnDate(createAppointmentRequest.getAppointmentDate().toLocalDate(),createAppointmentRequest.getDoctorId());
        Appointment appointment = modelMapperService.forRequest().map(createAppointmentRequest, Appointment.class);
        appointment.setAnimal(animalRepository.findById(createAppointmentRequest.getAnimalId()).get());
        appointment.setDoctor(doctorRepository.findById(createAppointmentRequest.getDoctorId()).get());

        appointmentRepository.save(appointment);

        return new SuccessResult("Appointment added");

    }
    @Override
    public DataResult<AppointmentResponse> update(UpdateAppointmentRequest updateAppointmentRequest) {
        // Randevu, hayvan ve doktor ID kontrolleri yap
        rules.checkIfAppointmentIdNotFind(updateAppointmentRequest.getAppointmentId());
        rules.checkAnimalExists(updateAppointmentRequest.getAnimalId());
        rules.checkIfDoctorIdNotFind(updateAppointmentRequest.getDoctorId());

        // Var olan randevuyu getir, null ise hata fırlat
        Appointment appointment = appointmentRepository.getById(updateAppointmentRequest.getAppointmentId());


        // Tarihleri karşılaştır
        LocalDate appointmentDate = appointment.getAppointmentDate().toLocalDate();
        LocalDate updateDate = updateAppointmentRequest.getAppointmentDate().toLocalDate();

        if (!appointmentDate.equals(updateDate))  {
            // Doktorun uygunluğunu kontrol et
            rules.checkDoctorAvailability(updateAppointmentRequest.getDoctorId(), updateAppointmentRequest.getAppointmentDate());
            rules.checkIfDoctorWorkingThisDay(updateAppointmentRequest.getAppointmentDate().toLocalDate(), updateAppointmentRequest.getDoctorId());
            rules.checkIfDoctorAvailableOnDate(updateAppointmentRequest.getAppointmentDate().toLocalDate(), updateAppointmentRequest.getDoctorId());
        }


        Animal animal = animalRepository.getById(updateAppointmentRequest.getAnimalId());


        Doctor doctor = doctorRepository.getById(updateAppointmentRequest.getDoctorId());

        appointment.setAppointmentDate(updateAppointmentRequest.getAppointmentDate());
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);

        appointmentRepository.save(appointment);

        AppointmentResponse response = modelMapperService.forResponse().map(appointment, AppointmentResponse.class);
        return new SuccessDataResult<>(response, "Appointment updated successfully");
    }

    @Override
    public Result delete(int id) {
        this.rules.checkIfAppointmentIdNotFind(id);
        Appointment deletedAppointment = this.appointmentRepository.getById(id);
        this.appointmentRepository.delete(deletedAppointment);
        return new SuccessResult("Appointment deleted");
    }

    @Override
    public DataResult<GetAppointmentResponse> getAppointmentById(int id) {
        this.rules.checkIfAppointmentIdNotFind(id);
        Appointment existingAppointment = this.appointmentRepository.getById(id);
        GetAppointmentResponse response = this.modelMapperService.forResponse().map(existingAppointment, GetAppointmentResponse.class);
        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<List<GetAllAppointmentResponse>> getByAnimalIdAndDateRange(Animal animal, LocalDateTime startDate, LocalDateTime endDate) {

        rules.checkAnimalExists(animal.getAnimalId());
        rules.existsByDateTimeBetween(startDate,endDate);

        List<Appointment> appointments = appointmentRepository.findByAppointmentDateBetweenAndAnimal(startDate, endDate, animal);

        List<GetAllAppointmentResponse> responses = appointments.stream()
                .map(appointment -> modelMapperService.forResponse().map(appointment, GetAllAppointmentResponse.class))
                .toList();
        return new SuccessDataResult<>(responses);
    }

    @Override
    public DataResult<List<GetAllAppointmentResponse>> getByDoctorIdAndDateRange(Doctor doctor, LocalDateTime startDate, LocalDateTime endDate) {

        rules.checkIfDoctorIdNotFind(doctor.getDoctorId());
        rules.existsByDateTimeBetween(startDate,endDate);

        List<Appointment> appointments = appointmentRepository.findByAppointmentDateBetweenAndDoctor(startDate, endDate, doctor);

        List<GetAllAppointmentResponse> responses = appointments.stream()
                .map(appointment -> modelMapperService.forResponse().map(appointment, GetAllAppointmentResponse.class))
                .toList();
        return new SuccessDataResult<>(responses);
    }
}
