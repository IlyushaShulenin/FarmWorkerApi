package ru.shulenin.farmworkerapi.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shulenin.farmworkerapi.datasource.entity.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    public Optional<Plan> findByWorkerIdAndProductId(Long workerId, Long productId);

    public List<Plan> findAllByWorkerId(Long workerId);

    public List<Plan> findAllByWorkerEmail(String email);
}
