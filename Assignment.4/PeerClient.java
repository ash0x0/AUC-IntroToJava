import java.awt.*;
import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeerClient extends Thread {

    private static final String CLASS_NAME = PeerClient.class.getSimpleName();
    private static final Logger Log = Logger.getLogger(CLASS_NAME);
    // Socket timeout, set to a high value, either way will not create issues
    private static final int TIMEOUT = 1000000;
    private static PeerClient mInstance;
    private Thread mThreadInstance;
    private ServerSocket serverSocket;
    private int port;
    private Socket client;
    private String serverName;
    private Point mousePoint;

    public PeerClient(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
        Log.setLevel(Level.SEVERE);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Log.log(Level.INFO, "Connecting to " + serverName + " on port " + port);
                client = new Socket(serverName, port);
                // The client consistently sends points to the other application node even if there are no updates
                sendPoint();
                client.close();
            } catch (ConnectException e) {
                // Consume, this just means the server isn't online yet, doesn't matter for our use case
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // An externally accessible function to update the mouse location on this application node
    public void setPoint(Point mousePoint) {
        this.mousePoint = mousePoint;
    }

    // The function that sends the updated mouse point to the other peer application node
    public void sendPoint() {
        Log.log(Level.INFO, "Just connected to " + client.getRemoteSocketAddress());
        try {
            OutputStream outToServer = client.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outToServer);
            out.writeObject(this.mousePoint);
        } catch (IOException e) {
            Log.log(Level.FINEST, "Exception sending point object " + e.getMessage());
        }
    }

    public void start () {
        if (mThreadInstance == null) {
            mThreadInstance = new Thread (this, CLASS_NAME);
            mThreadInstance.start ();
        }
    }
}
