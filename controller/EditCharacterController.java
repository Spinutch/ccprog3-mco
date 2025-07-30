package controller;

import model.*;
import model.Character;
import view.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class EditCharacterController {
    private EditCharacterView view;
    private GameModel model;
    private MainMenuView mainView;
    private boolean isPlayer1;

    public EditCharacterController(EditCharacterView view, GameModel model, MainMenuView mainView, boolean isPlayer1) {
        this.view = view;
        this.model = model;
        this.mainView = mainView;
        this.isPlayer1 = isPlayer1;

        List<Character> characters = model.getCharactersForPlayer(isPlayer1 ? 1 : 2);

        if (characters.isEmpty()) {
            view.showNoCharacters();
            return;
        }

        view.showCharacterList(characters, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = e.getActionCommand();
                Character selected = model.getCharacterByName(name, isPlayer1 ? 1 : 2);

                int result = JOptionPane.showConfirmDialog(
                    view,
                    "Do you want to edit " + selected.getName() + "?",
                    "Edit Character",
                    JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                    view.dispose();
                    new ActualEditCharacterController(model, selected, isPlayer1, mainView);
                }
            }
        });

        // Add back button listener
        view.addBackButtonListener(e -> {
            view.dispose();
            mainView.setVisible(true);
        });

        view.setVisible(true);
    }
}
