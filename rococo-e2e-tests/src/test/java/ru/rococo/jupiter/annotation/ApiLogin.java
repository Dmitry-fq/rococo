package ru.rococo.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.rococo.utils.DataUtils.DEFAULT_PASSWORD;

/**
 * Выполняет логин через API и добавляет id_token и JSESSIONID в браузер
 * <p>
 * Если username заполнен, ищет существующего пользователя в базе
 * <p>
 * Если username не заполнен, ищет созданного пользователя через @User в контексте
 * <p>
 * Если username не заполнен, и в контексте нет пользователя, создает случайного пользователя. Не добавляет его в контекст
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiLogin {
    String username() default "";

    String password() default DEFAULT_PASSWORD;
}
