package com.psi.calendar.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psi.calendar.api.filters.JwtRequestFilter;
import com.psi.calendar.api.models.dto.auth.AuthRequestDTO;
import com.psi.calendar.api.models.dto.auth.AuthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(this.jwtRequestFilter).build();
    }

    @Test
    public void itShouldReturnJwt() throws Exception {
        var requestBody = AuthRequestDTO.builder().username("foo").password("foo").build();
        var jsonRequestBody = mapper.writeValueAsString(requestBody);

        this.mockMvc.perform(
                post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(jsonRequestBody)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().string(containsString("jwt"))
                );
    }

    @Test
    public void itShouldReturn403statusCode_whenInvalidJwtIsSentUponProtectedRoute() throws Exception {
        this.mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
