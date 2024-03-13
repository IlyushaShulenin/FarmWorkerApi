package ru.shulenin.farmworkerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.shulenin.farmworkerapi.datasource.entity.Score;
import ru.shulenin.farmworkerapi.dto.ScoreReadDto;

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

}
