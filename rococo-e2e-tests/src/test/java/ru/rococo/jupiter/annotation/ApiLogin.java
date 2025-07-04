package ru.rococo.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Производит вход через API и добавляет id_token и JSESSIONID в браузер
 * <p>
 * Если username и password не заполнены, нужно добавить аннотацию @User для создания пользователя
 * <p>
 * Если username и password заполнены, то аннотация @User должна отсутствовать
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiLogin {
    String username() default "";

    String password() default "";
}
