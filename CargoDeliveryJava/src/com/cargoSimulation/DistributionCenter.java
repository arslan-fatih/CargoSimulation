package com.cargoSimulation;

/**
 * --------------------------------------------------------
 * Summary: Represents a distribution center in a city.
 * Holds packages and vehicles available in the city.
 * --------------------------------------------------------
 */
public class DistributionCenter {
    private MyStack<Package> packages;
    private MyQueue<Vehicle> vehicles;

    /**
     * --------------------------------------------------------
     * Summary: Initializes a distribution center with empty packages and vehicles.
     * Precondition: None.
     * Postcondition: Distribution center is initialized with empty stacks and queues.
     * --------------------------------------------------------
     */
    public DistributionCenter() {
        this.packages = new MyStack<>();
        this.vehicles = new MyQueue<>();
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the stack of packages in the distribution center.
     * Precondition: None.
     * Postcondition: Returns the packages stack.
     * --------------------------------------------------------
     */
    public MyStack<Package> getPackages() {
        return packages;
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the queue of vehicles in the distribution center.
     * Precondition: None.
     * Postcondition: Returns the vehicles queue.
     * --------------------------------------------------------
     */
    public MyQueue<Vehicle> getVehicles() {
        return vehicles;
    }
}
