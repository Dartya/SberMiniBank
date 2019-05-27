package ru.sber.alex.minibank.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sber.alex.minibank.entities.DictOperationsEntity;

public interface DictOperationsRepo extends CrudRepository<DictOperationsEntity, Long> {
}
