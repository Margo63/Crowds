package model.ca;


import model.State;

public class Cell{
//    private boolean isAvailable = true;
//    private boolean isGoal = false;
    private State state = State.EMPTY;

    Cell(){}
    Cell( State state){
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return state;
    }
    public boolean getAvailable() {
        return state == State.EMPTY || state == State.EXIT;
    }
    public boolean getIsPedestrian(){
        return false;
    }
    public boolean isGoalAchieved(int row, int col) {
        return false;
    }
}
