package model;

import java.util.List;
import java.util.ArrayList;

/**
 * GameModel handles the business logic and data management for the game.
 * This class manages character storage, validation, and game state.
 */
public class GameModel {

    private List<model.Character> player1Characters;
    private List<model.Character> player2Characters;

    private AllAbilities allAbilities;
    private AllMagicItems allMagicItems;
    private Character player1Character;
    private Character player2Character;

    public GameModel() {
        this.player1Characters = new ArrayList<>();
        this.player2Characters = new ArrayList<>();
        this.allAbilities = new AllAbilities();
        this.allMagicItems = new AllMagicItems();
    }

    /**
     * Retrieves characters for a specific player
     * 
     * @param player player number (1 or 2)
     * @return list of characters for the player
     */
    public List<model.Character> getCharactersForPlayer(int player) {
        if (player == 1) {
            return player1Characters;
        } else {
            return player2Characters;
        }
    }

    /**
     * Creates and adds a character for the specified player
     * 
     * @param player    player number (1 or 2)
     * @param character character to add
     * @return true if added successfully, false if name already exists
     */
    public boolean createCharacterForPlayer(int player, Character character) {
        List<model.Character> targetList = (player == 1) ? player1Characters : player2Characters;

        for (Character c : targetList) {
            if (c.getName().equals(character.getName())) {
                return false; // Duplicate name
            }
        }

        targetList.add(character);
        return true;

    }

    // Added methods to manage selected characters for battles

    public void setPlayer1Character(Character character) {
        this.player1Character = character;
    }

    public void setPlayer2Character(Character character) {
        this.player2Character = character;
    }

    public Character getPlayer1Character() {
        return player1Character;
    }

    public Character getPlayer2Character() {
        return player2Character;
    }

    /**
     * Creates a battle with two characters
     * 
     * @param char1 first character
     * @param char2 second character
     * @return battle instance, or null if either character is null
     */
    public Battle createBattle(Character char1, Character char2) {
        if (char1 == null || char2 == null)
            return null;
        return new Battle(char1, char2);
    }

    /**
     * Creates a character with specified parameters
     * 
     * @param player            player number (1 or 2)
     * @param name              character name
     * @param race              character race
     * @param characterClass    character class
     * @param selectedAbilities list of abilities
     * @return true if created successfully
     */
    public boolean createCharacter(int player, String name, Race race, String characterClass,
            List<Ability> selectedAbilities) {
        List<model.Character> characters = (player == 1) ? player1Characters : player2Characters;

        if (isNameTaken(name, characters)) {
            return false;
        }

        if (characters.size() >= 6) {
            return false;
        }

        Ability[] abilitiesArray = selectedAbilities.toArray(new Ability[0]);
        model.Character newCharacter = new model.Character(name, race, characterClass, abilitiesArray);
        characters.add(newCharacter);
        return true;

    }

    /**
     * Checks if a character name is already taken
     * 
     * @param name the name to check
     * @return true if name is taken
     */
    public boolean isNameTaken(String name, List<model.Character> characters) {
        for (model.Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Character getCharacterByName(String name, int player) {
        List<Character> characters = (player == 1) ? player1Characters : player2Characters;
        for (Character c : characters) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null; // not found
    }

    /**
     * Gets all characters
     * 
     * @return list of characters
     */
    // public List<model.Character> getCharacters(int player) {
    // // return player == 1 ? player1Characters : player2Characters;
    // System.out.println("getCharacters() currentPlayer = " + player);
    // if (player == 1) {
    // return new ArrayList<>(player1Characters);
    // } else {
    // return new ArrayList<>(player2Characters);
    // }

    // }

    /**
     * Gets a character by index
     * 
     * @param index the character index
     * @return the character at the index, or null if invalid
     */
    public model.Character getCharacter(int player, int index) {
        List<model.Character> target = player == 1 ? player1Characters : player2Characters;
        if (index >= 0 && index < target.size()) {
            return target.get(index);
        }
        return null;
    }

    /**
     * Deletes a character at the specified index
     * 
     * @param index the index of the character to delete
     * @return true if deletion was successful
     */
    public boolean deleteCharacter(int player, int index) {
        List<model.Character> target = player == 1 ? player1Characters : player2Characters;
        if (index >= 0 && index < target.size()) {
            target.remove(index);
            return true;
        }
        return false;
    }

    // an overloaded method to delete a character by reference
    public boolean deleteCharacter(int player, Character character) {
        List<Character> target = (player == 1) ? player1Characters : player2Characters;
        return target.remove(character);
    }

    /**
     * Gets all available races
     * 
     * @return list of races
     */
    public List<Race> getAllRaces() {
        List<Race> races = new ArrayList<>();
        races.add(Race.HUMAN);
        races.add(Race.ELF);
        races.add(Race.DWARF);
        races.add(Race.GNOME);
        return races;
    }

    /**
     * Gets abilities by character class
     * 
     * @param characterClass the class to get abilities for
     * @return list of abilities for the class
     */
    public List<Ability> getAbilitiesByClass(String characterClass) {
        return allAbilities.getAbilitiesByClass(characterClass);
    }

    /**
     * Gets all abilities (for Gnome bonus selection)
     * 
     * @return list of all abilities
     */
    public List<Ability> getAllAbilities() {
        return allAbilities.getAllAbilities();
    }

    /**
     * Gets all magic items
     * 
     * @return list of all magic items
     */
    public List<MagicItem> getAllMagicItems() {
        return allMagicItems.getAllItems();
    }

    /**
     * Validates that the selected abilities are valid for the character
     * 
     * @param selectedAbilities the abilities to validate
     * @param characterClass    the character's class
     * @param race              the character's race
     * @return true if abilities are valid
     */
    public boolean validateAbilities(List<Ability> selectedAbilities, String characterClass, Race race) {
        if (race.hasExtraAbilitySlot()) {
            return selectedAbilities.size() == 4;
        } else {
            return selectedAbilities.size() == 3;
        }
    }

    /**
     * Creates a Battle instance with two characters
     * 
     * @param char1Index index of first character
     * @param char2Index index of second character
     * @return Battle instance, or null if invalid indices
     */
    public Battle createBattle(int char1Index, int char2Index) {
        model.Character char1 = getCharacter(1, char1Index);
        model.Character char2 = getCharacter(2, char2Index);

        if (char1 != null && char2 != null && char1 != char2) {
            return new Battle(char1, char2, null); // Scanner will be provided by controller
        }

        return null;
    }

    /**
     * Checks if there are enough characters for battle
     * 
     * @return true if at least 2 characters exist
     */
    public boolean canStartBattle(int player) {
        List<model.Character> target = (player == 1) ? player1Characters : player2Characters;
        return target.size() >= 2;
    }

    /**
     * Gets the number of characters
     * 
     * @return number of characters
     */
    public int getCharacterCount(int player) {
        List<model.Character> target = (player == 1) ? player1Characters : player2Characters;
        return target.size();
    }
}
