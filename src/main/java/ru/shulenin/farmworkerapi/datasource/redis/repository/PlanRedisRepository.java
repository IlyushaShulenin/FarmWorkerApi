package ru.shulenin.farmworkerapi.datasource.redis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.shulenin.farmworkerapi.datasource.entity.Plan;
import ru.shulenin.farmworkerapi.exception.exception.ThereAreNoEntities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Redis репозиторий для работы с планами
 */
@Repository
@RequiredArgsConstructor
public class PlanRedisRepository extends KeyGetter implements RedisRepository<Plan, Long> {
    private static final String PREFIX = "PLAN";

    private final RedisTemplate<String, Object> planRedisTemplate;

    /**
     * Получение всех значений по ключу
     * @param keyPostfix id рабочего
     * @return список планов рабочего
     */
    @Override
    public List<Plan> findAll(Long keyPostfix) {
        var key = key(PREFIX, String.valueOf(keyPostfix));

        return planRedisTemplate.opsForHash().values(key)
                .stream()
                .map(pln -> (Plan) pln)
                .toList();
    }

    /**
     * Получение конкретного плана рабочего
     * @param keyPostfix id рабочгео
     * @param id id плана
     * @return конкретный план для заданного рабочего
     */
    @Override
    public Optional<Plan> findById(Long keyPostfix, Long id) {
        var key = key(PREFIX, String.valueOf(keyPostfix));
        var plan = (Plan) planRedisTemplate.opsForHash().get(key, id);
        return Optional.ofNullable(plan);
    }

    /**
     * Сохранение плана для рабочего
     * @param keyPostfix id рабочего
     * @param entity план
     */
    @Override
    public void save(Long keyPostfix, Plan entity) {
        var key = key(PREFIX, String.valueOf(keyPostfix));
        planRedisTemplate.opsForHash()
                .put(key, entity.getId(), entity);
    }

    /**
     * Сохранение списка планов для заданного рабочего
     * @param keyPostfix id рабочего
     * @param entities список планов
     * @throws ThereAreNoEntities
     */
    @Override
    public void saveAll(Long keyPostfix, List<Plan> entities) throws ThereAreNoEntities {
        if (entities.isEmpty())
            throw new ThereAreNoEntities("There are not workers");

        var key = key(PREFIX, String.valueOf(keyPostfix));
        Map<Long, Plan> entries = new HashMap<>();

        for (var entity : entities)
            entries.put(entity.getId(), entity);

        planRedisTemplate.opsForHash().putAll(key, entries);
    }

    /**
     * Удаление плана для заданного рабочего
     * @param keyPostfix id рабочего
     * @param id id плана
     */
    @Override
    public void delete(Long keyPostfix, Long id) {
        var key = key(PREFIX, String.valueOf(keyPostfix));
        planRedisTemplate.opsForHash()
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
        var values = planRedisTemplate.opsForHash().values(key);
        return values.isEmpty();
    }

    /**
     * Очистка кэша
     */
    @Override
    public void clear() {
        for (var k : keys)
            planRedisTemplate.delete(k);
    }

    @Override
    public String getKey() {
        return PREFIX;
    }
}
