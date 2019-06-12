package ru.sber.alex.minibank.businesslogic.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CommonLogicTest {

    @Mock
    private Model model1, model2, model3;

    @Test
    @WithUserDetails(value = "alex", userDetailsServiceBeanName = "getUserDetailService")
    @Sql(value = { "/create-user-before.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = { "/create-user-after.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCurrentLoginTest(){
        String currLogin = CommonLogic.getCurrentLogin();

        assertEquals("alex", currLogin);
    }

    @Test
    public void checkSumTest() {
        boolean sum1 = CommonLogic.checkSum(new BigDecimal(300.00), model1);
        boolean sum2 = CommonLogic.checkSum(new BigDecimal(0), model2);
        boolean sum3 = CommonLogic.checkSum(new BigDecimal(-300.00), model3);

        assertFalse(sum1);
        assertTrue(sum2);
        assertTrue(sum3);
    }
}
