package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shulenin.farmworkerapi.dto.PlanReceiveDto;
import ru.shulenin.farmworkerapi.service.PlanService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker-api/v1/plan")
public class PlanRestController {
    private final PlanService planService;

    @PostMapping
    @KafkaListener(id = "PlanSave", topics = {"plan.save"}, containerFactory = "singleFactory")
    public void consume(PlanReceiveDto planDto) {
        planService.save(planDto);
    }

    @DeleteMapping
    @KafkaListener(id = "PlanDelete", topics = {"plan.delete"}, containerFactory = "singleFactory")
    public void consumeDelete(PlanReceiveDto planDto) {
        planService.delete(planDto.getId());
    }
}
