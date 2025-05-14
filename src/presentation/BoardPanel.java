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
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BoardPanel extends ViewModelPanel {
    private Board board;
    private Analyze analyze;
    private int hour = 0;
    private int minute = 0;

    private boolean loaded = false;
    private Map<MapPoint, ArrayList<PedestrianInput>> pedestrianEntryQueue = new HashMap<>();

    private JLabel timerLabel;
    private JLabel conflictLabel;
    private int conflictPercent = 0;
    private double probabilityDeviation = 0.0;
    private long allTime = 0;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.getViewModel().checkBoard() && board != null && loaded) {
            DrawUtils.drawBoard(g, board.getBoardOfIntegers(), getWidth());
            for (int i = 1; i < board.getAmountOfRows() - 1; i++) {
                for (int j = 1; j < board.getAmountOfCols() - 1; j++) {
                    //DrawUtils.draw(g, board.getCell(i, j).getState(), j * Constants.SIZE_OF_CELL, i * Constants.SIZE_OF_CELL);

                    if (board.getCell(i, j).getState() == State.PEDESTRIAN || board.getCell(i, j).getState() == State.AGGRESSIVE) {
                        PedestrianCell cell = (PedestrianCell) board.getCell(i, j);
                        DrawUtils.drawText(g, String.valueOf(cell.num), j - 1, i - 1, getWidth(), board.getAmountOfCols()-2);
//                        FontMetrics fm = g.getFontMetrics();
//
//                        int textWidth = fm.stringWidth(String.valueOf(cell.num));
//                        int textHeight = fm.getHeight();
//
//                        int textX = (int) ((j - 1) * ConstantUtil.SIZE_OF_CELL + (ConstantUtil.SIZE_OF_CELL - textWidth) / 2);
//                        int textY = (int) ((i - 1) * ConstantUtil.SIZE_OF_CELL + (ConstantUtil.SIZE_OF_CELL + textHeight) / 2 - fm.getDescent());
//
//                        g.drawString(String.valueOf(cell.num), textX, textY);
                    }

                }
            }
        }


    }

    public BoardPanel() {
        this.board = new Board();
        this.analyze = new Analyze();
        this.board.addObserver(this.analyze);

        //pedestrianEntryQueue = new ArrayList<>();

        timerLabel = new JLabel("00:00");
        add(timerLabel);

        JLabel conflictPercentLabel = new JLabel("conflict percent: ");
        this.add(conflictPercentLabel);
        JSpinner conflictPercentSpinner = new JSpinner(new SpinnerNumberModel(conflictPercent, 0, 100, 1));
        conflictPercentSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        conflictPercentSpinner.setMaximumSize(conflictPercentSpinner.getPreferredSize());
        conflictPercentSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    conflictPercent = (int) conflictPercentSpinner.getValue();
                    board.setConflictPercent((double) conflictPercent / 100);

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
        probabilityDeviationSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        probabilityDeviationSpinner.setMaximumSize(probabilityDeviationSpinner.getPreferredSize());
        probabilityDeviationSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    probabilityDeviation = (double) probabilityDeviationSpinner.getValue();
                    board.setProbabilityDeviation(probabilityDeviation);
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

        JButton reportButton = new JButton("analysis");
        add(reportButton);

        reportButton.addActionListener(e -> {
            analyze.report();
            this.getViewModel().setConflictPoints(analyze.getConflictPoints());
            this.showAnalysis();
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
                ArrayList<PedestrianInput> inputs = new ArrayList<>();
                for (int j = 0; j < pedestrian.get(i).amountOfPedestrian; j++) {
                    inputs.addLast(pedestrian.get(i));
                }
                PedestrianInput pedestrianGroup = pedestrian.get(i);
                MapPoint entry = new MapPoint(pedestrianGroup.pedestrianEntry.y, pedestrianGroup.pedestrianEntry.x);
                if (pedestrianEntryQueue.containsKey(entry)) {
                    pedestrianEntryQueue.get(entry).addLast(pedestrianGroup);
                } else {
                    pedestrianEntryQueue.put(entry, inputs);
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

        long startTime = System.nanoTime();
        board.step(hour * 60L + minute);
        long endTime = System.nanoTime();
        allTime += (endTime - startTime);
        //System.out.println("time:"+(endTime - startTime));
        //System.out.println("all:"+allTime);


        analyze.analyzeStep(board.getBoardOfIntegers());
        conflictLabel.setText("количество конфликтов: " + analyze.getAmountOfAllConflict());

        for (MapPoint key : pedestrianEntryQueue.keySet()) {
            ArrayList<PedestrianInput> inputs = pedestrianEntryQueue.get(key);
            if (!inputs.isEmpty()) {

                Date date = new Date(inputs.getFirst().timeIn);

                if (date.getHours() * 60 + date.getMinutes() <= hour * 60 + minute) {
                    ArrayList<MapPoint> way = new ArrayList<>();
                    //TODO make to map point
                    for (Point point : inputs.getFirst().way) {
                        way.add(new MapPoint(point.y + 1, point.x + 1));
                    }
                    //TODO
                    //check that exit exist
                    Date out = new Date(inputs.getFirst().timeOut);
                    if (this.board.addPedestrian(inputs.getFirst().pedestrianEntry, way,
                            inputs.getFirst().pedestrianExit, out.getHours() * 60 + out.getMinutes())) {
                        pedestrianEntryQueue.get(key).removeFirst();
                        //System.out.println("added");
                    }

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


        Timer timer = new Timer(100, new ActionListener() {
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
