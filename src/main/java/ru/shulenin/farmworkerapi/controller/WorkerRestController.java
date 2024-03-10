package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shulenin.farmworkerapi.dto.WorkerReceiveDto;
import ru.shulenin.farmworkerapi.service.WorkerService;

@RestController
@RequestMapping("/worker-api/v1/workers")
@RequiredArgsConstructor
public class WorkerRestController {
    private final WorkerService workerService;

    @PostMapping
    @KafkaListener(id = "Worker", topics = {"worker.save"}, containerFactory = "singleFactory")
    public void consume(WorkerReceiveDto workerSaveEditDto) {
        workerService.consume(workerSaveEditDto);
    }

    @DeleteMapping
    @KafkaListener(id = "WorkerDelete", topics = {"worker.delete"}, containerFactory = "singleFactory")
    public void consumeDelete(WorkerReceiveDto workerDto) {
        workerService.delete(workerDto.getId());
    }
}
