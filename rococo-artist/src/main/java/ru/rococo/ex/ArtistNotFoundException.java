package ru.rococo.ex;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException() {
    }

    public ArtistNotFoundException(String message) {
        super(message);
    }
}
