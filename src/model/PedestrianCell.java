package model;

public class PedestrianCell extends Cell {

    private int rowGoal=10, colGoal=5;
    private int [][] goalMap ={
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,13,12,13,12,11,10,11,12,13,-1},
            {-1,12,11,12,11,10, 9,10,11,12,-1},
            {-1,11,10,11,10, 9, 8, 9,10,11,-1},
            {-1,10, 9,10,-1,-1, 7, 8, 9,10,-1},
            {-1, 9, 8, 7, 6, 5, 6, 7,-1,-1,-1},
            {-1, 8, 7, 6, 5, 4, 5, 6, 7, 8,-1},
            {-1, 7, 8,-1, 4, 3, 4, 5, 6, 7,-1},
            {-1, 6,-1,-1, 3, 2, 3, 4, 5, 6,-1},
            {-1, 5, 4, 3, 2, 1, 2, 3, 4, 5,-1},
            {-1,-1,-1,-1,-1, 0,-1,-1,-1,-1,-1},
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
    @Override
    public boolean achievedGoal(int row, int col) {
        return row == rowGoal && col == colGoal;
    }
}
