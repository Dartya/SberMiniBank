package ru.sber.alex.minibank.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sber.alex.minibank.businesslogic.dto.ClientDto;
import ru.sber.alex.minibank.businesslogic.dto.ClientOperationDto;
import ru.sber.alex.minibank.businesslogic.dto.OperationDto;
import ru.sber.alex.minibank.businesslogic.logic.CommonLogic;
import ru.sber.alex.minibank.businesslogic.services.ClientService;

import java.util.List;

/**
 * Контроллер работы с клиентами.
 */
@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Возвращает страницу личного кабинета, при этом заполняет ее данными о пользователе, его счетах и историей операций.
     * @param model контейнер параметров, принимает при вызове страницы, для дальнейшего их представления на странице шаблонизатором Thymeleaf.
     * @return либо страницу успешной операции, либо страницу ошибки.
     */
    @GetMapping("/personaloffice")
    public String persOffice(Model model){

        List<ClientOperationDto> clientOperationDtoList = clientService.getClientHistory(CommonLogic.getCurrentLogin());

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
}
