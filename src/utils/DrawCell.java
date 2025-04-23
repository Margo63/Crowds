package utils;

import model.State;

import java.awt.*;

public class DrawCell {

    public void draw(Graphics g, State state, int x, int y){
        switch (state) {
            case EMPTY:
                g.drawRect(x,y, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
                break;
            case EXIT:
                g.setColor(Color.RED);
                g.fillRect(x,y, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
                g.setColor(Color.BLACK);
                break;
            case ENTRY:
                g.setColor(Color.BLUE);
                g.fillRect(x,y, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
                g.setColor(Color.BLACK);
                break;
            case PEDESTRIAN:
                g.setColor(Color.GREEN);
                g.fillRect(x,y, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
                g.setColor(Color.BLACK);
                break;
            case OBSTRUCTION:
                g.fillRect(x,y, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
                break;
            default:
                g.drawRect(x,y, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
                break;

        }
    }
}
