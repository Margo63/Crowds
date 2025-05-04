import presentation.AnalysisPanel;
import presentation.BoardPanel;
import presentation.Screen;
import presentation.input.InputDrawPanel;
import presentation.input.InputFilePanel;
import presentation.input.InputZonePanel;
import presentation.input.pedestrian.InputPedestrianPanel;
import utils.ConstantUtil;


public class System {
    public System() {
        Screen screen = new Screen();

        //System.out.println(board.getCell(0,5).getAvailable());

        BoardPanel boardPanel = new BoardPanel();
        //boardPanel.addBoard(board);
        AnalysisPanel analysisPanel = new AnalysisPanel();

        InputFilePanel inputPanel = new InputFilePanel();
        InputDrawPanel drawPanel = new InputDrawPanel();
        InputZonePanel zonePanel = new InputZonePanel();
        InputPedestrianPanel pedestrianPanel = new InputPedestrianPanel();

        screen.addPanel(inputPanel, ConstantUtil.INPUT_FILE);
        screen.addPanel(boardPanel, ConstantUtil.BOARD);
        screen.addPanel(analysisPanel, ConstantUtil.ANALYSIS);
        screen.addPanel(drawPanel, ConstantUtil.INPUT_DRAW);
        screen.addPanel(zonePanel, ConstantUtil.INPUT_ZONE);
        screen.addPanel(pedestrianPanel, ConstantUtil.INPUT_PEDESTRIAN);

        screen.changePanel(ConstantUtil.INPUT_FILE);
    }
}
