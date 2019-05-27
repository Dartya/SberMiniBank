package ru.sber.alex.minibank.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sber.alex.minibank.entities.CurrenciesEntity;

public interface CurrencyRepo extends CrudRepository<CurrenciesEntity, Long> {
}
