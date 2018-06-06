package navigator;

import java.util.List;
import java.util.ArrayList;

public class Location {
    private int identificationNumber;
    private double x;
    private double y;
    private List<Stage> neighbors;
    
    private double timeFromStart;
    private List<Location> path;

    public Location(int identificationNumber, double x, double y) {
        this.identificationNumber = identificationNumber;
        this.x = x;
        this.y = y;
        this.neighbors = new ArrayList<>();
        this.timeFromStart = -1;
        this.path = new ArrayList<>();;
    }
    
    public void clearTime() {
        this.timeFromStart = -1;
    }

    public int getIdentificationNumber() {
        return this.identificationNumber;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public void addNeighbor(Stage stage) {
        this.neighbors.add(stage);
    }

    public List<Stage> getNeighbors() {
        return neighbors;
    }  

    public void setPath(List<Location> path) {
        this.path = path;
    }

    public List<Location> getPath() {
        return path;
    }   
    
    public double distance(Location otherLocation) {        
        return Math.sqrt((this.getX()-otherLocation.getX())*(this.getX()-otherLocation.getX()) + (this.getY()-otherLocation.getY())*(this.getY()-otherLocation.getY()));
    }

    @Override
    public String toString() {
        return "Location: ID=" + this.identificationNumber + ", x=" + this.x + ", y=" + this.y;
    }
    
    public List<Stage> defineNeighbors(List<Stage> stages) {
        List<Stage> neighbors = new ArrayList<>();
        for (Stage stage : stages) {
            if (stage.getStart() == this) {
                this.addNeighbor(stage);
            }
        }        
        return neighbors;
    }

    public double getTimeFromStart() {
        return timeFromStart;
    }

    public void setTimeFromStart(double timeFromStart) {
        this.timeFromStart = timeFromStart;
    }
    
    public String toStringID() {
        return "" + this.identificationNumber;
    }
    
    public String toStringTime() {
        return "" + this.identificationNumber + "-" + this.getTimeFromStart();
    }
}
