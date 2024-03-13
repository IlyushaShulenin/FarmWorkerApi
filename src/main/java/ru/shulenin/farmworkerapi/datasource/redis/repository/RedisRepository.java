package ru.shulenin.farmworkerapi.datasource.redis.repository;

import java.util.List;
import java.util.Optional;

public interface RedisRepository<E, K> {
    public List<E> findAll(K keyPostfix);

    public Optional<E> findById(K keyPostfix, K id);

    public void save(K keyPostfix, E entity);

    public void saveAll(K keyPostfix, List<E> entities) throws Exception;

    public void delete(K keyPostfix, K id);

    public boolean isEmpty(K keyPostfix);

    public void clear();

    public String getKey();

}
