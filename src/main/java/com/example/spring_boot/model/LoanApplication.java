package com.example.spring_boot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_details")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    //@Column(name = "city")
    private String city;

    //@Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "ssn_number")
    private String ssnNumber;

    @Column(name = "phone_home")
    private String phoneHome;

    @Column(name = "phone_office")
    private String phoneOffice;

    @Column(name = "phone_mobile")
    private String phoneMobile;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "loan_purpose")
    private String loanPurpose;

    //@Column(name = "description")
    private String description;

    @Column(name = "work_experience_years")
    private Integer workExperienceYears;

    @Column(name = "work_experience_months")
    private Integer workExperienceMonths;

    @Column(name = "annual_salary")
    private Double annualSalary;

    //@Column(name = "designation")
    private String designation;

    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "employer_address_line1")
    private String employerAddressLine1;

    @Column(name = "employer_address_line2")
    private String employerAddressLine2;

    @Column(name = "employer_city")
    private String employerCity;

    @Column(name = "employer_state")
    private String employerState;

    @Column(name = "employer_postal_code")
    private String employerPostalCode;

    @Column(name = "application_status")
    private String applicationStatus;

    //@Column(name = "score")
    private Integer score;

    @Column(name = "decline_reason")
    private String declineReason;

    @CreationTimestamp
    @Column(name = "submitted_date", nullable = false, updatable = false)
    private LocalDateTime submittedDate;

}

