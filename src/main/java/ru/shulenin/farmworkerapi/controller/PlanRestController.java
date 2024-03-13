package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shulenin.farmworkerapi.dto.PlanReceiveDto;
import ru.shulenin.farmworkerapi.service.PlanService;

/**
 * Вспомогательным контроллер для получения сообщений о планах со стороны владельца фермы
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/worker-api/v1/plan")
@Slf4j
public class PlanRestController {
    private final PlanService planService;

    @PostMapping
    @KafkaListener(id = "PlanSave", topics = {"plan.save"}, containerFactory = "singleFactory")
    public void consume(PlanReceiveDto planDto) {
        log.info(String.format("PlanRestController.consume: message %s received", planDto));
        planService.save(planDto);
    }

    @DeleteMapping
    @KafkaListener(id = "PlanDelete", topics = {"plan.delete"}, containerFactory = "singleFactory")
    public void consumeDelete(PlanReceiveDto planDto) {
        log.info(String.format("PlanRestController.delete: message %s received", planDto));
        planService.delete(planDto.getId());
    }
}
