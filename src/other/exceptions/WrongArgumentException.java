package other.exceptions;

public class WrongArgumentException extends Exception{
    private final ErrorType type;

    public WrongArgumentException(ErrorType type) {
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}

