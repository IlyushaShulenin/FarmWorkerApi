package ru.shulenin.farmworkerapi.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.shulenin.farmworkerapi.datasource.entity.Score;
import ru.shulenin.farmworkerapi.datasource.entity.Worker;
import ru.shulenin.farmworkerapi.datasource.repository.WorkerRepository;
import ru.shulenin.farmworkerapi.dto.ScoreReadDto;
import ru.shulenin.farmworkerapi.dto.ScoreReceiveDto;

/**
 * Маппер для баллов
 */
@Mapper
public interface ScoreMapper {
    ScoreMapper INSTANCE = Mappers.getMapper( ScoreMapper.class );

    /**
     * от сущности к dto для чтения
     * @param score сущность
     * @param workerMapper маппер для рабочих
     * @return dto для чтения
     */
    default public ScoreReadDto scoreToScoreReadDto(Score score, WorkerMapper workerMapper) {
        var worker = workerMapper.workerToWorkerReadDto(score.getWorker());

        return new ScoreReadDto(
                worker,
                score.getScore(),
                score.getDate()
        );
    }

    default public Score scoreReceiveDtoToScore(ScoreReceiveDto scoreDto, WorkerRepository workerRepository) {
        var worker = workerRepository.getReferenceById(scoreDto.getWorkerId());

        return new Score(
                scoreDto.getId(),
                worker,
                scoreDto.getScore(),
                scoreDto.getDate()
        );
    }

}
