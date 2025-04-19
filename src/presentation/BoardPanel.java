package presentation;

import analyze.Analyze;
import kotlin.Pair;
import model.ca.Board;
import presentation.models.PedestrianInput;
import utils.Constants;
import utils.DrawCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class BoardPanel extends ViewModelPanel {
    private Board board;
    private Analyze analyze = new Analyze();
    private DrawCell drawCell = new DrawCell();
    private int hour = 0;
    private int minute = 0;
    private boolean loaded = false;
    private ArrayList<PedestrianInput> queue;
    private JLabel timerLabel;
    private JLabel label;
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
        timerLabel = new JLabel("00:00");
        add(timerLabel);




        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadBoard();
                minute = 0;
                hour = 0;
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        step();

                    }
                });

                timer.start();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                loaded = false;
            }
        });

        JButton button = new JButton("Step");
        label = new JLabel();
        add(button);
        add(label);

        button.addActionListener(e -> {
            step();
        });

    }

    private void loadBoard() {
        if (getViewModel().checkBoard()) {
            queue = new ArrayList<>();
            this.board = new Board(this.getViewModel().getBoardInteger());
            ArrayList<PedestrianInput> pedestrian = getViewModel().getPedestrians();
            Comparator<PedestrianInput> byTimeIn = Comparator.comparingLong(PedestrianInput::getTimeIn);
            pedestrian.sort(byTimeIn);

            for (int i = 0; i < pedestrian.size(); i++) {
                for (int j = 0; j < pedestrian.get(i).amountOfPedestrian; j++) {
                    queue.addLast( pedestrian.get(i));
                }
//                System.out.println(pedestrian.get(i).getTimeIn());
//                ArrayList<Pair<Integer, Integer>> way = new ArrayList<>();
//                for (Point point : pedestrian.get(i).way) {
//                    way.add(new Pair<>(point.y, point.x));
//                }
//                this.board.addPedestrian(pedestrian.get(i).pedestrianEntry, way, pedestrian.get(i).pedestrianExit);
            }
            //System.out.println(queue);
            loaded = true;

        }

        //this.board.printBoard();
        repaint();

    }

    public void addBoard(Board board) {
        this.board = board;
    }

    private void step(){
        minute+=5;
        if(minute==60){
            minute=0;
            hour++;
        }
        timerLabel.setText(String.format("%02d:%02d", hour, minute));



        try {
            board.step();
            analyze.analyze_board(board);
            label.setText("количество конфликтов: " + analyze.getConflict());

        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        if(!queue.isEmpty()){
//            Calendar calendar = Calendar.getInstance();
//
//            calendar.set(Calendar.HOUR_OF_DAY, hour);
//            calendar.set(Calendar.MINUTE, minute);
//
//            Date date = calendar.getTime();

            Date date = new Date(queue.getFirst().timeIn);

            //System.out.println("size: "+queue.size()+" time in: "+(date.getHours()*60 + date.getMinutes()) + "current time: "+(hour*60+minute));

            if(date.getHours()*60 + date.getMinutes() <= hour*60+minute ){
                ArrayList<Pair<Integer, Integer>> way = new ArrayList<>();
                for (Point point : queue.getFirst().way) {
                    way.add(new Pair<>(point.y, point.x));
                }
                this.board.addPedestrian(queue.getFirst().pedestrianEntry, way, queue.getFirst().pedestrianExit);
                queue.removeFirst();
            }

        }
        repaint();

    }

}
