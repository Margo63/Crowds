package presentation;

import model.State;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class DrawPanel extends JPanel {
    // size of board
    private int sizeRows = 0;
    private int sizeCols = 0;
    // board to draw
    private ArrayList<ArrayList<State>> board = new ArrayList<>();
    // what draw
    private State currentState = State.EMPTY;

    //size to draw
    private int sizeToDraw = 10;


    // painting board accroding to size
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < sizeRows; i++) {
            for (int j = 0; j < sizeCols; j++) {
                //TODO
                //draw according array
                switch (board.get(i).get(j)) {
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
        }
    }

    public DrawPanel() {
        JButton wallButton = new JButton("Wall");
        JButton exitButton = new JButton("Exit");
        JButton entryButton = new JButton("Entry");
        JButton emptyButton = new JButton("Empty");

        this.add(wallButton);
        this.add(exitButton);
        this.add(entryButton);
        this.add(emptyButton);

        wallButton.addActionListener(e -> {
            currentState = State.OBSTRUCTION;
        });
        exitButton.addActionListener(e -> {
            currentState = State.EXIT;
        });
        entryButton.addActionListener(e -> {
            currentState = State.ENTRY;
        });
        emptyButton.addActionListener(e -> {
            currentState = State.EMPTY;
        });

        JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(sizeRows, 0, 100, 1));
        rowsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    sizeRows = (int) rowsSpinner.getValue();
                    resize();
                    repaint();
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });
        this.add(rowsSpinner);

        JSpinner colsSpinner = new JSpinner(new SpinnerNumberModel(sizeCols, 0, 100, 1));
        colsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    sizeCols = (int) colsSpinner.getValue();

                    resize();
                    repaint();
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });
        this.add(colsSpinner);

    }

    void resize() {
        ArrayList<State> resized = new ArrayList<>();
        //System.out.println("in resize");
        try {
            int rows = board.size();

            //remove rows
            if (rows > sizeRows) {
                //System.out.println("remove row:");
                while (rows > sizeRows) {
                    board.removeLast();
                    rows--;
                }
            }

            //add row
            if (rows < sizeRows) {
                //System.out.println("add row:");
                while (rows < sizeRows) {
                    board.add(new ArrayList<>(Collections.nCopies(sizeCols, State.EMPTY)));
                    rows++;
                }

//                for (int i = 0; i < sizeRows; i++) {
//                    for (int j = 0; j < sizeCols; j++) {
//                        board.get(i).set(j, State.EMPTY);
//                    }
//                }
            }


        } catch (Exception e) {
            System.out.println("error with size rows");
        }


        try {
            int cols = board.getFirst().size();
            //remove cols
            if (cols > sizeCols) {
                //System.out.println("remove col:");
                while (cols > sizeCols) {
                    for (int i = 0; i < sizeRows; i++) {
                        board.get(i).removeLast();
                    }
                    cols--;
                }
            }
            //add cols
            if (cols < sizeCols) {
                //System.out.println("add col:");
                while (cols < sizeCols) {
                    for (int i = 0; i < sizeRows; i++) {
                        board.get(i).add(State.EMPTY);
                    }
                    cols++;
                }
            }
        } catch (Exception e) {
            System.out.println("error with size colums");
        }

    }
}
