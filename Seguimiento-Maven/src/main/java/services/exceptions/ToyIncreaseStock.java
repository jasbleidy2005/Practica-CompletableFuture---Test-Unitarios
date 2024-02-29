package services.exceptions;

public class ToyIncreaseStock extends RuntimeException{
    public ToyIncreaseStock(String message) {
        super(message);
    }
}
