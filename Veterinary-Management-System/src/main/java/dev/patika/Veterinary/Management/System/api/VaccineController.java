package dev.patika.Veterinary.Management.System.api;

import dev.patika.Veterinary.Management.System.business.abstracts.IVaccineService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.vaccine.CreateVaccineRequest;
import dev.patika.Veterinary.Management.System.dto.requests.vaccine.UpdateVaccineRequest;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.GetAllVaccineResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.GetVaccineResponse;
import dev.patika.Veterinary.Management.System.dto.responses.vaccine.VaccineResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
@AllArgsConstructor
public class VaccineController {
    private final IVaccineService vaccineService;

    @GetMapping()
    public DataResult<List<GetAllVaccineResponse>> getAll(){
        return this.vaccineService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetVaccineResponse> getVaccine(@PathVariable("id") int id){
        return vaccineService.getVaccineById(id);


    }

    @PostMapping()
    public ResponseEntity<Result> create(@Valid @RequestBody CreateVaccineRequest createVaccineRequest){

        Result result = vaccineService.create(createVaccineRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping()
    public DataResult<VaccineResponse> update(@RequestBody UpdateVaccineRequest updateVaccineRequest){
        return vaccineService.update(updateVaccineRequest);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable("id") int id){
        Result result = vaccineService.delete(id);
        return ResponseEntity.ok(result);
    }
    // Endpoint to get vaccines by animal id
    @GetMapping("/animal/{animalId}")
    public DataResult<List<GetAllVaccineResponse>> getByAnimalId(@PathVariable int animalId) {
        return vaccineService.getByAnimalId(animalId);
    }


    @GetMapping("/protection-date")
    public DataResult<List<GetAllVaccineResponse>> getByProtectionFinishDateBetween(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return vaccineService.getByProtectionFinishDateBetween(startDate, endDate);
    }
}
