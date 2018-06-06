package navigator;

import java.util.List;
import java.util.ArrayList;

public class RoadNetwork {

    private List<Location> locations;
    private List<Stage> stages;

    private Location start;
    private Location end;

    public RoadNetwork() {
        this.locations = new ArrayList<>();
        this.stages = new ArrayList<>();
    }

    public int largestIdentificationNumber() {
        int largestIdentificationNumber = -1;
        for (Location location : this.locations) {
            if (location.getIdentificationNumber() > largestIdentificationNumber) {
                largestIdentificationNumber = location.getIdentificationNumber();
            }
        }
        return largestIdentificationNumber;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getStart() {
        return start;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public Location getEnd() {
        return end;
    }

    public Stage findStage(Location start, Location end) {
        for (Stage stage : stages) {
            if (stage.getStart() == start && stage.getEnd() == end) {
                return stage;
            }
        }
        return null;
    }

    public void clearTimes() {
        for (Location location : this.locations) {
            location.clearTime();
        }
    }

    public List<Stage> pathToStages(Path path) {
        if (path.getPath().size() > 1) {
            List<Stage> stages = new ArrayList();
            for (int i = 1; i < path.getPath().size(); i++) {
                Location start = path.getPath().get(i - 1);
                Location end = path.getPath().get(i);
                Stage stage = findStage(start, end);
                stages.add(stage);
            }
            return stages;
        }
        return null;
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public boolean containsStage(Stage stage) {
        for (Stage stageToCheck : this.stages) {
            if (stage.getStart() == stageToCheck.getStart() && stage.getEnd() == stageToCheck.getEnd()) {
                return true;
            }
        }
        return false;
    }

    public void addStage(Stage stage) {
        if (!(containsStage(stage))) {
            this.stages.add(stage);
        }
    }

    public List<Stage> getStages() {
        return stages;
    }

    public Location getLocationWithID(int id) {
        for (Location location : locations) {
            if (location.getIdentificationNumber() == id) {
                return location;
            }
        }
        return null;
    }

    public void defineNeighbors() {
        for (Location location : locations) {
            location.defineNeighbors(this.stages);
        }
    }

    public Path fastestRoute(Location start, Location end) {
        this.clearTimes();
        List<Path> paths = new ArrayList<>();
        List<Location> alku = new ArrayList<>();
        alku.add(start);
        start.setTimeFromStart(0);
        paths.add(new Path(alku, start, 0));
        List<Path> newPaths = null;
        Path fastestPath = null;
        boolean continueOrNot;

        do {
            newPaths = new ArrayList<>();
            for (Path path : paths) {
                if (path.isValidPath()) {
                    List<Stage> stagesToContinue = new ArrayList<>();
                    for (Stage stage : path.getLastLocation().getNeighbors()) {
                        if (stage.getEnd().getTimeFromStart() < 0 || path.getTime() + stage.defineTime() < stage.getEnd().getTimeFromStart()) {
                            stage.getEnd().setTimeFromStart(path.getTime() + stage.defineTime());
                            stagesToContinue.add(stage);
                        }
                    }

                    if (stagesToContinue.size() > 1) {
                        for (int i = 1; i < stagesToContinue.size(); i++) {
                            Path pathToAdd = new Path(path.getPathAsNewList(), path.getLastLocation(), path.getTime());
                            pathToAdd.addLocation(stagesToContinue.get(i));
                            newPaths.add(pathToAdd);
                            if (stagesToContinue.get(i).getEnd() == end) {
                                pathToAdd.setValidPath(false);
                                if (fastestPath == null) {
                                    fastestPath = pathToAdd;
                                } else if (pathToAdd.getTime() < fastestPath.getTime()) {
                                    fastestPath = pathToAdd;
                                }
                            }
                        }
                    }
                    if (stagesToContinue.size() == 0) {
                        path.setValidPath(false);
                    } else {
                        path.addLocation(stagesToContinue.get(0));
                        if (stagesToContinue.get(0).getEnd() == end) {
                            path.setValidPath(false);
                            if (fastestPath == null) {
                                fastestPath = path;
                            } else if (path.getTime() < fastestPath.getTime()) {
                                fastestPath = path;
                            }
                        }
                    }
                }
            }
            for (Path path : newPaths) {
                paths.add(path);
            }

            continueOrNot = false;
            for (Path path : paths) {
                if (path.isValidPath()) {
                    continueOrNot = true;
                }
            }
        } while (continueOrNot);

        return fastestPath;
    }
}
