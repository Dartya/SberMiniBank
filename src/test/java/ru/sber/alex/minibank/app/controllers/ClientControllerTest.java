package ru.sber.alex.minibank.app.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "alex", userDetailsServiceBeanName = "getUserDetailService")
@TestPropertySource("/application-test.properties")
@Sql(value = { "/get-account-history-before.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = { "/create-user-after.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void persOffice() throws Exception {
        mockMvc.perform(get("/personaloffice"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Информация о клиенте")))
                .andExpect(content().string(containsString("История операций")))
                .andExpect(content().string(containsString("qwe")))
                .andExpect(content().string(containsString("2019-06-02 22:51:00.0")))
                .andExpect(content().string(containsString("2019-06-02 22:51:14.0")));
    }
}