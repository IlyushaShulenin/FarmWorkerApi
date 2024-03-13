package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.entity.Worker;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.WorkerReceiveDto;
import ru.shulenin.farmworkerapi.dto.WorkerSaveEditDto;
import ru.shulenin.farmworkerapi.mapper.WorkerMapper;

/**
 * Сервис для работы с рабочими
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WorkerService {
    private final WorkerRepository workerRepository;

    private final WorkerMapper workerMapper = WorkerMapper.INSTANCE;

    /**
     * Сохранение рабочего
     * @param workerDto dto для получения рабочего
     */
    @Transactional
    public void consume(WorkerReceiveDto workerDto) {
        Worker worker = workerMapper.workerReceiveDtoToWorker(workerDto);
        workerRepository.save(worker);
        log.info(String.format("WorkerService.save(): entity %s saved", worker));
    }

    /**
     * удаление рабочего
     * @param id id для сообщения
     */
    @Transactional
    public void delete(Long id) {
        workerRepository.deleteById(id);
        log.info(String.format("WorkerService.delete(): entity with id= %d deleted", id));
    }
}
