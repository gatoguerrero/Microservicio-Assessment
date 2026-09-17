package com.empresa.devops.controller;

import com.empresa.devops.dto.DevOpsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DevOpsController.class)
class DevOpsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String API_KEY =
            "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c";

    @Test
    void shouldReturn200WhenRequestIsValid() throws Exception {

        String json = """
                {
                    "message": "This is a test",
                    "to": "Juan Perez",
                    "from": "Rita Asturia",
                    "timeToLifeSec": 45
                }
                """;

        mockMvc.perform(post("/DevOps")
                        .header("X-Parse-REST-API-Key", API_KEY)
                        .header("X-JWT-KWY", "test-jwt-001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"message\":\"Hello Juan Perez your message will be send\"}"
                ));
    }

    @Test
    void shouldReturn401WhenApiKeyIsInvalid() throws Exception {

        String json = """
                {
                    "message": "This is a test",
                    "to": "Juan Perez",
                    "from": "Rita Asturia",
                    "timeToLifeSec": 45
                }
                """;

        mockMvc.perform(post("/DevOps")
                        .header("X-Parse-REST-API-Key", "incorrecta")
                        .header("X-JWT-KWY", "test-jwt-001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("ERROR"));
    }

    @Test
    void shouldReturn401WhenJwtIsMissing() throws Exception {

        String json = """
                {
                    "message": "This is a test",
                    "to": "Juan Perez",
                    "from": "Rita Asturia",
                    "timeToLifeSec": 45
                }
                """;

        mockMvc.perform(post("/DevOps")
                        .header("X-Parse-REST-API-Key", API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("ERROR"));
    }

    @Test
    void shouldReturn400WhenBodyIsIncomplete() throws Exception {

        String json = """
                {
                    "message": "This is a test"
                }
                """;

        mockMvc.perform(post("/DevOps")
                        .header("X-Parse-REST-API-Key", API_KEY)
                        .header("X-JWT-KWY", "test-jwt-001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ERROR"));
    }

    @Test
    void shouldReturn405WhenUsingGet() throws Exception {

        mockMvc.perform(get("/DevOps"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string("ERROR"));
    }
}

