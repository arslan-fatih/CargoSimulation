package com.cargoSimulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * --------------------------------------------------------
 * Summary: Main class for the cargo simulation program.
 * Reads input files, processes missions, and writes the results to an output file.
 * --------------------------------------------------------
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java com.cargoSimulation.Main cities.txt packages.txt vehicles.txt missions.txt result.txt");
            return;
        }

        String citiesFile = args[0];
        String packagesFile = args[1];
        String vehiclesFile = args[2];
        String missionsFile = args[3];
        String resultFile = args[4];

        Map<String, City> cities = new HashMap<>();
        try {
            readCities(citiesFile, cities);
            readPackages(packagesFile, cities);
            readVehicles(vehiclesFile, cities);
            processMissions(missionsFile, cities);
            writeResults(resultFile, cities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * --------------------------------------------------------
     * Summary: Reads city names from the file and adds them to the cities map.
     * Precondition: filename is a valid file path; cities is not null.
     * Postcondition: cities map is populated with city names as keys and City objects as values.
     * --------------------------------------------------------
     */
    public static void readCities(String filename, Map<String, City> cities) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String cityName = line.trim();
            if (!cityName.isEmpty()) {
                cities.put(cityName, new City(cityName));
            }
        }
        br.close();
    }

    /**
     * --------------------------------------------------------
     * Summary: Reads packages from the file and adds them to the respective city's distribution center.
     * Precondition: filename is a valid file path; cities map is populated.
     * Postcondition: Packages are added to the corresponding city's package stack.
     * --------------------------------------------------------
     */
    public static void readPackages(String filename, Map<String, City> cities) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split(" ");
            if (parts.length == 2) {
                String packageId = parts[0];
                String cityName = parts[1];

                Package pkg = new Package(packageId, cityName);
                City city = cities.get(cityName);
                if (city != null) {
                    city.getDistributionCenter().getPackages().push(pkg);
                } else {
                    System.err.println("City not found: " + cityName);
                }
            }
        }
        br.close();
    }

    /**
     * --------------------------------------------------------
     * Summary: Reads vehicles from the file and adds them to the respective city's distribution center.
     * Precondition: filename is a valid file path; cities map is populated.
     * Postcondition: Vehicles are added to the corresponding city's vehicle queue.
     * --------------------------------------------------------
     */
    public static void readVehicles(String filename, Map<String, City> cities) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split(" ");
            if (parts.length == 3) {
                String vehicleId = parts[0];
                String cityName = parts[1];
                double volume = Double.parseDouble(parts[2]);

                Vehicle vehicle = new Vehicle(vehicleId, volume);
                City city = cities.get(cityName);
                if (city != null) {
                    city.getDistributionCenter().getVehicles().enqueue(vehicle);
                } else {
                    System.err.println("City not found: " + cityName);
                }
            }
        }
        br.close();
    }

    /**
     * --------------------------------------------------------
     * Summary: Processes the missions from the file.
     * Precondition: filename is a valid file path; cities map is populated.
     * Postcondition: Missions are executed, affecting the state of cities, vehicles, and packages.
     * --------------------------------------------------------
     */
    public static void processMissions(String filename, Map<String, City> cities) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            // Mission format: SourceCity-MiddleCity-DestCity-A-B-C1,C2,...
            String[] parts = line.trim().split("-");
            if (parts.length == 4) {
                String sourceCityName = parts[0];
                String middleCityName = parts[1];
                String destCityName = parts[2];

                String[] cargoInfo = parts[3].split("-");
                if (cargoInfo.length == 3) {
                    int a = Integer.parseInt(cargoInfo[0]);
                    int b = Integer.parseInt(cargoInfo[1]);
                    String[] dropOffIndices = cargoInfo[2].split(",");

                    executeMission(cities, sourceCityName, middleCityName, destCityName, a, b, dropOffIndices);
                }
            }
        }
        br.close();
    }

    /**
     * --------------------------------------------------------
     * Summary: Executes a single mission based on the provided parameters.
     * Precondition: cities map is populated; city names are valid; dropOffIndices are valid integers.
     * Postcondition: The mission is executed, affecting the state of involved cities, vehicles, and packages.
     * --------------------------------------------------------
     */
    public static void executeMission(Map<String, City> cities, String sourceCityName, String middleCityName,
                                      String destCityName, int a, int b, String[] dropOffIndices) {
        City sourceCity = cities.get(sourceCityName);
        City middleCity = cities.get(middleCityName);
        City destCity = cities.get(destCityName);

        if (sourceCity == null || middleCity == null || destCity == null) {
            System.err.println("One or more cities not found in mission.");
            return;
        }

        DistributionCenter sourceDC = sourceCity.getDistributionCenter();
        DistributionCenter middleDC = middleCity.getDistributionCenter();
        DistributionCenter destDC = destCity.getDistributionCenter();

        // 1. Get a vehicle from the source city
        Vehicle vehicle = sourceDC.getVehicles().dequeue();
        if (vehicle == null) {
            System.err.println("No vehicles available in source city: " + sourceCityName);
            return;
        }

        // 2. Load 'a' packages from the source city onto the vehicle
        for (int i = 0; i < a; i++) {
            Package pkg = sourceDC.getPackages().pop();
            if (pkg != null) {
                vehicle.getCargoPackages().addFirst(pkg);
            } else {
                System.err.println("Not enough packages in source city: " + sourceCityName);
                break;
            }
        }

        // 3. Load 'b' packages from the middle city onto the vehicle
        for (int i = 0; i < b; i++) {
            Package pkg = middleDC.getPackages().pop();
            if (pkg != null) {
                vehicle.getCargoPackages().addFirst(pkg);
            } else {
                System.err.println("Not enough packages in middle city: " + middleCityName);
                break;
            }
        }

        // 4. Drop off specified packages at the middle city
        // Indices need to be sorted in descending order
        int[] indices = new int[dropOffIndices.length];
        for (int i = 0; i < dropOffIndices.length; i++) {
            indices[i] = Integer.parseInt(dropOffIndices[i]);
        }
        java.util.Arrays.sort(indices);

        for (int i = indices.length - 1; i >= 0; i--) {
            int index = indices[i];
            Package pkg = vehicle.getCargoPackages().removeAt(index);
            if (pkg != null) {
                middleDC.getPackages().push(pkg);
            } else {
                System.err.println("Invalid package index: " + index);
            }
        }

        // 5. Deliver the vehicle and remaining packages to the destination city
        destDC.getVehicles().enqueue(vehicle);

        while (!vehicle.getCargoPackages().isEmpty()) {
            Package pkg = vehicle.getCargoPackages().removeFirst();
            destDC.getPackages().push(pkg);
        }
    }

    /**
     * --------------------------------------------------------
     * Summary: Writes the final state of each city to the output file.
     * Precondition: filename is a valid file path; cities map is populated.
     * Postcondition: Output file is created with the current state of cities.
     * --------------------------------------------------------
     */
    public static void writeResults(String filename, Map<String, City> cities) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

        for (City city : cities.values()) {
            bw.write(city.getName());
            bw.newLine();
            bw.write("Packages:");
            bw.newLine();

            MyStack<Package> packages = city.getDistributionCenter().getPackages();
            if (!packages.isEmpty()) {
                MyStack<Package> tempStack = new MyStack<>();
                while (!packages.isEmpty()) {
                    Package pkg = packages.pop();
                    bw.write(pkg.getId());
                    bw.newLine();
                    tempStack.push(pkg);
                }
                while (!tempStack.isEmpty()) {
                    packages.push(tempStack.pop());
                }
            } else {
                bw.write("No packages");
                bw.newLine();
            }

            bw.write("Vehicles:");
            bw.newLine();
            MyQueue<Vehicle> vehicles = city.getDistributionCenter().getVehicles();
            if (!vehicles.isEmpty()) {
                MyQueue<Vehicle> tempQueue = new MyQueue<>();
                while (!vehicles.isEmpty()) {
                    Vehicle vehicle = vehicles.dequeue();
                    bw.write(vehicle.getId());
                    bw.newLine();
                    tempQueue.enqueue(vehicle);
                }
                while (!tempQueue.isEmpty()) {
                    vehicles.enqueue(tempQueue.dequeue());
                }
            } else {
                bw.write("No vehicles");
                bw.newLine();
            }
        }

        bw.close();
    }
}
