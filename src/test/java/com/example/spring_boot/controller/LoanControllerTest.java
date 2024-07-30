package com.example.spring_boot.controller;

import com.example.spring_boot.model.LoanApplication;
import com.example.spring_boot.repository.LoanApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanApplicationRepository loanRepo;

    @BeforeEach
    public void setup() {
        loanRepo.deleteAll();

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setFirstName("John");
        loanApplication.setLastName("Doe");
        loanApplication.setLoanAmount(5000.0);
        loanRepo.save(loanApplication);
    }

    @Test
    public void testGetAllIDs() throws Exception {
        mockMvc.perform(get("/phansbank/v1/viewapps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    public void testCreateApp() throws Exception {
        LoanApplication newLoanApplication = new LoanApplication();
        newLoanApplication.setFirstName("Jane");
        newLoanApplication.setLastName("Doe");
        newLoanApplication.setLoanAmount(7000.0);

        mockMvc.perform(post("/phansbank/v1/submit")
                .contentType("application/json")
                .content(asJsonString(newLoanApplication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Jane")));
    }

    @Test
    public void testGetApplicationById() throws Exception {
        LoanApplication savedApplication = loanRepo.findAll().get(0);

        mockMvc.perform(get("/phansbank/v1/viewapplication/" + savedApplication.getApplicationId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

