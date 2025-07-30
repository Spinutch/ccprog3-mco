package model;

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
 *   - Magic item inventory and equipment system
 */

import java.util.*;

public class Character {
    private String name;
    private Race race;
    private String characterClass;
    private int hp;
    private int ep;
    private int maxHP;
    private int maxEP;
    private Ability[] abilities;
    private boolean isDefending;
    private boolean isEvading;
    private boolean isShielded = false;
    private ArrayList<MagicItem> inventory;
    private MagicItem equippedItem;
    private int winCount;

    public static final int BASE_MAX_HP = 100;
    public static final int BASE_MAX_EP = 50;

    /**
     * Constructs a Character object with the specified name, race, class name, and
     * list of abilities.
     * Initializes the character's HP and EP to their maximum values (including race
     * bonuses) and sets all combat flags to false.
     *
     * @param name           the unique name of the character
     * @param race           the race of the character (e.g., Human, Dwarf, Elf,
     *                       Gnome)
     * @param characterClass the class type of the character (e.g., Mage, Rogue,
     *                       Warrior)
     * @param abilities[]    the list of abilities selected by the character from
     *                       their class pool
     */
    public Character(String name, Race race, String characterClass, Ability[] abilities) {
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
        // Calculate max HP and EP with race bonuses
        int baseMaxHP;
        if (race != null) {
            baseMaxHP = BASE_MAX_HP + race.getHpBonus();
        } else {
            baseMaxHP = BASE_MAX_HP;
        }
        int baseMaxEP;
        if (race != null) {
            baseMaxEP = BASE_MAX_EP + race.getEpBonus();
        } else {
            baseMaxEP = BASE_MAX_EP;
        }
        // Initialize current HP and EP to max values
        this.hp = this.maxHP = baseMaxHP;
        this.ep = this.maxEP = baseMaxEP;
        this.abilities = abilities;
        this.isDefending = false;
        this.inventory = new ArrayList<>();
        this.equippedItem = null;
        this.winCount = 0;
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
     * Returns the character's race.
     * 
     * @return The race of the character.
     */
    public Race getRace() {
        return race;
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
     * Returns the character's maximum HP (including race bonuses).
     * 
     * @return The maximum HP of the character.
     */
    public int getMaxHP() {
        return maxHP;
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
     * Returns the character's maximum EP (including race bonuses).
     * 
     * @return The maximum EP of the character.
     */
    public int getMaxEP() {
        return maxEP;
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

    public void setEquippedItem(MagicItem item) {
        this.equippedItem = item;
    }

    /**
     * Sets the character's class.
     * 
     * @param characterClass The class to set for the character.
     */
    public void setAbilities(Ability[] abilities) {
        this.abilities = abilities;
    }

    /**
     * Returns the character's magic item inventory.
     * 
     * @return ArrayList of magic items in inventory
     */
    public ArrayList<MagicItem> getInventory() {
        return inventory;
    }

    /**
     * Returns the character's currently equipped magic item.
     * 
     * @return The equipped magic item, or null if none equipped
     */
    public MagicItem getEquippedItem() {
        return equippedItem;
    }

    /**
     * Returns the character's win count.
     * 
     * @return The number of wins this character has
     */
    public int getWinCount() {
        return winCount;
    }

    /**
     * Increments the character's win count and awards magic item if applicable.
     */
    public void incrementWinCount() {
        this.winCount++;
        // Award magic item every third win
        if (winCount % 3 == 0) {
            MagicItem newItem = AllMagicItems.getRandomItem();
            if (newItem != null) {
                addItemToInventory(newItem);
                System.out.println("\n[You got a magic item!] " + name + " received: " + newItem.getName());
                System.out.println("   " + newItem.getEffect());
            }
        }
    }

    /**
     * Adds a magic item to the character's inventory.
     * 
     * @param item The magic item to add
     */
    public void addItemToInventory(MagicItem item) {
        inventory.add(item);
    }

    /**
     * Removes a magic item from the character's inventory.
     * 
     * @param item The magic item to remove
     * @return true if the item was successfully removed, false if not found
     */
    public boolean removeItemFromInventory(MagicItem item) {
        return inventory.remove(item);
    }

    /**
     * Equips a magic item from the inventory.
     * 
     * @param item The magic item to equip
     * @return true if successfully equipped, false if item not in inventory
     */
    public boolean equipItem(MagicItem item) {
        if (inventory.contains(item)) {
            this.equippedItem = item;
            if (item.isPassive()) {
                adjustStats();
            }
            return true;
        }
        return false;
    }

    /**
     * Unequips the currently equipped magic item.
     */
    public void unequipItem() {
        if (equippedItem != null && equippedItem.isPassive()) {
            this.equippedItem = null; // Remove the item first
            adjustStats(); // Then recalculate stats without the item
        } else {
            this.equippedItem = null;
        }
    }

    /**
     * Uses a single-use magic item and removes it from inventory.
     * 
     * @param item The single-use magic item to use
     * @return true if successfully used, false if not usable
     */
    public boolean useMagicItem(MagicItem item) {
        if (!inventory.contains(item) || !item.isSingleUse()) {
            return false;
        }

        // Apply the item's effects
        if (item.getHpRestore() > 0) {
            heal(item.getHpRestore());
            System.out.println(name + " restored " + item.getHpRestore() + " HP!");
        }
        if (item.getEpRestore() > 0) {
            restoreEP(item.getEpRestore());
            System.out.println(name + " restored " + item.getEpRestore() + " EP!");
        }
        if (item.shieldAll()) {
            this.isShielded = true;
            System.out.println(name + " is protected by a magical barrier!");
        }

        // Remove the item from inventory after use
        removeItemFromInventory(item);
        return true;
    }

    /**
     * Updates max HP and EP based on equipped passive items.
     */
    private void adjustStats() {
        // Reset to base values with race bonuses
        int baseMaxHP = BASE_MAX_HP + (race != null ? race.getHpBonus() : 0);
        int baseMaxEP = BASE_MAX_EP + (race != null ? race.getEpBonus() : 0);

        if (equippedItem != null && equippedItem.isPassive()) {
            this.maxHP = baseMaxHP + equippedItem.getHpBonus();
            this.maxEP = baseMaxEP + equippedItem.getEpBonus();
        } else {
            this.maxHP = baseMaxHP;
            this.maxEP = baseMaxEP;
        }

        if (hp > maxHP)
            hp = maxHP;
        if (ep > maxEP)
            ep = maxEP;
    }

    /**
     * Applies passive effects at the start of turn (regeneration, EP gain).
     */
    public void applyPassiveEffects() {
        if (equippedItem != null && equippedItem.isPassive()) {
            if (equippedItem.isHealPerTurn()) {
                heal(equippedItem.getHealAmount());
                System.out.println(
                        name + " regenerated " + equippedItem.getHealAmount() + " HP from " + equippedItem.getName());
            }
            if (equippedItem.getEpPerTurn() > 0) {
                restoreEP(equippedItem.getEpPerTurn());
                System.out.println(
                        name + " gained " + equippedItem.getEpPerTurn() + " EP from " + equippedItem.getName());
            }
        }
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
     * Heals the character by the specified amount
     * 
     * @param amount the amount to heal
     */
    public void heal(int amount) {
        if (hp + amount > maxHP) {
            this.hp = maxHP;
        } else {
            this.hp += amount;
        }
    }

    /**
     * Restores EP by the specified amount, capped at maximum
     * 
     * @param amount the amount of EP to restore
     */
    public void restoreEP(int amount) {
        if (ep + amount > maxEP) {
            this.ep = maxEP;
        } else {
            this.ep += amount;
        }
    }

    /**
     * Applies damage to the character considering defensive states
     * 
     * @param damage the amount of damage to apply
     * @return message describing the damage result
     */
    public String takeDamage(int damage) {
        if (isShielded) {
            return name + " is shielded and takes no damage!";
        }
        if (isEvading) {
            if (Math.random() < 0.5) {
                return name + " evaded the attack!";
            } else {
                // continue to damage
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

        return name + " takes " + damage + " damage!";
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
     * Regenerates the character's EP by 5, to make sure that it does not exceed the
     * maximum EP
     * also called at the start of each round in battle.
     */
    public void recharge() {
        if (ep + 5 > maxEP) {
            this.ep = maxEP;
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
     * Displays the character's details, including name, race, class, HP, EP, magic
     * items, and
     * abilities.
     */
    public void displayCharacter() {
        System.out.println("\nName: " + name);
        System.out.println("Race: " + race.getName());
        System.out.println("Class: " + characterClass);
        System.out.println("HP: " + hp + "/" + maxHP);
        System.out.println("EP: " + ep + "/" + maxEP);
        System.out.println("Wins: " + winCount);

        // Display equipped item
        if (equippedItem != null) {
            System.out.println(
                    "Equipped Item: " + equippedItem.getName() + " (" + equippedItem.getActivationType() + ")");
            System.out.println("   Effect: " + equippedItem.getEffect());
        } else {
            System.out.println("Equipped Item: None");
        }

        // Display inventory
        System.out.println("Inventory (" + inventory.size() + " items):");
        if (inventory.isEmpty()) {
            System.out.println("   No items");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                MagicItem item = inventory.get(i);
                System.out.println("   " + (i + 1) + ". " + item.getName() + " (" + item.getActivationType() + ")");
            }
        }

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
     * Resets the character's stats to their maximum values (including race bonuses
     * and equipped items) for the start of the
     * battle.
     */
    public void resetStats() {
        adjustStats(); // Recalculate max values based on currently equipped items
        this.hp = maxHP;
        this.ep = maxEP;
    }

    /**
     * Restores the character's HP or EP by a specified amount, ensuring it does not
     * exceed the maximum values (including race bonuses).
     * 
     * @param type   The type of bonus the player will get
     * @param amount The amount to restore.
     */
    public void restore(String type, int amount) {
        if ("HP".equalsIgnoreCase(type)) {
            if (hp + amount > maxHP) {
                this.hp = maxHP;
            } else {
                this.hp += amount;
            }
        } else if ("EP".equalsIgnoreCase(type)) {
            if (ep + amount > maxEP) {
                this.ep = maxEP;
            } else {
                this.ep += amount;
            }
        }
    }
}