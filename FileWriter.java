/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navigator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;

public class FileWriter {
    
    private String fileName;

    public FileWriter() {
        this.fileName = "";
    }
    
    public void setFileName(String file) {
        this.fileName = file;
    }

    public String getFileName() {
        return fileName;
    }
    
    public void writeFile(RoadNetwork network) {
        List<String> fileRows = new ArrayList<>();
        
        fileRows.add("#LOCATIONS");        
        // Add locations
        // 1;0;0 = idcode;x;y
        for (Location location : network.getLocations()) {
            String row = String.valueOf(location.getIdentificationNumber());
            row = row + ";" + String.valueOf(location.getX());
            row = row + ";" + String.valueOf(location.getY());
            fileRows.add(row);
        }

        // Add stages
        // 1;2;60 = location1;location2;speed
        fileRows.add("#STAGES");        
        // Add locations
        // 1;0;0 = idcode;x;y
        for (Stage stage : network.getStages()) {
            String row = String.valueOf(stage.getStart().getIdentificationNumber());
            row = row + ";" + String.valueOf(stage.getEnd().getIdentificationNumber());
            row = row + ";" + String.valueOf(stage.getSpeed());
            fileRows.add(row);
        }
                
        try (PrintWriter fileWriter = new PrintWriter(new File(this.fileName))) {
            for (String row : fileRows) {
                fileWriter.println(row);
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error when writing to file " + this.fileName + ": " + e.getMessage());
        }
        
    }    
}
