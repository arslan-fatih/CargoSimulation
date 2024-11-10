package com.cargoSimulation;

/**
 * --------------------------------------------------------
 * Summary: Represents a vehicle used in the cargo simulation.
 * Each vehicle has an ID, volume capacity, and carries packages.
 * --------------------------------------------------------
 */
public class Vehicle {
    private String id;
    private double volume;
    private DoublyLinkedList<Package> cargoPackages;

    /**
     * --------------------------------------------------------
     * Summary: Initializes a vehicle with the given ID and volume.
     * Precondition: id is not null; volume is a positive number.
     * Postcondition: Vehicle is created with an empty cargo package list.
     * --------------------------------------------------------
     */
    public Vehicle(String id, double volume) {
        this.id = id;
        this.volume = volume;
        this.cargoPackages = new DoublyLinkedList<>();
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the ID of the vehicle.
     * Precondition: None.
     * Postcondition: Returns the vehicle's ID.
     * --------------------------------------------------------
     */
    public String getId() {
        return id;
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the volume capacity of the vehicle.
     * Precondition: None.
     * Postcondition: Returns the vehicle's volume capacity.
     * --------------------------------------------------------
     */
    public double getVolume() {
        return volume;
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the list of cargo packages in the vehicle.
     * Precondition: None.
     * Postcondition: Returns the cargo packages list.
     * --------------------------------------------------------
     */
    public DoublyLinkedList<Package> getCargoPackages() {
        return cargoPackages;
    }
}
