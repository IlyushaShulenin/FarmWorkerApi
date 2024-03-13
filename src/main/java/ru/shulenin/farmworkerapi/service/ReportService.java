package ru.shulenin.farmworkerapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Сервис для работы с отчетами
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final WorkerRepository workerRepository;
    private final ProductRepository productRepository;
    private final PlanRepository planRepository;

    private final KafkaTemplate<Long, ReportSendDto> kafkaReportTemplate;

    private final ReportMapper reportMapper = ReportMapper.INSTANCE;
    private final WorkerMapper workerMapper = WorkerMapper.INSTANCE;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    /**
     * сохранение плана
     * @param reportDto dto для сохранения плана
     * @return dto для чтения плана
     * @throws EntityNotFoundException
     */
    @Transactional
    public ReportReadDto createReport(ReportSaveDto reportDto) throws EntityNotFoundException {
        var workerId = reportDto.getWorkerId();
        var productId = reportDto.getProductId();

        if (!productRepository.existsById(productId)) {
            log.warn(String.format("ReportService.createReport: product with id=%d does not exist", productId));
            throw new EntityNotFoundException(
                    String.format("Product with id=%d does not exist", productId)
            );
        }

        if (!workerRepository.existsById(workerId)) {
            log.warn(String.format("ReportService.createReport: worker with id=%d does not exist", workerId));
            throw new EntityNotFoundException(
                    String.format("Product with id=%d does not exist", workerId)
            );
        }

        Report report = reportMapper.reportSaveDtoToReport(reportDto, workerRepository,
                productRepository);

        planRepository.findByWorkerIdAndProductId(workerId, productId)
                .map(pln -> {
                    Boolean planIsCompleted = report.getAmount() >= pln.getAmount();
                    report.setPlanIsCompleted(planIsCompleted);
                    return true;
                });

        reportRepository.saveAndFlush(report);
        log.info(String.format("ReportService.createReport: entity %s saved", report));

        var message = reportMapper.reportToReportSendDto(report);

        kafkaReportTemplate.send("report.save", message);
        log.info(String.format("ReportService.createReport: message %s sent", message));

        return reportMapper.reportToReportReadDto(report, workerMapper, productMapper);
    }
}
