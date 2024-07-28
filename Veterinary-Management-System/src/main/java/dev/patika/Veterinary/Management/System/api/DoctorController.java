package dev.patika.Veterinary.Management.System.api;

import dev.patika.Veterinary.Management.System.business.abstracts.IDoctorService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.doctor.CreateDoctorRequest;
import dev.patika.Veterinary.Management.System.dto.requests.doctor.UpdateDoctorRequest;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.DoctorResponse;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.GetAllDoctorResponse;
import dev.patika.Veterinary.Management.System.dto.responses.doctor.GetDoctorResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/doctors")
@AllArgsConstructor
public class DoctorController {

    private final IDoctorService doctorService;

    @GetMapping()
    public DataResult<List<GetAllDoctorResponse>> getAll(){
        return this.doctorService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetDoctorResponse> getDoctor(@PathVariable("id") int id){
        return doctorService.getDoctorById(id);


    }

    @PostMapping()
    public ResponseEntity<Result> create(@Valid @RequestBody CreateDoctorRequest createDoctorRequest){
        Result result = doctorService.create(createDoctorRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping()
    public DataResult<DoctorResponse> update(@RequestBody UpdateDoctorRequest updateDoctorRequest){
        return doctorService.update(updateDoctorRequest);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable("id") int id){
        Result result = doctorService .delete(id);
        return ResponseEntity.ok(result);
    }
}
