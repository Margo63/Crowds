package presentation.input.pedestrian;

import model.State;
import presentation.ViewModelPanel;
import presentation.input.DrawRect;
import utils.Constants;
import utils.DrawCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InputPedestrianPanel extends ViewModelPanel {
    // board to draw
    private ArrayList<ArrayList<DrawRect>> board = new ArrayList<>();
    //list of entery
    private ArrayList<PedestrianInput> enteries = new ArrayList<>();

    //
    JComboBox<PedestrianInput> comboBox;
    JComboBox<Point> comboBoxExits;
    DrawCell drawCell = new DrawCell();

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //System.out.println(board);
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                drawCell.draw(g, board.get(i).get(j).getState(), (int) board.get(i).get(j).getX(), (int) board.get(i).get(j).getY());

            }
            //System.out.println();
        }
        g.setColor(Color.RED);
        PedestrianInput pedestrianInput = (PedestrianInput)comboBox.getSelectedItem();
        if(pedestrianInput != null){
            Point selectedPoint = pedestrianInput.pedestrianEntry;
            if (selectedPoint != null){
                g.drawRect(selectedPoint.x*Constants.SIZE_OF_CELL, selectedPoint.y*Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
            }
            Point selectedExitPoint = pedestrianInput.pedestrianExit;
            if (selectedExitPoint != null){
                g.setColor(Color.GREEN);
                g.drawRect(selectedExitPoint.x*Constants.SIZE_OF_CELL, selectedExitPoint.y*Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
            }

        }



    }

    public InputPedestrianPanel() {
       this.add(Box.createVerticalStrut(500));
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

        comboBox = new JComboBox();
        comboBox.setRenderer(new InputPedestrianRender());
        this.add(comboBox);



        JLabel labelWay = new JLabel("way: ");
        this.add(labelWay);
        JButton addWay = new JButton("Add Way");
        this.add(addWay);
        addWay.addActionListener(e -> {
            labelWay.setText(labelWay.getText() + ", ");

            repaint();
        });


        JLabel labelAmountPedestrian = new JLabel("amount pedestrian: ");
        this.add(labelAmountPedestrian);
        JSpinner spinnerAmount = new JSpinner();
        this.add(spinnerAmount);
        spinnerAmount.addChangeListener(e -> {
           PedestrianInput pedestrianInput = (PedestrianInput)comboBox.getSelectedItem();
           if(pedestrianInput != null){
                pedestrianInput.amountOfPedestrian = (int) spinnerAmount.getValue();
           }
        });

        JLabel time = new JLabel("time: ");
        this.add(time);

        Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, 0);
        JSpinner spinnerTime = new JSpinner(sm);
        JSpinner.DateEditor te = new JSpinner.DateEditor(spinnerTime, "HH:mm");
        spinnerTime.setEditor(te);
        this.add(spinnerTime);

        spinnerTime.addChangeListener(e -> {
            PedestrianInput pedestrianInput = (PedestrianInput)comboBox.getSelectedItem();
            if(pedestrianInput != null && spinnerTime.getValue() != null){
                SpinnerDateModel model = (SpinnerDateModel)spinnerTime.getModel();
                Date dateValue = (Date) model.getValue();
                pedestrianInput.timeIn = dateValue.getTime();}
        });


        comboBoxExits = new JComboBox();
        comboBoxExits.setRenderer(new ExitRender());
        this.add(comboBoxExits);
        comboBoxExits.addActionListener(e -> {
            PedestrianInput pedestrianInput = (PedestrianInput) comboBox.getSelectedItem();
            if(pedestrianInput!=null)
                pedestrianInput.pedestrianExit = (Point) comboBoxExits.getSelectedItem();
            repaint();
        });


        comboBox.addActionListener(e -> {
            //System.out.println("Выбран: " + comboBox.getSelectedItem());
            PedestrianInput pedestrianInput = (PedestrianInput)comboBox.getSelectedItem();
            if(pedestrianInput != null){
                labelWay.setText("way: "+pedestrianInput.way);
                spinnerAmount.setValue(pedestrianInput.amountOfPedestrian);
                comboBoxExits.setSelectedItem(pedestrianInput.pedestrianExit);
                spinnerTime.setValue(new Date(pedestrianInput.timeIn));
            }
            repaint();
        });
    }

    private void loadBoard() {
        if (this.getViewModel().checkBoard()) {
            this.board = this.getViewModel().getBoard();
            loadExits();
            loadEnteries();

            repaint();
        }
    }

    private void saveBoard() {

    }

    private void loadExits() {
        comboBoxExits.removeAllItems();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                if(board.get(i).get(j).getState() == State.EXIT){
                    Point exit = new Point(j, i);
                    comboBoxExits.addItem(exit);
                }

            }
        }

    }

    private void loadEnteries() {
        comboBox.removeAllItems();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                if (board.get(i).get(j).getState() == State.ENTRY) {
                    Point entry = new Point(j, i);
                    PedestrianInput pedestrianInput = new PedestrianInput();
                    pedestrianInput.pedestrianEntry = entry;
                    if(comboBoxExits.getSelectedItem()!=null){
                        pedestrianInput.pedestrianExit = (Point) comboBoxExits.getSelectedItem();
                    }
                    enteries.add(pedestrianInput);
                    comboBox.addItem(pedestrianInput);

                }

            }
        }

    }

}
