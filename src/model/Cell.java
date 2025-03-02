package model;


public class Cell{
    private boolean isAvailable = true;
    private boolean isGoal = false;
    private State state = State.EMPTY;


    public void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return state;
    }
    public boolean getAvailable() {
        return state == State.EMPTY;
    }
    public boolean getIsPedestrian(){
        return state == State.PEDESTRIAN;
    }
    public void setGoal() {
        state = State.EXIT;
    }
    public boolean getIsCellGoal() {
        return state == State.EXIT;
    }
}
