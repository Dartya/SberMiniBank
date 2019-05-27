package ru.sber.alex.minibank.layers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.alex.minibank.dto.ClientDto;

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
    //get registration page mapping
    @GetMapping(name="/registration")
    public String registrationGet(Model model){
        model.addAttribute("client", new ClientDto());
        return "registration";
    }
    //push POST form data
    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute ClientDto client, Model model){
        model.addAttribute("client", client);
        return "success";
    }
/*
    @GetMapping("/login")
    public ModelAndView login(){
        return null;
    }
*/
}
