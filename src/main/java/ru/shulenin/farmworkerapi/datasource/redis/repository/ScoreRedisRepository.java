package ru.shulenin.farmworkerapi.datasource.redis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.shulenin.farmworkerapi.datasource.entity.Score;
import ru.shulenin.farmworkerapi.exception.exception.ThereAreNoEntities;

import java.util.*;

/**
 * Redis репозиторий для работы с баллыми
 */
@Repository
@RequiredArgsConstructor
public class ScoreRedisRepository extends KeyGetter implements RedisRepository<Score, Long> {
    private final static String PREFIX = "SCORE_";

    private final RedisTemplate<String, Object> scoreRedisTemplate;

    /**
     * Получение всех баллов по ключу
     * @param keyPostfix id рабочего
     * @return список баллов рабочего
     */
    @Override
    public List<Score> findAll(Long keyPostfix) {
        var key = key(PREFIX, String.valueOf(keyPostfix));

        return scoreRedisTemplate.opsForHash().values(key)
                .stream()
                .map(scr -> (Score) scr)
                .toList();
    }

    /**
     * Получение конкретных быллов рабочего
     * @param keyPostfix id рабочгео
     * @param id id баллов
     * @return конкретные баллы для заданного рабочего
     */
    @Override
    public Optional<Score> findById(Long keyPostfix, Long id) {
        var key = key(PREFIX, String.valueOf(keyPostfix));
        var score = (Score) scoreRedisTemplate.opsForHash().get(key, id);
        return Optional.ofNullable(score);
    }

    /**
     * Соханение баллов для рабочего
     * @param keyPostfix id рабочего
     * @param entity баллы
     */
    @Override
    public void save(Long keyPostfix, Score entity) {
        var key = key(PREFIX, String.valueOf(keyPostfix));
        scoreRedisTemplate.opsForHash()
                .put(key, entity.getId(), entity);
    }

    /**
     * Сохранение списка баллов для заданного рабочего
     * @param keyPostfix id рабочего
     * @param entities список баллов
     * @throws ThereAreNoEntities
     */
    @Override
    public void saveAll(Long keyPostfix, List<Score> entities) throws ThereAreNoEntities {
        if (entities.isEmpty())
            throw new ThereAreNoEntities("There are not workers");

        var key = key(PREFIX, String.valueOf(keyPostfix));
        Map<Long, Score> entries = new HashMap<>();

        for (var entity : entities)
            entries.put(entity.getId(), entity);

        scoreRedisTemplate.opsForHash().putAll(key, entries);
    }

    /**
     * Удаление баллов для заданного рабочего
     * @param keyPostfix id рабочего
     * @param id id баллов
     */
    @Override
    public void delete(Long keyPostfix, Long id) {
        var key = key(PREFIX, String.valueOf(keyPostfix));
        scoreRedisTemplate.opsForHash()
                .delete(key, id);
    }

    /**
     * Проверка на пустоту кэша для заданного рабочего
     * @param keyPostfix id рабочего
     * @return true если пусто, иначе false
     */
    @Override
    public boolean isEmpty(Long keyPostfix) {
        var key = key(PREFIX, String.valueOf(keyPostfix));
        var values = scoreRedisTemplate.opsForHash().values(key);
        return values.isEmpty();
    }

    /**
     * Очистка кэша
     */
    @Override
    public void clear() {
        for (var k : keys)
            scoreRedisTemplate.delete(k);
    }

    @Override
    public String getKey() {
        return PREFIX;
    }

}
