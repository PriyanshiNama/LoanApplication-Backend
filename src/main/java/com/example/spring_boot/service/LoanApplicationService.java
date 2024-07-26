//package com.example.spring_boot.service;
//
//import com.example.spring_boot.model.LoanApplication;
//import com.example.spring_boot.repository.LoanApplicationRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class LoanApplicationService {
//    @Autowired
//    private LoanApplicationRepository loanApplicationRepository;
//
//    public List<LoanApplication> getAllApplications() {
//        return loanApplicationRepository.findAll();
//    }
//
//    public Optional<LoanApplication> getApplicationById(Long id) {
//        Optional<LoanApplication> applicationByID = loanApplicationRepository.findById(id);
//        if (applicationByID.isPresent()) {
//            return loanApplicationRepository.findById(id);
//        }
//        else {
//            System.out.println("Application with ID " + id + " not found");
//            throw new EntityNotFoundException("Application with ID " + id + " not found");
//        }
//    }
//
//    public LoanApplication saveApplication(LoanApplication loanApplication) {
//        return loanApplicationRepository.save(loanApplication);
//    }
//
//    public void deleteApplication(Long id) {
//        loanApplicationRepository.deleteById(id);
//    }
//}
