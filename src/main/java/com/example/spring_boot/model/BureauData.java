package com.example.spring_boot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bureau_data")
public class BureauData {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "delinq_2yrs")
    private Integer delinq2yrs;

    @Column(name = "inq_last_6mths")
    private Integer inqLast6mths;

    @Column(name = "mths_since_last_delinq")
    private Integer mthsSinceLastDelinq;

    @Column(name = "mths_since_last_record")
    private Integer mthsSinceLastRecord;

    @Column(name = "open_acc")
    private Integer openAcc;

    @Column(name = "pub_rec")
    private Integer pubRec;

    @Column(name = "revol_bal")
    private Integer revolBal;

    @Column(name = "revol_util")
    private Double revolUtil;

    @Column(name = "total_acc")
    private Integer totalAcc;

    @Column(name = "earliest_cr_line")
    private LocalDateTime earliestCrLine;

}