package ru.rococo.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Museum {
    String title() default "";

    String description() default "";

    /**
     * Путь до изображения от /resource . Например: img/museums/louvre.jpg
     */
    String photoPath() default "";

    /**
     * Нужно передавать название страны из БД rococo-geo таблицы country или оставить пустым
     */
    String country() default "";

    String city() default "";
}
