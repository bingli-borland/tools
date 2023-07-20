package com.bingli.tools;

import java.io.FileWriter;
import java.io.IOException;

public class TestCSV {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        int count = 10;
        for (int i=0; i<count;i++) {
            String name = "C:\\Users\\bes\\Downloads\\users" + i + ".csv";
            create(i, name);
        }
    }


    private static void create(int index, String filename) {

        int numberOfUsernames = 500000;
        int count = 10;
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = numberOfUsernames / count * index; i < numberOfUsernames / count * (index + 1); i++) {
                String username = "user" + i;
                writer.append(username);
                writer.append("\n");
            }
            writer.flush();
            System.out.println("CSV file generated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }

    }

    private static void all() {
        String csvFile = "users.csv";
        int numberOfUsernames = 500000;

        try (FileWriter writer = new FileWriter(csvFile)) {
            for (int i = 1; i <= numberOfUsernames; i++) {
                String username = "user" + i;
                writer.append(username);
                writer.append("\n");
            }
            writer.flush();
            System.out.println("CSV file generated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}