/*
    Ahmed Elshafey
    900131045
    Question 3
 */

import java.util.ArrayList;

public abstract class AbstractTuple implements Tuple {
    ArrayList<Integer> tuple = new ArrayList<>();

    @Override
    public int getFirst() {
        return tuple.get(0);
    }

    @Override
    public int getSecond() {
        return tuple.get(1);
    }

    @Override
    public boolean isIncreasing() {
        return this.getFirst() < this.getSecond() ;
    }
}
