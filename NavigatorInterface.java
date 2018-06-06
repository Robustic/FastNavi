package navigator;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NavigatorInterface {

    private GraphicsContext gc;
    private RoadNetwork roadNet;
    private DrawObjects drawObjects;

    private boolean defineStart;

    public NavigatorInterface(GraphicsContext gc, int width, int height) {
        this.gc = gc;
        this.roadNet = new RoadNetwork();
        this.drawObjects = new DrawObjects(gc, 1, width, height);
        this.defineStart = true;
    }

    public void start() {
        this.drawObjects.setScale(200);

        // read file input
        String fileName = "Network.txt";
        FileReader fileReader = new FileReader();
        fileReader.setFileName(fileName);
        fileReader.readFile(this.roadNet);

        // update heghbors
        this.roadNet.defineNeighbors();
    }

    public void writeFile() {
        // write file input
        String fileNameWrite = "Network.txt";
        FileWriter fileWriter = new FileWriter();
        fileWriter.setFileName(fileNameWrite);
        fileWriter.writeFile(this.roadNet);
    }

    public void defineNeighbors() {
        this.roadNet.defineNeighbors();
    }

    public void drawLocations() {
        this.gc.setFill(Color.BLACK);
        this.drawObjects.drawLocations(roadNet.getLocations());
        drawStartAndEndLocations();
    }

    public void drawStartAndEndLocations() {
        if (!(this.roadNet.getStart() == null)) {
            this.gc.setFill(Color.GREEN);
            this.drawObjects.drawLocation(this.roadNet.getStart());
        }
        if (!(this.roadNet.getEnd() == null)) {
            this.gc.setFill(Color.RED);
            this.drawObjects.drawLocation(this.roadNet.getEnd());
        }
    }

    public void drawRoads() {
        this.gc.setLineWidth(1);
        this.gc.setStroke(Color.BLACK);
        this.drawObjects.drawLines(roadNet.getStages());
    }

    public void drawFastestPath() {
        if (!(this.roadNet.getStart() == null) && !(this.roadNet.getEnd() == null)) {
            Location start = this.roadNet.getStart();
            Location end = this.roadNet.getEnd();
            Path fastestRoute = this.roadNet.fastestRoute(start, end);
            List<Stage> stages = this.roadNet.pathToStages(fastestRoute);
            this.gc.setLineWidth(5);
            this.gc.setStroke(Color.CORNFLOWERBLUE);
            this.drawObjects.drawLines(stages);
            this.gc.setFill(Color.CORNFLOWERBLUE);
            this.drawObjects.drawLocations(fastestRoute.getPath());
            drawStartAndEndLocations();
        }
    }

    public Location snappedLocation(int mouseX, int mouseY) {
        for (Location location : this.roadNet.getLocations()) {
            int locationX = drawObjects.graphX(location.getX());
            int locationY = drawObjects.graphY(location.getY());
            if (locationX - 5 <= mouseX && locationX + 5 >= mouseX && locationY - 5 <= mouseY && locationY + 5 >= mouseY) {
                return location;
            }
        }
        return null;
    }

    public void defineNewStageStartOrEnd(int mouseX, int mouseY) {
        if (this.defineStart == true) {
            defineNewStartLocation(mouseX, mouseY);
        } else {
            defineNewEndLocation(mouseX, mouseY);
        }
    }

    public void defineNewStartLocation(int mouseX, int mouseY) {
        Location startLocation = snappedLocation(mouseX, mouseY);
        if (!(startLocation == null)) {
            this.roadNet.setStart(startLocation);
            this.defineStart = false;
            this.roadNet.setEnd(null);
        } else {
            double x = drawObjects.realX(mouseX);
            double y = drawObjects.realY(mouseY);;
            Location newLocation = new Location(this.roadNet.largestIdentificationNumber() + 1, x, y);
            roadNet.addLocation(newLocation);
            this.roadNet.setStart(newLocation);
            this.defineStart = false;
            this.roadNet.setEnd(null);
        }
    }

    public void defineNewEndLocation(int mouseX, int mouseY) {
        Location endLocation = snappedLocation(mouseX, mouseY);
        if (!(endLocation == null)) {
            if (endLocation == this.roadNet.getStart()) {
                return;
            } else {
                if (this.roadNet.getStart() == null) {
                    this.roadNet.setEnd(endLocation);
                    this.defineStart = true;
                } else {
                    if (endLocation != this.roadNet.getStart()) {
                        this.roadNet.setEnd(endLocation);
                        this.defineStart = true;
                    }
                }
            }
        } else {
            double x = drawObjects.realX(mouseX);
            double y = drawObjects.realY(mouseY);;
            Location newLocation = new Location(this.roadNet.largestIdentificationNumber() + 1, x, y);
            roadNet.addLocation(newLocation);
            this.roadNet.setEnd(newLocation);
            this.defineStart = true;
        }
        Stage newStage = new Stage(this.roadNet.getStart(), this.roadNet.getEnd(), 60);
        roadNet.addStage(newStage);
        Stage newStageOpposite = new Stage(this.roadNet.getEnd(), this.roadNet.getStart(), 60);
        roadNet.addStage(newStageOpposite);
    }

    public void defineStartOrEndLocation(int mouseX, int mouseY) {
        if (this.defineStart == true) {
            setStartLocation(mouseX, mouseY);
        } else {
            setEndLocation(mouseX, mouseY);
        }
    }

    public void setStartLocation(int mouseX, int mouseY) {
        Location startLocation = snappedLocation(mouseX, mouseY);
        if (!(startLocation == null)) {
            this.roadNet.setStart(startLocation);
            this.defineStart = false;
            this.roadNet.setEnd(null);
        }
    }

    public void setEndLocation(int mouseX, int mouseY) {
        Location endLocation = snappedLocation(mouseX, mouseY);
        if (!(endLocation == null)) {
            if (this.roadNet.getStart() == null) {
                this.roadNet.setEnd(endLocation);
                this.defineStart = true;
            } else {
                if (endLocation != this.roadNet.getStart()) {
                    this.roadNet.setEnd(endLocation);
                    this.defineStart = true;
                }
            }
            this.drawFastestPath();
        }
    }

}
