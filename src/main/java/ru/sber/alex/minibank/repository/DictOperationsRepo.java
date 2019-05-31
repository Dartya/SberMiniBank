package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.DictOperationsEntity;

public interface DictOperationsRepo extends JpaRepository<DictOperationsEntity, Long> {
}
