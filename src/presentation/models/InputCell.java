package presentation.models;

import model.State;
import utils.Constants;

import java.awt.*;

public class InputCell extends Rectangle {
    private State state;
    private int sizeToDraw = Constants.SIZE_OF_CELL;

    public InputCell(int x, int y){
        this.state = State.EMPTY;
        setBounds(x * sizeToDraw, y * sizeToDraw, sizeToDraw, sizeToDraw);
    }

    public State getState() {
        return state;
    }

    public void changeState(State state){
        this.state = state;
    }
}
