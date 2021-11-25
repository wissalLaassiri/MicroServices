package com.example.billingservice.services;

import com.example.billingservice.dto.InvoiceRequestDTO;
import com.example.billingservice.dto.InvoiceResponseDTO;
import com.example.billingservice.exception.CustomerNotFoundException;
import com.example.billingservice.feign.CustomerRestClient;
import com.example.billingservice.mappers.InvoiceMapper;
import com.example.billingservice.models.Customer;
import com.example.billingservice.models.Invoice;
import com.example.billingservice.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;
    private CustomerRestClient customerRestClient;


    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerRestClient customerRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRestClient = customerRestClient;
    }

    @Override
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) throws CustomerNotFoundException {
        Invoice invoice = invoiceMapper.fromInvoiceResponseDTO(invoiceRequestDTO);
        Customer customer = null;
        customer = customerRestClient.customerById(invoiceRequestDTO.getCustomerId());
        System.out.println(customer);
//        try {
//        }catch (Exception e) {
//            throw new CustomerNotFoundException("Customer not found");
//        }
        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(new Date());
        invoice.setCustomerID(customer.getId());
        invoice.setCustomer(customer);
        Invoice save = invoiceRepository.save(invoice);
        return invoiceMapper.invoiceToInvoiceResponseDTO(save);
    }

    @Override
    public InvoiceResponseDTO getInvoice(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        Customer customer = customerRestClient.customerById(invoice.getCustomerID());
        invoice.setCustomer(customer);
        return invoiceMapper.invoiceToInvoiceResponseDTO(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> invoicesByCustomerId(String customerId) {
        List<Invoice> invoices = invoiceRepository.findByCustomerID(customerId);
        for (Invoice invoice :invoices) {
            Customer customer = customerRestClient.customerById(invoice.getCustomerID());
            invoice.setCustomer(customer);
        }
        return invoices.stream()
                .map(invoice->invoiceMapper.invoiceToInvoiceResponseDTO(invoice))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoice() {
        List<Invoice> invoices = invoiceRepository.findAll();
        for (Invoice invoice : invoices) {
            Customer customer = customerRestClient.customerById(invoice.getCustomerID());
            invoice.setCustomer(customer);
        }
        return  invoices.stream()
                .map(invoice->invoiceMapper.invoiceToInvoiceResponseDTO(invoice))
                .collect(Collectors.toList());
    }
}
