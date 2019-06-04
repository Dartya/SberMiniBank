package ru.sber.alex.minibank.layers.services.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.CurrenciesEntity;

/**
 * Интерфейс - JpaRepository сущности БД "Валюта"
 */
public interface CurrencyRepo extends JpaRepository<CurrenciesEntity, Long> {
}
