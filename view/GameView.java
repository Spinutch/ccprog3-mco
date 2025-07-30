package view;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import model.*;

/**
 * GameView handles all user interface and presentation logic for the game.
 * This class is responsible for displaying information to the user and capturing user input.
 */
public class GameView {
    private Scanner scanner;
    
    public GameView() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Displays the main menu and gets user choice
     * @return user's menu choice
     */
    public int displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Create Character");
        System.out.println("2. View Character");
        System.out.println("3. Edit Character");
        System.out.println("4. Delete Character");
        System.out.println("5. Battle");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Gets character name from user input
     * @return character name
     */
    public String getCharacterName() {
        System.out.print("Enter character name: ");
        return scanner.nextLine();
    }
    
    /**
     * Displays available races and gets user selection
     * @param races list of available races
     * @return selected race index
     */
    public int selectRace(List<Race> races) {
        System.out.println("\n=== SELECT RACE ===");
        for (int i = 0; i < races.size(); i++) {
            Race race = races.get(i);
            System.out.printf("%d. %s (HP: %d, EP: %d)\n", 
                i + 1, race.getName(), race.getHpBonus(), race.getEpBonus());
        }
        System.out.print("Select race (1-" + races.size() + "): ");
        
        try {
            return Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Gets class selection from user
     * @return selected class name
     */
    public String selectClass() {
        System.out.println("\n=== SELECT CLASS ===");
        System.out.println("1. Mage");
        System.out.println("2. Rogue");
        System.out.println("3. Warrior");
        System.out.print("Select class (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: return "Mage";
                case 2: return "Rogue";
                case 3: return "Warrior";
                default: return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Displays available abilities and gets user selections
     * @param abilities list of available abilities
     * @param maxSelections maximum number of abilities to select
     * @return list of selected ability indices
     */
    public List<Integer> selectAbilities(List<Ability> abilities, int maxSelections) {
        List<Integer> selectedIndices = new ArrayList<>();
        
        System.out.println("\n=== SELECT ABILITIES ===");
        System.out.println("Available abilities:");
        for (int i = 0; i < abilities.size(); i++) {
            Ability ability = abilities.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, ability.getName(), ability.getDescription());
        }
        
        System.out.println("\nSelect " + maxSelections + " abilities (enter numbers separated by spaces):");
        String input = scanner.nextLine();
        String[] selections = input.split("\\s+");
        
        for (String selection : selections) {
            try {
                int index = Integer.parseInt(selection) - 1;
                if (index >= 0 && index < abilities.size() && !selectedIndices.contains(index)) {
                    selectedIndices.add(index);
                }
            } catch (NumberFormatException e) {
                // Ignore invalid input
            }
        }
        
        return selectedIndices;
    }
    
    /**
     * Displays a list of characters for selection
     * @param characters list of characters to display
     * @return selected character index
     */
    public int selectCharacter(List<model.Character> characters) {
        if (characters.isEmpty()) {
            System.out.println("No characters available.");
            return -1;
        }
        
        System.out.println("\n=== SELECT CHARACTER ===");
        for (int i = 0; i < characters.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, characters.get(i).getName());
        }
        System.out.print("Select character (1-" + characters.size() + "): ");
        
        try {
            return Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Displays detailed character information
     * @param character the character to display
     */
    public void displayCharacter(model.Character character) {
        character.displayCharacter();
    }
    
    /**
     * Displays character edit menu and gets user choice
     * @return edit menu choice
     */
    public int displayEditMenu() {
        System.out.println("\n=== EDIT CHARACTER ===");
        System.out.println("1. Add Magic Item");
        System.out.println("2. Remove Magic Item");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Displays success message
     * @param message the success message to display
     */
    public void displaySuccess(String message) {
        System.out.println("✓ " + message);
    }
    
    /**
     * Displays error message
     * @param message the error message to display
     */
    public void displayError(String message) {
        System.out.println("✗ " + message);
    }
    
    /**
     * Displays confirmation prompt and gets user response
     * @param message the confirmation message
     * @return true if user confirms, false otherwise
     */
    public boolean confirm(String message) {
        System.out.print(message + " (y/n): ");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
    
    /**
     * Displays battle selection menu for choosing two characters to battle
     * @param characters list of available characters
     * @return array of two selected character indices, or null if invalid
     */
    public int[] selectBattleCharacters(List<model.Character> characters) {
        if (characters.size() < 2) {
            displayError("At least 2 characters are needed for battle.");
            return null;
        }
        
        System.out.println("\n=== SELECT BATTLE CHARACTERS ===");
        
        System.out.println("Select first character:");
        int char1Index = selectCharacter(characters);
        if (char1Index < 0 || char1Index >= characters.size()) {
            return null;
        }
        
        System.out.println("Select second character:");
        int char2Index = selectCharacter(characters);
        if (char2Index < 0 || char2Index >= characters.size() || char2Index == char1Index) {
            displayError("Please select a different character for the second fighter.");
            return null;
        }
        
        return new int[]{char1Index, char2Index};
    }
    
    /**
     * Pauses execution and waits for user input
     */
    public void waitForUser() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Closes the scanner resource
     */
    public void close() {
        scanner.close();
    }
}
