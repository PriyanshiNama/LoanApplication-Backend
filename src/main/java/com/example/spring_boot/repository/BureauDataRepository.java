package com.example.spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.model.BureauData;

@Repository
public interface BureauDataRepository extends JpaRepository<BureauData, String> {
    // boolean existsBySsnNumber(String ssn_no);
}

