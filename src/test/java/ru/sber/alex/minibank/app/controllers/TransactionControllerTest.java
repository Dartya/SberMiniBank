package ru.sber.alex.minibank.app.controllers;

import org.junit.Before;
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
import ru.sber.alex.minibank.businesslogic.dto.TransactionDto;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "alex", userDetailsServiceBeanName = "getUserDetailService")
@TestPropertySource("/application-test.properties")
@Sql(value = { "/update-account-before.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = { "/create-user-after.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void transactionGet() throws Exception {
        mockMvc.perform(get("/transaction"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Перевод пользователю")));
    }

    @Test
    public void transactionPost() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("summ", "100")
                .param("accFrom", "1")
                .param("accTo", "2")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Перевод успешно проведен!")));
    }

    @Test
    public void transactionWrongAccountPost() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("summ", "100")
                .param("accFrom", "1")
                .param("accTo", "3")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Указанный счет адресата не найден.")));
    }

    @Test
    public void transactionBadAccountPost() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("summ", "100")
                .param("accFrom", "3")
                .param("accTo", "1")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Указанный счет отправителя не найден.")));
    }

    @Test
    public void transactionNotMyAccountPost() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("summ", "100")
                .param("accFrom", "2")
                .param("accTo", "1")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ошибка при переводе средств.")));
    }

    @Test
    public void transactionToMyselfPost() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("summ", "100")
                .param("accFrom", "1")
                .param("accTo", "1")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Нельзя сделать перевод между одним и тем же счетом!")));
    }

    @Test
    public void transactionZeroSummPost() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("summ", "0")
                .param("accFrom", "1")
                .param("accTo", "2")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Сумма должна быть больше 0!")));
    }

    @Test
    public void transactionNegativeSummPost() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("summ", "-100")
                .param("accFrom", "1")
                .param("accTo", "2")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Сумма должна быть больше 0!")));
    }

    @Test
    public void transactionBigSummPost() throws Exception {
        mockMvc.perform(post("/transaction")
                .param("summ", "300")
                .param("accFrom", "1")
                .param("accTo", "2")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ошибка при переводе средств.")));
    }
}