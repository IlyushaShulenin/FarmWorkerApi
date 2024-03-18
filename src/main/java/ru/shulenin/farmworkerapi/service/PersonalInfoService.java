package ru.shulenin.farmworkerapi.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.repository.PlanRepository;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.datasource.repository.ScoreRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.*;
import ru.shulenin.farmworkerapi.mapper.PlanMapper;
import ru.shulenin.farmworkerapi.mapper.ProductMapper;
import ru.shulenin.farmworkerapi.mapper.ScoreMapper;
import ru.shulenin.farmworkerapi.mapper.WorkerMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сервис для работы с данными рабочих
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PersonalInfoService {
    private final WorkerRepository workerRepository;
    private final ScoreRepository scoreRepository;
    private final PlanRepository planRepository;
    private final ProductRepository productRepository;

    private final EntityManagerFactory entityManagerFactory;

    private final ScoreMapper scoreMapper = ScoreMapper.INSTANCE;
    private final WorkerMapper workerMapper = WorkerMapper.INSTANCE;
    private final PlanMapper planMapper = PlanMapper.INSTANCE;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    /**
     * получение баллов рабочего
     * @param email email рабочего
     * @return баллы рабочего
     */
    public List<ScoreReadDto> getScore(String email) {
        return scoreRepository.findAllByWorkerEmail(email)
                .stream()
                .map(scr -> scoreMapper.scoreToScoreReadDto(scr, workerMapper))
                .toList();
    }

    /**
     * получение планов рабочего
     * @param email рабочего
     * @return планы рабочего
     */
    public List<PlanReadDto> getPlans(String email) {
        return planRepository.findAllNotCompleted(email)
                .stream()
                .map(pln -> planMapper.planToPlanReadDto(pln, workerMapper, productMapper))
                .toList();
    }

    /**
     * получение информации о продуктивности рабочего
     * @param email email рабочего
     * @return продуктивность рабочего
     */
    public List<ProductivityReport> getProductivityForWorker(String email) {
        if (!workerRepository.existsByEmail(email)) {
            log.warn(String.format("PersonalInfoService.getProductivityForWorker: entity with id=%s does not exist",
                    email));
            return Collections.emptyList();
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var queryResult = entityManager.createNativeQuery(
                        "SELECT t.email, t.name, SUM(t.r_amount), SUM(t.p_amount) FROM " +
                                "(SELECT w.email, prod.name, r.amount AS r_amount, p.amount AS p_amount " +
                                "FROM report AS r JOIN plan AS p " +
                                "ON r.worker_id = p.worker_id AND r.product_id = p.product_id AND p.date = r.date " +
                                "JOIN worker AS w ON r.worker_id = w.id " +
                                "JOIN product AS prod On r.product_id = prod.id) " +
                                "AS t " +
                                "GROUP BY t.email, t.name " +
                                "HAVING t.email = :id")
                .setParameter("id", email)
                .getResultList();

        return downcastToReport(queryResult);
    }

    /**
     * получение информации о продуктивности рабочего по месяцам
     * @param email email рабочего
     * @param month месяц
     * @return продуктивность рабочего
     */
    public List<ProductivityReport> getProductivityForWorkerByMonth(String email, Integer month) {
        if (!workerRepository.existsByEmail(email)) {
            log.warn(String.format("PersonalInfoService.getProductivityForWorker: entity with id=%s does not exist",
                    email));
            return Collections.emptyList();
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var queryResult = entityManager.createNativeQuery(
                        "SELECT t.email, t.name, SUM(t.r_amount), SUM(t.p_amount), t.r_date FROM " +
                                "(SELECT w.email, prod.name, r.amount AS r_amount, r.date AS r_date, p.amount AS p_amount " +
                                "FROM report AS r JOIN plan AS p " +
                                "ON r.worker_id = p.worker_id AND r.product_id = p.product_id AND p.date = r.date " +
                                "JOIN worker AS w ON r.worker_id = w.id " +
                                "JOIN product AS prod On r.product_id = prod.id) " +
                                "AS t " +
                                "GROUP BY t.email, t.name, t.r_date " +
                                "HAVING t.email = :id AND EXTRACT(MONTH from t.r_date) = :month")
                .setParameter("id", email)
                .setParameter("month", month)
                .getResultList();

        return downcastToReport(queryResult);
    }

    private List<ProductivityReport> downcastToReport(List queryResult) {
        List<ProductivityReport> productivity = new ArrayList<>();

        for (var row : queryResult) {
            var obj = (Object[]) row;

            var worker = (String) obj[0];
            var product = (String) obj[1];
            var reportAmount = (Double) obj[2];
            var planAmount = (Double) obj[3];

            if (obj.length == 5) {
                var date = (Date) obj[4];

                productivity.add(new ProductivityReportWithDate(
                        worker,
                        product,
                        reportAmount,
                        planAmount,
                        date.toLocalDate()
                ));
            }
            else {
                productivity.add(new CommonProductivityReport(
                        worker,
                        product,
                        reportAmount,
                        planAmount
                ));
            }
        }

        return productivity;
    }
}
