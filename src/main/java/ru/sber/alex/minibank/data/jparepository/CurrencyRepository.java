package ru.sber.alex.minibank.data.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.data.entities.CurrenciesEntity;

/**
 * Интерфейс - JpaRepository сущности БД "Валюта"
 */
public interface CurrencyRepository extends JpaRepository<CurrenciesEntity, Long> {
}
