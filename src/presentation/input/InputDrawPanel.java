package presentation.input;

import data.State;
import presentation.ViewModelPanel;
import presentation.models.InputCell;
import utils.Constants;
import utils.DrawUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InputDrawPanel extends ViewModelPanel {
    // size of board
    private int sizeRows = 0;
    private int sizeCols = 0;
    // board to draw
    private ArrayList<ArrayList<InputCell>> board = new ArrayList<>();
    // what draw
    private State currentState = State.EMPTY;

    JSpinner colsSpinner = new JSpinner(new SpinnerNumberModel(sizeCols, 0, 100, 1));
    JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(sizeRows, 0, 100, 1));

    // painting board according to size
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //System.out.println(board);
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                DrawUtils.draw(g,board.get(i).get(j).getState(),(int) board.get(i).get(j).getX(),(int) board.get(i).get(j).getY() );

            }
            //System.out.println();
        }
    }

    public InputDrawPanel() {

        //System.out.println(this.getViewModel().getBoard());

//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentShown(ComponentEvent e) {
//                loadBoard();
//            }
//            @Override
//            public void componentHidden(ComponentEvent e) {
//                saveBoard();
//            }
//        });

        JButton wallButton = new JButton(Constants.WALL_BUTTON);
        JButton exitButton = new JButton(Constants.EXIT_BUTTON);
        JButton entryButton = new JButton(Constants.ENTRY_BUTTON);
        JButton emptyButton = new JButton(Constants.EMPTY_BUTTON);


//        JButton check = new JButton("Check");
//        this.add(check);
//        check.addActionListener(e ->{
//            System.out.println(this.getViewModel().getBoard());
//        });

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



        rowsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    sizeRows = (int) rowsSpinner.getValue();
                    resizeBoard();
                    repaint();
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });
        this.add(rowsSpinner);


        colsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    sizeCols = (int) colsSpinner.getValue();

                    resizeBoard();
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
                        Rectangle cellRectangle = new Rectangle(board.get(i).get(j).getX(), board.get(i).get(j).getY(),
                                board.get(i).get(j).getWidth(), board.get(i).get(j).getHeight());
                        if(cellRectangle.contains(e.getX(), e.getY())){
                            board.get(i).get(j).setState(currentState);
                            repaint();
                        }
                    }
                }
            }
        });



    }

    void resizeBoard() {
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
                        InputCell rect = new InputCell(i, rows);

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
                        InputCell rect = new InputCell(cols,i);
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


    @Override
    public void panelShown() {
        if(this.getViewModel().checkBoard()){
            ArrayList<ArrayList<InputCell>> board = this.getViewModel().getBoard();
            sizeRows = board.size();
            rowsSpinner.setValue(sizeRows);
            if(sizeRows > 0){
                sizeCols = board.getFirst().size();
                colsSpinner.setValue(sizeCols);
            }
            this.board = board;
            //resize();

//            for (int i = 0; i < board.size(); i++) {
//                for (int j = 0; j < board.get(i).size(); j++) {
//                    this.board.get(i).get(j).changeState(State.getFromInt(board.get(i).get(j)));
//                }
//            }
            repaint();
        }
    }

    @Override
    public void panelHidden() {
        getViewModel().setBoard(board);
    }
}
