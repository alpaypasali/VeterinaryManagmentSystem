package dev.patika.Veterinary.Management.System.business.abstracts;

import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.animal.CreateAnimalRequest;
import dev.patika.Veterinary.Management.System.dto.requests.animal.UpdateAnimalRequest;
import dev.patika.Veterinary.Management.System.dto.responses.animal.AnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.animal.GetAllAnimalResponse;
import dev.patika.Veterinary.Management.System.dto.responses.animal.GetAnimalResponse;


import java.util.List;

public interface IAnimalService {
    DataResult<List<GetAllAnimalResponse>> getAll();
    Result create(CreateAnimalRequest createAnimalRequest);
    DataResult<AnimalResponse> update(UpdateAnimalRequest updateAnimalRequest);
    Result delete(int id);
    DataResult<GetAnimalResponse> getAnimalById(int id);

}
