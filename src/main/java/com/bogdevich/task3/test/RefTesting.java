package com.bogdevich.task3.test;

/**
 * Created by eugene on 18.4.17.
 */
public class RefTesting {
    private int x;

    public RefTesting(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "RefTesting{" +
                "x=" + x +
                '}';
    }
}
