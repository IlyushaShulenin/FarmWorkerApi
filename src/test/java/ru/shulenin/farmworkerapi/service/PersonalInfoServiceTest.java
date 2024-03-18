package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.TestBase;
import ru.shulenin.farmworkerapi.annotation.IntegrationTest;
import ru.shulenin.farmworkerapi.controller.AuthRestController;
import ru.shulenin.farmworkerapi.datasource.repository.PlanRepository;
import ru.shulenin.farmworkerapi.datasource.repository.ScoreRepository;
import ru.shulenin.farmworkerapi.dto.PlanReadDto;
import ru.shulenin.farmworkerapi.dto.ScoreReadDto;
import ru.shulenin.farmworkerapi.mapper.PlanMapper;
import ru.shulenin.farmworkerapi.mapper.ProductMapper;
import ru.shulenin.farmworkerapi.mapper.ScoreMapper;
import ru.shulenin.farmworkerapi.mapper.WorkerMapper;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PersonalInfoServiceTest extends TestBase {
    private final PersonalInfoService personalInfoService;
    private final ScoreRepository scoreRepository;
    private final PlanRepository planRepository;

    private final ScoreMapper scoreMapper = ScoreMapper.INSTANCE;
    private final PlanMapper planMapper = PlanMapper.INSTANCE;
    private final WorkerMapper workerMapper = WorkerMapper.INSTANCE;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    private final static String EMAIL = "jhon@mail.com";

    @Test
    public void getScoreTest() {
        var scoresFromService = personalInfoService.getScore(EMAIL);
        var scoresFromRepository = scoreRepository.findAllByWorkerEmail(EMAIL)
                .stream()
                .map(score -> scoreMapper.scoreToScoreReadDto(score, workerMapper))
                .toList();

        Set<ScoreReadDto> orderedScoresFromService = new HashSet<>(scoresFromService);
        Set<ScoreReadDto> orderedScoresFromRepository = new HashSet<>(scoresFromRepository);

        assertThat(orderedScoresFromService).isNotEmpty();
        assertThat(orderedScoresFromRepository).isNotEmpty();
        assertThat(orderedScoresFromService).isEqualTo(orderedScoresFromRepository);

    }

    @Test
    public void getPlansTest() {
        var plansFromService = personalInfoService.getPlans(EMAIL);
        var planFromRepository = planRepository.findAllNotCompleted(EMAIL)
                .stream()
                .map(plan -> planMapper.planToPlanReadDto(plan, workerMapper, productMapper))
                .toList();

        Set<PlanReadDto> orderedPlansFromService = new HashSet<>(plansFromService);
        Set<PlanReadDto> orderedPlansFromRepository = new HashSet<>(planFromRepository);

        assertThat(orderedPlansFromService).isNotEmpty();
        assertThat(orderedPlansFromRepository).isNotEmpty();
        assertThat(orderedPlansFromService).hasSize(1);
        assertThat(orderedPlansFromService).isEqualTo(orderedPlansFromRepository);
    }
}