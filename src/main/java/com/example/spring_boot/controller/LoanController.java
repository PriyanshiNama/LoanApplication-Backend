package com.example.spring_boot.controller;

// import com.example.spring_boot.dto.LoanDTO;
import com.example.spring_boot.model.LoanApplication;
import com.example.spring_boot.repository.LoanApplicationRepository;
// import com.example.spring_boot.service.LoanApplicationService;

// import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.AotInitializerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// import java.util.HashMap;
import java.util.List;
// import java.util.Optional;
// import java.util.Map;

@RestController
@RequestMapping("/phansbank/v1/")
public class LoanController {

    @Autowired
    private LoanApplicationRepository loanRepo;
    // @Autowired
    // private LoanApplicationService loanService;

    //	get all applcn
    @GetMapping("/viewapps")
    public List<LoanApplication> getAllIDs() {
         return loanRepo.findAll();
    }

    // @GetMapping("/viewapps")
    // public List<LoanDTO> getAllIDs() {
    //      return loanRepo.findAll();
    //     return loanService.getspecific();
    // }

    //POST
    @PostMapping("/submit")
    public LoanApplication createApp(@RequestBody LoanApplication data) {
       
        System.out.println("added");
        return loanRepo.save(data);
    }

    // GET APPCN BY ID-path variable : to view applcn by id
    @GetMapping("/viewapplication/{id}")
    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable Long id) {
        LoanApplication data = loanRepo.findById(id)
                .orElseThrow(() -> new AotInitializerNotFoundException(null, 
				"Application doesn't exist for id"));
        return ResponseEntity.ok(data);
    }

    // if needed optn for update--not complete
    // @PutMapping("/appl/{id}")
    // public ResponseEntity<LoanApplication> updateApplicationById(@PathVariable Long id, @RequestBody LoanApplication data) {
    //     LoanApplication la= loanRepo.findById(id)
    //             .orElseThrow(() -> new AotInitializerNotFoundException(null, 
	// 			"Application doesn't exist for id"));
    //     //use setters
    // }

    // delete-405 err
    // @DeleteMapping("/appl/{id}")
    // public ResponseEntity<Map<String, Boolean>> deleteApp(@PathVariable Long id) {
    //     LoanApplication data = loanRepo.findById(id)
    //             .orElseThrow(() -> new AotInitializerNotFoundException(null, 
	// 			"Application doesn't exist for id"));
    
    //             loanRepo.delete(data);
    //             Map<String, Boolean> res=new HashMap<>();
    //             res.put("deleted", Boolean.TRUE);
    //             return ResponseEntity.ok(res);
    //         }
}



