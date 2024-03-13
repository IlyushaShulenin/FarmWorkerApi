package ru.shulenin.farmworkerapi.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.entity.Report;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.ReportReadDto;
import ru.shulenin.farmworkerapi.dto.ReportSaveDto;
import ru.shulenin.farmworkerapi.dto.ReportSendDto;

/**
 * Маппер для отчетов
 */
@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper( ReportMapper.class );

    /**
     * от сущности к сообщению
     * @param report сущность
     * @return сообщение
     */
    @Mapping(target = "workerId", source = "report.worker.id")
    @Mapping(target = "productId", source = "report.product.id")
    public ReportSendDto reportToReportSendDto(Report report);

    /**
     * от сущности к dto для чтения
     * @param plan сущность
     * @param workerMapper маппер для рабочих
     * @param productMapper маппер для продуктов
     * @return dto для чтения
     */
    default public ReportReadDto reportToReportReadDto(Report plan,
                                                   WorkerMapper workerMapper,
                                                   ProductMapper productMapper) {
        var workerDto = workerMapper.workerToWorkerReadDto(plan.getWorker());
        var productDto = productMapper.productToReadDto(plan.getProduct());

        return new ReportReadDto(
                workerDto,
                productDto,
                plan.getAmount(),
                plan.getDate(),
                plan.getPlanIsCompleted()
        );
    }

    /**
     * от dto для сохранения к сущности
     * @param planDto dto для сохранения
     * @param workerRepository репозиторий для рабочих
     * @param productRepository репозиторий для продектов
     * @return сущность
     */
    @Transactional(readOnly = true)
    default public Report reportSaveDtoToReport(ReportSaveDto planDto,
                                                WorkerRepository workerRepository,
                                                ProductRepository productRepository) {
        var worker = workerRepository.findById(planDto.getWorkerId());
        var product = productRepository.findById(planDto.getProductId());

        Report report = new Report();

        worker.map(wrk -> {
            report.setWorker(wrk);
            return wrk;
        }).orElseThrow(EntityNotFoundException::new);

        product.map(prod -> {
            report.setProduct(prod);
            return prod;
        }).orElseThrow(EntityNotFoundException::new);

        report.setAmount(planDto.getAmount());
        report.setDate(planDto.getDate());

        return report;
    }

}
