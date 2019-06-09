package ru.sber.alex.minibank.businesslogic.logic;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.math.BigDecimal;

/**
 * Класс общих функций приложения, не выделенных в отдельные группы.
 */
public class CommonLogic {

    /**
     * Запрашивает у Security логин текущего пользователя, выполняющего запрос.
     * @return String логин пользователя
     */
    public static String getCurrentLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    /**
     * Проверяет сумму на 0 или отрицательное значение. Если оно таковое - заполняет модель необходимыми данными
     * @param sum Сумма транзакции
     * @param model контейнер переменных страницы
     * @return true - есть ошибка, false - нет ошибки
     */
    public static boolean checkSum(BigDecimal sum, Model model){
        if (sum.doubleValue() <= 0.00) {
            String error = "Сумма должна быть больше 0!";
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", error);
            return true;
        } else
            return false;
    }
}
