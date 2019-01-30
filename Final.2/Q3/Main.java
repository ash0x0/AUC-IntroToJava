package com.intro;

public class Main {

    public static void main(String[] args) {
        StockData stockData = new StockData();
        Visualizer visualizer = new Visualizer();
        /*
        This could be IOC (Inversion of control) for the parameters of the question.
        I am assuming that this main thread is the controller therefore this is correct control.
         */
        stockData.setStockDataChangeListener(visualizer);
        // Test the observer, only the setElement(int index, int value) function is available as this is an array.
        stockData.setElement(5, 50);
    }
}
