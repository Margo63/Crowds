package presentation;

import analyze.Analyze;
import kotlin.Pair;
import model.ca.Board;
import presentation.input.pedestrian.PedestrianInput;
import utils.Constants;
import utils.DrawCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class BoardPanel extends ViewModelPanel {
    private Board board;
    private Analyze analyze = new Analyze();
    private DrawCell drawCell = new DrawCell();
    private int hour = 0;
    private int minute = 0;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //System.out.println("paint");
        //board.printBoard();
        if (this.getViewModel().checkBoard() && board != null) {
            for (int i = 0; i < board.getAmountOfRows(); i++) {
                for (int j = 0; j < board.getAmountOfCols(); j++) {
                    drawCell.draw(g, board.getCell(i, j).getState(), j * Constants.SIZE_OF_CELL, i * Constants.SIZE_OF_CELL);
//                    switch (board.getCell(i,j).getState()){
//                        case EMPTY:
//                            g.drawRect(j * 10, i * 10, 10, 10);
//                            break;
//                        case EXIT:
//                            g.setColor(Color.RED);
//                            g.fillRect(j * 10, i * 10, 10, 10);
//                            g.setColor(Color.BLACK);
//                            break;
//                        case ENTRY:
//                            g.setColor(Color.BLUE);
//                            g.fillRect(j * 10, i * 10, 10, 10);
//                            g.setColor(Color.BLACK);
//                            break;
//                        case PEDESTRIAN:
//                            g.setColor(Color.GREEN);
//                            g.fillRect(j * 10, i * 10, 10, 10);
//                            g.setColor(Color.BLACK);
//                            break;
//                        case OBSTRUCTION:
//                            g.fillRect(j * 10, i * 10, 10, 10);
//                            break;
//
//                    }


                    //  System.out.print(i + " "+j+"; ");
                }
                // System.out.println();
            }
        }


    }

    public BoardPanel() {
        JLabel timerLabel = new JLabel("00:00");
        add(timerLabel);

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minute+=5;
                if(minute==60){
                    minute=0;
                    hour++;
                }
                timerLabel.setText(String.format("%02d:%02d", hour, minute));
                repaint();
            }
        });
        timer.start();


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadBoard();

            }
        });

        JButton button = new JButton("Step");
        JLabel label = new JLabel();
        add(button);
        add(label);

        button.addActionListener(e -> {
            try {
                board.step();
                analyze.analyze_board(board);
                label.setText("количество конфликтов: " + analyze.getConflict());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            repaint();
        });

    }

    private void loadBoard() {
        if (getViewModel().checkBoard()) {
            this.board = new Board(this.getViewModel().getBoardInteger());
            ArrayList<PedestrianInput> pedestrian = getViewModel().getPedestrians();
            for (int i = 0; i < pedestrian.size(); i++) {
                ArrayList<Pair<Integer, Integer>> way = new ArrayList<>();
                for (Point point : pedestrian.get(i).way) {
                    way.add(new Pair<>(point.y, point.x));
                }
                this.board.addPedestrian(pedestrian.get(i).pedestrianEntry.y, pedestrian.get(i).pedestrianEntry.x, way, pedestrian.get(i).pedestrianExit);
            }

        }

        this.board.printBoard();
        repaint();

    }

    public void addBoard(Board board) {
        this.board = board;
    }

}
