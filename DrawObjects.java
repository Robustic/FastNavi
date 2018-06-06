package navigator;

import java.util.List;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class DrawObjects {

    private GraphicsContext gc;
    private double scale;
    private int origoX;
    private int origoY;

    public DrawObjects(GraphicsContext gc, double scale, int width, int height) {
        this.gc = gc;
        this.scale = scale;
        this.origoX = (int) width / 2;
        this.origoY = (int) height / 2;
    }

    public void drawLine(Stage stage) {
        int startX = graphX(stage.getStart().getX());
        int startY = graphY(stage.getStart().getY());
        int endX = graphX(stage.getEnd().getX());
        int endY = graphY(stage.getEnd().getY());

        this.gc.strokeLine(startX, startY, endX, endY);
    }

    public int graphX(double x) {
        return (int) (x * this.scale) + this.origoX;
    }

    public int graphY(double y) {
        return (int) (y * this.scale) + this.origoY;
    }
    
    public double realX(int x) {
        return (double) (x - this.origoX) / this.scale;
    }
    
    public double realY(int y) {
        return (double) (y - this.origoY) / this.scale;
    }

    public void drawLines(List<Stage> stages) {
        for (Stage stage : stages) {
            this.drawLine(stage);
        }
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void drawLocation(Location location) {
        int x = graphX(location.getX());
        int y = graphY(location.getY());
        this.gc.fillOval(x - 3, y - 3, 7, 7);
    }
    
    public void drawLocations(List<Location> locations) {
        for (Location location : locations) {
            this.drawLocation(location);
        }
    }

}
