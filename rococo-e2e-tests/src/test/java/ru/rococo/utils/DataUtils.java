package ru.rococo.utils;

import com.github.javafaker.Faker;
import lombok.Getter;

import javax.annotation.Nonnull;

public class DataUtils {

    private static final Faker faker = new Faker();

    @Getter
    private static final String DEFAULT_PASSWORD = "12345";

    @Nonnull
    public static String randomUsername() {
        return faker.name().username();
    }

    @Nonnull
    public static String getDefaultPassword() {
        return DEFAULT_PASSWORD;
    }
}
