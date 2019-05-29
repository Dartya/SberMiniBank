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
    @GetMapping("/error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "error";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
/*
    @GetMapping("/logout")
    public String logout(){
        return "index";
    }
*/
    @GetMapping("/personaloffice")
    public String persOffice(){
        return "personaloffice";
    }

}
