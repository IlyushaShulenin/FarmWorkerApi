package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.entity.Worker;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.WorkerReceiveDto;
import ru.shulenin.farmworkerapi.dto.WorkerSaveEditDto;
import ru.shulenin.farmworkerapi.mapper.WorkerMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkerService {
    private final WorkerRepository workerRepository;

    private final WorkerMapper workerMapper = WorkerMapper.INSTANCE;

    @Transactional
    public void consume(WorkerReceiveDto workerDto) {
        Worker worker = workerMapper.workerReceiveDtoToWorker(workerDto);
        workerRepository.save(worker);
    }

    @Transactional
    public void delete(Long id) {
        if (!workerRepository.existsById(id))
            return;

        workerRepository.deleteById(id);
    }
}
