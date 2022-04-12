package spring_main.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String s) {
        super(s);
    }
}
