import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseSyncClient extends JFrame implements PeerServer.PointerUpdateListener, PeerServer.ServerAddressListener {

    private static final String CLASS_NAME = MouseSyncClient.class.getSimpleName();
    private static final Logger Log = Logger.getLogger(CLASS_NAME);
    // The radius for the red dot that matches the other peer node, bigger number is bigger dot
    private static final int RADIUS = 3;
    // The timer delay for the mouse action listener to listen to mouse movements
    private static final int TIMER_DELAY = 1;
    // This is the address (IP) or special name of the other application node to connect to, change to connect to other nodes
    private static final String SERVER_NAME = "localhost";
    // This is the port number of the other application node to change to, change to connect to other nodes
    private static final int SERVER_PORT = 9100;
    // This is the port number of this application node, use this with the IP of the machine to connect to this node
    private static final int PORT = 9200;
    // This implementation uses both a client and a server on every application node
    // The references are static because they need to be accessible from the main function
    private static PeerClient peerClient;
    private static PeerServer peerServer;
    // This point is the mouse coordinate on the PEER application node not this application instance
    private Point mMousePoint;
    // This label should display the address of this node, not yet implemented
    private JLabel addressLabel;

    public MouseSyncClient(String title) {
        super(title);
        // This is merely initialization to avoid NullPointerException
        Point p = MouseInfo.getPointerInfo().getLocation();
        // This is merely initialization to avoid NullPointerException
        mMousePoint = p;
        ActionListener actionListener = new ActionListener() {
            Point lastPoint;
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                if (!p.equals(lastPoint)) {
                    // Update the pointer location with the server using the client
                    peerClient.setPoint(p);
                }
                lastPoint = p;
            }
        };
        // The timer schedules an action listener for mouse
        Timer timer = new Timer(TIMER_DELAY, actionListener);
        timer.start();
        // This is the label displaying this node address
        addressLabel = new JLabel("Node address");
        addressLabel.setBounds(this.getBounds().x / 2, this.getBounds().y / 2, 200, 50);
        addressLabel.setVisible(true);
        add(addressLabel);
    }

    @Override
    public void onPointerLocationUpdate(Point point) {
        // Update the red dot coordinates and repaint
        mMousePoint = point;
        repaint();
    }

    @Override
    public void onAddressConnected(String address) {
        // Update the server address label
        addressLabel.setText(address);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.fillOval(mMousePoint.x - RADIUS, mMousePoint.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    public static void main(String[] args) {
        MouseSyncClient applicationFrame = new MouseSyncClient("Peer Mouse Movement");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();
                applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                applicationFrame.setBackground(Color.WHITE);
                applicationFrame.setPreferredSize(new Dimension(width, height));
                applicationFrame.setLayout(null);
                applicationFrame.setResizable(true);
                applicationFrame.pack();
                applicationFrame.setVisible(true);
                applicationFrame.setFocusable(true);
                applicationFrame.requestFocus();
            }
        });
        // This implementations uses nodes with both a client and a server
        try {
            // The server awaits updates from the other node and updates this one
            peerServer = new PeerServer(PORT);
            peerServer.setPointerUpdateListener(applicationFrame);
            peerServer.setServerAddressListener(applicationFrame);
            peerServer.start();
        } catch (IOException e) {
            Log.log(Level.INFO, "Exception " + e.getLocalizedMessage());
        }
        // The client sends updates to the other peer node
        peerClient = new PeerClient(SERVER_NAME, SERVER_PORT);
        peerClient.start();
    }
}
