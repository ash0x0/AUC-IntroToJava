package com.intro;

/**
 * This class implements a listener for StockData class. It is an observer of the underlying dataset for StockData.
 */
public class Visualizer implements StockData.StockDataChangeListener {

    private static final String PRINT_MESSAGE = "I am now drawing something";

    /**
     * This method is called whenever there is a change to the StockData array.
     * Control for the change is with the StockData class.
     */
    @Override
    public void onDataChanged() {
        System.out.println(PRINT_MESSAGE);
    }
}
