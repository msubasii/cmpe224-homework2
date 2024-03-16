//-----------------------------------------------------
//Title:Flight Network (homework2 q1)
//Author: Melisa SUBAŞI
//ID: 22829169256
//Section: 1
//Assignment: 2
//Description: The code reads a file representing city routes, constructs a flight network 
//graph, and outputs the number and details of routes from a specified source city within a given number of hops.
//-----------------------------------------------------

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class question1 {
    private final Map<String, List<String>> graph;

    public question1() {
        this.graph = new HashMap<>();
    }

    public void addRoute(String source, String destination) {
        graph.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
    }

    public List<String> findCitiesWithHops(String source, int hops) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        findCitiesWithHopsHelper(source, hops, visited, result);
        return result;
    }

    private void findCitiesWithHopsHelper(String currentCity, int remainingHops,
                                          Set<String> visited, List<String> result) {
        if (remainingHops < 0) {
            return;
        }

        visited.add(currentCity);

        List<String> neighbors = graph.getOrDefault(currentCity, Collections.emptyList());
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                findCitiesWithHopsHelper(neighbor, remainingHops - 1, visited, result);
            }
        }

        // Add the current city to the result only if remainingHops is exactly 0
        if (remainingHops == 0) {
            result.add(currentCity);
        }
    }

    public static void main(String[] args) {
        // Hardcoded file path
        String fileName = "input.txt";

        question1 flightNetwork = new question1();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cities = line.split(",");
                flightNetwork.addRoute(cities[0], cities[1]);
            }

            // Assuming hops and source city are determined based on the first line of the file
            int numberOfHops = 2;  // Set a default value
            String sourceCity = "Ankara";  // Set a default value
            String firstLine = br.readLine();
            if (firstLine != null) {
                String[] cities = firstLine.split(",");
                numberOfHops = cities.length - 1;
                sourceCity = cities[0];
            }

            List<String> reachableCities = flightNetwork.findCitiesWithHops(sourceCity, numberOfHops);

            System.out.println("Number of total routes: " + reachableCities.size());
            System.out.println("Routes are:");
            System.out.println(String.join("-", reachableCities));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
