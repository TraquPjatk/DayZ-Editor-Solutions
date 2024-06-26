package Utilities;

import GraphicInterfaces.Constants.Interfaces.UserPathConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CustomClassExtractor implements UserPathConstants {

    public CustomClassExtractor(String path) throws IOException {
        createDirectoryIfAbsent();
        importToResourceFile(fetchFileName(path), readFromFile(path));
//TODO        importToResourceFile(fetchFileName(path));
    }

    private static void createDirectoryIfAbsent() {
        File customResourceFile = new File(CUSTOM_RESOURCES_PATH);
        if (!customResourceFile.exists()) {
            if (customResourceFile.mkdirs()) {
                System.out.println("Created new directory: " + CUSTOM_RESOURCES_PATH);
            }
        }
    }

    private List<String> readFromFile(String path) throws IOException {
        List<String> linesList = new ArrayList<>();

        File sourceFile = new File(path);
        BufferedReader fileReader = new BufferedReader(new FileReader(sourceFile.getPath()));
        String line;
        while ((line = fileReader.readLine()) != null) {
            if (line.contains("<group name=\"")) {
                linesList.add(line);
            }
        }
        return linesList;
    }

    private static String fetchFileName(String path) {
        String[] split = path.split("\\\\");
        String[] splitByFileExtention = split[split.length - 1].split("\\.");
        return splitByFileExtention[0];
    }

    public static void importToResourceFile(String fileName, List<String> linesList) {
        try {
            PrintWriter customResourceWriter = new PrintWriter(CUSTOM_RESOURCES_PATH + "\\" + fileName);
            for (int i = 0; i < linesList.size(); i++) {
                String line = linesList.get(i);
                String[] split = line.split("\"");
                if (split.length != 1) {
                    customResourceWriter.print(split[1]);
                    if (i < linesList.size() - 1) {
                        customResourceWriter.println();
                    }
                }
            }
            customResourceWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}