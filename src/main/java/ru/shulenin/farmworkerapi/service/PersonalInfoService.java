package ru.shulenin.farmworkerapi.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.redis.repository.PlanRedisRepository;
import ru.shulenin.farmworkerapi.datasource.redis.repository.ScoreRedisRepository;
import ru.shulenin.farmworkerapi.datasource.repository.PlanRepository;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.datasource.repository.ScoreRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.PlanReadDto;
import ru.shulenin.farmworkerapi.dto.ProductivityReport;
import ru.shulenin.farmworkerapi.dto.ScoreReadDto;
import ru.shulenin.farmworkerapi.exception.exception.ThereAreNoEntities;
import ru.shulenin.farmworkerapi.mapper.PlanMapper;
import ru.shulenin.farmworkerapi.mapper.ProductMapper;
import ru.shulenin.farmworkerapi.mapper.ScoreMapper;
import ru.shulenin.farmworkerapi.mapper.WorkerMapper;

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

    private final ScoreRedisRepository scoreRedisRepository;
    private final PlanRedisRepository planRedisRepository;

    private final EntityManagerFactory entityManagerFactory;

    private final ScoreMapper scoreMapper = ScoreMapper.INSTANCE;
    private final WorkerMapper workerMapper = WorkerMapper.INSTANCE;
    private final PlanMapper planMapper = PlanMapper.INSTANCE;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    /**
     * Очистка кэша
     */
    @PostConstruct
    public void init() {
        scoreRedisRepository.clear();
    }

    /**
     * получение баллов рабочего
     * @param workerId id рабочего
     * @return баллы рабочего
     */
    public List<ScoreReadDto> getScore(Long workerId) {
        if (scoreRedisRepository.isEmpty(workerId)) {
            var scores = scoreRepository.findAllByWorkerId(workerId);
            try {
                scoreRedisRepository.saveAll(workerId, scores);
                log.info(String.format("PersonalInfoService.getScore: score for worker with id=%d saved to cache", workerId));
            } catch(ThereAreNoEntities e) {
                log.warn(String.format("PersonalInfoService.getScore: there is no scores for worker with id=%d", workerId));
                return Collections.emptyList();
            }
        }

        return scoreRedisRepository.findAll(workerId)
                .stream()
                .map(scr -> scoreMapper.scoreToScoreReadDto(scr, workerMapper))
                .toList();
    }

    /**
     * получение планов рабочего
     * @param workerId id рабочего
     * @return планы рабочего
     */
    public List<PlanReadDto> getPlans(Long workerId) {
        if (planRedisRepository.isEmpty(workerId)) {
            var plans = planRepository.findAllByWorkerId(workerId);
            try {
                planRedisRepository.saveAll(workerId, plans);
                log.info(String.format("PersonalInfoService.getPlans: plans for worker with id=%d saved to cache", workerId));
            } catch(ThereAreNoEntities e) {
                log.warn(String.format("PersonalInfoService.getPlans: there is no plans for worker with id=%d", workerId));
                return Collections.emptyList();
            }
        }

        return planRedisRepository.findAll(workerId)
                .stream()
                .map(pln -> planMapper.planToPlanReadDto(pln, workerMapper, productMapper))
                .toList();
    }

    /**
     * получение информации о продуктивности рабочего
     * @param workerId id рабочего
     * @return продуктивность рабочего
     */
    public List<ProductivityReport> getProductivityForWorker(Long workerId) {
        if (!workerRepository.existsById(workerId)) {
            log.warn(String.format("PersonalInfoService.getProductivityForWorker: entity with id=% does not exist",
                    workerId));
            return Collections.emptyList();
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var queryResult = entityManager.createNativeQuery(
                        "SELECT t.worker_id, t.product_id, SUM(t.r_amount), SUM(t.p_amount) FROM " +
                                "(SELECT r.worker_id, r.product_id, r.amount AS r_amount, p.amount AS p_amount " +
                                "FROM report AS r JOIN plan AS p " +
                                "ON r.worker_id = p.worker_id AND r.product_id = p.product_id AND p.date = r.date) AS t " +
                                "GROUP BY t.worker_id, t.product_id " +
                                "HAVING t.worker_id = :id")
                .setParameter("id", workerId)
                .getResultList();

        return downcastToReport(queryResult);
    }

    /**
     * получение информации о продуктивности рабочего по месяцам
     * @param workerId id рабочего
     * @param month месяц
     * @return продуктивность рабочего
     */
    public List<ProductivityReport> getProductivityForWorkerByMonth(Long workerId, Integer month) {
        if (!workerRepository.existsById(workerId)) {
            log.warn(String.format("PersonalInfoService.getProductivityForWorker: entity with id=% does not exist",
                    workerId));
            return Collections.emptyList();
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var queryResult = entityManager.createNativeQuery(
                        "SELECT t.worker_id, t.product_id, SUM(t.r_amount), SUM(t.p_amount) FROM " +
                                "(SELECT r.worker_id, r.product_id, r.amount AS r_amount, p.amount AS p_amount " +
                                "FROM report AS r JOIN plan AS p " +
                                "ON r.worker_id = p.worker_id AND r.product_id = p.product_id AND p.date = r.date) AS t " +
                                "GROUP BY t.worker_id, t.product_id " +
                                "HAVING t.worker_id = :id AND EXTRACT(MONTH from t.date) = :month")
                .setParameter("id", workerId)
                .setParameter("month", month)
                .getResultList();

        return downcastToReport(queryResult);
    }

    public List<ProductivityReport> downcastToReport(List queryResult) {
        List<ProductivityReport> productivity = new ArrayList<>();

        for (var row : queryResult) {
            var obj = (Object[]) row;

            var worker = workerRepository.getReferenceById((Long) obj[0]);
            var product = productRepository.getReferenceById((Long) obj[1]);
            var reportAmount = (Double) obj[2];
            var planAmount = (Double) obj[3];

            productivity.add(new ProductivityReport(
                    workerMapper.workerToWorkerReadDto(worker),
                    productMapper.productToReadDto(product),
                    reportAmount,
                    planAmount
            ));
        }

        return productivity;
    }
}
