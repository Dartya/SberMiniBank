package ru.sber.alex.minibank.layers.services.jpaservices;

import ru.sber.alex.minibank.entities.AccountEntity;

public interface AccountService {

    AccountEntity getById(int id);
}
