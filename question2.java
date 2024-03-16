//-----------------------------------------------------
//Title:Treasure Hunt (homework2 q2)
//Author: Melisa SUBAŞI
//ID: 22829169256
//Section: 1
//Assignment: 2
//Description: This Java program analyzes a maze file, identifies treasures,
//and outputs the number of treasures along with sorted paths to each.
//-----------------------------------------------------

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class question2 {

    private static char[][] maze;
    private static List<String> paths;
    private static int numRows;
    private static int numCols;

    public static void main(String[] args) {
        try {
            // Read maze file name from console
            BufferedReader reader = new BufferedReader(new FileReader("maze1.txt"));

            // Read maze dimensions
            String dimensionsLine = reader.readLine();
            String[] dimensions = dimensionsLine.trim().split("\\s+");
            
            if (dimensions.length != 2) {
                System.out.println("Invalid maze file format.");
                return;
            }

            numRows = Integer.parseInt(dimensions[0]);
            numCols = dimensions[1].length();  // Use the length of the second part as the number of columns

            // Initialize maze
            maze = new char[numRows][numCols];
            paths = new ArrayList<>();

            // Read maze content
            for (int i = 0; i < numRows; i++) {
                String line = reader.readLine();
                if (line.length() < numCols) {
                    System.out.println("Invalid maze file format.");
                    return;
                }
                maze[i] = line.toCharArray();
            }

            reader.close();

            // Find treasures and print paths
            findTreasures();
            printPaths();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findTreasures() {
        int treasureCount = 0;

        // Iterate through the maze to find treasures
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (maze[i][j] == 'E') {
                    // Start DFS to find paths to the treasure
                    dfs(i, j, "");
                    treasureCount++;
                }
            }
        }

        if (treasureCount == 0) {
            System.out.println("0 treasures are found.");
        } else {
            System.out.println(treasureCount + " treasures are found.");
        }
    }

    
    private static void dfs(int row, int col, String path) {
        // Check if the current position is out of bounds or a wall
        if (row < 0 || row >= numRows || col < 0 || col >= numCols || maze[row][col] == '+' || maze[row][col] == '-' || maze[row][col] == '|') {
            return;
        }

        // Check if the current position is a treasure
        if (maze[row][col] == 'E') {
            paths.add(path + maze[row][col]);
            return;
        }

        // Mark the current position as visited
        char original = maze[row][col];
        maze[row][col] = '*';

        // Explore all possible directions (up, down, left, right)
        dfs(row - 1, col, path + original);
        dfs(row + 1, col, path + original);
        dfs(row, col - 1, path + original);
        dfs(row, col + 1, path + original);

        // Restore the original character at the current position
        maze[row][col] = original;
    }

    private static void printPaths() {
        System.out.println("Paths are:");

        // Sort paths by length before printing
        paths.sort((a, b) -> Integer.compare(a.length(), b.length()));

        for (int i = 0; i < paths.size(); i++) {
            System.out.println((i + 1) + ") " + paths.get(i));
        }
    }
}
