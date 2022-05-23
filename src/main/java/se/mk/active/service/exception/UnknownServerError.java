package se.mk.active.service.exception;

public class UnknownServerError extends RuntimeException{
    public UnknownServerError(String message) {
        super(message);
    }
}
