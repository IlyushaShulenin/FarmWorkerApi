package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.repository.PlanRepository;
import ru.shulenin.farmworkerapi.dto.PlanReceiveDto;
import ru.shulenin.farmworkerapi.mapper.PlanMapper;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    private final PlanMapper planMapper = PlanMapper.INSTANCE;

    @Transactional
    public void save(PlanReceiveDto planDto) {
        var plan = planMapper.planReceiveDtoToPlan(planDto);
        planRepository.save(plan);
    }

    @Transactional
    public void delete(Long id) {
        planRepository.deleteById(id);
    }
}
