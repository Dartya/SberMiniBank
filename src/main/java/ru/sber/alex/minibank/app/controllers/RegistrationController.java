package ru.sber.alex.minibank.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sber.alex.minibank.businesslogic.dto.ClientDto;
import ru.sber.alex.minibank.businesslogic.services.RegistrationService;

/**
 * Контроллер регистрации пользователей.
 */
@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String registrationGet(Model model){
        model.addAttribute("client", new ClientDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute ClientDto client, Model model) {
        String error = "Ошибка регистрации пользователя";
        String errorLogin = "Пользователь с указанным логином существует";
        String errorEmail = "Пользователь с указанным E-mail существует";

        if (client.getName().equals("")) {
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", error);
            return "error";
        }

        switch (registrationService.registerAcc(client)){
            case 1:
                model.addAttribute("client", client);
                return "success";
            case -1:
                model.addAttribute("errorMessage", error);
                break;
            case -2:
                model.addAttribute("errorMessage", errorLogin);
                break;
            case -3:
                model.addAttribute("errorMessage", errorEmail);
                break;
        }

        model.addAttribute("userError", true);
        return "error";
    }
}
