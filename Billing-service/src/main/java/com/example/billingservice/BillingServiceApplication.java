package com.example.billingservice;

import com.example.billingservice.dto.InvoiceRequestDTO;
import com.example.billingservice.exception.CustomerNotFoundException;
import com.example.billingservice.services.InvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(InvoiceService invoiceService){
        return args -> {
            try {
                invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(13000),"A1"));
                invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(11000),"A2"));
                invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(12000),"A1"));
            } catch (CustomerNotFoundException e) {
                e.printStackTrace();
            }

        };
    }
}
