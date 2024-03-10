package ru.shulenin.farmworkerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.shulenin.farmworkerapi.datasource.entity.Worker;
import ru.shulenin.farmworkerapi.dto.WorkerReadDto;
import ru.shulenin.farmworkerapi.dto.WorkerReceiveDto;
import ru.shulenin.farmworkerapi.dto.WorkerSaveEditDto;

@Mapper
public interface WorkerMapper {
    WorkerMapper INSTANCE = Mappers.getMapper( WorkerMapper.class );

    public WorkerReadDto workerToWorkerReadDto(Worker worker);
    public Worker workerSaveEditdtoToWorker(WorkerSaveEditDto worker);
    public Worker workerReceiveDtoToWorker(WorkerReceiveDto worker);

}
