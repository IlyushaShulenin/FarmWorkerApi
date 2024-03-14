package ru.shulenin.farmworkerapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shulenin.farmworkerapi.dto.ReportReadDto;
import ru.shulenin.farmworkerapi.dto.ReportSaveDto;
import ru.shulenin.farmworkerapi.service.ReportService;

/**
 * Rest контроллер для создания отчетов о проделланой работе
 */
@RestController
@RequestMapping("/worker-api/v1/reports")
@RequiredArgsConstructor
public class ReportRestController {
    private final ReportService reportService;

    /**
     * Создание отчета
     * @param reportDto тело отчета
     * @return отчет
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportReadDto createReport(@RequestBody @Valid ReportSaveDto reportDto) {
        return reportService.createReport(reportDto);
    }
}
