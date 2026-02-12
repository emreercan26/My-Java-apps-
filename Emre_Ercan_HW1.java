/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package emre_ercan_hw1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
 
public class Emre_Ercan_HW1 {

  private static final String DEFAULT_INPUT_PATH = "test.txt";
  private static final String DEFAULT_OUTPUT_PATH = "out.txt";
  private static final int[] FILE_SIZES = new int[]{100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000, 1000000};
  private static final String REPORT_OUTPUT_PATH = "report_times.txt";
    
    public static void main(String[] args) {
        
       
   System.out.println("--- PART B: Generating Large Test Files ---");
        TestDataGenerator.generateAll(FILE_SIZES);
        System.out.println("Test files generated successfully.");
        System.out.println("------------------------------------------");
        System.out.println("--- PART A: Processing Default File: test.txt ---");

        try {
            List<Student> studentList = readStudents("test.txt");
            Student[] students = (Student[])studentList.toArray(new Student[0]);
            long startTime = System.nanoTime();
            Arrays.sort(students);
            long endTime = System.nanoTime();
            long sortTimeNs = endTime - startTime;
            writeResults("out.txt", students, sortTimeNs);
            System.out.println("Part A results written to: out.txt");
        } catch (IOException | DataValidationException e) {
            System.err.println("--- FATAL ERROR IN PART A ---");
            System.err.println("Could not process default file. " + ((Exception)e).getMessage());
        }

        System.out.println("\n--- PART B: Comparative Sorting Time Analysis ---");
        System.out.println("Writing summary report to console and report_times.txt");
        List<String> reportLines = new ArrayList();
        reportLines.add("FILE\t\t\tN\tSORT_ALGO\tTIME_MS");

        for(int N : FILE_SIZES) {
            String filePath = "students_" + N + ".txt";

            try {
                List<Student> studentList = readStudents(filePath);
                Student[] students = (Student[])studentList.toArray(new Student[0]);
                long startTime = System.nanoTime();
                Arrays.sort(students);
                long endTime = System.nanoTime();
                long sortTimeNs = endTime - startTime;
                double sortTimeMs = (double)sortTimeNs / (double)1000000.0F;
                String reportLine = String.format("FILE=%s\tN=%d\tSORT=compare-by-ID\tTIME_MS=%.2f", filePath, N, sortTimeMs);
                System.out.println(reportLine);
                reportLines.add(reportLine);
            } catch (IOException | DataValidationException e) {
                System.err.println("Error processing Part B file " + filePath + ": " + ((Exception)e).getMessage());
                reportLines.add(String.format("FILE=%s\tN=%d\tERROR", filePath, N));
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report_times.txt"))) {
            for(String line : reportLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not write Part B report file: " + e.getMessage());
        }

        System.out.println("------------------------------------------");
    }

    public static List<Student> readStudents(String inputPath) throws IOException, DataValidationException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            String line = reader.readLine();
            if (line == null) {
                throw new DataValidationException("Input file is empty or missing the N (record count) header.");
            } else {
                int N;
                try {
                    N = Integer.parseInt(line.trim());
                } catch (NumberFormatException var17) {
                    throw new DataValidationException("First line (N) must be a valid integer.");
                }

                if (N < 0) {
                    throw new DataValidationException("The record count N must be zero or positive.");
                } else {
                    List<Student> students = new ArrayList(N);
                    int lineNumber = 1;

                    for(int i = 0; i < N; ++i) {
                        line = reader.readLine();
                        ++lineNumber;
                        if (line == null) {
                            throw new DataValidationException("Expected " + N + " records, but file ended early at line " + lineNumber);
                        }

                        String delimiter = line.contains(",") ? "," : "\\s+";
                        String[] fields = line.trim().split(delimiter);
                        if (fields.length != 5) {
                            throw new DataValidationException("Record at line " + lineNumber + " must have exactly 5 fields (found " + fields.length + "): " + line);
                        }

                        try {
                            long id = Long.parseLong(fields[0]);
                            String name = fields[1];
                            String surname = fields[2];
                            int age = Integer.parseInt(fields[3]);
                            int atdYear = Integer.parseInt(fields[4]);
                            students.add(new Student(id, name, surname, age, atdYear));
                        } catch (NumberFormatException e) {
                            throw new DataValidationException("Invalid numeric format in record at line " + lineNumber + ". Details: " + e.getMessage());
                        }
                    }

                    if (reader.readLine() != null) {
                        System.err.println("Warning: File contains extra lines after the expected " + N + " records.");
                    }

                    PrintStream var10000 = System.out;
                    int var10001 = students.size();
                    var10000.println("Successfully read " + var10001 + " records from " + inputPath);
                    return students;
                }
            }
        } catch (IOException e) {
            throw new IOException("File I/O Error for " + inputPath + ": " + e.getMessage(), e);
        }
    }

    public static void writeResults(String outputPath, Student[] students, long sortTimeNs) throws IOException {
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
                for(Student student : students) {
                    writer.write(student.toString());
                    writer.newLine();
                }

                double sortTimeMs = (double)sortTimeNs / (double)1000000.0F;
                double sortTimeUs = (double)sortTimeNs / (double)1000.0F;
                String timeLine = String.format(Locale.US, "Time: %.2f ms (%.2f μs)", sortTimeMs, sortTimeUs);
                writer.write(timeLine);
                System.out.println("Timing result: " + timeLine);
            }

        } catch (IOException e) {
            throw new IOException("File I/O Error while writing to " + outputPath + ": " + e.getMessage(), e);
        }
    }

    public static class DataValidationException extends Exception {
        public DataValidationException(String message) {
            super(message);
        }
    }
}