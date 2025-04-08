package presentation;

import model.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawRect extends JComponent {
    private State state;
    private int j,i;
    private int sizeToDraw = 10;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //System.out.println(j+" "+i);
        switch (state) {
            case EMPTY:
                g.drawRect(j * sizeToDraw, i * sizeToDraw, sizeToDraw, sizeToDraw);
                break;
            case EXIT:
                g.setColor(Color.RED);
                g.fillRect(j * sizeToDraw, i * sizeToDraw, sizeToDraw, sizeToDraw);
                g.setColor(Color.BLACK);
                break;
            case ENTRY:
                g.setColor(Color.BLUE);
                g.fillRect(j * sizeToDraw, i * sizeToDraw, sizeToDraw, sizeToDraw);
                g.setColor(Color.BLACK);
                break;
            case OBSTRUCTION:
                g.fillRect(j * sizeToDraw, i * sizeToDraw, sizeToDraw, sizeToDraw);
                break;

        }
    }

    public DrawRect (int x, int y){
        this.state = State.EMPTY;
        this.i = y;
        this.j = x;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(state+ "clicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


        });
    }

    public void setPosition(int x, int y){
        this.i = y;
        this.j = x;
    }
    public State getState() {
        return state;
    }
}
