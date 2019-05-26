package ru.sber.alex.minibank.layers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ServiceController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
    }
    
    /*
    @GetMapping("/registration")
    public ModelAndView registration(){
        return null;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return null;
    }
*/
}
