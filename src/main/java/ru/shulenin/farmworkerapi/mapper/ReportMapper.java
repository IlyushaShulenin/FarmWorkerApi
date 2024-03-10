package ru.shulenin.farmworkerapi.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.entity.Report;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.ReportReadDto;
import ru.shulenin.farmworkerapi.dto.ReportSaveDto;
import ru.shulenin.farmworkerapi.dto.ReportSendDto;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper( ReportMapper.class );

    public ReportSendDto reportToReportSendDto(Report report);

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
