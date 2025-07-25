/**
 * The Character class represents a player-controlled fighter in the Fatal Fantasy: Tactics game.
 * Each character has a name, class type, health points (HP), energy points (EP),
 * and a set of abilities specific to their chosen class.
 *
 * This class provides methods to manage stats, track combat status flags (e.g., defending, shielding),
 * and perform actions during a battle.
 *
 * Character Features:
 *   - Stores basic stats: name, class, max HP, max EP
 *   - Manages ability list and EP cost validation
 *   - Tracks battle-related flags like defending or recharging
 *   - Supports utility methods for damage, healing, energy recovery, and stat resets
 */

public class Character {
    private String name;
    private String characterClass;
    private int hp;
    private int ep;
    private Ability[] abilities;
    private boolean isDefending;
    private boolean isEvading;
    private boolean isShielded = false;

    public static final int MAX_HP = 100;
    public static final int MAX_EP = 50;

    /**
     * Constructs a Character object with the specified name, class name, and list of abilities.
     * Initializes the character's HP and EP to their maximum values and sets all combat flags to false.
     *
     * @param name the unique name of the character
     * @param characterClass the class type of the character (e.g., Mage, Rogue, Warrior)
     * @param abilities[] the list of abilities selected by the character from their class pool
     */

    public Character(String name, String characterClass, Ability[] abilities) {
        this.name = name;
        this.characterClass = characterClass;
        this.hp = MAX_HP;
        this.ep = MAX_EP;
        this.abilities = abilities;
        this.isDefending = false;
    }

    // GETTERS

    /**
     * Returns the character's name.
     * 
     * @return The name of the character.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the character's class.
     * 
     * @return The class of the character.
     */
    public String getCharacterClass() {
        return characterClass;
    }

    /**
     * Returns the character's current HP.
     * 
     * @return The current HP of the character.
     */
    public int getHP() {
        return hp;
    }

    /**
     * Returns the character's current EP.
     * 
     * @return The current EP of the character.
     */
    public int getEP() {
        return ep;
    }

    /**
     * Returns the character's abilities.
     * 
     * @return An array of abilities of the character.
     */
    public Ability[] getAbilities() {
        return abilities;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the character's class.
     * 
     * @param characterClass The class to set for the character.
     */
    public void setAbilities(Ability[] abilities) {
        this.abilities = abilities;
    }

    // HELPER METHODS

    /*
     * This method checks if the character is currently defending.
     * 
     * @return true if the character is defending, false otherwise.
     */
    public boolean isDefending() {
        return isDefending;
    }

    /*
     * This method checks if the character is currently evading.
     * 
     * @return true if the character is evading, false otherwise.
     */
    public boolean isEvading() {
        return isEvading;
    }

    /**
     * Sets the character's evading status.
     * 
     * @param isEvading true if the character is evading, false otherwise.
     */
    public void setEvading(boolean isEvading) {
        this.isEvading = isEvading;
    }

    /**
     * Heals the character by depending on the amount, while it also checks if it
     * exceeds MAX_HP.
     * 
     * @param amount The amount to heal the character.
     */
    public void heal(int amount) {
        if (hp + amount > MAX_HP) {
            this.hp = MAX_HP;
        } else {
            this.hp += amount;
        }
    }

    /**
     * Reduces the character's HP by a given damage amount, considering if the
     * character is defending, evading, or shielded.
     * 
     * @param damage The amount of damage to be taken by the character.
     */
    public void takeDamage(int damage) {
        if (isShielded) {
            System.out.println(name + " is shielded and takes no damage!");
            return;
        }
        if (isEvading) {
            if (Math.random() < 0.5) {
                System.out.println(name + " evaded the attack!");
                return;
            } else {
                System.out.println(name + " tried to evade but failed!");
            }
        }
        if (isDefending) {
            damage /= 2;
        }
        if (hp - damage < 0) {
            hp = 0;
        } else {
            hp -= damage;
        }
    }

    /**
     * Uses a specified amount of EP, ensuring it does not go below zero.
     * 
     * @param cost The amount of EP to be used.
     */
    public void useEP(int cost) {
        if (ep - cost < 0) {
            ep = 0;
        } else {
            ep -= cost;
        }
    }

    /**
     * Recharges the character's EP by a given amount, ensuring it does not exceed
     * MAX_EP.
     */
    public void recharge() {
        if (ep + 5 > MAX_EP) {
            this.ep = MAX_EP;
        } else {
            this.ep += 5;
        }
    }

    /**
     * Sets the character's defending status.
     * 
     * @param defending true if the character is defending, false otherwise.
     */
    public void setDefending(boolean defending) {
        this.isDefending = defending;
    }

    /**
     * Sets the character's shielded status.
     * 
     * @param shielded true if the character is shielded, false otherwise.
     */
    public void setShielded(boolean shielded) {
        this.isShielded = shielded;
    }

    /**
     * Checks if the character is currently shielded.
     * 
     * @return true if the character is shielded, false otherwise.
     */
    public boolean isShielded() {
        return isShielded;
    }

    /**
     * Displays the character's details, including name, class, HP, EP, and
     * abilities.
     */
    public void displayCharacter() {
        System.out.println("\nName: " + name);
        System.out.println("Class: " + characterClass);
        System.out.println("HP: " + hp);
        System.out.println("EP: " + ep);
        System.out.println("Abilities:");
        for (Ability ability : abilities) {
            System.out.println("- " + ability.getName() + ": " + ability.getDescription());
        }

    }

    /**
     * Deletes the character by setting its attributes to null or zero depending on
     * the type.
     */
    public void deleteCharacter() {
        System.out.println("\n[" + name + " has been deleted.]\n");
        this.name = null;
        this.characterClass = null;
        this.hp = 0;
        this.ep = 0;
        this.abilities = null;
    }

    /**
     * Resets the character's stats to their maximum values for the start of the
     * battle.
     */
    public void resetStats() {
        this.hp = MAX_HP;
        this.ep = MAX_EP;
    }

    /**
     * Restores the character's HP or EP by a specified amount, ensuring it does not
     * exceed the maximum values.
     * 
     * @param type   The type of restoration ("HP" or "EP").
     * @param amount The amount to restore.
     */
    public void restore(String type, int amount) {
        if ("HP".equalsIgnoreCase(type)) {
            if (hp + amount > MAX_HP) {
                this.hp = MAX_HP;
            } else {
                this.hp += amount;
            }
        } else if ("EP".equalsIgnoreCase(type)) {
            if (ep + amount > MAX_EP) {
                this.ep = MAX_EP;
            } else {
                this.ep += amount;
            }
        }
    }
}