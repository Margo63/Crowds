package model;


public class Cell{
//    private boolean isAvailable = true;
//    private boolean isGoal = false;
    private State state = State.EMPTY;

    Cell(){

    }
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
    public void setGoal() {
        state = State.EXIT;
    }
    public boolean getIsCellGoal() {
        return state == State.EXIT;
    }

    public String printCell(){
        if(state == State.EXIT) return "9";
        else if(state == State.EMPTY) return "0";
        else if(state == State.OBSTRUCTION) return "1";
        else return "-1";
    }
    public boolean achievedGoal(int row, int col) {
        return false;
    }
}
