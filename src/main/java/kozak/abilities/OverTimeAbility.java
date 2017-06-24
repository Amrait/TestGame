package kozak.abilities;

import kozak.Character;
import kozak.flags.AbilityType;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Спадкоємець класу Ability
//По суті, представляє собою відсотковий ефект
public class OverTimeAbility extends Ability {

    //Модифікатор задіяних полів
    private double modifier;
    //
    private boolean isDismissible;

    //Тривалість ефекту. Для завдання це, як правило, 1 (1 хід команди), однак можливі варіації
    private int duration;

    //Тип вміння. Дозволяє передавати цей флаг ефекту, який накладатиметься на ціль.
    //Наявність цього флагу дозволяє:
    //1. Знімати з цілі ефекти лише певного типу, як-от debuff/buff
    //2. Блокувати у власника вмінь певні навички, наприклад, блокувати здатність накладати прокляття чи абощо
    private AbilityType type = AbilityType.NOT_SPECIFIED;

    public OverTimeAbility(double _modifier, int _duration, boolean _isDismissible,
                           AbilityType _type, List<Field> _affectedFields) {
        this.modifier = _modifier;
        this.type = _type;
        this.duration = _duration;
        super.setAffectedFields(_affectedFields);
        this.isDismissible = _isDismissible;
    }

    @Override
    public void apply(List<Character> targets) {
        if (targets != null) {
            //на кожну ціль
            //створення даних в ефект
            Map<Field, Double> changes = new HashMap<>();
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
                                        PropertyUtils.getProperty(character, field.getName()) * modifier;
                                //Створення ефекту, який вміння накладає на ціль
                                //додання запису про змінене поле, а також число, на яке було модифіковано поле
                                changes.put(field,value-(Double) PropertyUtils.getProperty(character, field.getName()));
                                //створення самого ефекту. Задається власник ефекту та прапор, чи буде зроблено відкат
                                //модифікованих параметрів.
                                Effect effect = new Effect(character,this.isDismissible);
                                //ініціалізація початкової тривалості
                                effect.setTimeLeft(this.duration);
                                //додання даних про зміни
                                effect.setChangeValues(changes);
                                character.getEffects().add(effect);
                                //зміна параметру
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

    public double getModifier() {
        return modifier;
    }

    public void setModifier(double modifier) {
        this.modifier = modifier;
    }

    public AbilityType getType() {
        return type;
    }

    public void setType(AbilityType type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
