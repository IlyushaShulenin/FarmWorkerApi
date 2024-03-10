package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shulenin.farmworkerapi.dto.ReportReadDto;
import ru.shulenin.farmworkerapi.dto.ReportSaveDto;
import ru.shulenin.farmworkerapi.service.ReportService;

@RestController
@RequestMapping("/worker-api/v1/reports")
@RequiredArgsConstructor
public class ReportRestController {
    private final ReportService reportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportReadDto createReport(@RequestBody ReportSaveDto reportDto) {

        return reportService.createReport(reportDto);
    }
}
