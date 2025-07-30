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
    private boolean isPlayer1;

    public CharacterListController(CharacterListView view, GameModel model, JFrame previousFrame, boolean isPlayer1) {
        this.model = model;
        this.view = view;
        this.previousFrame = previousFrame;
        this.isPlayer1 = isPlayer1;
        setupListeners();
        setupView();
       
    }

    private void setupView() {

    System.out.println("[DEBUG] Loading characters for Player " + (isPlayer1 ? "1" : "2"));

        List<Character> characters = model.getCharactersForPlayer(isPlayer1 ? 1 : 2);

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
