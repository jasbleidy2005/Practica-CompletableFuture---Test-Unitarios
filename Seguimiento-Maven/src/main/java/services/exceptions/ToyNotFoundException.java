package services.exceptions;

public class ToyNotFoundException extends Exception{
    public ToyNotFoundException(String message) {
        super(message);
    }
}
