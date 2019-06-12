package ru.sber.alex.minibank.businesslogic.logic;

import org.junit.Before;
import org.junit.Test;
import ru.sber.alex.minibank.businesslogic.dto.ClientDto;
import ru.sber.alex.minibank.data.entities.AccountEntity;
import ru.sber.alex.minibank.data.entities.ClientEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Класс с unit-тестами класса TransferConverter
 * Здесь описан только один тест одного метода, остальные методы делются по аналогии, и я не хочу их писать в этом
 * учебном проекте, тем более, что методы неоднократно проверены при функциональном тестирвании и не явно покрыты
 * интеграционными тестами в полной мере.
 */
public class TransferConverterTest {

    List<AccountEntity> accountEntities = new ArrayList<>();

    @Before
    public void createAccountEntities(){
        int x = 10;

        for (int i = 0; i < x; i++) {
            accountEntities.add(new AccountEntity(i, 1, new BigDecimal(100.00)));
            accountEntities.get(i).setId(i);
        }
    }

    @Test
    public void clientEntityToDto() {
        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setId(1);
        clientEntity.setLogin("alex");
        clientEntity.setPass("123");
        clientEntity.setEmail("alexpit63@gmail.com");
        clientEntity.setName("Alex");
        clientEntity.setSurname("Pitasov");
        clientEntity.setSecondName("Vladimirovich");

        ClientDto clientDto = TransferConverter.clientEntityToDto(clientEntity);

        assertNotNull(clientDto);
        assertEquals(clientEntity.getId(), clientDto.getId());
        assertEquals(clientEntity.getLogin(), clientDto.getLogin());
        assertEquals(clientEntity.getPass(), clientDto.getPass());
        assertEquals(clientEntity.getEmail(), clientDto.getEmail());
        assertEquals(clientDto.getName(), clientDto.getName());
        assertEquals(clientDto.getSurname(), clientDto.getSurname());
        assertEquals(clientDto.getSecondName(), clientDto.getSecondName());

        for (int i = 0; i < clientDto.getAccountDtos().size(); i++) {
            assertEquals(clientDto.getAccountDtos().get(i).getId(), accountEntities.get(i).getId());
            assertEquals(clientDto.getAccountDtos().get(i).getClientId(), accountEntities.get(i).getClientId());
            assertEquals(clientDto.getAccountDtos().get(i).getCurrencyID(), accountEntities.get(i).getCurrencyID());
            assertEquals(clientDto.getAccountDtos().get(i).getCurrency(), accountEntities.get(i).getCurrency());
            assertEquals(clientDto.getAccountDtos().get(i).getDeposit(), accountEntities.get(i).getDeposit());
        }
    }
}