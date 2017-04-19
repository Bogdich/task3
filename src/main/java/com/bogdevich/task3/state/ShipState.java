package com.bogdevich.task3.state;

import com.bogdevich.task3.entity.Harbor;
import com.bogdevich.task3.entity.Ship;
import com.bogdevich.task3.exception.HarborException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by eugene on 12.4.17.
 */
public enum ShipState {
    UPLOAD {
        /**
         * Load containers TO ship
         *
         * @param ship a thread
         * @throws HarborException to notify about harbor free space problem
         * @throws InterruptedException TimeUnit
         */
        @Override
        public void action(Ship ship) throws HarborException, InterruptedException {

            int ship_free_space = ship.getCapacity() - ship.getContainers();

            if (Harbor.getInstance().getContainers() < 1) {

                throw new HarborException(" Empty storage");

            } else if (Harbor.getInstance().getContainers() >= ship_free_space) {

                Harbor.getInstance().setContainers(Harbor.getInstance().getContainers() - ship_free_space);
                ship.setContainers(ship.getCapacity());

                TimeUnit.MILLISECONDS.sleep(ship_free_space);

                LOGGER.log(Level.INFO, ship.toString() + " UPLOAD OK");

            } else if (Harbor.getInstance().getContainers() < ship_free_space) {

                LOGGER.log(Level.INFO,
                        "There is not enough containers in the storage for " + ship.toString()
                                + " " + Harbor.getInstance().getContainers()
                );

                TimeUnit.MILLISECONDS.sleep(Harbor.getInstance().getContainers());

                ship.setContainers(Harbor.getInstance().getContainers());
                Harbor.getInstance().setContainers(0);

                LOGGER.log(Level.INFO, ship.toString() + " UPLOAD OK (not full)");
            }
        }
    },

    UNLOAD {
        /**
         * Unload containers FROM ship
         *
         * @param ship a thread
         * @throws HarborException to notify about harbor free space problem
         * @throws InterruptedException TimeUnit
         */
        @Override
        public void action(Ship ship) throws HarborException, InterruptedException {
            int storage_free_space = Harbor.getInstance().CAPACITY - Harbor.getInstance().getContainers();

            if (storage_free_space < 1) {

                throw new HarborException(" Storage full");

            } else if (storage_free_space >= ship.getContainers()) {

                TimeUnit.MILLISECONDS.sleep(ship.getContainers());

                Harbor.getInstance().setContainers(Harbor.getInstance().getContainers() + ship.getContainers());
                ship.setContainers(0);

                LOGGER.log(Level.INFO, ship.toString() + " UNLOAD OK");

            } else if (storage_free_space < ship.getContainers()) {

                LOGGER.log(Level.INFO,
                        "There is not enough space in the storage for " + ship.toString()
                                + " Storage space: " + storage_free_space
                );

                TimeUnit.MILLISECONDS.sleep(storage_free_space);

                Harbor.getInstance().setContainers(Harbor.getInstance().CAPACITY);
                ship.setContainers(ship.getContainers() - storage_free_space);

                LOGGER.log(Level.INFO, ship.toString() + " UNLOAD OK (not full)");
            }
        }
    },

    RELOAD {
        /**
         * Use UNLOAD & UPLOAD methods
         *
         * @param ship
         * @throws HarborException to notify about harbor free space problem
         * @throws InterruptedException TimeUnit
         */
        @Override
        public void action(Ship ship) throws HarborException, InterruptedException {
            UNLOAD.action(ship);
            UPLOAD.action(ship);
        }
    };

    private static Logger LOGGER = LogManager.getLogger();

    public abstract void action(Ship ship) throws HarborException, InterruptedException;
}
