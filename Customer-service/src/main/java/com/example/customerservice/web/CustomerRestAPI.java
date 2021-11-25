package com.example.customerservice.web;


import com.example.customerservice.dto.CustomerRequestDTO;
import com.example.customerservice.dto.CustomerResponseDTO;
import com.example.customerservice.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api")
public class CustomerRestAPI {

    private CustomerService customerservice;

    public CustomerRestAPI(CustomerService customerservice) {
        this.customerservice = customerservice;
    }

    @GetMapping(path = "/customers")
    public List<CustomerResponseDTO> getAllCustomers(){
        return customerservice.listCustomers();
    }
    @PostMapping(path="/customers")
    public CustomerResponseDTO save(@RequestBody CustomerRequestDTO customerRequestDTO){
        customerRequestDTO.setId(UUID.randomUUID().toString());
        return customerservice.save(customerRequestDTO);
    }
    @GetMapping(path="/customers/{id}")
    public CustomerResponseDTO getCustomer(@PathVariable String id){
        return customerservice.getCustomer(id);
    }
}
