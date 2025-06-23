package ru.rococo.ex;

public class PaintingNotFoundException extends RuntimeException {
    public PaintingNotFoundException() {
    }

    public PaintingNotFoundException(String message) {
        super(message);
    }
}
