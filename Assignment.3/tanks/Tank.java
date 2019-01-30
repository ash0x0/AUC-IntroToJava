package tanks;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Tank extends Entity {

    private static final int TANK_WIDTH = 40;
    private static final int TANK_HEIGHT = 84;

    private int mHits;
    private int dx;
    private int dy;
    private ArrayList<Missile> missiles;
    private ArrayList<Rectangle> mBounds;

    Tank(int x, int y) {
        super(x, y, "tank.png");
        initTank();
    }

    private void initTank() {
        this.mHits = 0;
        missiles = new ArrayList<>();
    }

    public void move() {
        if (x + dx > Board.WIDTH || y + dy > Board.HEIGHT || x + dx < 0 || y + dy < 0) return;
        for (Rectangle rectangle : mBounds) {
            if (rectangle.contains(x+dx, y+dy)) return;
        }
        x += dx;
        y += dy;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            fire();
        }
        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }
        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }

    public void setCollisionBounds(ArrayList<Rectangle> bounds) {
        this.mBounds = bounds;
    }

    public boolean hit() {
        this.mHits++;
        return this.mHits >= 3;
    }
}
