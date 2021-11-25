package com.example.authentificationservice;

import com.example.authentificationservice.models.AppRole;
import com.example.authentificationservice.models.AppUser;
import com.example.authentificationservice.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class AuthentificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthentificationServiceApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {
            accountService.addNewRole(new AppRole(null,"USER"));
            accountService.addNewRole(new AppRole(null,"ADMIN"));
            accountService.addNewRole(new AppRole(null,"CUSTOMER_MANAGER"));
            accountService.addNewRole(new AppRole(null,"BILLS_MANAGER"));
            accountService.addNewUser(new AppUser(null,"user_1","wissal",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"admin","laassiri",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"user_2","simo",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"user_3","saad",new ArrayList<>()));
            accountService.addRoleToUser("user_1","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("user_2","USER");
            accountService.addRoleToUser("user_3","USER");
            accountService.addRoleToUser("admin","ADMIN");
            accountService.addRoleToUser("user_2","CUSTOMER_MANAGER");
            accountService.addRoleToUser("user_3","BILLS_MANAGER");
        };
    }
}
