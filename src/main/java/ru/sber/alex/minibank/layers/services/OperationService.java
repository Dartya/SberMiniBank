package ru.sber.alex.minibank.layers.services;

import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.entities.OperationEntity;

public interface OperationService {

    OperationEntity getOperation(int id);
}
