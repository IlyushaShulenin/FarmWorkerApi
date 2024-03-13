package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.repository.PlanRepository;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.PlanReceiveDto;
import ru.shulenin.farmworkerapi.mapper.PlanMapper;

/**
 * Сервис для работы с паланми рабоочего
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PlanService {
    private final PlanRepository planRepository;
    private final WorkerRepository workerRepository;
    private final ProductRepository productRepository;

    private final PlanMapper planMapper = PlanMapper.INSTANCE;

    /**
     * сохранение нового плана рабочего
     * @param planDto dto для сохранения плана
     */
    @Transactional
    public void save(PlanReceiveDto planDto) {
        var plan = planMapper.planReceiveDtoToPlan(planDto, workerRepository, productRepository);
        planRepository.save(plan);
        log.info(String.format("PlanService.save(): entity %s saved", plan));
    }

    /**
     * удаление плана
     * @param id id плана
     */
    @Transactional
    public void delete(Long id) {
        planRepository.deleteById(id);
        log.info(String.format("PlanService.delete(): entity with id= %d deleted", id));
    }
}
