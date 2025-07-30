package controller;

import model.*;
import model.Character;
import view.*;
import javax.swing.*;
import java.util.List;

public class AbilitySelectionController {
    private AbilitySelectionView view;
    private GameModel model;
    private Character character;
    private List<Ability> currentAbilities;
    private boolean isPlayer1;
    private MainMenuView mainView;

    public AbilitySelectionController(AbilitySelectionView view, GameModel model, Character character, 
                                    List<Ability> currentAbilities, boolean isPlayer1, MainMenuView mainView) {
        this.view = view;
        this.model = model;
        this.character = character;
        this.currentAbilities = currentAbilities;
        this.isPlayer1 = isPlayer1;
        this.mainView = mainView;

        view.setCurrentAbilities(currentAbilities);
        setupListeners();
    }

    private void setupListeners() {
        view.addConfirmButtonListener(e -> {
            List<Ability> selectedAbilities = view.getSelectedAbilities();
            
            int requiredAbilities = character.getRace().hasExtraAbilitySlot() ? 4 : 3;
            
            if (selectedAbilities.size() != requiredAbilities) {
                JOptionPane.showMessageDialog(view, 
                    "Please select exactly " + requiredAbilities + " abilities.", 
                    "Invalid Selection", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Update character abilities
            character.setAbilities(selectedAbilities.toArray(new Ability[0]));
            
            JOptionPane.showMessageDialog(view,
                "Abilities updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

            // Return to edit screen
            view.dispose();
            ActualEditCharacterView editView = new ActualEditCharacterView();
            new ActualEditCharacterController(model, character, isPlayer1, mainView);
            editView.setVisible(true);
        });

        // Back button listener
        view.addBackButtonListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to go back? Any changes will be lost.",
                "Confirm Back",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose();
                ActualEditCharacterView editView = new ActualEditCharacterView();
                new ActualEditCharacterController(model, character, isPlayer1, mainView);
                editView.setVisible(true);
            }
        });
    }
}