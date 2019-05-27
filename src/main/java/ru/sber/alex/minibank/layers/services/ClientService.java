package ru.sber.alex.minibank.layers.services;

import ru.sber.alex.minibank.entities.ClientEntity;

public interface ClientService {

    ClientEntity getClient(String login);
}
