package com.example.springwordgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;


    public Admin findByEmail(String email) {return AdminRepository.findByEmail(email);}

    // Add additional admin-related methods if needed
}


