package presentation.models;

import model.State;
import utils.Constants;

import java.awt.*;

public class InputCell {
    private State state;
    private int sizeToDraw = Constants.SIZE_OF_CELL;
    private int x,y,width = Constants.SIZE_OF_CELL,height=Constants.SIZE_OF_CELL;

    public InputCell(int x, int y){
        this.state = State.EMPTY;
        this.x = x * sizeToDraw;
        this.y = y * sizeToDraw;

    }

    public State getState() {
        return state;
    }

    public void changeState(State state){
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
