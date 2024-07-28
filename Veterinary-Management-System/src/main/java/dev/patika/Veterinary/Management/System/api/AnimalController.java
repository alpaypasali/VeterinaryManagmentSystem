package dev.patika.Veterinary.Management.System.api;

import dev.patika.Veterinary.Management.System.business.abstracts.IAnimalService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.animal.CreateAnimalRequest;
import dev.patika.Veterinary.Management.System.dto.requests.animal.UpdateAnimalRequest;
import dev.patika.Veterinary.Management.System.dto.responses.animal.AnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.animal.GetAllAnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.animal.GetAnimalResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animals")
@AllArgsConstructor
public class AnimalController {
    private final IAnimalService animalService;
    @GetMapping()
    public DataResult<List<GetAllAnimalResponse>> getAll(){
        return this.animalService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetAnimalResponse> getAnimal(@PathVariable("id") int id){
        return animalService.getAnimalById(id);


    }

    @PostMapping()
    public ResponseEntity<Result> create(@Valid @RequestBody CreateAnimalRequest createAnimalRequest){
        Result result = animalService.create(createAnimalRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping()
    public DataResult<AnimalResponse> update(@RequestBody UpdateAnimalRequest updateAnimalRequest){
        return animalService.update(updateAnimalRequest);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable("id") int id){
        Result result = animalService.delete(id);
        return ResponseEntity.ok(result);
    }
}
