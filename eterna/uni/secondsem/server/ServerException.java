package eterna.uni.secondsem.server;

public class ServerException extends Exception {
    public final Throwable cause;
    public ServerException(Throwable cause) {
        this.cause = cause;
    }
}
