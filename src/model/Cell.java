package model;

import java.awt.*;



public class Cell{
    private boolean isAvailable = true;
    private boolean isGoal = false;

    public void setAvailable(boolean b) {
        isAvailable = b;
    }
    public boolean getAvailable() {
        return isAvailable;
    }
    public void setGoal(boolean b) {
        isGoal = b;
    }
    public boolean getGoal() {
        return isGoal;
    }
}
