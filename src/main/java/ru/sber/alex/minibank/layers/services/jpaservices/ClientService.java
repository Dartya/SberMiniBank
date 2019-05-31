package ru.sber.alex.minibank.layers.services.jpaservices;

import ru.sber.alex.minibank.entities.ClientEntity;

public interface ClientService {

    ClientEntity getClient(String login);
}
