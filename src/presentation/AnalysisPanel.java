package presentation;

import data.MapPoint;
import utils.ConstantUtil;
import utils.DrawUtils;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AnalysisPanel extends ViewModelPanel {

    int max;
    int min;
    double step;
    Map<MapPoint,Integer> map = new HashMap<>();
    boolean loaded = false;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(getViewModel().checkBoard())
            DrawUtils.drawBoard(g,getViewModel().getBoardInteger(), getWidth());

        if(loaded){
            for (MapPoint key: map.keySet()){
                int value = map.get(key);

                if(value>=min && value<=(min + step)){
                    g.setColor(new Color(0xCAD5FF));

                }else if(value>=(min + step) && value<=(min + step*2)){
                    g.setColor(new Color(0x8CA3FF));

                }else if(value>=(min + step*2) && value<=(min + step*3)){
                    g.setColor(new Color(0xFF597AFF, true));
                }else if(value>=(min + step*3) && value<=(min + step*4)){
                    g.setColor(new Color(0xFF4161FF, true));
                }else{
                    g.setColor(new Color(0x003CFF));
                }
                Rectangle rectangle = DrawUtils.getCellRectangle(key.column(), key.row(), getWidth(), getViewModel().getBoardInteger().getFirst().size());
                g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
//                g.fillRect(key.column()* ConstantUtil.SIZE_OF_CELL, key.row()*ConstantUtil.SIZE_OF_CELL,
//                        ConstantUtil.SIZE_OF_CELL, ConstantUtil.SIZE_OF_CELL);
                g.setColor(Color.BLACK);
            }
        }

    }

    public AnalysisPanel(){

    }

    @Override
    public void panelShown() {
        super.panelShown();
        if(!getViewModel().getConflictPoints().isEmpty()){
            map = getViewModel().getConflictPoints();
            min = Collections.min(map.values());
            max = Collections.max(map.values());
            step = (double) (max - min) /5;
            loaded = true;
        }
    }

    @Override
    public void panelHidden() {
        super.panelHidden();
        loaded = false;
    }
}
