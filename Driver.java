import controller.*;
import model.GameModel;
import view.*;

public class Driver {
    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        MainMenuView menu = new MainMenuView();
        GameModel model = new GameModel();
        new MainMenuController(menu, model);
        menu.setVisible(true);  
    }); 
}
}
