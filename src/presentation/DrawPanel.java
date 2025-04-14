package presentation;

import model.State;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class DrawPanel extends ViewModelPanel{
    // size of board
    private int sizeRows = 0;
    private int sizeCols = 0;
    // board to draw
    private ArrayList<ArrayList<DrawRect>> board = new ArrayList<>();
    // what draw
    private State currentState = State.EMPTY;

    //size to draw
    private int sizeToDraw = 10;


    // painting board accroding to size
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //System.out.println(board);
        for (int i = 0; i < sizeRows; i++) {
            for (int j = 0; j < sizeCols; j++) {

                //TODO
                //draw according array
                switch (board.get(i).get(j).getState()) {
                    case EMPTY:
                        g.drawRect((int) board.get(i).get(j).getX(), (int) board.get(i).get(j).getY(),
                                (int) board.get(i).get(j).getWidth(), (int) board.get(i).get(j).getHeight());
                        break;
                    case EXIT:
                        g.setColor(Color.RED);
                        g.fillRect((int) board.get(i).get(j).getX(), (int) board.get(i).get(j).getY(),
                                (int) board.get(i).get(j).getWidth(), (int) board.get(i).get(j).getHeight());
                        g.setColor(Color.BLACK);
                        break;
                    case ENTRY:
                        g.setColor(Color.BLUE);
                        g.fillRect((int) board.get(i).get(j).getX(), (int) board.get(i).get(j).getY(),
                                (int) board.get(i).get(j).getWidth(), (int) board.get(i).get(j).getHeight());
                        g.setColor(Color.BLACK);
                        break;
                    case OBSTRUCTION:
                        g.fillRect((int) board.get(i).get(j).getX(), (int) board.get(i).get(j).getY(),
                                (int) board.get(i).get(j).getWidth(), (int) board.get(i).get(j).getHeight());
                        break;

                }

            }
            //System.out.println();
        }
    }

    public DrawPanel() {

        //System.out.println(this.getViewModel().getBoard());


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
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i  < sizeRows; i++) {
                    for (int j = 0; j < sizeCols; j++) {
                        if(board.get(i).get(j).contains(e.getX(), e.getY())){
                            board.get(i).get(j).changeState(currentState);
                            repaint();
                        }
                    }
                }
            }
        });
    }

    void resize() {
        //ArrayList<State> resized = new ArrayList<>();
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

                    board.add(new ArrayList<>());
                    for (int i = 0; i < sizeCols; i++) {
                        DrawRect rect = new DrawRect(i, rows);

                        //System.out.print(i+" "+rows+"; ");
                        board.getLast().add(rect);
                    }
                    //System.out.println();
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
                        DrawRect rect = new DrawRect(cols,i);
                        board.get(i).add(rect);
                        //board.get(i).getLast().setPosition(cols,i);
                        //this.add(rect);
                        //System.out.print(cols+" "+i+"; ");
                    }
                    //System.out.println();
                    cols++;
                }
            }
        } catch (Exception e) {
            System.out.println("error with size colums");
        }

    }
}
