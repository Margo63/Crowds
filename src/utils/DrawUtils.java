package utils;

import data.State;

import java.awt.*;
import java.util.ArrayList;

public class DrawUtils {
    private DrawUtils(){}
    public static void drawBoard(Graphics g, ArrayList<ArrayList<Integer>> board){
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                draw(g, State.getFromInt(board.get(i).get(j)), j* ConstantUtil.SIZE_OF_CELL, i* ConstantUtil.SIZE_OF_CELL);
            }
        }
    }
    public static void draw(Graphics g, State state, int x, int y){
        switch (state) {
            case EMPTY:
                g.drawRect(x,y, ConstantUtil.SIZE_OF_CELL, ConstantUtil.SIZE_OF_CELL);
                break;
            case EXIT:
                g.setColor(Color.RED);
                g.fillRect(x,y, ConstantUtil.SIZE_OF_CELL, ConstantUtil.SIZE_OF_CELL);
                g.setColor(Color.BLACK);
                break;
            case ENTRY:
                g.setColor(Color.BLUE);
                g.fillRect(x,y, ConstantUtil.SIZE_OF_CELL, ConstantUtil.SIZE_OF_CELL);
                g.setColor(Color.BLACK);
                break;
            case PEDESTRIAN:
                g.setColor(Color.GREEN);
                g.fillRect(x,y, ConstantUtil.SIZE_OF_CELL, ConstantUtil.SIZE_OF_CELL);
                g.setColor(Color.BLACK);
                break;
            case AGGRESSIVE:
                g.setColor(Color.ORANGE);
                g.fillRect(x,y, ConstantUtil.SIZE_OF_CELL, ConstantUtil.SIZE_OF_CELL);
                g.setColor(Color.BLACK);
                break;
            case OBSTRUCTION:
                g.fillRect(x,y, ConstantUtil.SIZE_OF_CELL, ConstantUtil.SIZE_OF_CELL);
                break;

            default:
                g.drawRect(x,y, ConstantUtil.SIZE_OF_CELL, ConstantUtil.SIZE_OF_CELL);
                break;

        }
    }
}
