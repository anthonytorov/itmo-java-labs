package eterna.uni.secondsem.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import eterna.uni.secondsem.FailedToInitializeException;

public class ServerResponseSender {
    private ObjectOutputStream outStream;
    
    public ServerResponseSender(Socket socket) throws FailedToInitializeException {
        try {
            outStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new FailedToInitializeException(e);
        }
    }

    public void pushObject(Object object) {

        try {
            outStream.writeObject(object);
            outStream.flush();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
