package tanks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements ActionListener {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final int BORDER = 20;
    private static final int DELAY = 10;
    private static final int MOUNTAIN_LIMIT = 5;

    private boolean ingame;
    private boolean won;
    private int mMineNumber;
    private Timer timer;
    private ArrayList<Entity> mEntities;
    private ArrayList<Mine> mMines;
    private ArrayList<Mountain> mMountains;
    private Tank mPlayerTank;
    private House mEnemyHouse;

    Board(int mineNumber) {
        this.mMineNumber = mineNumber;
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setLayout(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
        setFocusable(true);
        requestFocus();
        initEntities();
    }

    public void reset(int mineNumber) {
        this.mMineNumber = mineNumber;
        requestFocus();
        initEntities();
    }

    private void initEntities() {
        won = false;
        this.mEntities = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        this.mPlayerTank = new Tank(random.nextInt(WIDTH - BORDER), random.nextInt(HEIGHT - BORDER));
        this.mEnemyHouse = new House(random.nextInt(WIDTH - BORDER), random.nextInt(HEIGHT - BORDER));
        initMountains();
        initMines();
        initCollisionBounds();
        ingame = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initCollisionBounds() {
        ArrayList<Rectangle> bounds = new ArrayList<>();
        bounds.add(this.mEnemyHouse.getBounds());
        for (Mountain mountain: mMountains) {
            bounds.add(mountain.getBounds());
        }
        this.mPlayerTank.setCollisionBounds(bounds);
    }

    public void initMountains() {
        mMountains = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        int mountainCount = random.nextInt(MOUNTAIN_LIMIT) + 1;
        for (int i = 0; i < mountainCount; i++) {
            mMountains.add(new Mountain(random.nextInt(WIDTH - BORDER), random.nextInt(HEIGHT - BORDER)));
        }
    }

    public void initMines() {
        mMines = new ArrayList<Mine>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < this.mMineNumber; i++) {
            mMines.add(new Mine(random.nextInt(WIDTH - BORDER), random.nextInt(HEIGHT - BORDER)));
        }
    }

    Tank getPlayerTank() {
        return mPlayerTank;
    }

    House getmEnemyHouse() { return mEnemyHouse; }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ingame) {
            drawObjects(g);
        } else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (this.mPlayerTank.isVisible()) {
            g2d.drawImage(this.mPlayerTank.getImage(), this.mPlayerTank.getX(),
                    this.mPlayerTank.getY(), this);
        }
        if (this.mEnemyHouse.isVisible()) {
            g2d.drawImage(this.mEnemyHouse.getImage(), this.mEnemyHouse.getX(),
                    this.mEnemyHouse.getY(), this);
        }
        ArrayList<Missile> missiles = this.mPlayerTank.getMissiles();
        missiles.addAll(this.mEnemyHouse.getMissiles());
        for (Missile missile : missiles) {
            if (missile.isVisible()) {
                g2d.drawImage(missile.getImage(), missile.getX(),
                        missile.getY(), this);
            }
        }
        for (Mine mine : mMines) {
            if (mine.isVisible()) {
                g2d.drawImage(mine.getImage(), mine.getX(),
                        mine.getY(), this);
            }
        }
        for (Mountain mountain : mMountains) {
            if (mountain.isVisible()) {
                g2d.drawImage(mountain.getImage(), mountain.getX(),
                        mountain.getY(), this);
            }
        }
        g.setColor(Color.WHITE);
    }

    private void drawGameOver(Graphics g) {
        String msg = "You died";
        if (won) msg = "You Won";
        g.setColor(Color.black);
        g.drawString(msg, (WIDTH) / 2, HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        updateTank();
        updateMissiles();
        checkCollisions();
        repaint();
    }

    private void inGame() {
        if (!ingame) {
            timer.stop();
        }
    }

    private void updateTank() {
        this.mPlayerTank.move();
    }

    private void updateHouse() {
        this.mEnemyHouse.updateTarget(this.mPlayerTank.getX(), this.mPlayerTank.getY());
    }

    private void updateMissiles() {
        // TODO add the missiles from the house
        ArrayList<Missile> missiles = this.mPlayerTank.getMissiles();
        missiles.addAll(this.mEnemyHouse.getMissiles());
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            if (missile.isVisible()) {
                missile.move();
            } else {
                missiles.remove(i);
            }
        }
    }

    public void updateMines(int mineNumber) {

    }

    public void checkCollisions() {
        Rectangle tank = this.mPlayerTank.getBounds();
        ArrayList<Entity> others = new ArrayList();
        others.addAll(mMines);
        for (Mine mine : mMines) {
            Rectangle bounds = mine.getBounds();
            if (tank.intersects(bounds)) {
                mPlayerTank.setVisible(false);
                mine.setVisible(false);
                ingame = false;
            }
        }
        Rectangle house = this.mEnemyHouse.getBounds();
        // TODO add the missiles from the house
        ArrayList<Missile> ms = this.mPlayerTank.getMissiles();
        ms.addAll(this.getmEnemyHouse().getMissiles());
        for (Missile m : ms) {
            Rectangle r1 = m.getBounds();
            if (r1.intersects(this.mPlayerTank.getBounds())) {
                m.setVisible(false);
                if (this.mPlayerTank.hit()) {
                    ingame = false;
                }
            }
            if (r1.intersects(this.mEnemyHouse.getBounds())) {
                m.setVisible(false);
                if (this.getmEnemyHouse().hit()) {
                    ingame = false;
                    won = true;
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            mPlayerTank.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            mPlayerTank.keyPressed(e);
        }
    }
}
