package com.yandex.ajwar.kameleoon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandex.ajwar.kameleoon.dtos.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = KameleoonApplication.class)
class KameleoonApplicationTests {

    // TODO I was too lazy to write tests for all other controllers, all by analogy :)

    @Autowired
    private MockMvc mvc;

    @Autowired
    protected ObjectMapper jsonMapper;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @BeforeEach
    void setUp() {
        assertThat(contextPath).isNotBlank();
        ((MockServletContext) mvc.getDispatcherServlet().getServletContext()).setContextPath(contextPath);
    }

    @Test
    public void createUserThenStatus200() throws Exception {
        final var userDto = new UserDto("ajwarcat@yandex.ru");
        mvc.perform(MockMvcRequestBuilders.post(contextPath + "/v1/users")
                        .contextPath(contextPath)
                        .content(toJson(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    public <T> String toJson(T t) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(t);
    }

    public <T> T jsonToObj(String json, Class<T> t) throws IOException {
        return jsonMapper.readValue(json, t);
    }
}
