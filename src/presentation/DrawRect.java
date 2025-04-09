package presentation;

import model.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawRect extends Rectangle {
    private State state;
    private int j,i;
    private int sizeToDraw = 10;
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        System.out.print(getWidth());
//        switch (state) {
//            case EMPTY:
//                g.drawRect(j * sizeToDraw, i * sizeToDraw, sizeToDraw, sizeToDraw);
//                break;
//            case EXIT:
//                g.setColor(Color.RED);
//                g.fillRect(j * sizeToDraw, i * sizeToDraw, sizeToDraw, sizeToDraw);
//                g.setColor(Color.BLACK);
//                break;
//            case ENTRY:
//                g.setColor(Color.BLUE);
//                g.fillRect(j * sizeToDraw, i * sizeToDraw,sizeToDraw, sizeToDraw);
//                g.setColor(Color.BLACK);
//                break;
//            case OBSTRUCTION:
//                g.fillRect(j * sizeToDraw, i * sizeToDraw, sizeToDraw, sizeToDraw);
//                break;
//
//        }
//    }

    public DrawRect (int x, int y){
        this.state = State.EMPTY;
        setBounds(x * sizeToDraw, y * sizeToDraw, sizeToDraw, sizeToDraw);
        //setPreferredSize(new Dimension(sizeToDraw,sizeToDraw));

//        addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println(state+ "clicked");
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//
//
//        });
    }

    public void setPosition(int x, int y){
        this.i = y;
        this.j = x;
    }
    public State getState() {
        return state;
    }
    public void changeState(State state){
        this.state = state;
    }
}
