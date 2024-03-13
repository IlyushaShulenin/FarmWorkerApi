package ru.shulenin.farmworkerapi.exception.exception;

/**
 * Исключение при попытке сохранить пустой список
 */
public class ThereAreNoEntities extends Exception {
    public ThereAreNoEntities(String message) {
        super(message);
    }
}
