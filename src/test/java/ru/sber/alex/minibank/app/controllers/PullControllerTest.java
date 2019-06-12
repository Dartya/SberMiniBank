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
public class PullControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Запрашивает страницу вывода средств. Выполняется каждый раз до очередного теста POST запроса.
     * @throws Exception
     */
    @Before
    public void pullMoneyGet() throws Exception {
        mockMvc.perform(get("/pull"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Вывод средств")));
    }

    /**
     * Проверяет правильность вывода суммы = 100 - перевод должен пройти.
     * @throws Exception
     */
    @Test
    public void pullMoneyPost() throws Exception {
        mockMvc.perform(post("/pull")
                .param("summ", "100")
                .param("accFrom", "1")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Средства выведены!")));
    }

    /**
     * Проверяет правильность вывода суммы = 100.57 - перевод должен пройти.
     * @throws Exception
     */
    @Test
    public void pullMoneyFractionPost() throws Exception {
        mockMvc.perform(post("/pull")
                .param("summ", "100.57")
                .param("accFrom", "1")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Средства выведены!")));
    }

    @Test
    public void pullMoneyWhenNotMoneyPost() throws Exception {
        mockMvc.perform(post("/pull")
                .param("summ", "300")
                .param("accFrom", "1")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ошибка проведения операции")));
    }

    /**
     * Проверяет правильность вывода суммы = 0 - должна вернуться ошибка.
     * @throws Exception
     */
    @Test
    public void pushZeroMoneyPost() throws Exception {
        mockMvc.perform(post("/pull")
                .param("summ", "0")
                .param("accFrom", "1")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Сумма должна быть больше 0!")));
    }

    /**
     * Проверяет правильность вывода суммы = -100 - должна вернуться ошибка.
     * @throws Exception
     */
    @Test
    public void pushMinusMoneyPost() throws Exception {
        mockMvc.perform(post("/pull")
                .param("summ", "-100")
                .param("accFrom", "1")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Сумма должна быть больше 0!")));
    }

    /**
     * Проверяет правильность вывода с несуществующего счета - должна вернуться ошибка.
     * @throws Exception
     */
    @Test
    public void pushMoneyBadAccountPost() throws Exception {
        mockMvc.perform(post("/pull")
                .param("summ", "100")
                .param("accFrom", "3")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Указанный счет не найден.")));
    }

    /**
     * Проверяет правильность вывода со счета, не принадлежащего клиенту - должна вернуться ошибка.
     * @throws Exception
     */
    @Test
    public void pushMoneyWrongAccountPost() throws Exception {
        mockMvc.perform(post("/pull")
                .param("summ", "100")
                .param("accFrom", "2")
                .flashAttr("TransactionDto", new TransactionDto())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ошибка проведения операции")));
    }
}