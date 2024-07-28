package dev.patika.Veterinary.Management.System.api;

import dev.patika.Veterinary.Management.System.business.abstracts.IAvailableDateService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.availableDate.CreateAvailableDateRequest;
import dev.patika.Veterinary.Management.System.dto.requests.availableDate.UpdateAvailableDateRequest;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.AvailableDateResponse;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.GetAllAvailableDateResponse;
import dev.patika.Veterinary.Management.System.dto.responses.availableDate.GetAvailableDateResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/availableDates")
@AllArgsConstructor
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    @GetMapping()
    public DataResult<List<GetAllAvailableDateResponse>> getAll(){
        return this.availableDateService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetAvailableDateResponse> getAvaibleDate(@PathVariable("id") int id){
        return availableDateService.getAvailableDateById(id);


    }

    @PostMapping()
    public ResponseEntity<Result> create(@Valid @RequestBody CreateAvailableDateRequest createAvailableDateRequest){
        Result result = availableDateService.create(createAvailableDateRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping()
    public DataResult<AvailableDateResponse> update(@RequestBody UpdateAvailableDateRequest updateAvailableDateRequest){
        return availableDateService.update(updateAvailableDateRequest);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable("id") int id){
        Result result = availableDateService.delete(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/doctor/{doctorId}")
    public DataResult<List<GetAllAvailableDateResponse>> getByDoctorId(@PathVariable("doctorId") int doctorId){
        return availableDateService.getByDoctorId(doctorId);
    }

    @GetMapping("/dateRange")
    public DataResult<List<GetAllAvailableDateResponse>> getByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        return availableDateService.getByDateRange(startDate, endDate);
    }
}
