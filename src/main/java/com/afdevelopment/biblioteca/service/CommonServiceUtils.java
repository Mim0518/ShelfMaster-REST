package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.generic.InvalidParametersException;

import java.util.function.Supplier;

/**
 * Common reusable helpers for service-layer validation and checks to reduce duplication.
 */
public final class CommonServiceUtils {

    private CommonServiceUtils() {}

    /**
     * Returns a safe human-readable field name for error messages.
     */
    private static String displayName(String fieldName) {
        return (fieldName == null || fieldName.isBlank()) ? "value" : fieldName;
    }

    /**
     * Ensures the provided text is neither null nor blank. Throws InvalidParametersException otherwise.
     * @param text the text to validate
     * @param fieldName the human-readable field name to include in the error message
     */
    public static void requireNotBlank(String text, String fieldName) {
        if (text == null || text.isBlank()) {
            throw new InvalidParametersException("The parameter '" + displayName(fieldName) + "' must not be empty");
        }
    }

    /**
     * Ensures the provided object reference is not null. Throws InvalidParametersException otherwise.
     * @param obj the object reference to validate
     * @param fieldName the human-readable field name to include in the error message
     */
    public static void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new InvalidParametersException("The parameter '" + displayName(fieldName) + "' must not be null");
        }
    }

    /**
     * Returns entity if not null, or throws the provided exception from supplier.
     * @param entity the entity to check
     * @param exceptionSupplier supplies the exception to throw when entity is null
     * @return the same entity if not null
     * @param <T> entity type
     */
    public static <T> T ensureFound(T entity, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (entity == null) {
            throw exceptionSupplier.get();
        }
        return entity;
    }
}
