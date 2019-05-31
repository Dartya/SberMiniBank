package ru.sber.alex.minibank.layers.services;

import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.entities.OperationEntity;

import java.util.List;

public interface OperationService {

    List<OperationEntity> findByAccountsId(Integer id);
}
