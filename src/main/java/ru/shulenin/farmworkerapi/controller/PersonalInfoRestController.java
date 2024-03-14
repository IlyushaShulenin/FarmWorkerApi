package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
     * @return список боллов рабочего
     */
    @GetMapping("/score")
    @ResponseStatus(HttpStatus.OK)
    public List<ScoreReadDto> getScore(Authentication authentication) {
        var username = authentication.getName();
        return personalInfoService.getScore(username);
    }

    /**
     * Планы выработки для рабочего
     * @return список баллов рабочего
     */
    @GetMapping("/plan")
    @ResponseStatus(HttpStatus.OK)
    public List<PlanReadDto> getPlans(Authentication authentication) {
        var username = authentication.getName();
        return personalInfoService.getPlans(username);
    }

    /**
     * Результаты выработки рабочего(данные об объеме проведенной работы в сравнении с планами)
     * @param month месяц
     * @return результаты выработки
     */
    @GetMapping("/productivity")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductivityReport> getProductivity(Authentication authentication,
                                                    @RequestParam(value = "month", required = false) Integer month) {
        var username = authentication.getName();
        if (month == null)
            return personalInfoService.getProductivityForWorker(username);
        return personalInfoService.getProductivityForWorkerByMonth(username, month);
    }
}
