package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shulenin.farmworkerapi.datasource.repository.ScoreRepository;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.ScoreReceiveDto;
import ru.shulenin.farmworkerapi.mapper.ScoreMapper;

/**
 * Сервис для работы с баллами
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ScoreService {
    private final WorkerRepository workerRepository;
    private final ScoreRepository scoreRepository;

    private final ScoreMapper scoreMapper = ScoreMapper.INSTANCE;

    /**
     * Сохранение баллов
     * @param scoreDto dto для получения баллов
     */
    @Transactional
    public void save(ScoreReceiveDto scoreDto) {
        var score = scoreMapper.scoreReceiveDtoToScore(scoreDto, workerRepository);
        scoreRepository.save(score);
        log.info(String.format("ScoreService.save(): entity %s saved", score));
    }

    /**
     * удаление баллов
     * @param id id для сообщения
     */
    @Transactional
    public void delete(Long id) {
        scoreRepository.deleteById(id);
        log.info(String.format("ScoreService.delete(): entity with id= %d deleted", id));
    }
}
