package ru.shulenin.farmworkerapi.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shulenin.farmworkerapi.FarmWorkerApiApplicationTest;
import ru.shulenin.farmworkerapi.TestBase;
import ru.shulenin.farmworkerapi.annotation.IntegrationTest;
import ru.shulenin.farmworkerapi.dto.SignInRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@RequiredArgsConstructor
@AutoConfigureMockMvc
class AuthRestControllerTest extends TestBase {
    private final static String APP_NAME = "/auth/sign-in";
    private final static String RIGHT_USERNAME = "jack@mail.com";
    private final static String WRONG_USERNAME = "unregistered@mail.com";
    private final static String PASSWORD = "123";

    private final MockMvc mockMvc;
    private Gson gson = new Gson();

    @Contract(pure = true)
    private String getUri(String path) {
        return APP_NAME + path;
    }

    @Test
    //@WithMockUser(username = RIGHT_USERNAME, password = PASSWORD)
    void signIn() throws Exception {
        var request = new SignInRequest(
                RIGHT_USERNAME,
                PASSWORD
        );
        var json = gson.toJson(request);

        mockMvc.perform(post(APP_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
   // @WithMockUser(username = WRONG_USERNAME, password = PASSWORD)
    void wrongSignIn() throws Exception {
        var request = new SignInRequest(
            WRONG_USERNAME,
            PASSWORD
        );

        mockMvc.perform(post(APP_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isForbidden());
    }
}