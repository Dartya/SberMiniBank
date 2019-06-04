package ru.sber.alex.minibank.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sber.alex.minibank.businesslogic.dto.TransactionDto;
import ru.sber.alex.minibank.businesslogic.logic.CommonLogic;
import ru.sber.alex.minibank.businesslogic.services.TransactionService;

/**
 * Контроллер страницы перевода средств другому клиенту
 */
@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

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
        transaction.setLogin(CommonLogic.getCurrentLogin());
        if (transactionService.makeTransfer(transaction) != -1){
            model.addAttribute("message", "Перевод успешно проведен!");
            return "successtransaction";
        }else {
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", "Ошибка при переводе средств.");
            return "error";
        }
    }
}
