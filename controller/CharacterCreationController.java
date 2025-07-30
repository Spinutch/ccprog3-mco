package controller;

import model.*;
import view.*;

import javax.swing.*;
// import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

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

    public CharacterCreationController(GameModel model, JFrame previousFrame) {
        this.model = model;
        this.previousFrame = previousFrame;
        showNameInputView();
    }

    private void showNameInputView() {
        nameInputView = new CharacterNameInputView();
        nameInputView.setVisible(true);
        

        nameInputView.addNextButtonListener(e -> {
            String name = nameInputView.getCharacterName();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(nameInputView, "Name cannot be empty.");
                return;
            }
            if (model.isNameTaken(name)) {
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
        // List<Ability> classAbilities = model.getAllAbilities().getAbilitiesByClass(selectedClass);
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
    boolean success = model.createCharacter(characterName, selectedRace, selectedClass, selectedAbilities);
    if (!success) {
        JOptionPane.showMessageDialog(abilitySelectionView, "Character creation failed. Name might be taken or limit reached.");
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
