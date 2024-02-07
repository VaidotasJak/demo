package fun.app.demo.exception;

public class MandatoryFieldsMissingException extends Exception{
    public MandatoryFieldsMissingException(final String message) {
        super(message);
    }

}
