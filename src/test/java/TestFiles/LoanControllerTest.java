//package TestFiles;
//
//import com.example.spring_boot.Application;
//import com.example.spring_boot.model.LoanApplication;
//import com.example.spring_boot.repository.LoanApplicationRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
////import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = Application.class)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class LoanControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private LoanApplicationRepository loanRepo;
//
//    private LoanApplication loanApplication;
//
//    @BeforeEach
//    void insertData() {
//        loanRepo.deleteAll(); // Clean the database before each test
//
//        loanApplication = new LoanApplication();
//        loanApplication.setFirstName("Priyanshi");
//        loanApplication.setLastName("Nama");
//        loanApplication.setDateOfBirth("2001-05-04");
//        loanApplication.setMaritalStatus("Single");
//        loanApplication.setAddressLine1("123");
//        loanApplication.setCity("Bangalore");
//        loanApplication.setState("Karnataka");
//        loanApplication.setPostalCode("12345");
//        loanApplication.setSsnNumber("33333");
//        loanApplication.setPhoneMobile("1234567809");
//        loanApplication.setEmailAddress("Priyanshi@gmail.com");
//        loanApplication.setLoanAmount(3000.0);
//        loanApplication.setLoanPurpose("Personal Loan");
//        loanApplication.setWorkExperienceYears(1);
//        loanApplication.setWorkExperienceMonths(1);
//        loanApplication.setAnnualSalary(1200000.0);
//        loanApplication.setEmployerName("Fico");
//        loanApplication.setEmployerAddressLine1("Opp to Leela Palace");
//        loanApplication.setEmployerCity("Bangalore");
//        loanApplication.setEmployerState("Karnataka");
//        loanApplication.setEmployerPostalCode("12345");
//
//        loanApplication = loanRepo.save(loanApplication);
//    }
//
//    @Test
//    void testGetAllIDs() throws Exception {
//        mockMvc.perform(get("/phansbank/v1/viewapps"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].firstName").value("Priyanshi"))
//                .andExpect(jsonPath("$[0].lastName").value("Nama"));
//    }
//
//    @Test
//    void testCreateApp() throws Exception {
//        mockMvc.perform(post("/phansbank/v1/submit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\":\"Sans\",\"lastName\":\"Shri\",\"dateOfBirth\":\"2001-01-01\",\"maritalStatus\":\"Single\",\"addressLine1\":\"Kodihalli\",\"postalCode\":\"12333\",\"loanAmount\":2000.0,\"ssnNumber\": \"18976\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("Sans"))
//                .andExpect(jsonPath("$.lastName").value("Shri"));
//    }
//
//    @Test
//    void testGetApplicationById() throws Exception {
//        Integer id = loanApplication.getApplicationId();
//
//        mockMvc.perform(get("/phansbank/v1/viewapps/" + id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("Sans"))
//                .andExpect(jsonPath("$.lastName").value("Shri"));
//    }
//
//    @Test
//    void testCreateAppWithInvalidData() throws Exception {
//        mockMvc.perform(post("/phansbank/v1/submit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\":\"\",\"lastName\":\"\",\"dateOfBirth\":\"\",\"maritalStatus\":\"\",\"addressLine1\":\"\",\"postalCode\":\"\",\"loanAmount\":-1000.00}"))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void testGetApplicationByNonExistentId() throws Exception {
//        mockMvc.perform(get("/phansbank/v1/viewapps/33333"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testGetAllIDsWhenEmpty() throws Exception {
//        //loanRepo.deleteAll();
//
//        mockMvc.perform(get("/phansbank/v1/viewapps"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());
//    }
//
//}
