package ru.sber.alex.minibank.layers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.alex.minibank.dto.ClientDto;
import ru.sber.alex.minibank.dto.ClientOperationDto;
import ru.sber.alex.minibank.dto.OperationDto;
import ru.sber.alex.minibank.dto.TransactionDto;
import ru.sber.alex.minibank.entities.ClientEntity;
import ru.sber.alex.minibank.entities.OperationEntity;
import ru.sber.alex.minibank.layers.logic.BusinessLogic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Основной контроллер сервиса.
 */
@Controller
public class ServiceController {

    @Autowired
    private BusinessLogic businessLogic;

    /**
     * Запрашивает у Security логин текущего пользователя, выполняющего запрос.
     * @return String логин пользователя
     */
    private String getCurrentLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

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

    /**
     * Возвращает страницу личного кабинета, при этом заполняет ее данными о пользователе, его счетах и историей операций.
     * @param model контейнер параметров, принимает при вызове страницы, для дальнейшего их представления на странице шаблонизатором Thymeleaf.
     * @return либо страницу успешной операции, либо страницу ошибки.
     */
    @GetMapping("/personaloffice")
    public String persOffice(Model model){

        List<ClientOperationDto> clientOperationDtoList = businessLogic.getClientHistory(getCurrentLogin());

        ClientOperationDto clientOperationDto = clientOperationDtoList.get(0);
        ClientDto clientDto = clientOperationDto.getClientDto();
        List<OperationDto> history = clientOperationDto.getOperationDto();

        try {
            String name = clientDto.getName();
            String surname = clientDto.getSurname();
            String secondName = clientDto.getSecondName();
            Double deposit = clientDto.getAccountDtos().get(0).getDeposit().doubleValue();
            String currency = clientDto.getAccountDtos().get(0).getCurrency().getCurrencyCode();

            history.forEach(System.out::println);

            model.addAttribute("name", name);
            model.addAttribute("surname", surname);
            model.addAttribute("secondName", secondName);
            model.addAttribute("deposit", deposit);
            model.addAttribute("currency", currency);
            model.addAttribute("history", history);

            System.out.println("name = "+name
                    +", surname = "+surname
                    +", secondName = "+secondName
                    +", deposit = "+deposit
                    +", currency = "+currency);  //для проверки

            return "personaloffice";

        }catch (ClassCastException | NullPointerException e){
            e.printStackTrace();
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", "Ошибка получения истории операций.");
            return "error";
        }

    }

    /**
     * Возвращает страницу с формой пополнения счета.
     * @param model контейнер параметров, принимает при вызове страницы. В нее помещается пустой объект TransactionDto для дальнейшего его заполнения пользователем.
     * @return
     */
    @GetMapping("/push")
    public String pushMoneyGet(Model model){
        model.addAttribute("transaction", new TransactionDto());
        return "pushmoney";
    }

    /**
     * Инициирует цепочку логики добавления средств на счет.
     * @param transaction заполненный из формы TransactionDto, ранее переданный в нее методом pushMoneyGet().
     * @param model контейнер параметров. Заполняется методом, в зависимости от кода успешности операции будут выведены данные на соответствующих страницах.
     * @return либо страницу успешной операции, либо страницу ошибки.
     */
    @PostMapping("/push")
    public String pushMoneyPost(@ModelAttribute TransactionDto transaction, Model model){
        transaction.setLogin(getCurrentLogin());
        int result = businessLogic.makePush(transaction);
        if (result != -1){
            model.addAttribute("message", "Счет пополнен!");
            return "successtransaction";
        }else {
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", "Ошибка при внесении средств.");
            return "error";
        }
    }

    /**
     * Возвращает страницу с формой вывода средств со счета.
     * @param model контейнер параметров, принимает при вызове страницы. В нее помещается пустой объект TransactionDto для дальнейшего его заполнения пользователем.
     * @return
     */
    @GetMapping("/pull")
    public String pullMoneyGet(Model model){
        model.addAttribute("transaction", new TransactionDto());
        return "pullmoney";
    }

    /**
     * Инициирует цепочку логики вывода средств со счета.
     * @param transaction заполненный из формы TransactionDto, ранее переданный в нее методом pullMoneyGet().
     * @param model контейнер параметров. Заполняется методом, в зависимости от кода успешности операции будут выведены данные на соответствующих страницах.
     * @return либо страницу успешной операции, либо страницу ошибки.
     */
    @PostMapping("/pull")
    public String pullMoneyPost(@ModelAttribute TransactionDto transaction, Model model){
        transaction.setLogin(getCurrentLogin());
        if (businessLogic.makePull(transaction) != -1){
            model.addAttribute("message", "Средства выведены!");
            return "successtransaction";
        }else {
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", "Ошибка при выводе средств.");
            return "error";
        }
    }

    /**
     * Возвращает страницу с формой перевода средств со счета на счет.
     * @param model контейнер параметров, принимает при вызове страницы. В нее помещается пустой объект TransactionDto для дальнейшего его заполнения пользователем.
     * @return
     */
    @GetMapping("/transaction")
    public String transactionGet(Model model){
        model.addAttribute("transaction", new TransactionDto());
        return "transaction";
    }

    /**
     * Инициирует цепочку логики перевода средств со счета на другой счет.
     * @param transaction заполненный из формы TransactionDto, ранее переданный в нее методом transactionGet().
     * @param model контейнер параметров. Заполняется методом, в зависимости от кода успешности операции будут выведены данные на соответствующих страницах.
     * @return либо страницу успешной операции, либо страницу ошибки.
     */
    @PostMapping("/transaction")
    public String transactionPost(@ModelAttribute TransactionDto transaction, Model model){
        transaction.setLogin(getCurrentLogin());
        if (businessLogic.makeTransfer(transaction) != -1){
            model.addAttribute("message", "Перевод успешно проведен!");
            return "successtransaction";
        }else {
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", "Ошибка при переводе средств.");
            return "error";
        }
    }
}
