package kozak;

import kozak.abilities.Ability;
import kozak.abilities.OverTimeAbility;
import kozak.flags.AbilityType;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    public static void main(String[] args) {
        //Попередні тести
        Character character = new Character();
        character.setName("Necromancer");
        character.setMaxHealth(100);
        character.setCurrentHealth(character.getMaxHealth());
        Set<Ability> abilities = new HashSet<>();
        List<Field> fields = new ArrayList<>();
        try {
            fields.add(character.getClass().getDeclaredField("maxHealth"));
            System.out.println("Main: field added");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Ability ability = new OverTimeAbility(1.5f,1, AbilityType.BLESSING, fields);
        System.out.println("Main: ability created");
        ability.apply(Stream.of(character).collect(Collectors.toList()));
        System.out.println(character.getMaxHealth());
//        abilities.add(ability);
//        character.setAbilities(abilities);

    }
}
