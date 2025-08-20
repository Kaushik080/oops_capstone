package exception;

public class InvalidDepositValueException extends RuntimeException{

    public InvalidDepositValueException(String message) {
        super(message);
    }
}
