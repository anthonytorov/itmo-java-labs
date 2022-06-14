package eterna.uni.secondsem.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import eterna.uni.secondsem.FailedToInitializeException;

public class ServerRequestReader {
    private ObjectInputStream inStream;

    public ServerRequestReader(Socket socket) throws FailedToInitializeException {
        try {
            inStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new FailedToInitializeException(e);
        }
    }

    public Object readRequest() throws ServerException {
        try {
            return inStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ServerException(e);
        }
    }
}
