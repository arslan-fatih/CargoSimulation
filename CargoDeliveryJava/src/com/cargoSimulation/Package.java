package com.cargoSimulation;

/**
 * --------------------------------------------------------
 * Summary: Represents a package to be delivered in the cargo simulation.
 * Each package has an ID and a destination city.
 * --------------------------------------------------------
 */
public class Package {
    private String id;
    private String city;

    /**
     * --------------------------------------------------------
     * Summary: Initializes a package with the given ID and city.
     * Precondition: id and city are not null.
     * Postcondition: Package is created with specified ID and city.
     * --------------------------------------------------------
     */
    public Package(String id, String city) {
        this.id = id;
        this.city = city;
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the ID of the package.
     * Precondition: None.
     * Postcondition: Returns the package's ID.
     * --------------------------------------------------------
     */
    public String getId() {
        return id;
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the destination city of the package.
     * Precondition: None.
     * Postcondition: Returns the package's destination city.
     * --------------------------------------------------------
     */
    public String getCity() {
        return city;
    }
}
