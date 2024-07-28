package dev.patika.Veterinary.Management.System.business.abstracts;

import dev.patika.Veterinary.Management.System.core.utilities.results.DataResult;
import dev.patika.Veterinary.Management.System.core.utilities.results.Result;
import dev.patika.Veterinary.Management.System.dto.requests.customer.CreateCustomerRequest;
import dev.patika.Veterinary.Management.System.dto.requests.customer.UpdateCustomerRequest;
import dev.patika.Veterinary.Management.System.dto.responses.customer.CustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.GetAllCustomerResponse;
import dev.patika.Veterinary.Management.System.dto.responses.customer.GetCustomerResponse;

import java.util.List;

public interface ICustomerService {
    DataResult<List<GetAllCustomerResponse>> getAll();
    Result create(CreateCustomerRequest createCustomerRequest);
    DataResult<CustomerResponse> update(UpdateCustomerRequest updateCustomerRequest);
    Result delete(int id);
    DataResult<GetCustomerResponse> getCustomerById(int id);
    DataResult<List<GetAllCustomerResponse>> getByName(String name);
}
