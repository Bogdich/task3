package com.bogdevich.task3.entity;

import com.bogdevich.task3.exception.HarborException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by eugene on 12.4.17.
 */
public class Harbor {
    private final static Logger LOGGER = LogManager.getLogger();

    private static Lock terminalLock = new ReentrantLock();
    private static Lock pierLock = new ReentrantLock();
    private static Harbor instance = new Harbor();
    private static AtomicBoolean terminalExists = new AtomicBoolean(false);

    public final int CAPACITY = 10000;
    public final int PIERS_AMOUNT = 4;

    private int containers;
    private Deque<Pier> piers;
    private Semaphore throughput;


    public static Harbor getInstance() {
        if (!terminalExists.get()) {
            terminalLock.lock();
            try {
                if (instance == null) {
                    instance = new Harbor();
                    terminalExists.set(true);
                }

            } finally {
                terminalLock.unlock();
            }
        }

        return instance;
    }

    private Harbor() {
        containers = new Random().nextInt(CAPACITY + 1);
        piers = new LinkedList<>();
        throughput = new Semaphore(PIERS_AMOUNT, true);
        while (piers.size() < PIERS_AMOUNT) {
            piers.push(new Pier(piers.size() + 1));
        }
    }

    public int getContainers() {
        return containers;
    }

    public void setContainers(int containers) {
        this.containers = containers;
    }

    public Pier moor() throws HarborException {
        Pier pier = null;
        try {
            throughput.acquire();
            TimeUnit.SECONDS.sleep(1);
            pierLock.lock();
            pier = piers.poll();
            pierLock.unlock();
            return pier;
        } catch (NoSuchElementException ex) {
            LOGGER.log(Level.ERROR, "No pier is available", ex);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Mooring is interrupted", ex);
        }
        throw new HarborException("Unsuccessful mooring");
    }

    public void unmoor(Pier pier) {
        try {
            if (pier != null) {
                pierLock.lock();
                piers.push(pier);
                pierLock.unlock();
            }
            TimeUnit.SECONDS.sleep(1);
            throughput.release();
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Unmooring is interrupted", ex);
        } finally {
            LOGGER.log(Level.INFO, pier.toString() + " is free");
        }
    }

    @Override
    public String toString() {
        return "Harbor{" +
                containers + " containers, " +
                piers.size() + " piers available" +
                '}';
    }
}
