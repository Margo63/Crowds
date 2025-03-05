package model;

public class PedestrianCell extends Cell {

    private int xGoal, yGoal;
    private int [][] goalMap ={
            {-1,-1,-1,-1,-1},
            {-1, 4, 3, 4,-1},
            {-1, -1, 2, -1,-1},
            {-1, 2, 1, 2,-1},
            {-1, -1, 0, -1,-1},
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
