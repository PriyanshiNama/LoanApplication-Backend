package com.example.spring_boot.controller;

import com.example.spring_boot.model.LoanApplication;
import com.example.spring_boot.repository.LoanApplicationRepository;
import com.example.spring_boot.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.AotInitializerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/phansbank/v1/")
public class LoanController {

    @Autowired
    private LoanApplicationService loanService;

    // Get all applications
    @GetMapping("/viewapps")
    public List<LoanApplication> getAllIDs() {
        return loanService.getAllIDs();
    }

    // Create a new application
    @PostMapping("/submit")
    public LoanApplication createApp(@RequestBody LoanApplication data) {
        return loanService.createApp(data);
    }

    // Get application by ID
    @GetMapping("/viewapps/{id}")
    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable Long id) {
        LoanApplication data = loanService.getApplicationById(id);
        return ResponseEntity.ok(data);
    }

//    @Autowired
//    private LoanApplicationRepository loanRepo;
//
//    @GetMapping("/viewapps")
//    public List<LoanApplication> getAllIDs() {
//        return loanRepo.findAll();
//    }
//
//    @PostMapping("/submit")
//    public LoanApplication createApp(@RequestBody LoanApplication data) {
//        System.out.println("added");
//        return loanRepo.save(data);
//    }
//
//    @GetMapping("/viewapps/{id}")
//    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable Long id) {
//        LoanApplication data = loanRepo.findById(id)
//                .orElseThrow(() -> new AotInitializerNotFoundException(null,
//                        "Application doesn't exist for id"));
//        return ResponseEntity.ok(data);
//    }

}


