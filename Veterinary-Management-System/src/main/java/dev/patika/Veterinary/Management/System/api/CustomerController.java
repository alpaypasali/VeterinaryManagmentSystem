package dev.patika.Veterinary.Management.System.api;

import dev.patika.Veterinary.Management.System.business.abstracts.ICustomerService;
import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.customer.CreateCustomerRequest;
import dev.patika.Veterinary.Management.System.dto.requests.customer.UpdateCustomerRequest;
import dev.patika.Veterinary.Management.System.dto.responses.customer.CustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.GetAllCustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.GetCustomerResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;
    @GetMapping()
    public DataResult<List<GetAllCustomerResponse>> getAll(){
        return this.customerService.getAll();
    }

    @GetMapping("/{id}")
    public DataResult<GetCustomerResponse> getCustomer(@PathVariable("id") int id){
        return customerService.getCustomerById(id);


    }
    @GetMapping("/filter")
    public DataResult<List<GetAllCustomerResponse>>  filtredByName(@RequestParam String name){
        return this.customerService.getByName(name);
    }

    @PostMapping()
    public ResponseEntity<Result> create(@Valid @RequestBody CreateCustomerRequest createCustomerRequest){
        Result result = customerService.create(createCustomerRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping()
    public DataResult<CustomerResponse> update(@RequestBody UpdateCustomerRequest updateCustomerRequest){
        return customerService.update(updateCustomerRequest);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable("id") int id){
        Result result = customerService.delete(id);
        return ResponseEntity.ok(result);
    }

}
