package navigator;

import java.util.List;
import java.util.ArrayList;

public class Path {

    private double time;
    private List<Location> path;
    private Location lastLocation;
    private boolean validPath;

    public Path() {
        this.time = 0;
        this.path = new ArrayList<>();
        this.validPath = true;
    }
    
    public Path(List<Location> path, Location lastLocation, double time) {
        this.time = time;
        this.path = path;
        this.lastLocation = lastLocation;
        this.validPath = true;
    }

    public List<Location> getPath() {
        return path;
    }
    
    public List<Location> getPathAsNewList() {
        List<Location> newList = new ArrayList<>();
        for (Location location : this.path) {
            newList.add(location);
        }        
        return newList;
    }
    
    public void addLocation(Stage stage) {
        if (stage.defineTime() > 0) {
            this.path.add(stage.getEnd());
            this.lastLocation = stage.getEnd();
            this.addTime(stage.defineTime());
        }
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void addTime(double time) {
        this.time = this.time + time;
    }

    public Location getLastLocation() {
        return this.lastLocation;
    }

    public boolean isValidPath() {
        return this.validPath;
    }

    public void setValidPath(boolean validPath) {
        this.validPath = validPath;
    }

    @Override
    public String toString() {
        String print = "";
        for (Location location : this.path) {
            print = print + ", " + location.toStringID();
        }        
        return print;
    }
    
    
    
}
