package ru.shulenin.farmworkerapi.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shulenin.farmworkerapi.datasource.entity.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    public Optional<Plan> findByWorkerIdAndProductId(Long workerId, Long productId);

    public List<Plan> findAllByWorkerId(Long workerId);

    public List<Plan> findAllByWorkerEmail(String email);

    @Query(
            value = "SELECT DISTINCT p.id, p.worker_id, p.product_id, p.amount, p.date FROM plan AS p " +
                    "JOIN report AS r ON p.worker_id = r.worker_id AND p.date = r.date AND p.product_id = r.product_id " +
                    "JOIN worker AS w ON p.worker_id = w.id " +
                    "WHERE r.plan_is_completed = false AND w.email = :email",
            nativeQuery = true
    )
    public List<Plan> findAllNotCompleted(@Param("email") String email);
}
