package fun.app.demo.exception;

public class NoDoctorsFoundException extends Exception{
    public NoDoctorsFoundException(final String message) {
        super(message);
    }
}
