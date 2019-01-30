package com.intro;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*
IMPORTANT NOTE: I found this implementation on StackOverflow when solving assignment 4, I liked it so I saved it
and I'm modifying on it here like I did.
link: https://stackoverflow.com/questions/25467866/detecting-mouse-movement-on-screen
(I think this is the link, not sure as I didn't actually open the page I just got it from my history, no WIFI)
 */

/**
 * This program will track mouse movements over the entire screen and trail the mouse in the panel.
 * When the mouse is clicked however, it must be clicked inside the panel for the click to register.
 */

public class Main {

    private static final int DELAY = 1;
    Robot robot;
    JLabel label;
    GeneralPath generalPath;
    static JLabel locationLabel;
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    Main() throws AWTException {
        robot = new Robot();
        label = new JLabel();
        generalPath = new GeneralPath();
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        generalPath.moveTo(mousePoint.x, mousePoint.y);
        drawLatestMouseMovement();
        ActionListener actionListener = new ActionListener() {
            Point lastPoint;
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                if (!p.equals(lastPoint)) {
                    generalPath.lineTo(p.x, p.y);
                    drawLatestMouseMovement();
                }
                lastPoint = p;
            }
        };
        Timer timer = new Timer(DELAY, actionListener);
        timer.start();
    }

    public void drawLatestMouseMovement() {
        BufferedImage biOrig = robot.createScreenCapture(new Rectangle(0, 0, dimension.width, dimension.height));
        BufferedImage small = new BufferedImage(biOrig.getWidth() / 4, biOrig.getHeight() / 4, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = small.createGraphics();
        g.scale(.25, .25);
        g.drawImage(biOrig, 0, 0, label);
        g.setStroke(new BasicStroke(8));
        g.setColor(Color.RED);
        g.draw(generalPath);
        g.dispose();
        label.setIcon(new ImageIcon(small));
    }

    public JComponent getUI() {
        return label;
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel panel = new JPanel(new BorderLayout(2, 2));
                JFrame frame = new JFrame("Track Mouse");
                locationLabel = new JLabel();
                panel.add(locationLabel, BorderLayout.SOUTH);
                panel.setBorder(new EmptyBorder(4, 4, 4, 4));
                try {
                    Main main = new Main();
                    panel.add(main.getUI());
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }

                MouseListener mouseListener = new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        locationLabel.setText("x: " + MouseInfo.getPointerInfo().getLocation().x + ", y: " + MouseInfo.getPointerInfo().getLocation().y);
                    }
                    @Override
                    public void mousePressed(MouseEvent e) { }
                    @Override
                    public void mouseReleased(MouseEvent e) { }
                    @Override
                    public void mouseEntered(MouseEvent e) { }
                    @Override
                    public void mouseExited(MouseEvent e) { }
                };
                panel.addMouseListener(mouseListener);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }
}
