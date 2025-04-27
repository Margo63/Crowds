package presentation;

import analyze.Analyze;
import data.State;
import kotlin.Pair;
import model.ca.Board;
import data.MapPoint;
import model.ca.PedestrianCell;
import presentation.models.PedestrianInput;
import utils.ConstantUtil;
import utils.DrawUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class BoardPanel extends ViewModelPanel {
    private Board board;
    private Analyze analyze;
    private int hour = 0;
    private int minute = 0;

    private boolean loaded = false;
    private ArrayList<PedestrianInput> pedestrianEntryQueue;

    private JLabel timerLabel;
    private JLabel conflictLabel;
    private int conflictPercent = 0;
    private double probabilityDeviation = 0.0;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.getViewModel().checkBoard() && board != null && loaded) {
            DrawUtils.drawBoard(g, board.getBoardOfIntegers());
            for (int i = 1; i < board.getAmountOfRows() - 1; i++) {
                for (int j = 1; j < board.getAmountOfCols() - 1; j++) {
                    //DrawUtils.draw(g, board.getCell(i, j).getState(), j * Constants.SIZE_OF_CELL, i * Constants.SIZE_OF_CELL);

                    if (board.getCell(i, j).getState() == State.PEDESTRIAN) {
                        FontMetrics fm = g.getFontMetrics();
                        PedestrianCell cell = (PedestrianCell) board.getCell(i, j);
                        int textWidth = fm.stringWidth(String.valueOf(cell.num));
                        int textHeight = fm.getHeight();

                        int textX = (int) ((j - 1) * ConstantUtil.SIZE_OF_CELL + (ConstantUtil.SIZE_OF_CELL - textWidth) / 2);
                        int textY = (int) ((i - 1) * ConstantUtil.SIZE_OF_CELL + (ConstantUtil.SIZE_OF_CELL + textHeight) / 2 - fm.getDescent());

                        g.drawString(String.valueOf(cell.num), textX, textY);
                    }

                }
            }
        }


    }

    public BoardPanel() {
        this.board = new Board();
        this.analyze = new Analyze();
        this.board.addObserver(this.analyze);

        pedestrianEntryQueue = new ArrayList<>();

        timerLabel = new JLabel("00:00");
        add(timerLabel);

        JLabel conflictPercentLabel = new JLabel("conflict percent: ");
        this.add(conflictPercentLabel);
        JSpinner conflictPercentSpinner = new JSpinner(new SpinnerNumberModel(conflictPercent, 0, 100, 1));
        conflictPercentSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    conflictPercent = (int) conflictPercentSpinner.getValue();
                    repaint();
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });
        this.add(conflictPercentSpinner);

        JLabel probabilityDeviationLabel = new JLabel("probability deviation: ");
        this.add(probabilityDeviationLabel);
        JSpinner probabilityDeviationSpinner = new JSpinner(new SpinnerNumberModel(probabilityDeviation, 0.0, 1.0, 0.01));
        probabilityDeviationSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    probabilityDeviation = (double) probabilityDeviationSpinner.getValue();
                    repaint();
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });
        this.add(probabilityDeviationSpinner);


        JButton button = new JButton("Step");
        conflictLabel = new JLabel();
        add(button);
        add(conflictLabel);

        button.addActionListener(e -> {
            step();
        });

        JButton reportButton = new JButton("report");
        add(reportButton);

        reportButton.addActionListener(e -> {
            analyze.report();
        });

        JButton panicButton = new JButton("panic");
        add(panicButton);

        panicButton.addActionListener(e -> {
            board.setPanicMode();
        });


    }

    private void loadBoard() {
        if (getViewModel().checkBoard()) {
            analyze.loadZones(getViewModel().getZones());

            this.board.loadMap(getViewModel().getBoardInteger());
            loaded = true;
            ArrayList<PedestrianInput> pedestrian = getViewModel().getPedestrianInputs();
            Comparator<PedestrianInput> byTimeIn = Comparator.comparingLong(PedestrianInput::getTimeIn);
            pedestrian.sort(byTimeIn);

            for (int i = 0; i < pedestrian.size(); i++) {
                for (int j = 0; j < pedestrian.get(i).amountOfPedestrian; j++) {
                    pedestrianEntryQueue.addLast(pedestrian.get(i));
                }
            }
            //System.out.println(pedestrianEntryQueue);
            loaded = true;

        }

        //this.board.printBoard();
        repaint();

    }


    private void step() {
        minute++;
        if (minute == 60) {
            minute = 0;
            hour++;
        }
        timerLabel.setText(String.format("%02d:%02d", hour, minute));


        board.step(hour * 60L + minute);
        analyze.analyzeStep(board.getBoardOfIntegers());
        conflictLabel.setText("количество конфликтов: " + analyze.getAmountOfAllConflict());

        if (!pedestrianEntryQueue.isEmpty()) {

            Date date = new Date(pedestrianEntryQueue.getFirst().timeIn);

            if (date.getHours() * 60 + date.getMinutes() <= hour * 60 + minute) {
                ArrayList<MapPoint> way = new ArrayList<>();
                for (Point point : pedestrianEntryQueue.getFirst().way) {
                    way.add(new MapPoint(point.y, point.x));
                }
                //TODO
                //check that exit exist
                Date out = new Date(pedestrianEntryQueue.getFirst().timeOut);
                if (this.board.addPedestrian(pedestrianEntryQueue.getFirst().pedestrianEntry, way,
                        pedestrianEntryQueue.getFirst().pedestrianExit, out.getHours() * 60 + out.getMinutes())) {
                    pedestrianEntryQueue.removeFirst();
                    //System.out.println("added");
                }

            }

        }


        repaint();

    }

    @Override
    public void panelShown() {
        loadBoard();
        minute = 0;
        hour = 0;
        Timer timer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step();

            }
        });

        timer.start();
    }

    @Override
    public void panelHidden() {
        loaded = false;
    }
}
