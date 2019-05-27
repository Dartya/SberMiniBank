package ru.sber.alex.minibank.layers.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sber.alex.minibank.dto.ClientDto;
import ru.sber.alex.minibank.entities.ClientEntity;
import ru.sber.alex.minibank.layers.services.ClientServiceImpl;

@Service
public class BusinessLogic {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public int registerAcc(ClientDto client){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setLogin(client.getLogin());
        clientEntity.setPass(bCryptPasswordEncoder.encode(client.getPass()));
        clientEntity.setEmail(client.getEmail());
        clientEntity.setName(client.getName());
        clientEntity.setSurname(client.getSurname());
        clientEntity.setSecondName(client.getSecondName());
        return clientService.addClient(clientEntity);
    }
}
