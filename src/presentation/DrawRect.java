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
    private int sizeToDraw = 20;

    public DrawRect (int x, int y){
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
