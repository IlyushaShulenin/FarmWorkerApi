package ru.shulenin.farmworkerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.shulenin.farmworkerapi.datasource.entity.Plan;
import ru.shulenin.farmworkerapi.datasource.repository.ProductRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.PlanReadDto;
import ru.shulenin.farmworkerapi.dto.PlanReceiveDto;


/**
 * Маппер для планов
 */
@Mapper
public interface PlanMapper {
    PlanMapper INSTANCE = Mappers.getMapper( PlanMapper.class );

    /**
     * От сообщения к сущность
     * @param plan сообщение
     * @param workerRepository репозиторий для рабочих
     * @param productRepository репозиторий для рабочих
     * @return сущность
     */
    default public Plan planReceiveDtoToPlan(PlanReceiveDto plan,
                                             WorkerRepository workerRepository,
                                             ProductRepository productRepository) {
        var worker = workerRepository.getReferenceById(plan.getWorkerId());
        var product = productRepository.getReferenceById(plan.getProductId());

        return new Plan(
            plan.getId(),
            worker,
            product,
            plan.getAmount(),
            plan.getDate()
        );
    }

    /**
     * От сущности к dto для чтения
     * @param plan сущность
     * @param workerMapper маппер для рабочих
     * @param productMapper маппер для продутов
     * @return dto для чтения
     */
    default public PlanReadDto planToPlanReadDto(Plan plan,
                                                 WorkerMapper workerMapper,
                                                 ProductMapper productMapper) {
        var workerDto = workerMapper.workerToWorkerReadDto(plan.getWorker());
        var productDto = productMapper.productToReadDto(plan.getProduct());

        return new PlanReadDto(
                workerDto,
                productDto,
                plan.getAmount(),
                plan.getDate()
        );
    }
}
