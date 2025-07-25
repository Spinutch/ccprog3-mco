package controller;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import model.*;
import view.GameView;

/**
 * GameController coordinates between the Model and View components.
 * This class handles user input and manages the flow of the application.
 */
public class GameController {
    private GameModel model;
    private GameView view;
    private Scanner scanner;
    
    public GameController() {
        this.model = new GameModel();
        this.view = new GameView();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Starts the main game loop
     */
    public void startGame() {
        boolean running = true;
        
        System.out.println("=== WELCOME TO FATAL FANTASY: TACTICS ===");
        
        while (running) {
            int choice = view.displayMainMenu();
            
            switch (choice) {
                case 1:
                    createCharacter();
                    break;
                case 2:
                    viewCharacter();
                    break;
                case 3:
                    editCharacter();
                    break;
                case 4:
                    deleteCharacter();
                    break;
                case 5:
                    startBattle();
                    break;
                case 6:
                    running = false;
                    view.displaySuccess("Thank you for playing!");
                    break;
                default:
                    view.displayError("Invalid choice. Please try again.");
            }
        }
        
        view.close();
        scanner.close();
    }
    
    /**
     * Handles character creation
     */
    private void createCharacter() {
        if (model.getCharacterCount() >= 6) {
            view.displayError("Maximum of 6 characters allowed.");
            return;
        }
        
        // Get character name
        String name = view.getCharacterName();
        if (model.isNameTaken(name)) {
            view.displayError("Name already taken. Please choose another.");
            return;
        }
        
        // Get race selection
        List<Race> races = model.getAllRaces();
        int raceIndex = view.selectRace(races);
        if (raceIndex < 0 || raceIndex >= races.size()) {
            view.displayError("Invalid race selection.");
            return;
        }
        Race selectedRace = races.get(raceIndex);
        
        // Get class selection
        String characterClass = view.selectClass();
        if (characterClass == null) {
            view.displayError("Invalid class selection.");
            return;
        }
        
        // Get ability selections
        List<Ability> classAbilities = model.getAbilitiesByClass(characterClass);
        int maxAbilities = selectedRace.hasExtraAbilitySlot() ? 4 : 3;
        
        List<Integer> abilityIndices = view.selectAbilities(classAbilities, maxAbilities);
        if (abilityIndices.size() != maxAbilities) {
            view.displayError("Please select exactly " + maxAbilities + " abilities.");
            return;
        }
        
        // Convert indices to abilities
        List<Ability> selectedAbilities = new ArrayList<>();
        for (int index : abilityIndices) {
            selectedAbilities.add(classAbilities.get(index));
        }
        
        // Create character
        if (model.createCharacter(name, selectedRace, characterClass, selectedAbilities)) {
            view.displaySuccess("Character created successfully!");
        } else {
            view.displayError("Failed to create character.");
        }
    }
    
    /**
     * Handles character viewing
     */
    private void viewCharacter() {
        List<model.Character> characters = model.getCharacters();
        if (characters.isEmpty()) {
            view.displayError("No characters available. Create one first.");
            return;
        }
        
        int index = view.selectCharacter(characters);
        if (index >= 0 && index < characters.size()) {
            view.displayCharacter(characters.get(index));
        } else {
            view.displayError("Invalid character selection.");
        }
    }
    
    /**
     * Handles character editing (simplified version)
     */
    private void editCharacter() {
        List<model.Character> characters = model.getCharacters();
        if (characters.isEmpty()) {
            view.displayError("No characters available. Create one first.");
            return;
        }
        
        int index = view.selectCharacter(characters);
        if (index < 0 || index >= characters.size()) {
            view.displayError("Invalid character selection.");
            return;
        }
        
        model.Character character = characters.get(index);
        boolean editing = true;
        
        while (editing) {
            int choice = view.displayEditMenu();
            
            switch (choice) {
                case 1:
                    // Add magic item functionality would go here
                    view.displayError("Magic item management not implemented yet.");
                    break;
                case 2:
                    // Remove magic item functionality would go here
                    view.displayError("Magic item management not implemented yet.");
                    break;
                case 3:
                    editing = false;
                    break;
                default:
                    view.displayError("Invalid choice.");
            }
        }
    }
    
    /**
     * Handles character deletion
     */
    private void deleteCharacter() {
        List<model.Character> characters = model.getCharacters();
        if (characters.isEmpty()) {
            view.displayError("No characters available.");
            return;
        }
        
        int index = view.selectCharacter(characters);
        if (index < 0 || index >= characters.size()) {
            view.displayError("Invalid character selection.");
            return;
        }
        
        model.Character character = characters.get(index);
        if (view.confirm("Are you sure you want to delete " + character.getName() + "?")) {
            if (model.deleteCharacter(index)) {
                view.displaySuccess("Character deleted successfully.");
            } else {
                view.displayError("Failed to delete character.");
            }
        }
    }
    
    /**
     * Handles battle initiation
     */
    private void startBattle() {
        if (!model.canStartBattle()) {
            view.displayError("At least 2 characters are needed for battle.");
            return;
        }
        
        List<model.Character> characters = model.getCharacters();
        int[] battleIndices = view.selectBattleCharacters(characters);
        
        if (battleIndices == null) {
            view.displayError("Invalid character selection for battle.");
            return;
        }
        
        // Create battle with scanner
        model.Character char1 = model.getCharacter(battleIndices[0]);
        model.Character char2 = model.getCharacter(battleIndices[1]);
        
        if (char1 != null && char2 != null) {
            Battle battle = new Battle(char1, char2, scanner);
            battle.startBattle();
        } else {
            view.displayError("Failed to start battle.");
        }
    }
}
