package ru.shulenin.farmworkerapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.shulenin.farmworkerapi.service.PersonalInfoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonalInfoRestController.class)
class PersonalInfoRestControllerTest {
    @MockBean
    private PersonalInfoService service;
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void getScoreTest() throws Exception {
//        mockMvc.perform(get("/worker-api/v1/personal-info/1"))
//                .andExpect(jsonPath("$.workerId").value(1))
//                .andExpect(jsonPath("$.workerproduct").value(1))
//                .andExpect(status().isOk());
//    }
}