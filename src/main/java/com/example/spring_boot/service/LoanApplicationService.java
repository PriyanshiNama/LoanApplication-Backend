package com.example.spring_boot.service;


import com.example.spring_boot.model.LoanApplication;
import com.example.spring_boot.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanRepo;

    public List<LoanApplication> getAllIDs() {
        return loanRepo.findAll();
    }

    public LoanApplication createApp(LoanApplication data) {
        System.out.println("added");
        return loanRepo.save(data);
    }

    public LoanApplication getApplicationById(Long id) {
        return loanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application doesn't exist for id: " + id));
    }
}