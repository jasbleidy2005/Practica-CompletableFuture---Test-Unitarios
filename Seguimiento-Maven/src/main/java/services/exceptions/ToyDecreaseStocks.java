package services.exceptions;

public class ToyDecreaseStocks extends RuntimeException{
    public ToyDecreaseStocks(String message) {
        super(message);
    }
}
