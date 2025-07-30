package controller;

import model.*;
import view.*;
import model.Character;

import javax.swing.*;
import java.util.List;

public class ActualEditCharacterController {
    private ActualEditCharacterView view;
    private GameModel model;
    private Character character;
    private boolean isPlayer1;
    private MainMenuView mainView;

    public ActualEditCharacterController(GameModel model, Character character, boolean isPlayer1, MainMenuView mainView) {
        this.model = model;
        this.character = character;
        this.isPlayer1 = isPlayer1;
        this.mainView = mainView;
        this.view = new ActualEditCharacterView();
        
        // Update title to show which player's character is being edited
        view.setTitle("Edit " + (isPlayer1 ? "Player 1" : "Player 2") + "'s Character: " + character.getName());
        
        setupListeners();
        view.setVisible(true);
    }

    private void setupListeners() {
        // Edit Abilities listener
        view.setEditAbilitiesListener(e -> {
            view.dispose();
            AbilitySelectionView abilityView = new AbilitySelectionView(character.getCharacterClass());
            new AbilitySelectionController(model, character, isPlayer1, abilityView);
            abilityView.setVisible(true);
        });

        // Manage Equipment listener
        view.setManageMagicItemsListener(e -> {
            view.dispose();
            MagicItemManagementView magicView = new MagicItemManagementView();
            new MagicItemController(model, character, isPlayer1);
            magicView.setVisible(true);
        });

        // View Details listener
        view.setViewDetailsListener(e -> {
            StringBuilder details = new StringBuilder();
            details.append("Player: ").append(isPlayer1 ? "1" : "2").append("\n");
            details.append("Name: ").append(character.getName()).append("\n");
            details.append("Class: ").append(character.getCharacterClass()).append("\n");
            details.append("Race: ").append(character.getRace().getName()).append("\n\n");
            
            details.append("Stats:\n");
            details.append("HP: ").append(character.getHP()).append("/").append(character.getMaxHP()).append("\n");
            details.append("EP: ").append(character.getEP()).append("/").append(character.getMaxEP()).append("\n\n");
            
            details.append("Abilities:\n");
            for (Ability ability : character.getAbilities()) {
                details.append("- ").append(ability.getName())
                       .append(" (EP Cost: ").append(ability.getEpCost()).append(")\n");
            }

            details.append("\nEquipped Item: ");
            MagicItem equippedItem = character.getEquippedItem();
            details.append(equippedItem != null ? equippedItem.getName() : "None");

            JOptionPane.showMessageDialog(view, 
                details.toString(),
                (isPlayer1 ? "Player 1" : "Player 2") + "'s Character Details",
                JOptionPane.INFORMATION_MESSAGE);
        });

        // Finish Editing listener
        view.setBackListener(e -> handleFinishEditing());
    }

    private void handleFinishEditing() {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Are you sure you want to finish editing " + 
            (isPlayer1 ? "Player 1" : "Player 2") + "'s character?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            view.dispose();
            mainView.setVisible(true);
        }
    }
}
