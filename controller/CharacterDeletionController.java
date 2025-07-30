package controller;

import model.Character;
import model.GameModel;
import view.DeleteCharacterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;

// import controller.MainMenuController.DeleteListener;

public class CharacterDeletionController {
    private GameModel model;
    private DeleteCharacterView view;
    private JFrame previousFrame;
    private boolean isPlayer1;

    public CharacterDeletionController(DeleteCharacterView view, GameModel model, JFrame previousFrame, boolean isPlayer1) {
        this.model = model;
        this.view = view;
        this.isPlayer1 = isPlayer1;
        this.previousFrame = previousFrame;


        // view.addBackButtonListener(new BackButtonListener());
        setupListeners();
        view.addDeleteCharacterListener(new DeleteListener());

        updateCharacterList();
    }

    private void updateCharacterList() {

        List<Character> characters = model.getCharactersForPlayer   (isPlayer1 ? 1 : 2);
        if (characters.isEmpty()) {
            view.showNoCharacters();
        } else {
            view.showCharacterList(characters);
        }
    }

    private void setupListeners() {
        view.addBackButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                previousFrame.setVisible(true);
            }
        });
    }


    private class DeleteListener implements DeleteCharacterView.DeleteCharacterListener {
        @Override
        public void onCharacterDelete(Character character) {
            model.deleteCharacter(isPlayer1 ? 1 : 2,character);
            updateCharacterList();
        }
    }
}
