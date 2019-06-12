package ru.sber.alex.minibank.app.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Првоеряется возврат главной страницы при ее запросе
     * @throws Exception
     */
    @Test
    public void indexTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Добро пожаловать!")));
    }

    /**
     * Проверяется возврат страницы авторизации при ее запросе;
     * проверяется правильность редиректа на главную страницу после ввода правильной пары логин:пароль;
     * проверяется работоспособность POST-запроса /logout
     * @throws Exception
     */
    @Test
    public void loginCorrectTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Авторизация")));

        mockMvc.perform(formLogin().user("alex").password("123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated());

        mockMvc.perform(post("/logout"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * Проверяются редиректы со страниц push, pull, transaction, если пользователь не авторизован.
     * Должен произойти редирект на страницу авторизации.
     * @throws Exception
     */
    @Test
    public void loginRedirectTest() throws Exception {
        mockMvc.perform(get("/push"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        mockMvc.perform(get("/pull"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        mockMvc.perform(get("/transaction"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    /**
     * Проверяется неверные пары логин и пароль в разных вариантах ввода
     * @throws Exception
     */
    @Test
    public void loginErrorTest() throws Exception {
        mockMvc.perform(formLogin().user("alex").password("1231"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));

        mockMvc.perform(post("/login").param("username", "alex!"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
