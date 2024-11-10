package com.cargoSimulation;

import java.util.Queue;
import java.util.Stack;

public class DistributionCenter {
	private Stack<Package> packages;
	private Queue<Vehicle> vehicles;
	public DistributionCenter() {
		super();
		this.packages = packages;
		this.vehicles = vehicles;
	}
	public Stack<Package> getPackages() {
		return packages;
	}
	public void setPackages(Stack<Package> packages) {
		this.packages = packages;
	}
	public Queue<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(Queue<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	

}
