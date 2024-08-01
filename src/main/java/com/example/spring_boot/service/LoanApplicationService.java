package com.example.spring_boot.service;


import com.example.spring_boot.model.BureauData;
import com.example.spring_boot.model.LoanApplication;
import com.example.spring_boot.repository.BureauDataRepository;
import com.example.spring_boot.repository.LoanApplicationRepository;

import jakarta.transaction.Transactional;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;


@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanRepo;

    @Autowired
    private BureauDataRepository bureauRepo;

    double[] mean = {
            11186.92, // loan_amnt
            5.03,     // emp_length
            68105.31, // annual_inc
            0.15,     // delinq_2yrs
            0.87,     // inq_last_6mths
            90.19,    // mths_since_last_delinq
            126.01,   // mths_since_last_record
            9.32,     // open_acc
            0.06,     // pub_rec
            13629.90, // revol_bal
            0.49,     // revol_util
            22.20,    // total_acc
            0.19,     // dti
            27.49     // credit_age
    };

    double[] scale = {
            7424.98,  // loan_amnt
            3.36,     // emp_length
            52014.56, // annual_inc
            0.48,     // delinq_2yrs
            1.06,     // inq_last_6mths
            42.22,    // mths_since_last_delinq
            18.59,    // mths_since_last_record
            4.41,     // open_acc
            0.24,     // pub_rec
            16277.70, // revol_bal
            0.28,     // revol_util
            11.45,    // total_acc
            0.12,     // dti
            6.80      // credit_age
    };

    double[] coefficient = {
            -0.000029, // loan_amnt
            0.001393,  // emp_length
            0.000009,  // annual_inc
            -0.086652, // delinq_2yrs
            -0.359234, // inq_last_6mths
            0.002767,  // mths_since_last_delinq
            -0.001579, // mths_since_last_record
            -0.016060, // open_acc
            -0.111303, // pub_rec
            -0.000008, // revol_bal
            -0.257019, // revol_util
            0.018594,  // total_acc
            -0.016612, // dti
            -0.004782, // credit_age
            0.210112,  // purpose
            -0.012674, // added
            -0.021654, // borrower
            0.051669,  // loan
            0.183890,  // credit
            -0.013502  // pay
    };

    double[] x_scaled = new double[scale.length];


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

    public Double checkDescription (LoanApplication data, String word) {
        String description = data.getDescription();
        if (description == null) {
            return 0.0;
        }
        return description.toLowerCase().contains(word) ? 1.0 : 0.0;
    }

    public Double calculateDTI(LoanApplication data) {
        double amount = data.getLoanAmount();
        double salary = data.getAnnualSalary();
        double dti = amount/salary;
        return dti;
    }

    public Integer GetCurrentYear() {
        Year currentYearObj = Year.now();
        int currentYearFromYear = currentYearObj.getValue();
        return currentYearFromYear;

    }

    @Transactional
    public void calculateScore(LoanApplication data) {
        // System.out.print("CALLLEDDDD");

        LoanApplication newd= getApplicationById(data.getApplicationId());

        double score=0;

        Double lamt =data.getLoanAmount();
        Double emp =(double) data.getWorkExperienceYears()+ ((double)data.getWorkExperienceMonths() /12.0);
        Double salary=data.getAnnualSalary();
        String ssn=data.getSsnNumber();

        BureauData bd= null;

        Double dq=0.0;
        Double inqLast6mths=0.0;
        Double mthsSinceLastDelinq=0.0;
        Double mthsSinceLastRecord=0.0;
        Double openAcc=0.0;
        Double pubRec=0.0;
        Double revolBal=0.0;
        Double revolUtil=0.0;
        Double totalAcc=0.0;
        Double earliestCrLineYear = 0.0;
        Double creditAge=  0.0;


        // get bureau data
        if(checkIfSSNExists(ssn)){
            bd= bureauRepo.findById(ssn)
                    .orElseThrow(() -> new IllegalArgumentException("Application doesn't exist for id: " + ssn));

            dq= (double) bd.getDelinq2yrs();
            inqLast6mths=(double) bd.getInqLast6mths();
            mthsSinceLastDelinq=(double) bd.getMthsSinceLastDelinq();
            mthsSinceLastRecord=(double) bd.getMthsSinceLastRecord();
            openAcc=(double) bd.getOpenAcc();
            pubRec=(double) bd.getPubRec();
            revolBal=(double) bd.getRevolBal();
            revolUtil=(double) bd.getRevolUtil();
            totalAcc=(double) bd.getTotalAcc();

            LocalDateTime currentDate = bd.getEarliestCrLine();
            earliestCrLineYear=(double) currentDate.getYear();

            creditAge=GetCurrentYear() - earliestCrLineYear;
        }


        String purpose=data.getLoanPurpose();
        Double  purposeValue=0.0;

        if(purpose=="Debt" || purpose=="Home Loan" || purpose=="Personel Loan")  purposeValue=1.0;
        else if(purpose=="Educational Loan")purposeValue=0.0;
        else purposeValue=1.0 ; //others

        System.out.println("purposeValue: "+ purposeValue);

        Double dti=calculateDTI(data);
        //credit age calculated
        System.out.println("credit age" +creditAge);

        Double added = checkDescription(data,"added");
        Double borrower = checkDescription(data,"borrower");
        Double loan = checkDescription(data,"loan");
        Double credit = checkDescription(data,"credit");
        Double pay = checkDescription(data,"pay");
        // System.out.println("Value of added is " + added);

        //sample data arr
        Double X[]={lamt, emp, salary, dq, inqLast6mths, mthsSinceLastDelinq, mthsSinceLastRecord, openAcc, pubRec, revolBal, revolUtil, totalAcc, dti, creditAge, purposeValue, added, borrower, loan, credit, pay};

        //Scaled value
        for (int i = 0; i < scale.length; i++) {
            x_scaled[i] = (X[i] - mean[i]) / scale[i];
        }


        // Z= Intercept + X1_scaled.C1 + X2_scaled.C2 …….Xn_scaled.Cn
        double Z =  -0.03214149408297314;//intercept value
        for (int i=0;i<scale.length;i++){
            Z+=x_scaled[i]*coefficient[i];
        }
        for(int i= scale.length;i<X.length;i++){
            Z+=X[i]*coefficient[i];
        }

        // P= 1/(1+e^(-Z))
        double P =  1.0 / (1.0 + Math.exp(-Z));
        //score formula
        score = P * 1000;

        double cutoff_credit_score=  390.0;
        Integer fscore=  (int)score;

        newd.setScore(fscore);

        //  •	Applicant’s age is <18 or >65.
        // •	Applicant’s work experience is < 6 months
        // •	Applicants annual salary is < $10,000

        
        String dob=newd.getDateOfBirth();
        int doby= Integer.parseInt(dob.substring(0, 3));
       
        int age= GetCurrentYear()- doby;

        String res="";
        
        if(cutoff_credit_score>score || age<18 || age>65 || emp<0.6 || salary<10000){
            if(cutoff_credit_score>score){
               
                res+="Your score is less than the cutoff credit score.";
            }
            if(age<18 || age>65){
                res+="Applicant’s age is <18 or >65.";
            }
            if(emp<0.6){
                res+="Applicant’s work experience is < 6 months.";
            }
            if(salary<10000){
                res+="Applicants annual salary is < $10,000.";
            }
            newd.setApplicationStatus("Declined");
            newd.setApplicationStatus(res);
         }
        else{
            newd.setApplicationStatus("Approved");
        }

        System.out.println("score: "+ score);


        loanRepo.save(newd);

        // return score;
    }
}
