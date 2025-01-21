import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EditJsonFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Step 1: Import JSON file
            System.out.print("Enter the path to the JSON file: ");
            String filePath = scanner.nextLine();
            File jsonFile = new File(filePath);

            if (!jsonFile.exists()) {
                System.out.println("File not found!");
                return;
            }

            JsonNode rootNode = objectMapper.readTree(jsonFile);
            System.out.println("Current JSON content: \n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));

            // Step 2: Allow user to edit an object
            System.out.print("Enter the key of the object to edit: ");
            String keyToEdit = scanner.nextLine();

            if (!rootNode.has(keyToEdit)) {
                System.out.println("Key not found in JSON.");
                return;
            }

            System.out.print("Enter the new value for the key '" + keyToEdit + "': ");
            String newValue = scanner.nextLine();

            ((ObjectNode) rootNode).put(keyToEdit, newValue);

            System.out.println("Updated JSON content: \n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));

            // Step 3: Save updated JSON to a new file
            System.out.print("Enter the path to save the updated JSON file: ");
            String newFilePath = scanner.nextLine();

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(newFilePath), rootNode);

            System.out.println("Updated JSON file saved successfully at: " + newFilePath);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
