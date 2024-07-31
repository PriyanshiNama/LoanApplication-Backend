package com.example.spring_boot.repository;

// import com.example.spring_boot.dto.LoanDTO;
import com.example.spring_boot.model.LoanApplication;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication,Long>  {
  
  // @Query("SELECT new com.example.spring_boot.dto.LoanDTO(u.application_id, u.first_name, u.middle_name, u.last_name, u.application_status) FROM LoanApplication u")
  // List <LoanDTO> findSpecific();
}
