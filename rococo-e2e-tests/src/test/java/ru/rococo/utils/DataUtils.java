package ru.rococo.utils;

import com.github.javafaker.Faker;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Base64;
import java.util.function.Supplier;

public class DataUtils {

    public static final String DEFAULT_PASSWORD = "12345";

    private static final Faker faker = new Faker();

    // TODO не работает
    private static final Logger LOG = LoggerFactory.getLogger(DataUtils.class);

    @Nonnull
    public static String getNotBlankStringOrRandom(String value, Supplier<String> supplier) {
        if (!value.isBlank()) {
            return value;
        } else {
            return supplier.get();
        }
    }

    @Nonnull
    public static String getImageByPathOrEmpty(String path) {
        if (!path.isBlank()) {
            return findPictureByPath(path);
        } else {
            return "";
        }
    }

    @Nonnull
    public static String randomUsername() {
        return faker.name().username();
    }

    @Nonnull
    public static String randomSurname() {
        return faker.name().lastName();
    }

    @Nonnull
    public static String randomArtistName() {
        return faker.artist().name() + randomCharactersInQuantity(5);
    }

    @Nonnull
    public static String randomMuseumName() {
        return faker.lorem().sentence(1, 0).replace(".", "") + " museum";
    }

    @Nonnull
    public static String randomPaintingName() {
        return faker.lorem().sentence(1, 0).replace(".", "") + " painting";
    }

    @Nonnull
    public static String defaultCountryName() {
        return "Russia";
    }

    @Nonnull
    public static String randomCityName() {
        return faker.address().cityName();
    }

    @Nonnull
    public static String randomText() {
        return faker.lorem().sentence();
    }

    @Nonnull
    public static String randomCharactersInQuantity(int quantity) {
        return faker.lorem().characters(quantity);
    }

    @Nonnull
    private static String findPictureByPath(String path) {
        try {
            byte[] bytes = Resources.toByteArray(Resources.getResource(path));
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IllegalArgumentException | IOException e) {
            LOG.error("### Изображение не найдено", e);
            return "";
        }
    }
}
