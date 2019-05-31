package ru.sber.alex.minibank.layers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.alex.minibank.dto.TransactionDto;
import ru.sber.alex.minibank.layers.logic.BusinessLogic;

@Controller
public class ServiceController {

    @Autowired
    private BusinessLogic businessLogic;

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

    @GetMapping("/personaloffice")
    public String persOffice(){
        return "personaloffice";
    }

    @GetMapping("/push")
    public String pushMoneyGet(Model model){
        model.addAttribute("transaction", new TransactionDto());
        return "pushmoney";
    }

    @PostMapping("/push")
    public String pushMoneyPost(@ModelAttribute TransactionDto transaction, Model model){
        int result = businessLogic.makePush(transaction);
        if (result != -1){
            model.addAttribute("message", "Счет пополнен!");
            return "successtransaction";
        }else {
            model.addAttribute("errorMessage", "Ошибка при внесении средств.");
            return "error";
        }
    }

    @GetMapping("/pull")
    public String pullMoneyGet(Model model){
        model.addAttribute("transaction", new TransactionDto());
        return "pullmoney";
    }

    @PostMapping("/pull")
    public String pullMoneyPost(@ModelAttribute TransactionDto transaction, Model model){
        if (businessLogic.makePull(transaction) != -1){
            model.addAttribute("message", "Средства выведены!");
            return "successtransaction";
        }else {
            model.addAttribute("errorMessage", "Ошибка при выводе средств.");
            return "error";
        }
    }

    @GetMapping("/transaction")
    public String transactionGet(Model model){
        model.addAttribute("transaction", new TransactionDto());
        return "transaction";
    }

    @PostMapping("/transaction")
    public String transactionPost(@ModelAttribute TransactionDto transaction, Model model){
        if (businessLogic.makeTransfer(transaction) != -1){
            model.addAttribute("message", "Перевод успешно проведен!");
            return "successtransaction";
        }else {
            model.addAttribute("errorMessage", "Ошибка при переводе средств.");
            return "error";
        }
    }
}
