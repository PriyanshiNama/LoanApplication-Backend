package com.example.spring_boot.service;


import com.example.spring_boot.model.BureauData;
import com.example.spring_boot.model.LoanApplication;
import com.example.spring_boot.repository.BureauDataRepository;
import com.example.spring_boot.repository.LoanApplicationRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanRepo;

    @Autowired
    private BureauDataRepository bureauRepo;

    public List<LoanApplication> getAllIDs() {
        return loanRepo.findAll();
    }

    public LoanApplication createApp(LoanApplication data) {
        data.setApplicationStatus("pending");
        System.out.println("added");

        LoanApplication savedApplication = loanRepo.save(data);
        calculateScore(savedApplication);

        // add to db
        return loanRepo.save(data);
    }

    public LoanApplication getApplicationById(Long id) {
        return loanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application doesn't exist for id: " + id));
    }

    public boolean checkIfSSNExists(String ssn) {
        // Using findById()
        Optional<BureauData> data = bureauRepo.findById(ssn);
        return data.isPresent();
    }

    public int checkDescription (LoanApplication data, String word) {
        String description = data.getDescription();
        if (description == null) {
            return 0;
        }
        return description.toLowerCase().contains(word) ? 1 : 0;
    }

    public Double calculateDTI(LoanApplication data) {
        double amount = data.getLoanAmount();
        double salary = data.getAnnualSalary();
        double dti = amount/salary;
        return dti;
    }

    public double calculateScore(LoanApplication data) {
        double score=0;
        Double lamt =data.getLoanAmount();
        Double emp =(double) data.getWorkExperienceYears()+ ((double)data.getWorkExperienceMonths() /12.0);
        Double salaray=data.getAnnualSalary();
        String ssn=data.getSsnNumber();

        BureauData bd= null;

        Integer dq=null;
        Integer inqLast6mths=null;
        Integer mthsSinceLastDelinq=null;
        Integer mthsSinceLastRecord=null;
        Integer openAcc=null;
        Integer pubRec=null;
        Integer revolBal=null;
        Double revolUtil=null;
        Integer totalAcc=null;


        // get bureau data
        if(checkIfSSNExists(ssn)){
            bd= bureauRepo.findById(ssn)
                    .orElseThrow(() -> new IllegalArgumentException("Application doesn't exist for id: " + ssn));

            dq= bd.getDelinq2yrs();
            inqLast6mths=bd.getInqLast6mths();
            mthsSinceLastDelinq=bd.getMthsSinceLastDelinq();
            mthsSinceLastRecord=bd.getMthsSinceLastRecord();
            openAcc=bd.getOpenAcc();
            pubRec=bd.getPubRec();
            revolBal=bd.getRevolBal();
            revolUtil=bd.getRevolUtil();
            totalAcc=bd.getTotalAcc();
        }


        String purpose=data.getLoanPurpose();
        Integer  purposeValue=0;

        if(purpose=="Debt" || purpose=="Home Loan")  purposeValue=1;
        else if(purpose=="Personel Loan")purposeValue=0; //get value---------------
        else if(purpose=="Educational Loan")purposeValue=0;
        else purposeValue=0; //others----------------


        Double dti=calculateDTI(data);
        Double creditAge=null; //updatevalue-------------------------------------------

        int added = checkDescription(data,"added");
        int borrower = checkDescription(data,"borrower");
        int loan = checkDescription(data,"loan");
        int credit = checkDescription(data,"credit");
        int pay = checkDescription(data,"pay");
        // System.out.println("Value of added is " + added);

        //score formula
        //condn

        return score;
    }
}
