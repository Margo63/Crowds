package presentation.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class PedestrianInput {
    public Point pedestrianEntry;
    public Point pedestrianExit;
    public int amountOfPedestrian = 0;
    public long timeIn = new Date(0).getTime();
    public ArrayList<Point> way = new ArrayList<>();

    public Point getPedestrianEntry() {
        return pedestrianEntry;
    }

    public void setPedestrianEntry(Point pedestrianEntry) {
        this.pedestrianEntry = pedestrianEntry;
    }

    public Point getPedestrianExit() {
        return pedestrianExit;
    }

    public void setPedestrianExit(Point pedestrianExit) {
        this.pedestrianExit = pedestrianExit;
    }

    public int getAmountOfPedestrian() {
        return amountOfPedestrian;
    }

    public void setAmountOfPedestrian(int amountOfPedestrian) {
        this.amountOfPedestrian = amountOfPedestrian;
    }

    public long getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(long timeIn) {
        this.timeIn = timeIn;
    }

    public ArrayList<Point> getWay() {
        return way;
    }

    public void setWay(ArrayList<Point> way) {
        this.way = way;
    }
}
