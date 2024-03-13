package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shulenin.farmworkerapi.dto.WorkerReceiveDto;
import ru.shulenin.farmworkerapi.service.WorkerService;

/**
 * Вспомогательным контроллер для получения сообщений о планах со стороны владельца фермы
 */
@RestController
@RequestMapping("/worker-api/v1/workers")
@RequiredArgsConstructor
@Slf4j
public class WorkerRestController {
    private final WorkerService workerService;

    @PostMapping
    @KafkaListener(id = "Worker", topics = {"worker.save"}, containerFactory = "singleFactory")
    public void consume(WorkerReceiveDto workerDto) {
        log.info(String.format("WorkerRestController.consume: message %s received", workerDto));
        workerService.consume(workerDto);
    }

    @DeleteMapping
    @KafkaListener(id = "WorkerDelete", topics = {"worker.delete"}, containerFactory = "singleFactory")
    public void delete(WorkerReceiveDto workerDto) {
        log.info(String.format("WorkerRestController.delete: message %s received", workerDto));
        workerService.delete(workerDto.getId());
    }
}
