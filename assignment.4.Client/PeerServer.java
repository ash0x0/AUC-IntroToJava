import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeerServer extends Thread {

    private static final String CLASS_NAME = PeerServer.class.getSimpleName();
    private static final Logger Log = Logger.getLogger(CLASS_NAME);
    private static final int TIMEOUT = 1000000;
    private static PeerServer mInstance;
    private Thread mThreadInstance;
    private ServerSocket serverSocket;
    private Socket server;
    private PointerUpdateListener mPointerUpdateListener;
    private ServerAddressListener mServerAddressListener;

    // A listener for pointer updates on the other peer node
    public interface PointerUpdateListener {
        void onPointerLocationUpdate(Point point);
    }

    // A listener for server address to display to the user
    public interface ServerAddressListener {
        void onAddressConnected(String address);
    }

    public PeerServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(TIMEOUT);
        Log.setLevel(Level.SEVERE);
    }

    public void setPointerUpdateListener(PointerUpdateListener mPointerUpdateListener) {
        this.mPointerUpdateListener = mPointerUpdateListener;
    }

    public void setServerAddressListener(ServerAddressListener mServerAddressListener) {
        this.mServerAddressListener = mServerAddressListener;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Log.log(Level.INFO, "Waiting for client on port " + serverSocket.getLocalPort() + "...");
//                this.mServerAddressListener.onAddressConnected("Node Address " + serverSocket.getLocalSocketAddress().toString());
                server = serverSocket.accept();
                Log.log(Level.INFO, "Just connected to " + server.getRemoteSocketAddress());
                ObjectInputStream in = new ObjectInputStream(server.getInputStream());
                this.mPointerUpdateListener.onPointerLocationUpdate((Point) in.readObject());
            } catch (SocketTimeoutException s) {
                Log.log(Level.INFO, "Socket timed out!");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void start () {
        if (mThreadInstance == null) {
            mThreadInstance = new Thread (this, CLASS_NAME);
            mThreadInstance.start();
        }
    }
}
