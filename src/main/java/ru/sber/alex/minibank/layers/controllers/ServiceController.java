package ru.sber.alex.minibank.layers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.alex.minibank.dto.ClientDto;
import ru.sber.alex.minibank.layers.logic.BusinessLogic;

import java.util.Map;

@Controller
public class ServiceController {

    @Autowired
    private BusinessLogic businessLogic;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required = false, defaultValue = "World") String name, Map model){
        model.put("name", name);
        return "greeting";
    }
    //home page mapping
    @GetMapping("/")
    public String index() {
        return "index";
    }
    //get registration page mapping
    @GetMapping(name="/registration")
    public String registrationGet(Model model){
        model.addAttribute("client", new ClientDto());
        return "registration";
    }
    //push POST from data
    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute ClientDto client, Model model){
        String error = "Ошибка регистрации пользователя";
        model.addAttribute("name", error);
        if (businessLogic.registerAcc(client) != -1) {
            model.addAttribute("client", client);
            return "success";
        } else {
            return "error";
        }
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/personaloffice")
    public String persOffice(){
        return "personaloofice";
    }
}
