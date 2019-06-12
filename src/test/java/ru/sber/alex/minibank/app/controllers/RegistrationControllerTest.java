package ru.sber.alex.minibank.app.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.sber.alex.minibank.businesslogic.dto.ClientDto;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = { "/create-user-after.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void registrationGetTest() throws Exception {
        mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Регистрация нового пользователя")));
    }

    @Test
    @Sql(value = { "/create-user-after.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void registrationPostTest() throws Exception {
        mockMvc.perform(post("/registration")
                .param("login", "alex")
                .param("pass", "123")
                .param("email", "alexpit63@gmail.com")
                .param("name", "alex")
                .param("surname", "pit")
                .param("secondName", "secName")
                .flashAttr("ClientDto", new ClientDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Успешная регистрация!")));
    }

    @Test
    @Sql(value = { "/create-user-after.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void registrationWithoutSecondNamePostTest() throws Exception {
        mockMvc.perform(post("/registration")
                .param("login", "alex")
                .param("pass", "123")
                .param("email", "alexpit63@gmail.com")
                .param("name", "alex")
                .param("surname", "pit")
                .flashAttr("ClientDto", new ClientDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Успешная регистрация!")));
    }

    @Test
    @Sql(value = { "/update-account-before.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void registrationUserWithSuchLoginPostTest() throws Exception {
        mockMvc.perform(post("/registration")
                .param("login", "alex")
                .param("pass", "123")
                .param("email", "alexpit63@gmail.com")
                .param("name", "alex")
                .param("surname", "pit")
                .flashAttr("ClientDto", new ClientDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Пользователь с указанным логином существует")));
    }

    @Test
    @Sql(value = { "/update-account-before.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void registrationUserWithSuchEmailPostTest() throws Exception {
        mockMvc.perform(post("/registration")
                .param("login", "alex123")
                .param("pass", "123")
                .param("email", "alexpit63@gmail.com")
                .param("name", "alex")
                .param("surname", "pit")
                .flashAttr("ClientDto", new ClientDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Пользователь с указанным E-mail существует")));
    }

    @Test
    @Sql(value = { "/create-user-after.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void registrationWithAnyNullParamPostTest() throws Exception {
        mockMvc.perform(post("/registration")
                .param("pass", "123")
                .param("email", "alexpit63@gmail.com")
                .param("name", "alex")
                .param("surname", "pit")
                .param("secondName", "secName")
                .flashAttr("ClientDto", new ClientDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}