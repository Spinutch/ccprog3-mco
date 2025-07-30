import controller.*;
import model.GameModel;
import view.*;

public class Driver {
    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        int player = 1;
        MainMenuView view = new MainMenuView(player);
        GameModel model = new GameModel();
        new MainMenuController(view, model, player);
        view.setVisible(true);  
    }); 
}
}   