package com.cargoSimulation;

public class DistributionCenter {
    private MyStack<Package> packages;
    private MyQueue<Vehicle> vehicles;

    public DistributionCenter() {
        this.packages = new MyStack<>();
        this.vehicles = new MyQueue<>();
    }

    public MyStack<Package> getPackages() {
        return packages;
    }

    public MyQueue<Vehicle> getVehicles() {
        return vehicles;
    }
}
