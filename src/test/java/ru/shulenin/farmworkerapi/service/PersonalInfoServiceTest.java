package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import ru.shulenin.farmworkerapi.datasource.repository.ScoreRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.PlanReadDto;
import ru.shulenin.farmworkerapi.dto.ScoreReadDto;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PersonalInfoServiceTest {
    private final PersonalInfoService personalInfoService;
    private final ScoreRepository scoreRepository;
    private final WorkerRepository workerRepository;

    private static final Long ID = 1L;

    @Test
    public void getScoreTest() {
        List<ScoreReadDto> scores = personalInfoService.getScore(ID);

        assertThat(scores).hasSize(4);
        assertThat(scores.get(0).getWorkerReadDto().getId()).isEqualTo(ID);
        assertThat(scores.get(0).getScore()).isEqualTo(6);
        assertThat(scores.get(0).getDate()).isEqualTo(LocalDate.of(2023, 10, 12));
    }

    @Test
    public void GetPlanTest() {
        List<PlanReadDto> plan = personalInfoService.getPlans(ID);

        assertThat(plan).hasSize(4);
        assertThat(plan.get(0).getWorker().getId()).isEqualTo(ID);
        assertThat(plan.get(0).getProduct().getId()).isEqualTo(1);
        assertThat(plan.get(0).getAmount()).isEqualTo(100);
        assertThat(plan.get(0).getDate()).isEqualTo(LocalDate.of(2023, 10, 12));
    }

    @Test
    public void GetProductivityTest() {
        var productivity = personalInfoService.getProductivityForWorker(4L);

        assertThat(productivity).hasSize(3);
        assertThat(productivity.get(0).getProduct().getId()).isEqualTo(1);
        assertThat(productivity.get(0).getWorker().getId()).isEqualTo(4);
        assertThat(productivity.get(0).getReportAmount()).isEqualTo(300);
        assertThat(productivity.get(0).getPlanAmount()).isEqualTo(300);

    }

}