package presentation.input;

import model.State;
import presentation.ViewModel;
import presentation.ViewModelPanel;
import utils.Constants;
import utils.DrawCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InputZonePanel extends ViewModelPanel {

    DrawCell drawCell = new DrawCell();
    private ArrayList<ArrayList<DrawRect>> board = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> zones = new ArrayList<>();
    private Point start, end;


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ViewModel viewModel = getViewModel();
        //System.out.println(board);
        if(start!=null && end!=null) {
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);
            int width = Math.abs(start.x - end.x);
            int height = Math.abs(start.y - end.y);

            g.drawRect(x,y,width,height);
        }

        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                drawCell.draw(g, board.get(i).get(j).getState(), (int) board.get(i).get(j).getX(), (int) board.get(i).get(j).getY());
            }
            //System.out.println();
        }

    }


    public InputZonePanel() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadBoard();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        JLabel label = new JLabel("zone: ");
        this.add(label);

        TextField textField = new TextField("1");
        this.add(textField);

        JButton buttonPrev = new JButton("prev");
        buttonPrev.addActionListener(e-> {

        });
        this.add(buttonPrev);

        JButton buttonNext = new JButton("next");
        buttonNext.addActionListener(e-> {

        });
        this.add(buttonNext);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                start = e.getPoint();
                end = start;
                //System.out.println(start);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = Math.min(start.x, end.x);
                int y = Math.min(start.y, end.y);
                int width = Math.abs(start.x - end.x);
                int height = Math.abs(start.y - end.y);
                Rectangle rect = new Rectangle(x, y, width, height);

                for (int i = 0; i  < board.size(); i++) {
                    for (int j = 0; j < board.get(i).size(); j++) {

                        if(rect.contains(board.get(i).get(j))){

                            //zones.get(i).set(j,9);

                        }
                    }
                }

                System.out.println(zones);

                start = null;
                end = null;
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                end = e.getPoint();
                //System.out.println("end: " + end);
                repaint();
            }
        });
    }

    private void loadBoard() {
        if (this.getViewModel().checkBoard()) {
            this.board = this.getViewModel().getBoard();
            this.zones = this.getViewModel().getZones();

            repaint();
        }
    }
}
