package ru.sber.alex.minibank.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sber.alex.minibank.entities.OperationEntity;

public interface OperationRepo extends CrudRepository<OperationEntity, Long> {
}
