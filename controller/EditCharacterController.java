package controller;

import model.*;
import model.Character;
import view.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class EditCharacterController {
    public EditCharacterController(EditCharacterView view, GameModel model, MainMenuView mainView, boolean isPlayer1) {
        List<Character> characters = model.getCharactersForPlayer(isPlayer1 ? 1 : 2);

        if (characters.isEmpty()) {
            view.showNoCharacters();
            view.dispose();
            // mainView.setVisible(true);
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
                    new ActualEditCharacterView();
                    new ActualEditCharacterController(model, selected, isPlayer1);
                }
            }
        });

        view.setVisible(true);
    }

    
}
