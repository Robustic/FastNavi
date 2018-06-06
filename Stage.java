package navigator;

public class Stage {

    private Location start;
    private Location end;

    private double speed;

    public Stage(Location start, Location end, double speed) {
        this.start = start;
        this.end = end;
        this.speed = speed;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public double defineTime() {
        if (this.speed > 0) {
            return this.start.distance(this.end) / this.speed;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Stage: Start=" + this.start.getIdentificationNumber() + ", End=" + this.end.getIdentificationNumber() + ", Speed=" + this.speed;
    }

}
