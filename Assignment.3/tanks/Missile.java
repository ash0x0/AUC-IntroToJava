package tanks;

import static java.lang.Math.atan2;

public class Missile extends Entity {

    public static final int MISSILE_SIZE = 16;
    private static final int MISSILE_SPEED = 2;
    private boolean target;
    private int targetx, targety, dx, dy;

    Missile(int x, int y) {
        super(x, y, "missile.png");
        this.target = false;
    }

    Missile(int x, int y, int targetx, int targety) {
        super(x, y, "missile.png");
        this.targetx = targetx;
        this.targety = targety;
        this.target = true;
    }

    public void move() {
        if (target) {
            x += MISSILE_SPEED;
//            dx = targetx - x;
//            dy = targety - y;
//            double theta = atan2(dy, dx);
//            double vx, vy;
//            vx = MISSILE_SPEED*Math.cos(theta);
//            vy = MISSILE_SPEED*Math.sin(theta);
//            dx = vx * ;
//            dy = vy * dt;
//            x += dx;  // movement in x-direction after dt time w/ constant velocity
//            y += dy;  // movement in y-direction after dt time w/ constant velocity
        } else x += MISSILE_SPEED;
        if (x > Board.WIDTH) {
            visible = false;
        }
    }
}
