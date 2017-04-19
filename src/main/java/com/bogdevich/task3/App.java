package com.bogdevich.task3;

import com.bogdevich.task3.entity.Harbor;
import com.bogdevich.task3.entity.Ship;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;


public class App {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int THREAD_AMOUNT = 10;
    private static final int THREAD_CAPACITY = 2_000;
    private static final String[] STATES = new String[]{"upload","unload","reload"};

    public static void main(String[] args) {

        LOGGER.log(Level.INFO, Harbor.getInstance().toString());

        ArrayList<Ship> ships = new ArrayList<>(THREAD_AMOUNT);
        while (ships.size()<THREAD_AMOUNT){
            ships.add(new Ship(
                    ships.size()+1,
                    THREAD_CAPACITY,
                    new Random().nextInt(THREAD_CAPACITY)+1,
                    STATES[new Random().nextInt(3)]
            ));
        }
        ships.forEach(Thread::start);
    }
}
