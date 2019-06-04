package ru.sber.alex.minibank.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Основной контроллер приложения.
 */
@Controller
public class MainController {
    /**
     * Возвращает главную страницу.
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Возвращает страницу ошибки.
     * @return
     */
    @GetMapping("/error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "error";
    }

    /**
     * Возвращает страницу авторизации.
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
