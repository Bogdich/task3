package com.bogdevich.task3.test;

import com.sun.istack.internal.Nullable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by eugene on 12.4.17.
 */
public class RunnableImpl implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    public void run() {
        for (int i=0; i<10; i++){
            LOGGER.info("Walking");
            try{
                Thread.sleep(7);
            }catch (InterruptedException ex){
                LOGGER.log(Level.ERROR,ex.getMessage());
            }
        }

    }

}
