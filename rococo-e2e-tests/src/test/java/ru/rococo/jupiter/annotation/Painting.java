package ru.rococo.jupiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Painting {

    Artist[] artist() default {};

    Museum[] museum() default {};

    String imagePath() default "img/paintings/mona_lisa.jpg";

    String title() default "";

    String description() default "";
}
