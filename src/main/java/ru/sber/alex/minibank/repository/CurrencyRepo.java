package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.CurrenciesEntity;

public interface CurrencyRepo extends JpaRepository<CurrenciesEntity, Long> {
}
