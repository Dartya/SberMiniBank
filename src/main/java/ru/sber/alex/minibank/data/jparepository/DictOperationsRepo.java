package ru.sber.alex.minibank.data.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.data.entities.DictOperationsEntity;

/**
 * Интерфейс - JpaRepository сущности БД "Название операции"
 */
public interface DictOperationsRepo extends JpaRepository<DictOperationsEntity, Long> {
}
