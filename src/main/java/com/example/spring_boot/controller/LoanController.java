package com.example.spring_boot.controller;

import com.example.spring_boot.model.LoanApplication;
import com.example.spring_boot.repository.LoanApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.AotInitializerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/phansbank/v1/")
public class LoanController {

    @Autowired
    private LoanApplicationRepository loanRepo;

    //	get all applcn
    @GetMapping("/view")
    public List<LoanApplication> getAllIDs() {
        return loanRepo.findAll();
    }

    //POST
    @PostMapping("/submit")
    public LoanApplication createApp(@RequestBody LoanApplication data) {
        return loanRepo.save(data);
    }

    //GET APPCN BY ID-path variable : to view applcn by id
    @GetMapping("/view-application/{id}")
    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable Long id) {
        LoanApplication data = loanRepo.findById(id)
                .orElseThrow(() -> new AotInitializerNotFoundException(null,
                        "Application doesn't exist for id"));
        return ResponseEntity.ok(data);
    }
}

