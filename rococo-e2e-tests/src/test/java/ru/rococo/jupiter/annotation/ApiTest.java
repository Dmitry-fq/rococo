package ru.rococo.jupiter.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import ru.rococo.jupiter.extension.ApiLoginExtension;
import ru.rococo.jupiter.extension.ArtistExtension;
import ru.rococo.jupiter.extension.MuseumExtension;
import ru.rococo.jupiter.extension.PaintingExtension;
import ru.rococo.jupiter.extension.UserExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({
        UserExtension.class,
        ArtistExtension.class,
        MuseumExtension.class,
        PaintingExtension.class,
        ApiLoginExtension.class
})
public @interface ApiTest {
}
