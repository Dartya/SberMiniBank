package ru.sber.alex.minibank.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sber.alex.minibank.businesslogic.dto.TransactionDto;
import ru.sber.alex.minibank.businesslogic.logic.CommonLogic;
import ru.sber.alex.minibank.businesslogic.services.PullService;

/**
 * Контроллер страницы вывода средств
 */
@Controller
public class PullController {

    @Autowired
    private PullService pullService;

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
        transaction.setLogin(CommonLogic.getCurrentLogin());
        if (pullService.makePull(transaction) != -1){
            model.addAttribute("message", "Средства выведены!");
            return "successtransaction";
        }else {
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", "Ошибка при выводе средств.");
            return "error";
        }
    }
}
