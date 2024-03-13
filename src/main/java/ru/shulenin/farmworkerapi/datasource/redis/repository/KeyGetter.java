package ru.shulenin.farmworkerapi.datasource.redis.repository;

import java.util.HashSet;
import java.util.Set;

/**
 * Всопомгательный класс для получения ключа для Redis
 */
public class KeyGetter {
    protected Set<String> keys = new HashSet<>();

    /**
     * Получение ключа на основе ключа сущность и id
     * @param prefix
     * @param keyPostfix
     * @return
     */
    protected String key(String prefix, String keyPostfix) {
        String key = prefix.concat(keyPostfix);
        keys.add(key);
        return key;
    }
}
