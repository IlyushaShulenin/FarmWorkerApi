package ru.shulenin.farmworkerapi.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shulenin.farmworkerapi.datasource.entity.Score;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM score WHERE worker_id = :id"
    )
    public List<Score> findAllByWorkerId(@Param("id") Long id);

    public void deleteAllByWorkerId(Long workerId);

    public List<Score> findAllByWorkerEmail(String email);
}
