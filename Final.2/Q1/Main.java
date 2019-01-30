package com.intro;

public class Main {

    public static void main(String[] args) {
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 50; i++) {
                    System.out.println("B: " + i);
                }
            }
        });
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 50; i++) {
                    if (i == 26) {
                        try {
                            B.join();
                        } catch (InterruptedException e) {
                            // Consume the exception
                            // I know this is bad behavior but for the purposes of this question it should be acceptable
                        }
                    }
                    System.out.println("A: " + i);
                }
            }
        });
        // Or the other way around, doesn't matter
        A.start();
        B.start();
    }
}
