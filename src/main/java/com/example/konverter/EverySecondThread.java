package com.example.konverter;

public class EverySecondThread implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Test");
        }
    }
}
