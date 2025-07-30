package controller;

import model.*;
import view.*;
import java.awt.event.*;

/**
 * MainMenuController handles the main menu interactions
 * Coordinates between the main menu view and game model
 */
public class MainMenuController {

    private MainMenuView view;
    private GameModel model;
    private int currentPlayer;

    /**
     * Constructor sets up the controller with view, model and current player
     * 
     * @param view          the main menu view
     * @param model         the game model
     * @param currentPlayer the current player number
     */
    public MainMenuController(MainMenuView view, GameModel model, int currentPlayer) {
        this.view = view;
        this.model = model;
        this.currentPlayer = currentPlayer;
        this.view.setCurrentPlayer(currentPlayer);
        setUpListeners();
    }

    private void setUpListeners() {
        view.addViewListener(new ViewListener());
        view.addCreateListener(new CreateListener());
        view.addEditListener(new EditListener());
        view.addDeleteListener(new DeleteListener());
        view.addChooseListener(new ChooseListener());
    }

    class ViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            CharacterListView listView = new CharacterListView();
            new CharacterListController(listView, model, view, currentPlayer == 1);
            listView.setVisible(true);
        }
    }

    class CreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            new CharacterNameInputView();
            new CharacterCreationController(model, view, currentPlayer == 1);

        }
    }

    class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            EditCharacterView editView = new EditCharacterView();
            new EditCharacterController(editView, model, view, currentPlayer == 1);
            editView.setVisible(true);
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            DeleteCharacterView deleteView = new DeleteCharacterView();
            new CharacterDeletionController(deleteView, model, view, currentPlayer == 1);
            deleteView.setVisible(true);
        }
    }

    class ChooseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            CharacterSelectionView chooseView = new CharacterSelectionView();
            new CharacterSelectionController(chooseView, model, view, currentPlayer == 1);
            chooseView.setVisible(true);
        }
    }
}
