package navigator;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.lang.Character;

public class FileReader {

    private String fileName;

    public FileReader() {
        this.fileName = "";
    }

    public void setFileName(String file) {
        this.fileName = file;
    }

    public String getFileName() {
        return fileName;
    }

    public RoadNetwork readFile(RoadNetwork network) {
        List<String> fileRows = new ArrayList<>();
        try (Scanner fileReader = new Scanner(new File(this.fileName))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                if (!line.substring(0, 2).equals("//") && line.length() != 0) {
                    fileRows.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error when reading line from file " + this.fileName + ": " + e.getMessage());
        }
        fileRows.add("#END");

        // Add locations
        // 1;0;0 = idcode;x;y
        int rowNumber = 0;
        while (rowNumber < fileRows.size()) {
            if (fileRows.get(rowNumber).equals("#LOCATIONS")) {
                break;
            }
            rowNumber++;
        }
        if (rowNumber == fileRows.size()) {
            System.out.println("#LOCATIONS are missed");
        } else {
            rowNumber++;
            while (rowNumber < fileRows.size()) {
                if (Character.isDigit(fileRows.get(rowNumber).charAt(0))) {
                    String[] parts = fileRows.get(rowNumber).split(";");
                    if (!(parts.length < 3)) {
                        network.addLocation(new Location(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
                    }
                } else {
                    break;
                }
                rowNumber++;
            }
        }

        // Add stages
        // 1;2;60 = location1;location2;speed
        rowNumber = 0;
        while (rowNumber < fileRows.size()) {
            if (fileRows.get(rowNumber).equals("#STAGES")) {
                break;
            }
            rowNumber++;
        }
        if (rowNumber == fileRows.size()) {
            System.out.println("#STAGES are missed");
        } else {
            rowNumber++;
            while (rowNumber < fileRows.size()) {
                if (Character.isDigit(fileRows.get(rowNumber).charAt(0))) {
                    String[] parts = fileRows.get(rowNumber).split(";");
                    if (!(parts.length < 3)) {
                        Location locationStart = network.getLocationWithID(Integer.parseInt(parts[0]));
                        Location locationEnd = network.getLocationWithID(Integer.parseInt(parts[1]));
                        if (locationStart != null && locationEnd != null) {
                            network.addStage(new Stage(locationStart, locationEnd, Double.parseDouble(parts[2])));
                        }
                    }
                } else {
                    break;
                }
                rowNumber++;
            }
        }

        return network;
    }

}
