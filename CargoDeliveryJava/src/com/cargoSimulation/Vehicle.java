package com.cargoSimulation;

public class Vehicle {
	private String id;
	private double volume;
	private DoublyLinkedList<Package> cargoPackages;
	public Vehicle(String id, double volume) {
		super();
		this.id = id;
		this.volume = volume;
		this.cargoPackages = new DoublyLinkedList<>();
		
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public DoublyLinkedList<Package> getCargoPackages() {
		return cargoPackages;
	}
	public void setCargoPackages(DoublyLinkedList<Package> cargoPackages) {
		this.cargoPackages = cargoPackages;
	}
	
	

}
