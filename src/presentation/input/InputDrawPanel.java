package presentation.input;

import model.State;
import presentation.ViewModelPanel;
import utils.Constants;
import utils.DrawCell;

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
    private ArrayList<ArrayList<DrawRect>> board = new ArrayList<>();
    // what draw
    private State currentState = State.EMPTY;

    DrawCell drawCell = new DrawCell();
    JSpinner colsSpinner = new JSpinner(new SpinnerNumberModel(sizeCols, 0, 100, 1));
    JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(sizeRows, 0, 100, 1));

    // painting board accroding to size
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //System.out.println(board);
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                drawCell.draw(g,board.get(i).get(j).getState(),(int) board.get(i).get(j).getX(),(int) board.get(i).get(j).getY() );

            }
            //System.out.println();
        }
    }

    public InputDrawPanel() {

        //System.out.println(this.getViewModel().getBoard());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadBoard();
            }
            @Override
            public void componentHidden(ComponentEvent e) {
                saveBoard();
            }
        });

        JButton wallButton = new JButton("Wall");
        JButton exitButton = new JButton("Exit");
        JButton entryButton = new JButton("Entry");
        JButton emptyButton = new JButton("Empty");

        JButton buttonPrev = new JButton("prev");
        buttonPrev.addActionListener(e-> {
            this.getScreen().changePanel(Constants.INPUT_FILE);
        });
        this.add(buttonPrev);

        JButton buttonNext = new JButton("next");
        buttonNext.addActionListener(e-> {
            this.getScreen().changePanel(Constants.INPUT_ZONE);
        });
        this.add(buttonNext);

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
                    resize();
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

    private void loadBoard() {
        if(this.getViewModel().checkBoard()){
            ArrayList<ArrayList<DrawRect>> board = this.getViewModel().getBoard();
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

    private void saveBoard(){
        //System.out.println("load from draw");
        getViewModel().setBoard(board);
    }

}
