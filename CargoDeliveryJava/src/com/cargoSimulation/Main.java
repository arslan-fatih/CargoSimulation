package com.cargoSimulation;

import java.io.*;
import java.util.ArrayList;

/**
 * --------------------------------------------------------
 * Summary: Main class for the cargo simulation program.
 * Reads input files, processes missions, and writes the results to an output file.
 * Does not use iterators.
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

        ArrayList<City> cities = new ArrayList<>();
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
     * Summary: Reads city names from the file and adds them to the cities list.
     * Precondition: filename is a valid file path; cities is not null.
     * Postcondition: cities list is populated with City objects.
     * --------------------------------------------------------
     */
    public static void readCities(String filename, ArrayList<City> cities) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String cityName = line.trim();
            if (!cityName.isEmpty()) {
                cities.add(new City(cityName));
            }
        }
        br.close();
    }

    /**
     * --------------------------------------------------------
     * Summary: Finds a city in the list by its name.
     * Precondition: cities list is populated; cityName is not null.
     * Postcondition: Returns the City object with the matching name or null if not found.
     * --------------------------------------------------------
     */
    public static City findCityByName(ArrayList<City> cities, String cityName) {
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    /**
     * --------------------------------------------------------
     * Summary: Reads packages from the file and adds them to the respective city's distribution center.
     * Precondition: filename is a valid file path; cities list is populated.
     * Postcondition: Packages are added to the corresponding city's package stack.
     * --------------------------------------------------------
     */
    public static void readPackages(String filename, ArrayList<City> cities) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split(" ");
            if (parts.length == 2) {
                String packageId = parts[0];
                String cityName = parts[1];

                Package pkg = new Package(packageId, cityName);
                City city = findCityByName(cities, cityName);
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
     * Precondition: filename is a valid file path; cities list is populated.
     * Postcondition: Vehicles are added to the corresponding city's vehicle queue.
     * --------------------------------------------------------
     */
    public static void readVehicles(String filename, ArrayList<City> cities) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split(" ");
            if (parts.length == 3) {
                String vehicleId = parts[0];
                String cityName = parts[1];
                double volume = Double.parseDouble(parts[2]);

                Vehicle vehicle = new Vehicle(vehicleId, volume);
                City city = findCityByName(cities, cityName);
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
     * Precondition: filename is a valid file path; cities list is populated.
     * Postcondition: Missions are executed, affecting the state of cities, vehicles, and packages.
     * --------------------------------------------------------
     */
    public static void processMissions(String filename, ArrayList<City> cities) throws IOException {
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
     * Summary: Sorts an array of integers in descending order using bubble sort.
     * Precondition: indices array is not null.
     * Postcondition: indices array is sorted in descending order.
     * --------------------------------------------------------
     */
    public static void sortIndicesDescending(int[] indices) {
        int n = indices.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (indices[j] < indices[j + 1]) {
                    int temp = indices[j];
                    indices[j] = indices[j + 1];
                    indices[j + 1] = temp;
                }
            }
        }
    }

    /**
     * --------------------------------------------------------
     * Summary: Executes a single mission based on the provided parameters.
     * Precondition: cities list is populated; city names are valid; dropOffIndices are valid integers.
     * Postcondition: The mission is executed, affecting the state of involved cities, vehicles, and packages.
     * --------------------------------------------------------
     */
    public static void executeMission(ArrayList<City> cities, String sourceCityName, String middleCityName,
                                      String destCityName, int a, int b, String[] dropOffIndices) {
        City sourceCity = findCityByName(cities, sourceCityName);
        City middleCity = findCityByName(cities, middleCityName);
        City destCity = findCityByName(cities, destCityName);

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
        int[] indices = new int[dropOffIndices.length];
        for (int i = 0; i < dropOffIndices.length; i++) {
            indices[i] = Integer.parseInt(dropOffIndices[i]);
        }
        sortIndicesDescending(indices);

        for (int i = 0; i < indices.length; i++) {
            int index = indices[i];
            Package pkg = vehicle.getCargoPackages().removeAt(index);
            if (pkg != null) {
                middleDC.getPackages().push(pkg);
            } else {
                System.err.println("Invalid package index: " + index);
            }
        }

        // 5. Continue to the destination and drop off remaining packages
        while (!vehicle.getCargoPackages().isEmpty()) {
            Package pkg = vehicle.getCargoPackages().pop();
            if (pkg != null) {
                destDC.getPackages().push(pkg);
            }
        }

        // 6. Return vehicle to destination city
        destDC.getVehicles().enqueue(vehicle);
    }

    /**
     * --------------------------------------------------------
     * Summary: Writes the simulation results to the specified output file.
     * Precondition: filename is a valid file path; cities list is populated.
     * Postcondition: Results are written to the output file.
     * --------------------------------------------------------
     */
    public static void writeResults(String filename, ArrayList<City> cities) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (City city : cities) {
            DistributionCenter dc = city.getDistributionCenter();
            bw.write(city.getName() + ":\n");

            // Write vehicles in the city's distribution center
            bw.write("  Vehicles: ");
            Vehicle vehicle = dc.getVehicles().peek();
            while (vehicle != null) {
                bw.write(vehicle.getId() + " ");
                dc.getVehicles().dequeue();
                vehicle = dc.getVehicles().peek();
            }
            bw.write("\n");

            // Write packages in the city's distribution center
            bw.write("  Packages: ");
            Package pkg = dc.getPackages().peek();
            while (pkg != null) {
                bw.write(pkg.getId() + " ");
                dc.getPackages().pop();
                pkg = dc.getPackages().peek();
            }
            bw.write("\n");
        }
        bw.close();
    }
}
