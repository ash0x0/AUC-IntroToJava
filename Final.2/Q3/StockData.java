package com.intro;

public class StockData {

    /**
     * I have decided to use a listener interface to implement an Observer because the
     * Observer and Observable classes are deprecated and should no longer be used.
     * According to official documentation and the deprecation JEP, this is now (since Java 9) the recommended way of implementing observers.
     */
    public interface StockDataChangeListener {
        /**
         * This method for the listener is called when the array data changes, any change of the array data should be
         * immediately followed by the line
         *      mStockDataChangeListener.onDataChanged();
         */
        void onDataChanged();
    }

    private static final int ARRAY_SIZE = 100;
    private int[] mStockDataArray;
    private StockDataChangeListener mStockDataChangeListener;

    StockData() {
        mStockDataArray = new int[ARRAY_SIZE];
    }

    /**
     * Setter for the observer listener. Only one listener is allowed as per the parameters of the problem.
     * @param stockDataChangeListener
     */
    public void setStockDataChangeListener(StockDataChangeListener stockDataChangeListener) {
        this.mStockDataChangeListener = stockDataChangeListener;
    }

    void setElement(int index, int value) {
        mStockDataArray[index] = value;
        mStockDataChangeListener.onDataChanged();
    }

    int getLength() {
        return mStockDataArray.length;
    }
}
