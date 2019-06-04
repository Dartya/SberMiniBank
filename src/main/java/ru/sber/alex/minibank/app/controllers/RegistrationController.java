package ru.sber.alex.minibank.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/registration")
    public String registrationGet(Model model){
        model.addAttribute("client", new ClientDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute ClientDto client, Model model) {
        if (client.getName().equals("")) {
            String error = "Ошибка регистрации пользователя";
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", error);
            return "error";
        }
        if (registrationService.registerAcc(client) != -1) {
            model.addAttribute("client", client);
            return "success";
        } else {
            model.addAttribute("userError", true);
            return "error";
        }
    }
}
