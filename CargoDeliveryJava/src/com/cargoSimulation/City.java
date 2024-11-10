package com.cargoSimulation;

public class City {
	private String name;
	private DistributionCenter distributionCenter;
	public City(String name) {
		super();
		this.name = name;
		this.distributionCenter = new DistributionCenter ();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DistributionCenter getDistributionCenter() {
		return distributionCenter;
	}
	public void setDistributionCenter(DistributionCenter distributionCenter) {
		this.distributionCenter = distributionCenter;
	}
	
	
	
	

}
