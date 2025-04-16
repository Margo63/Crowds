package utils;

import model.State;

import java.awt.*;

public class DrawCell {
    private int sizeToDraw = Constants.SIZE_OF_CELL;

    public void draw(Graphics g, State state, int x, int y){
        switch (state) {
            case EMPTY:
                g.drawRect(x,y, sizeToDraw, sizeToDraw);
                break;
            case EXIT:
                g.setColor(Color.RED);
                g.fillRect(x,y, sizeToDraw, sizeToDraw);
                g.setColor(Color.BLACK);
                break;
            case ENTRY:
                g.setColor(Color.BLUE);
                g.fillRect(x,y, sizeToDraw, sizeToDraw);
                g.setColor(Color.BLACK);
                break;
            case PEDESTRIAN:
                g.setColor(Color.GREEN);
                g.fillRect(x,y, sizeToDraw, sizeToDraw);
                g.setColor(Color.BLACK);
                break;
            case OBSTRUCTION:
                g.fillRect(x,y, sizeToDraw, sizeToDraw);
                break;
            default:
                g.drawRect(x,y, sizeToDraw, sizeToDraw);
                break;

        }
    }
}
