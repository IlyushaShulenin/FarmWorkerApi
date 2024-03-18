package ru.shulenin.farmworkerapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shulenin.farmworkerapi.dto.ScoreReceiveDto;
import ru.shulenin.farmworkerapi.service.ScoreService;

@RestController
@RequestMapping("/worker-api/v1/score")
@RequiredArgsConstructor
@Slf4j
public class ScoreRestController {
    private final ScoreService scoreService;

    @PostMapping
    @KafkaListener(id = "ScoreSave", topics = {"score.save"}, containerFactory = "singleFactory")
    public void consume(ScoreReceiveDto scoreDto) {
        log.info(String.format("ScoreRestController.consume: message %s received", scoreDto));
        scoreService.save(scoreDto);
    }

    @DeleteMapping
    @KafkaListener(id = "ScoreDelete", topics = {"score.delete"}, containerFactory = "singleFactory")
    public void delete(ScoreReceiveDto scoreDto) {
        log.info(String.format("ScoreRestController.delete: message %s received", scoreDto));
        scoreService.delete(scoreDto.getId());
    }
}
