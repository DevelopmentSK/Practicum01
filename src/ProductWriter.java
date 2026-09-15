import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean enterAnother;

        System.out.println("Product File Writer");
        System.out.println("-------------------");

        do {
            String id = SafeInput.getNonZeroLenString(in, "Enter the ID");
            String name = SafeInput.getNonZeroLenString(in, "Enter the product name");
            String description = SafeInput.getNonZeroLenString(in, "Enter the description");
            double cost = SafeInput.getRangedDouble(in, "Enter the cost", 0, 1000000);

            String record = id + ", " + name + ", " + description + ", " + cost;
            records.add(record);
            System.out.println("\nComplete record added: " + record);

            enterAnother = SafeInput.getYNConfirm(in, "Enter another product");
        } while (enterAnother);

        String fileName = SafeInput.getNonZeroLenString(in, "Enter the output file name");
        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName += ".txt";
        }

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath(), fileName);

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for (String record : records) {
                writer.write(record, 0, record.length());
                writer.newLine();
            }

            writer.close();
            System.out.println("\nData file written: " + file.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing the data file: " + e.getMessage());
        }
    }
}
