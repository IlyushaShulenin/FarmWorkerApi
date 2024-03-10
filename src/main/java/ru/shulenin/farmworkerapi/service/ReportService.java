package ru.shulenin.farmworkerapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.entity.Report;
import ru.shulenin.farmworkerapi.datasource.repository.PlanRepository;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.datasource.repository.ReportRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.ReportReadDto;
import ru.shulenin.farmworkerapi.dto.ReportSaveDto;
import ru.shulenin.farmworkerapi.dto.ReportSendDto;
import ru.shulenin.farmworkerapi.mapper.ProductMapper;
import ru.shulenin.farmworkerapi.mapper.ReportMapper;
import ru.shulenin.farmworkerapi.mapper.WorkerMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final ReportRepository reportRepository;
    private final WorkerRepository workerRepository;
    private final ProductRepository productRepository;
    private final PlanRepository planRepository;

    private final KafkaTemplate<Long, ReportSendDto> kafkaReportTemplate;

    private final ReportMapper reportMapper = ReportMapper.INSTANCE;
    private final WorkerMapper workerMapper = WorkerMapper.INSTANCE;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Transactional
    public ReportReadDto createReport(ReportSaveDto reportDto) throws EntityNotFoundException {
        var workerId = reportDto.getWorkerId();
        var productId = reportDto.getProductId();

        if (!productRepository.existsById(productId))
            throw new EntityNotFoundException(
                    String.format("Product with id=%d does not exist", productId)
            );

        if (!workerRepository.existsById(workerId))
            throw new EntityNotFoundException(
                    String.format("Product with id=%d does not exist", workerId)
            );

        Report report = reportMapper.reportSaveDtoToReport(reportDto, workerRepository,
                productRepository);

        planRepository.findByWorkerIdAndProductId(workerId, productId)
                .map(pln -> {
                    Boolean planIsCompleted = report.getAmount() >= pln.getAmount();
                    report.setPlanIsCompleted(planIsCompleted);
                    return true;
                });

        reportRepository.saveAndFlush(report);

        kafkaReportTemplate.send("report.save", reportMapper.reportToReportSendDto(report));

        return reportMapper.reportToReportReadDto(report, workerMapper, productMapper);
    }
}
