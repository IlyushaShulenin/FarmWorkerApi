package ru.shulenin.farmworkerapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.shulenin.farmworkerapi.TestBase;
import ru.shulenin.farmworkerapi.annotation.IntegrationTest;
import ru.shulenin.farmworkerapi.controller.adapter.LocalDateTypeAdapter;
import ru.shulenin.farmworkerapi.service.PersonalInfoService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class PersonalInfoRestControllerTest extends TestBase {
    private final static String APP_NAME = "/worker-api/v1/personal-info/";
    private final static String USERNAME = "jhon@mai.com";
    private final static String PASSWORD = "123";

    private final PersonalInfoService service;
    private final MockMvc mockMvc;
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    private String getUri(String path) {
        return APP_NAME + path;
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    void getScore() throws Exception {
        var scores = gson.toJson(service.getScore(USERNAME));

        mockMvc.perform(get(getUri("score")))
                .andExpect(status().isOk())
                .andExpect(content().json(scores));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    void getPlans() throws Exception {
        var plans = gson.toJson(service.getPlans(USERNAME));

        mockMvc.perform(get(getUri("plan")))
                .andExpect(status().isOk())
                .andExpect(content().json(plans));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    void getProductivity() throws Exception {
        var productivity = gson.toJson(service.getProductivityForWorker(USERNAME));

        mockMvc.perform(get(getUri("productivity")))
                .andExpect(status().isOk())
                .andExpect(content().json(productivity));
    }
}