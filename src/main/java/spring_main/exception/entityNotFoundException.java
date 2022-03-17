package spring_main.exception;

public class entityNotFoundException extends RuntimeException {
    public entityNotFoundException(String message) {
        super(message);
    }
}
