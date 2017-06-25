package kozak.abilities;

import kozak.flags.AbilityType;
import kozak.units.Character;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AttackAbility extends Ability {
    //Кількість шкоди
    private double damage;
    //Власник
    private Character user;


    public AttackAbility(double _damage, String _name, Character _user,List<Field> _affectedFields){
        this.damage = _damage;
        this.user = _user;
        super.setType(AbilityType.ATTACK);
        super.setAffectedFields(_affectedFields);
        super.setName(_name);
    }

    @Override
    public void apply(List<Character> targets) {
        if (targets != null) {
            //на кожну ціль
            //створення даних в ефект
            for (Character character : targets
                    ) {
                //перебір полів, на які впливає дане вміння
                for (Field affected : this.getAffectedFields()) {
                    //порівняння з полями, які є у персонажа
                    for (Field field : character.getClass().getDeclaredFields()) {
                        if (affected.equals(field)) {
                            try {
                                //обрахування нового значення параметру
                                Double value = (Double)
                                        PropertyUtils.getProperty(character, field.getName())
                                        - damage*user.getDamageModifier();
                                PropertyUtils.setProperty(character, field.getName(), value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public String printActionText(){
        return "delivered " + damage*user.getDamageModifier() + " damage.";
    }
    @Override
    public String toString() {
        return "AttackAbility{" +
                "name=" + super.getName() +
                ", damage=" + damage +
                ", user=" + user +
                ", type=" + super.getType() +
                '}';
    }
}
