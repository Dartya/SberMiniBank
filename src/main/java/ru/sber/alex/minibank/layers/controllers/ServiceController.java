package ru.sber.alex.minibank.layers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.alex.minibank.dto.TransactionDto;
import ru.sber.alex.minibank.entities.ClientEntity;
import ru.sber.alex.minibank.entities.OperationEntity;
import ru.sber.alex.minibank.layers.logic.BusinessLogic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class ServiceController {

    @Autowired
    private BusinessLogic businessLogic;

    private String getCurrentLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

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
    public String persOffice(Model model){

        Map<ClientEntity, List<OperationEntity>> map = businessLogic.getClientHistory(getCurrentLogin());

        ClientEntity clientEntity;
        List<OperationEntity> history;

        try {
            Iterator<Map.Entry<ClientEntity, List<OperationEntity>>> itr = map.entrySet().iterator();
            Map.Entry<ClientEntity, List<OperationEntity>> entry = itr.next();

            clientEntity = entry.getKey();
            String name = clientEntity.getName();
            String surname = clientEntity.getSurname();
            String secondName = clientEntity.getSecondName();
            Double deposit = clientEntity.getAccounts().get(0).getDeposit().doubleValue();
            String currency = clientEntity.getAccounts().get(0).getCurrency().getCurrencyCode();

            history = entry.getValue();
            history.forEach(System.out::println);

            model.addAttribute("name", name);
            model.addAttribute("surname", surname);
            model.addAttribute("secondName", secondName);
            model.addAttribute("deposit", deposit);
            model.addAttribute("currency", currency);
            model.addAttribute("history", history);

            System.out.println("name = "+name+", surname = "+surname+", secondName = "+secondName+", deposit = "+deposit+", currency = "+currency);  //для проверки
            return "personaloffice";

        }catch (ClassCastException | NullPointerException e){
            e.printStackTrace();
            return "error";
        }

    }

    @GetMapping("/push")
    public String pushMoneyGet(Model model){
        model.addAttribute("transaction", new TransactionDto());
        return "pushmoney";
    }

    @PostMapping("/push")
    public String pushMoneyPost(@ModelAttribute TransactionDto transaction, Model model){
        transaction.setLogin(getCurrentLogin());
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
        transaction.setLogin(getCurrentLogin());
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
        transaction.setLogin(getCurrentLogin());
        if (businessLogic.makeTransfer(transaction) != -1){
            model.addAttribute("message", "Перевод успешно проведен!");
            return "successtransaction";
        }else {
            model.addAttribute("errorMessage", "Ошибка при переводе средств.");
            return "error";
        }
    }
}
