package com.bogdevich.task3.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by eugene on 16.4.17.
 */
public class ExceptThread extends Thread {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void run() {
        throw new RuntimeException();
/*        try {
            throw new RuntimeException();
        } catch (RuntimeException ex) {
            LOGGER.log(Level.ERROR, ex.toString());
        }*/
    }
}
