package com.bogdevich.task3.entity;

import com.bogdevich.task3.exception.HarborException;
import com.bogdevich.task3.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by eugene on 12.4.17.
 */
public class Ship extends Thread {

    private final static Logger LOGGER = LogManager.getLogger();

    private int id;
    private int capacity;
    private int containers;
    private ShipState state;
    private Pier pier;

    public Ship(int id, int capacity, int containers, String state) {
        this.id = id;
        this.capacity = capacity;
        this.containers = containers;
        this.state = ShipState.valueOf(state.toUpperCase());
        LOGGER.log(Level.INFO, this.toString() + " is ready for mooring");
    }

    @Override
    public void run() {
        try {

            pier = Harbor.getInstance().moor();
            LOGGER.log(Level.INFO, this.toString() + " has moored to " + pier.toString());

            pier.serve(this);

            LOGGER.log(Level.INFO, this.toString() + " is going away");
            Harbor.getInstance().unmoor(this.pier);
            this.pier = null;

        } catch (HarborException ex) {
            LOGGER.log(Level.ERROR, this.toString() + ex.getMessage());
        }
    }

    public int getShipId() {
        return id;
    }

    public void setShipId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getContainers() {
        return containers;
    }

    public void setContainers(int containers) {
        this.containers = containers;
    }

    public ShipState getShipState() {
        return state;
    }

    public void setState(ShipState state) {
        this.state = state;
    }


    public Pier getPier() {
        return pier;
    }

    public void setPier(Pier pier) {
        this.pier = pier;
    }

    @Override
    public String toString() {
        return "Ship " + id + " {" +
                containers + ", " +
                state +
                '}';
    }
}
