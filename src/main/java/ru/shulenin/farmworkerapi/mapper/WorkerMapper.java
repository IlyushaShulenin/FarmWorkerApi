package ru.shulenin.farmworkerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.shulenin.farmworkerapi.datasource.entity.Worker;
import ru.shulenin.farmworkerapi.dto.WorkerReadDto;
import ru.shulenin.farmworkerapi.dto.WorkerReceiveDto;
import ru.shulenin.farmworkerapi.dto.WorkerSaveEditDto;

/**
 * Маппер для рабочих
 */
@Mapper
public interface WorkerMapper {
    WorkerMapper INSTANCE = Mappers.getMapper( WorkerMapper.class );

    /**
     * от сущности к dto для чтения
     * @param worker сущность
     * @return dto для чтения
     */
    public WorkerReadDto workerToWorkerReadDto(Worker worker);

    /**
     * от dto для сохранения к сущности
     * @param worker dto для сохранения
     * @return сущность
     */
    public Worker workerSaveEditdtoToWorker(WorkerSaveEditDto worker);

    /**
     * от сообщения к сущности
     * @param worker сообщение
     * @return сущность
     */
    public Worker workerReceiveDtoToWorker(WorkerReceiveDto worker);

}
