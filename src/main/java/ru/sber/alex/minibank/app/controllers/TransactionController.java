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

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
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
        if (CommonLogic.checkSum(transaction.getSumm(), model)) return "error";
        if (transaction.getAccFrom() == transaction.getAccTo()){
            model.addAttribute("userError", true);
            model.addAttribute("errorMessage", "Нельзя сделать перевод между одним и тем же счетом!");
            return "error";
        }

        transaction.setLogin(CommonLogic.getCurrentLogin());
        int result = transactionService.makeTransfer(transaction);
        switch (result) {
            case 1:
                model.addAttribute("message", "Перевод успешно проведен!");
                return "successtransaction";
            case -1:
                model.addAttribute("errorMessage", "Ошибка при переводе средств.");
                break;
            case -2:
                model.addAttribute("errorMessage", "Указанный счет отправителя не найден.");
                break;
            case -3:
                model.addAttribute("errorMessage", "Указанный счет адресата не найден.");
                break;
        }
        model.addAttribute("userError", true);
        return "error";
    }
}
