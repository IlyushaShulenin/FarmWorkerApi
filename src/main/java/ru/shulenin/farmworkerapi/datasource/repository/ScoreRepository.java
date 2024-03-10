package ru.shulenin.farmworkerapi.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shulenin.farmworkerapi.datasource.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
