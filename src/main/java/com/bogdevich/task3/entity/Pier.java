package com.bogdevich.task3.entity;

import com.bogdevich.task3.exception.HarborException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by eugene on 12.4.17.
 */
public class Pier {
    private static Logger LOGGER = LogManager.getLogger();
    private static Lock lock = new ReentrantLock();

    private int id;

    public Pier(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Thread serving.
     * Method get a ShipState and than call its action.
     *
     * @param ship a thread
     */
    public void serve(Ship ship) {
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            ship.getShipState().action(ship);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Action was interrupted " + ship.toString());
        } catch (HarborException ex) {
            LOGGER.log(Level.ERROR, ship.toString() + ex.getMessage(), ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Pier{" +
                "id=" + id +
                '}';
    }
}
