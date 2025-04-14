package presentation;

import javax.swing.*;
import java.awt.*;

public class ViewModelPanel extends JPanel {
    private ViewModel viewModel;
    private Screen screen;

    public ViewModelPanel(){
        super();
    }

    //to navigation inside panel
    public void addScreen(Screen screen){
        this.screen = screen;
    }

    public Screen getScreen(){
        return screen;
    }

    public void addViewModel(ViewModel viewModel){
        //System.out.println("addViewModel");
        this.viewModel = viewModel;
    }

    public ViewModel getViewModel() {
        return viewModel;
    }
}
