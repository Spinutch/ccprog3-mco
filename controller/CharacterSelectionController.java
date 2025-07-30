package controller;

import model.GameModel;
import model.Character;
import view.CharacterSelectionView;
import view.MainMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class CharacterSelectionController {
    private CharacterSelectionView view;
    private GameModel model;
    private JFrame previousFrame;
    private boolean isPlayer1;

    public CharacterSelectionController(CharacterSelectionView view, GameModel model, JFrame previousFrame, boolean isPlayer1) {
        this.model = model;
        this.view = view;
        this.previousFrame = previousFrame;
        this.isPlayer1 = isPlayer1;
        setupListeners();
        setupView();
        
        
    }

    private void setupView() {
        List<Character> characters = model.getCharactersForPlayer(isPlayer1 ? 1 : 2);

        if (characters.isEmpty()) {
            view.showNoCharacters();
        } else {
            view.showCharacterList(characters);
        }

        view.setVisible(true);
    }

    private void setupListeners() {
        view.addBackButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                previousFrame.setVisible(true);
            }
        });

        view.addCharacterSelectionListener(new CharacterSelectionView.CharacterSelectionListener() {
            @Override
            public void onCharacterSelected(Character selectedCharacter) {
                if (isPlayer1) {
                    model.setPlayer1Character(selectedCharacter);

                    // Move to Player 2 Main Menu
                    view.dispose();
                    MainMenuView player2Menu = new MainMenuView(2);
                    new MainMenuController(player2Menu, model, 2);  // isPlayer1 = false
                } else {
                    model.setPlayer2Character(selectedCharacter);

                    // Proceed to Battle
                    view.dispose();
                    new BattleController(model);
                }
            }
        });
    }
}
