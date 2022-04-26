package eterna.uni.secondsem.networking;

import java.io.Serializable;

import eterna.uni.secondsem.LogPrinter;

public class ServerResponseMessage implements ServerResponse, Serializable {
    private final String message;
    public String get_message() { return message; }

    public ServerResponseMessage(String _message) {
        message = _message;
    }

    @Override
    public void execute() {
        LogPrinter.log(message);
    }
}