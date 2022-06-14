package eterna.uni.secondsem;

public class FailedToInitializeException extends Exception {
    public final Throwable cause;
    public FailedToInitializeException(Throwable reason) {
        this.cause = reason;
    }
}
