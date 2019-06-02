package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.DictOperationsEntity;

/**
 * Интерфейс - JpaRepository сущности БД "Название операции"
 */
public interface DictOperationsRepo extends JpaRepository<DictOperationsEntity, Long> {
}
