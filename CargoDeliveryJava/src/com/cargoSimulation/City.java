package com.cargoSimulation;

/**
 * --------------------------------------------------------
 * Summary: Represents a city in the cargo simulation.
 * Each city has a name and a distribution center.
 * --------------------------------------------------------
 */
public class City {
    private String name;
    private DistributionCenter distributionCenter;

    /**
     * --------------------------------------------------------
     * Summary: Initializes a city with the given name and a new distribution center.
     * Precondition: name is not null.
     * Postcondition: City is created with the specified name and an empty distribution center.
     * --------------------------------------------------------
     */
    public City(String name) {
        this.name = name;
        this.distributionCenter = new DistributionCenter();
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the name of the city.
     * Precondition: None.
     * Postcondition: Returns the city's name.
     * --------------------------------------------------------
     */
    public String getName() {
        return name;
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the distribution center of the city.
     * Precondition: None.
     * Postcondition: Returns the city's distribution center.
     * --------------------------------------------------------
     */
    public DistributionCenter getDistributionCenter() {
        return distributionCenter;
    }
}
