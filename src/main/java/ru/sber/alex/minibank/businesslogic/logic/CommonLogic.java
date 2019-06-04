package ru.sber.alex.minibank.businesslogic.logic;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
}
