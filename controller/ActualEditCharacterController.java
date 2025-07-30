package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.awt.event.*;

public class ActualEditCharacterController {
    public ActualEditCharacterController(GameModel model, model.Character character, boolean isPlayer1) {
        ActualEditCharacterView view = new ActualEditCharacterView();

        view.setEditAbilitiesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                new AbilitySelectionView(character.getCharacterClass());  // You may already have controller for this
            }
        });

        view.setManageMagicItemsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                new MagicItemManagementView();
                new MagicItemController(model, character, isPlayer1);
            }
        });

        view.setViewDetailsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(view, character.toString(), "Character Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        view.setBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                new MainMenuView(isPlayer1 ? 1 : 2).setVisible(true); // Or whatever view you return to
            }
        });

        view.setVisible(true);
    }
}
