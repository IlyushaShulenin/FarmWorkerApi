package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shulenin.farmworkerapi.dto.PlanReadDto;
import ru.shulenin.farmworkerapi.dto.ProductivityReport;
import ru.shulenin.farmworkerapi.dto.ScoreReadDto;
import ru.shulenin.farmworkerapi.service.PersonalInfoService;

import java.util.List;

/**
 * Конфигурация рест контроллера для получения информации о результатах работы
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/worker-api/v1/personal-info")
public class PersonalInfoRestController {
    private final PersonalInfoService personalInfoService;

    /**
     * Баллы рабочего
     * @param id id рабочего
     * @return список боллов рабочего
     */
    @GetMapping("/score/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ScoreReadDto> getScore(@PathVariable("id") Long id) {
        return personalInfoService.getScore(id);
    }

    /**
     * Планы выработки для рабочего
     * @param id id рабочего
     * @return список баллов рабочего
     */
    @GetMapping("/plan/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlanReadDto> getPlans(@PathVariable("id") Long id) {
        return personalInfoService.getPlans(id);
    }

    /**
     * Результаты выработки рабочего(данные об объеме проведенной работы в сравнении с планами)
     * @param id id рабочего
     * @return результаты выработки
     */
    @GetMapping("/productivity/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductivityReport> getProductivity(@PathVariable("id") Long id,
                                                    @RequestParam(value = "month", required = false) Integer month) {
        if (month == null)
            return personalInfoService.getProductivityForWorker(id);

        return personalInfoService.getProductivityForWorkerByMonth(id, month);
    }
}
