import presentation.BoardPanel;
import presentation.Screen;
import presentation.input.InputDrawPanel;
import presentation.input.InputFilePanel;
import presentation.input.InputZonePanel;
import presentation.input.pedestrian.InputPedestrianPanel;
import utils.Constants;

/*
* Состоит из нескольких глобальных задач: разработка симулятора (выбор существующего), проведение симуляции и анализ поведения толпы.
Требования к симулятору:
Создание карты местности (помещения/открытая), указание проходимых местностей
Расположение точек интереса - точек где люди могут останавливаться/задерживаться
Расположение целей - куда люди идут, например вход на стадион
Расположение точек прибытия и отбытия - точке где появляются и исчезают люди при симуляции

* Требования к симуляции:
Настройка маршрутов движения людей
Добавлений случайных факторов, человек отклонился от маршрута, решил пойти обратно, и т.д.
Настройка агрессивности людей
Настройка расписания прибытия и отбытия людей
Изучить какие параметры еще есть, и что можно настроить

* Требования к модели анализа:
Определение областей с высокой/низкой плотностью людей, а также причин возникновения (желательно с предложением путей решения)
Выделение точек интереса с крайне низкой посещаемостью
Выделение точек интереса, не справляющихся с нагрузкой
Выделения основных маршрутов движения людей
Выделение опасных точек, точек возникновения конфликтов и их причин
Формирование общего отчета по рассматриваемой области
*
* */
public class System {
    public System() {
        Screen screen = new Screen();

        //System.out.println(board.getCell(0,5).getAvailable());

        BoardPanel boardPanel = new BoardPanel();
        //boardPanel.addBoard(board);


        InputFilePanel inputPanel = new InputFilePanel();
        InputDrawPanel drawPanel = new InputDrawPanel();
        InputZonePanel zonePanel = new InputZonePanel();
        InputPedestrianPanel pedestrianPanel = new InputPedestrianPanel();

        screen.addPanel(inputPanel, Constants.INPUT_FILE);
        screen.addPanel(boardPanel,Constants.BOARD);
        screen.addPanel(drawPanel,Constants.INPUT_DRAW);
        screen.addPanel(zonePanel,Constants.INPUT_ZONE);
        screen.addPanel(pedestrianPanel,Constants.INPUT_PEDESTRIAN);

        screen.changePanel(Constants.INPUT_FILE);
    }
}
