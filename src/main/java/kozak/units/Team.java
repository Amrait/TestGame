package kozak.units;

import kozak.abilities.Ability;
import kozak.abilities.AttackAbility;
import kozak.abilities.OverTimeAbility;
import kozak.abilities.RemoveEffectAbility;
import kozak.flags.AbilityType;
import kozak.flags.CharacterStats;
import kozak.flags.Factions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Team {

    private int size;
    private Factions faction;
    private List<Character> members;

    public Team(Factions _faction){
        this.members = new ArrayList<>(10);
        this.faction = _faction;
        switch (faction){
            case Elf:
                assembleElves();
                break;
            case Human:
                assembleHumans();
                break;
            case Ork:
                assembleOrkz(); // Warhammer 40k orkz (not orcs)
                break;
            case Undead:
                assembleUndead();
                break;
        }
        this.size = members.size();
    }
    public Factions getFaction() {
        return faction;
    }

    public List<Character> getMembers() {
        return members;
    }

    public void setMembers(List<Character> members) {
        this.members = members;
    }

    public int getSize() {
        return size;
    }
    public void updateTeamSize(){
        this.size = members.size();
    }
    private void assembleElves(){
        this.members.add(createElvenMage(100,1,"Elven mage"));
        for (int i = 1; i < 4; i++) {
            this.members.add(createElvenArcher(100,1,"Elven archer #"+i));
        }
        for (int i = 1; i < 5; i++) {
            this.members.add(createElvenSwordsman(100,1,"Elven swordsman #"+i));
        }
    }
    private void assembleHumans(){
        this.members.add(createHumanMage(100,1,"Human wizard"));
        for (int i = 1; i < 4; i++) {
            this.members.add(createHumanArcher(100,1,"Human crossbowman #"+i));
        }
        for (int i = 1; i < 5; i++) {
            this.members.add(createHumanSwordsman(100,1,"Human swordsman #"+i));
        }
    }
    private void assembleOrkz(){
        this.members.add(createOrkMage(100,1,"Ork shaman"));
        for (int i = 1; i < 4; i++) {
            this.members.add(createOrkArcher(100,1,"Ork goblin-archer #"+i));
        }
        for (int i = 1; i < 5; i++) {
            this.members.add(createOrkSwordsman(100,1,"Ork warrior #"+i));
        }
    }
    private void assembleUndead(){
        this.members.add(createUndeadMage(100,1,"Necromancer"));
        for (int i = 1; i < 4; i++) {
            this.members.add(createUndeadArcher(100,1,"Undead hunter #"+i));
        }
        for (int i = 1; i < 5; i++) {
            this.members.add(createUndeadSwordsman(100,1,"Undead zombie #"+i));
        }
    }

    public void update(boolean hasActed){
        List<Character> aliveMembers = new ArrayList<>(10);
        for (Character chara: this.getMembers()
             ) {
            if (chara.getCurrentHealth()>0){
                chara.update(hasActed);
                aliveMembers.add(chara);
            }
        }
        this.setMembers(aliveMembers);
        this.updateTeamSize();
    }
    //region "Методи створення ельфів"
    //створення мага
    public Character createElvenMage(double maxHealth, double damageMod, String name){
        Character mage = new Character(maxHealth, damageMod, name);
        try{
            //Підхід грубої сили
            Ability ability = new OverTimeAbility(1.5,2,
                    true,"Enhancement", AbilityType.BLESSING,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.damageModifier.name()))
                            .collect(Collectors.toList()));
            mage.getAbilities().add(ability);
            //Нове вміння
            ability = new AttackAbility(10, "Magic flames",mage,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            mage.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return mage;
    }
    //створення лучника
    public Character createElvenArcher(double maxHealth, double damageMod, String name){
        Character archer = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new AttackAbility(7,"Bow shot", archer,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            archer.getAbilities().add(ability);
            //Нове вміння
            ability = new AttackAbility(3, "Melee attack",archer,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            archer.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return archer;
    }
    //створення мечника
    private Character createElvenSwordsman(double maxHealth, double damageMod, String name){
        Character swordsman = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new AttackAbility(15,"Sword slash", swordsman,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            swordsman.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return swordsman;
    }
    //endregion
    //region "Методи створення людей"
    //створення чаклуна
    private Character createHumanMage(double maxHealth, double damageMod, String name){
        Character mage = new Character(maxHealth, damageMod, name);
        try{
            //Підхід грубої сили
            Ability ability = new OverTimeAbility(1.5,2,
                    true,"Bulls strength", AbilityType.BLESSING,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.damageModifier.name()))
                            .collect(Collectors.toList()));
            mage.getAbilities().add(ability);
            //Нове вміння
            ability = new AttackAbility(4, "Magic missile",mage,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            mage.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return mage;
    }
    //створення арбалетника
    private Character createHumanArcher(double maxHealth, double damageMod, String name){
        Character crossbowman = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new AttackAbility(5,"Crossbow bolt", crossbowman,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            crossbowman.getAbilities().add(ability);
            //Нове вміння
            ability = new AttackAbility(3, "Dagger stab",crossbowman,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            crossbowman.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return crossbowman;
    }
    //створення мечника
    private Character createHumanSwordsman(double maxHealth, double damageMod, String name){
        Character swordsman = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new AttackAbility(18,"Greatsword cleave", swordsman,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            swordsman.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return swordsman;
    }
    //endregion
    //region "Методи створення орків"
    private Character createOrkMage(double maxHealth, double damageMod, String name){
        Character mage = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new  OverTimeAbility(1.5,2,
                    true,"Enhancement", AbilityType.BLESSING,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.damageModifier.name()))
                            .collect(Collectors.toList()));
            mage.getAbilities().add(ability);
            //Нове вміння
            ability = new RemoveEffectAbility(AbilityType.BLESSING, "Dismiss enchantment");
            mage.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return mage;
    }
    //створення лучника
    private Character createOrkArcher(double maxHealth, double damageMod, String name){
        Character archer = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new AttackAbility(3,"Bow shot", archer,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            archer.getAbilities().add(ability);
            //Нове вміння
            ability = new AttackAbility(2, "Sneaky slash",archer,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            archer.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return archer;
    }
    //створення мечника
    private Character createOrkSwordsman(double maxHealth, double damageMod, String name){
        Character swordsman = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new AttackAbility(20,"Mace bash", swordsman,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            swordsman.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return swordsman;
    }
    //endregion
    //region "Методи створення мерців"
    private Character createUndeadMage(double maxHealth, double damageMod, String name){
        Character mage = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new OverTimeAbility(0.5,2,
                    true,"Weakness", AbilityType.MALEDICTION,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.damageModifier.name()))
                            .collect(Collectors.toList()));
            mage.getAbilities().add(ability);
            //Нове вміння
            ability = new AttackAbility(5, "Life leach",mage,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            mage.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return mage;
    }
    //створення лучника
    private Character createUndeadArcher(double maxHealth, double damageMod, String name){
        Character archer = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new AttackAbility(4,"Bow shot", archer,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            archer.getAbilities().add(ability);
            //Нове вміння
            ability = new AttackAbility(2, "Undead assault",archer,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            archer.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return archer;
    }
    //створення мечника
    private Character createUndeadSwordsman(double maxHealth, double damageMod, String name){
        Character swordsman = new Character(maxHealth, damageMod, name);
        try{
            Ability ability = new AttackAbility(18,"Spear thrust", swordsman,
                    Stream.of(Character.class.getDeclaredField(CharacterStats.currentHealth.name()))
                            .collect(Collectors.toList()));
            swordsman.getAbilities().add(ability);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return swordsman;
    }

    //endregion
}
