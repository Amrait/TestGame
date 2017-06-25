package kozak.units;

import kozak.abilities.Ability;
import kozak.abilities.Effect;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private double maxHealth;
    private double currentHealth;
    private double damageModifier;
    private String name;
    private List<Ability> abilities;
    private List<Effect> effects;

    public Character(){
        this.abilities = new ArrayList<>(6);    //для економії пам'яті
        this.effects = new ArrayList<>(6);
    }

    public Character(double _maxHealth, double _damageMod, String _name){
        this.abilities = new ArrayList<>(6);
        this.effects = new ArrayList<>(6);
        this.name = _name;
        this.maxHealth = _maxHealth;
        this.currentHealth = maxHealth;
        this.damageModifier = _damageMod;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(double currentHealth) {
        this.currentHealth = currentHealth;
    }

    public double getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(double damageModifier) {
        this.damageModifier = damageModifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public void update(boolean hasActed) {
        List<Effect> remainingEffects = new ArrayList<>(6);
        for (Effect effect: effects
             ) {
            effect = effect.update(hasActed);
            if (effect!=null) remainingEffects.add(effect);
        }
        effects = remainingEffects;
    }

    public boolean isAlive() {
        return this.currentHealth>0;
    }
}
