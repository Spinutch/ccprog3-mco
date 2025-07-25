package model;

import java.util.List;
import java.util.ArrayList;

/**
 * GameModel handles the business logic and data management for the game.
 * This class manages character storage, validation, and game state.
 */
public class GameModel {
    private List<model.Character> characters;
    private AllAbilities allAbilities;
    private AllMagicItems allMagicItems;
    
    public GameModel() {
        this.characters = new ArrayList<>();
        this.allAbilities = new AllAbilities();
        this.allMagicItems = new AllMagicItems();
    }
    
    /**
     * Creates a new character with the specified parameters
     * @param name character name
     * @param race character race
     * @param characterClass character class
     * @param selectedAbilities list of selected abilities
     * @return true if character was created successfully
     */
    public boolean createCharacter(String name, Race race, String characterClass, List<Ability> selectedAbilities) {
        if (isNameTaken(name)) {
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
     * @param name the name to check
     * @return true if name is taken
     */
    public boolean isNameTaken(String name) {
        for (model.Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets all characters
     * @return list of characters
     */
    public List<model.Character> getCharacters() {
        return new ArrayList<>(characters);
    }
    
    /**
     * Gets a character by index
     * @param index the character index
     * @return the character at the index, or null if invalid
     */
    public model.Character getCharacter(int index) {
        if (index >= 0 && index < characters.size()) {
            return characters.get(index);
        }
        return null;
    }
    
    /**
     * Deletes a character at the specified index
     * @param index the index of the character to delete
     * @return true if deletion was successful
     */
    public boolean deleteCharacter(int index) {
        if (index >= 0 && index < characters.size()) {
            characters.remove(index);
            return true;
        }
        return false;
    }
    
    /**
     * Gets all available races
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
     * @param characterClass the class to get abilities for
     * @return list of abilities for the class
     */
    public List<Ability> getAbilitiesByClass(String characterClass) {
        return allAbilities.getAbilitiesByClass(characterClass);
    }
    
    /**
     * Gets all abilities (for Gnome bonus selection)
     * @return list of all abilities
     */
    public List<Ability> getAllAbilities() {
        return allAbilities.getAllAbilities();
    }
    
    /**
     * Gets all magic items
     * @return list of all magic items
     */
    public List<MagicItem> getAllMagicItems() {
        return allMagicItems.getAllItems();
    }
    
    /**
     * Validates that the selected abilities are valid for the character
     * @param selectedAbilities the abilities to validate
     * @param characterClass the character's class
     * @param race the character's race
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
     * @param char1Index index of first character
     * @param char2Index index of second character
     * @return Battle instance, or null if invalid indices
     */
    public Battle createBattle(int char1Index, int char2Index) {
        model.Character char1 = getCharacter(char1Index);
        model.Character char2 = getCharacter(char2Index);
        
        if (char1 != null && char2 != null && char1 != char2) {
            return new Battle(char1, char2, null); // Scanner will be provided by controller
        }
        
        return null;
    }
    
    /**
     * Checks if there are enough characters for battle
     * @return true if at least 2 characters exist
     */
    public boolean canStartBattle() {
        return characters.size() >= 2;
    }
    
    /**
     * Gets the number of characters
     * @return number of characters
     */
    public int getCharacterCount() {
        return characters.size();
    }
}
