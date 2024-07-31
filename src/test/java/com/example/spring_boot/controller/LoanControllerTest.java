package com.example.spring_boot.controller;

import com.example.spring_boot.model.LoanApplication;
import com.example.spring_boot.service.LoanApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanApplicationService loanService;

    @BeforeEach
    public void setup() {
        // Setup mock LoanApplication object
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationId(1L); // Ensure Long literals
        loanApplication.setFirstName("John");
        loanApplication.setLastName("Doe");
        loanApplication.setLoanAmount(5000.0);

        // Setup mocks for service methods
        Mockito.when(loanService.getAllIDs()).thenReturn(Collections.singletonList(loanApplication));
        Mockito.when(loanService.createApp(Mockito.any(LoanApplication.class))).thenAnswer(invocation -> {
            LoanApplication app = invocation.getArgument(0);
            app.setFirstName("Jane"); // Set the expected value for the test
            return app;
        });
        Mockito.when(loanService.getApplicationById(1L)).thenReturn(loanApplication);
    }

    @Test
    public void testGetAllIDs() throws Exception {
        mockMvc.perform(get("/phansbank/v1/viewapps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    public void testCreateApp() throws Exception {
        LoanApplication newLoanApplication = new LoanApplication();
        newLoanApplication.setFirstName("Jane");
        newLoanApplication.setLastName("Doe");
        newLoanApplication.setLoanAmount(7000.0);

        mockMvc.perform(post("/phansbank/v1/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newLoanApplication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Jane"))); // Verify the correct result
    }

    @Test
    public void testGetApplicationById() throws Exception {
        mockMvc.perform(get("/phansbank/v1/viewapps/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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

