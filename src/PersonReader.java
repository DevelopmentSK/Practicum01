import javax.swing.JFileChooser;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonReader {
    public static void main(String[] args) {
        Scanner inConsole = new Scanner(System.in);
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        ArrayList<String> lines = new ArrayList<>();
        final int FIELDS_LENGTH = 5;

        System.out.println("Person File Reader");
        System.out.println("------------------");

        if (!SafeInput.getYNConfirm(inConsole, "Ready to choose a Person data file")) {
            System.out.println("No file selected. Run the program again when ready.");
            return;
        }

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream input = new BufferedInputStream(Files.newInputStream(file));
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String record;
                while ((record = reader.readLine()) != null) {
                    lines.add(record);
                }
                reader.close();

                System.out.println("\nFile: " + selectedFile.getName());
                System.out.printf("%-10s %-15s %-15s %-10s %6s%n",
                        "ID#", "Firstname", "Lastname", "Title", "YOB");
                System.out.println("------------------------------------------------------------");

                for (String line : lines) {
                    String[] fields = line.split(",", -1);

                    if (fields.length == FIELDS_LENGTH) {
                        try {
                            String id = fields[0].trim();
                            String firstName = fields[1].trim();
                            String lastName = fields[2].trim();
                            String title = fields[3].trim();
                            int yearOfBirth = Integer.parseInt(fields[4].trim());

                            System.out.printf("%-10s %-15s %-15s %-10s %6d%n",
                                    id, firstName, lastName, title, yearOfBirth);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid year of birth in record: " + line);
                        }
                    } else {
                        System.out.println("Found a record that may be corrupt: " + line);
                    }
                }
            } else {
                System.out.println("No file selected. Run the program again and select a file.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading the data file: " + e.getMessage());
        }
    }
}
