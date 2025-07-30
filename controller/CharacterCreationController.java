package controller;

import model.*;
import view.*;

import javax.swing.*;
// import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CharacterCreationController manages the character creation workflow
 * Guides the user through name, race, class, and ability selection
 */
public class CharacterCreationController {
    private GameModel model;
    private JFrame previousFrame;

    private CharacterNameInputView nameInputView;
    private RaceSelectionView raceSelectionView;
    private ClassSelectionView classSelectionView;
    private AbilitySelectionView abilitySelectionView;
    private CharacterCreationSuccessView successView;

    private String characterName;
    private Race selectedRace;
    private String selectedClass;
    private List<Ability> selectedAbilities = new ArrayList<>();
    private boolean isPlayer1;

    /**
     * Constructor initializes the character creation process
     * 
     * @param model         the game model
     * @param previousFrame the previous frame to return to
     * @param isPlayer1     whether this is for player 1
     */
    public CharacterCreationController(GameModel model, JFrame previousFrame, boolean isPlayer1) {
        this.model = model;
        this.previousFrame = previousFrame;
        this.isPlayer1 = isPlayer1;
        showNameInputView();
    }

    private void showNameInputView() {
        List<model.Character> currentList = model.getCharactersForPlayer(isPlayer1 ? 1 : 2);
        nameInputView = new CharacterNameInputView();
        nameInputView.setVisible(true);

        nameInputView.addNextButtonListener(e -> {
            String name = nameInputView.getCharacterName();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(nameInputView, "Name cannot be empty.");
                return;
            }
            if (model.isNameTaken(name, currentList)) {
                JOptionPane.showMessageDialog(nameInputView, "Name already exists.");
                return;
            }
            characterName = name;
            nameInputView.dispose();
            showRaceSelectionView();
        });
    }

    private void showRaceSelectionView() {
        raceSelectionView = new RaceSelectionView();
        raceSelectionView.setVisible(true);
        raceSelectionView.addHumanButtonListener(e -> proceedWithRace(Race.HUMAN));
        raceSelectionView.addElfButtonListener(e -> proceedWithRace(Race.ELF));
        raceSelectionView.addDwarfButtonListener(e -> proceedWithRace(Race.DWARF));
        raceSelectionView.addGnomeButtonListener(e -> proceedWithRace(Race.GNOME));
    }

    private void proceedWithRace(Race race) {
        selectedRace = race;
        raceSelectionView.dispose();
        showClassSelectionView();
    }

    private void showClassSelectionView() {
        classSelectionView = new ClassSelectionView();
        classSelectionView.setVisible(true);
        classSelectionView.addWarriorButtonListener(e -> proceedWithClass("Warrior"));
        classSelectionView.addRogueButtonListener(e -> proceedWithClass("Rogue"));
        classSelectionView.addMageButtonListener(e -> proceedWithClass("Mage"));
    }

    private void proceedWithClass(String className) {
        selectedClass = className;
        classSelectionView.dispose();
        showAbilitySelectionView();
    }

    private void showAbilitySelectionView() {
        // List<Ability> classAbilities =
        // model.getAllAbilities().getAbilitiesByClass(selectedClass);
        abilitySelectionView = new AbilitySelectionView(selectedClass);
        abilitySelectionView.setVisible(true);

        abilitySelectionView.addConfirmButtonListener(e -> {
            List<Ability> chosen = abilitySelectionView.getSelectedAbilities(selectedClass);
            if (chosen.size() != 3) {
                JOptionPane.showMessageDialog(abilitySelectionView, "Please select exactly 3 abilities.");
                return;
            }
            selectedAbilities = chosen;
            abilitySelectionView.dispose();
            createCharacter();
            showSuccessView();
        });
    }

    private void createCharacter() {
        boolean success = model.createCharacter(isPlayer1 ? 1 : 2, characterName, selectedRace, selectedClass,
                selectedAbilities);
        if (!success) {
            JOptionPane.showMessageDialog(abilitySelectionView,
                    "Character creation failed. Name might be taken or limit reached.");
        }
    }

    private void showSuccessView() {
        successView = new CharacterCreationSuccessView();
        successView.setVisible(true);
        successView.addDoneButtonListener(e -> {
            successView.dispose();
            previousFrame.setVisible(true);
        });
    }
}
