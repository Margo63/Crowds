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

public class InputZonePanel extends ViewModelPanel {

    private ArrayList<ArrayList<InputCell>> board = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> zones = new ArrayList<>();
    private Point startZonePoint, endZonePoint;
    private int zoneIndex = 0;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //draw selected zone
        if (startZonePoint != null && endZonePoint != null) {
            int x = Math.min(startZonePoint.x, endZonePoint.x);
            int y = Math.min(startZonePoint.y, endZonePoint.y);
            int width = Math.abs(startZonePoint.x - endZonePoint.x);
            int height = Math.abs(startZonePoint.y - endZonePoint.y);

            g.drawRect(x, y, width, height);
        }

        //draw zone index on board
        for (int i = 0; i < zones.size(); i++) {
            for (int j = 0; j < zones.get(i).size(); j++) {

                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(String.valueOf(zones.get(i).get(j)));
                int textHeight = fm.getHeight();

                int textX = (int) (board.get(i).get(j).getX() + (board.get(i).get(j).getWidth() - textWidth) / 2);
                int textY = (int) (board.get(i).get(j).getY() + (board.get(i).get(j).getHeight() + textHeight) / 2 - fm.getDescent());

                g.drawString(String.valueOf(zones.get(i).get(j)), textX, textY);
            }
        }

        //draw board
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                DrawUtils.draw(g, board.get(i).get(j).getState(), (int) board.get(i).get(j).getX(), (int) board.get(i).get(j).getY());
            }
        }
    }


    public InputZonePanel() {
//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentShown(ComponentEvent e) {
//                loadBoard();
//            }
//
//            @Override
//            public void componentHidden(ComponentEvent e) {
//                save();
//            }
//        });

        JLabel label = new JLabel(Constants.INPUT_ZONE_LABEL);
        this.add(label);

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    zoneIndex = (int) spinner.getValue();
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                }

            }
        });
        this.add(spinner);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startZonePoint = e.getPoint();
                endZonePoint = startZonePoint;
                //System.out.println(start);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = Math.min(startZonePoint.x, endZonePoint.x);
                int y = Math.min(startZonePoint.y, endZonePoint.y);
                int width = Math.abs(startZonePoint.x - endZonePoint.x);
                int height = Math.abs(startZonePoint.y - endZonePoint.y);
                Rectangle rect = new Rectangle(x, y, width, height);

                for (int i = 0; i < board.size(); i++) {
                    for (int j = 0; j < board.get(i).size(); j++) {
                        Rectangle cellRectangle = new Rectangle(board.get(i).get(j).getX(), board.get(i).get(j).getY(),
                                board.get(i).get(j).getWidth(), board.get(i).get(j).getHeight());
                        if (rect.contains(cellRectangle) && board.get(i).get(j).getState() == State.EMPTY) {
                            zones.get(i).set(j, zoneIndex);

                        }
                    }
                }

                //System.out.println(zones);

                startZonePoint = null;
                endZonePoint = null;
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endZonePoint = e.getPoint();
                //System.out.println("end: " + end);
                repaint();
            }
        });
    }

    @Override
    public void panelShown() {
        if (this.getViewModel().checkBoard()) {
            this.board = this.getViewModel().getBoard();
            this.zones = this.getViewModel().getZones();

            repaint();
        }
    }

    @Override
    public void panelHidden() {
        if (!this.zones.isEmpty()) {
            this.getViewModel().setZones(zones);
        }
    }

}
