package model;

public class PedestrianCell extends Cell {

    private int xGoal, yGoal;
    private int [][] goalMap ={
            {-1,-1,-1,-1,-1},
            {-1, 3, 4, 5,-1},
            {-1, 2, 3, -1,-1},
            {-1, 1, 2, 3,-1},
            {-1, 0, -1, -1,-1},
    };

    @Override
    public boolean getIsPedestrian() {
        return true;
    }

    @Override
    public boolean getAvailable() {
        return false;
    }

    public int getProximityToExit(int x, int y) {
        return goalMap[x][y];
    }

    @Override
    public String printCell(){
        return "5";
    }
}
