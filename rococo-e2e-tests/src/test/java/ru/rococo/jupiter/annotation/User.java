package ru.rococo.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.rococo.utils.DataUtils.DEFAULT_PASSWORD;

/**
 * Создает нового пользователя
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface User {
    String username() default "";

    String password() default DEFAULT_PASSWORD;
}
