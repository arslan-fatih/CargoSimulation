package com.cargoSimulation;

import java.io.*;
import java.util.*;

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

    // Şehirleri okur ve haritaya ekler
    public static void readCities(String filename, Map<String, City> cities) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String cityName = line.trim();
                if (!cityName.isEmpty()) {
                    cities.put(cityName, new City(cityName));
                }
            }
        }
    }

    // Paketleri okur ve ilgili şehrin dağıtım merkezine ekler
    public static void readPackages(String filename, Map<String, City> cities) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
        }
    }

    // Araçları okur ve ilgili şehrin dağıtım merkezine ekler
    public static void readVehicles(String filename, Map<String, City> cities) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
        }
    }

    // Misyonları işler
    public static void processMissions(String filename, Map<String, City> cities) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
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
        }
    }

    // Misyonu uygular
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

        // 1. Kaynak şehirden araç al
        Vehicle vehicle = sourceDC.getVehicles().dequeue();
        if (vehicle == null) {
            System.err.println("No vehicles available in source city: " + sourceCityName);
            return;
        }

        // 2. Kaynak şehirden a adet paket yükle
        loadPackages(sourceDC, vehicle, a, sourceCityName);

        // 3. Orta şehirden b adet paket yükle
        loadPackages(middleDC, vehicle, b, middleCityName);

        // 4. Belirtilen paketleri orta şehirde bırak
        dropPackages(vehicle, middleDC, dropOffIndices);

        // 5. Aracı ve kalan paketleri varış şehrine bırak
        destDC.getVehicles().enqueue(vehicle);

        // Kalan paketleri varış şehrinin paket yığınına ekle
        while (!vehicle.getCargoPackages().isEmpty()) {
            Package pkg = vehicle.getCargoPackages().removeFirst();
            destDC.getPackages().push(pkg);
        }
    }

    private static void loadPackages(DistributionCenter dc, Vehicle vehicle, int numPackages, String cityName) {
        for (int i = 0; i < numPackages; i++) {
            Package pkg = dc.getPackages().pop();
            if (pkg != null) {
                vehicle.getCargoPackages().addFirst(pkg);
            } else {
                System.err.println("Not enough packages in city: " + cityName);
                break;
            }
        }
    }

    private static void dropPackages(Vehicle vehicle, DistributionCenter middleDC, String[] dropOffIndices) {
        int[] indices = new int[dropOffIndices.length];
        for (int i = 0; i < dropOffIndices.length; i++) {
            indices[i] = Integer.parseInt(dropOffIndices[i]);
        }
        Arrays.sort(indices);

        for (int i = indices.length - 1; i >= 0; i--) {
            int index = indices[i];
            Package pkg = vehicle.getCargoPackages().removeAt(index);
            if (pkg != null) {
                middleDC.getPackages().push(pkg);
            } else {
                System.err.println("Invalid package index: " + index);
            }
        }
    }

    // Sonuçları belirtilen dosyaya yazar
    public static void writeResults(String filename, Map<String, City> cities) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (City city : cities.values()) {
                bw.write(city.getName());
                bw.newLine();
                bw.write("Packages:");
                bw.newLine();

                writeStackPackages(bw, city);

                bw.write("Vehicles:");
                bw.newLine();

                writeQueueVehicles(bw, city);

                bw.write("-------------");
                bw.newLine();
            }
        }
    }

    private static void writeStackPackages(BufferedWriter bw, City city) throws IOException {
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
        }
    }

    private static void writeQueueVehicles(BufferedWriter bw, City city) throws IOException {
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
        }
    }
}
