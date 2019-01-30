package tanks;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class House extends Entity {

    public static final int HOUSE_SIZE = 128;

    private int mHits;
    private int targetX, targetY;
    private int mAccuracy;
    private int mFrequency;
    private Timer timer;
    private ArrayList<Missile> mMissiles;

    House(int x, int y) {
        super(x, y, "house.png");
        initHouse();
    }

    private void initHouse() {
        this.mHits = 0;
        this.mMissiles = new ArrayList<>();
        this.mFrequency = 10;
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 60000/ mFrequency, 60000/ mFrequency);
    }

    public ArrayList<Missile> getMissiles() {
        return mMissiles;
    }

    public void setAccuracy(int mAccuracy) {
        this.mAccuracy = mAccuracy;
    }

    public void setFrequency(int mFrequency) {
        this.mFrequency = mFrequency;
    }

    public boolean hit() {
        this.mHits++;
        return this.mHits >= 3;
    }

    public void updateTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            mMissiles.add(new Missile(x + width, y + height / 2, targetX, targetY));
        }
    }
}