import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MouseSync extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private JLabel label;

    public MouseSync(String title) {
        super(title);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MouseSync applicationFrame = new MouseSync("Peer Mouse Movement");
                applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                applicationFrame.setBackground(Color.WHITE);
                applicationFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                applicationFrame.setLayout(null);
                applicationFrame.setResizable(true);
                applicationFrame.pack();
                applicationFrame.setVisible(true);
                applicationFrame.setFocusable(true);
                applicationFrame.requestFocus();
            }
        });
        Point p = MouseInfo.getPointerInfo().getLocation();
        ActionListener al = new ActionListener() {
            Point lastPoint;
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                if (!p.equals(lastPoint)) {
                    // Update the red dot
                }
                lastPoint = p;
            }
        };
        Timer timer = new Timer(40, al);
        timer.start();
//        DiscoveryServiceServer.getInstance().start();
//        DiscoveryServiceClient.getInstance().start();
    }
}
