package presentation.models;

import data.State;
import utils.ConstantUtil;

public class InputCell {
    private State state;
    private int x,y,width = ConstantUtil.SIZE_OF_CELL,height= ConstantUtil.SIZE_OF_CELL;

    public InputCell(int x, int y){
        this.state = State.EMPTY;
        this.x = x * ConstantUtil.SIZE_OF_CELL;
        this.y = y * ConstantUtil.SIZE_OF_CELL;

    }

    public State getState() {
        return state;
    }

    public void setState(State state){
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
