package ru.rococo.ex;

public class MuseumNotFoundException extends RuntimeException {
    public MuseumNotFoundException() {
    }

    public MuseumNotFoundException(String message) {
        super(message);
    }
}
