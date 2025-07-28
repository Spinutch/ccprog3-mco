package controller;

import model.*;
import view.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuController {
    
    private MainMenuView menu;
    private GameModel model;

    public MainMenuController (MainMenuView menu, GameModel model) {
        this.menu = menu;
        this.model = model;
        setUpListeners();
    }  

    private void setUpListeners() {
        menu.addViewListener(new ViewListener());
        // menu.addCreateListener(new CreateListener());
        // menu.addEditListener(new EditListener());
        // menu.addDeleteListener(new DeleteListener());
        // menu.addChooseListener(new ChooseListener());
    }
 
    class ViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            menu.setVisible(false);
            CharacterListView listView = new CharacterListView();
            new CharacterListController(listView,model,menu);
            listView.setVisible(true);
        }
    }

    // class CreateListener implements ActionListener {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         menu.setVisible(false);
    //         CharacterCreationView creationView = new CharacterCreationView(menu);
    //         new CharacterCreationController(creationView, menu);
    //         creationView.setVisible(true);
    //     }
    // }

    // class EditListener implements ActionListener {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         menu.setVisible(false);
    //         CharacterEditView editView = new CharacterEditView(menu);
    //         new CharacterListController(editView, menu);
    //         editView.setVisible(true);
    //     }
    // }

    // class DeleteListener implements ActionListener {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         menu.setVisible(false);
    //         CharacterDeletionView deleteView = new CharacterDeletionView(menu);
    //         new CharacterDeletionController(deleteView, menu);
    //         deleteView.setVisible(true);
    //     }
    // }

    // class ChooseListener implements ActionListener {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         menu.setVisible(false);
    //         CharacterSelectView chooseView = new CharacterSelectView(menu);
    //         new CharacterSelectController(chooseView, menu);
    //         chooseView.setVisible(true);
    //     }
    // }
}
