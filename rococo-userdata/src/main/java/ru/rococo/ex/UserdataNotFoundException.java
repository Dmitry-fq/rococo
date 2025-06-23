package ru.rococo.ex;

public class UserdataNotFoundException extends RuntimeException {
    public UserdataNotFoundException() {
    }

    public UserdataNotFoundException(String message) {
        super(message);
    }
}
