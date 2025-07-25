package model;
/**
 * The Ability class represents a single action or move that a character can perform in battle.
 * Each ability has a name, description, energy cost, damage, healing amount, and status effects
 * such as shielding or evasion.
 *
 * This class is used to define the properties and effects of class-specific and universal moves
 * , and supports stat retrieval for use in battle logic.
 */

public class Ability {
    private String name;
    private String description;
    private int epCost;
    private int damage;
    private int restore;
    private boolean isSpecialAbility;
    private String restoreType;

    // Constructor methods

    /**
     * Constructs an Ability with basic parameters, without specifying a restore type.
     * This constructor is typically used for abilities that do not restore HP or EP explicitly.
     *
     * @param name the name of the ability
     * @param description a brief description of the ability's effect
     * @param epCost the energy cost to use the ability
     * @param damage the amount of damage the ability deals
     * @param restore the amount of HP or EP restored (if any)
     * @param isSpecialAbility true if the ability includes special effects; false otherwise
     */

    public Ability(String name, String description, int epCost, int damage, int restore,
            boolean isSpecialAbility) {
        this.name = name;
        this.description = description;
        this.epCost = epCost;
        this.damage = damage;
        this.restore = restore;
        this.isSpecialAbility = isSpecialAbility;
        this.restoreType = null;
    }

    /**
     * Constructs an Ability with full parameters including the restore type.
     * This constructor is used for abilities that restore HP or EP and need to specify which type.
     *
     * @param name the name of the ability
     * @param description a brief description of the ability's effect
     * @param epCost the energy cost to use the ability
     * @param damage the amount of damage the ability deals
     * @param restore the amount of HP or EP restored
     * @param isSpecialAbility true if the ability includes special effects; false otherwise
     * @param restoreType the type of stat being restored ("HP" or "EP")
     */

    public Ability(String name, String description, int epCost, int damage, int restore, boolean isSpecialAbility,
            String restoreType) {
        this.name = name;
        this.description = description;
        this.epCost = epCost;
        this.damage = damage;
        this.restore = restore;
        this.isSpecialAbility = isSpecialAbility;
        this.restoreType = restoreType;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getEpCost() {
        return epCost;
    }

    public int getDamage() {
        return damage;
    }

    public int getRestore() {
        return restore;
    }

    public boolean isSpecialAbility() {
        return isSpecialAbility;
    }

    public String getRestoreType() {
        return restoreType;
    }
}