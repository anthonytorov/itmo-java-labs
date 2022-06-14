package eterna.uni.secondsem;

public class FailedToInvokeCommandException extends Exception {
    public Throwable source;
    public FailedToInvokeCommandException(Throwable source) {
        this.source = source;
    }
}
