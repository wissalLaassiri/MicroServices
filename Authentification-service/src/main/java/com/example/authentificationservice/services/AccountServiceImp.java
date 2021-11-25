package com.example.authentificationservice.services;

import java.util.List;

import com.example.authentificationservice.models.AppRole;
import com.example.authentificationservice.models.AppUser;
import com.example.authentificationservice.repository.AppRoleRepository;
import com.example.authentificationservice.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImp implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    public  AccountServiceImp(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository,
            PasswordEncoder passwordEncoder){
                this.appUserRepository = appUserRepository;
                this.appRoleRepository = appRoleRepository;
                this.passwordEncoder = passwordEncoder;
    }
    @Override
    public AppUser addNewUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }
    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }
    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }
    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    };
    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
}
