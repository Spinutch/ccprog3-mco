// CharacterListController.java
package controller;

import model.GameModel;
import model.Character;
import view.CharacterListView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class CharacterListController {
    private CharacterListView view;
    private GameModel model;
    private JFrame previousFrame;

    public CharacterListController(CharacterListView view, GameModel model, JFrame previousFrame) {
        this.view = view;
        this.model = model;
        this.previousFrame = previousFrame;

        setupView();
        setupListeners();
    }

    private void setupView() {
        List<Character> characters = model.getCharacters();

        if (characters.isEmpty()) {
            view.showNoCharacters();
        } else if (characters.size() == 1) {
            view.showCharacterDetails(characters.get(0));
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

        view.addCharacterSelectListener(new CharacterListView.CharacterSelectListener() {
            @Override
            public void onCharacterSelected(Character character) {
                view.showCharacterDetails(character);
            }
        });
    }
}
