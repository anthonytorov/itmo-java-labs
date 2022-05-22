package eterna.uni.secondsem.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;

public class ClientObjectSerializer {
    private SocketChannel _socketChannel;
    private ObjectInputStream _inStream;
    private ObjectOutputStream _outStream;

    public ClientObjectSerializer(SocketChannel socketChannel) {
        _socketChannel = socketChannel;
        try {
            _inStream = new ObjectInputStream(_socketChannel.socket().getInputStream());
            _outStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void pushObject(Object object) {

        try {
            _outStream.writeObject(object);
            _outStream.flush();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public Object readResponse() {

        Object response = null;

        try {
            response = _inStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

    public boolean isConnectionOpen() {
        return _socketChannel.isOpen();
    }

    public void closeSocket() {
        try {
            _socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
