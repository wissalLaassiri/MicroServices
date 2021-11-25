package com.example.billingservice.mappers;


import com.example.billingservice.dto.InvoiceRequestDTO;
import com.example.billingservice.dto.InvoiceResponseDTO;
import com.example.billingservice.models.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceResponseDTO invoiceToInvoiceResponseDTO(Invoice invoice);
    Invoice fromInvoiceResponseDTO(InvoiceRequestDTO invoiceRequestDTO);
}
