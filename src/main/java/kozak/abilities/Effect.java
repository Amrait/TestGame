package kozak.abilities;

import kozak.flags.AbilityType;
import kozak.units.Character;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Effect {
    private Character owner;
    private int timeLeft;
    private boolean isDismissible;
    private Map<Field,Double> changeValues;
    private AbilityType type = AbilityType.NOT_SPECIFIED;

    public Effect(Character _owner, boolean _isDismissible, AbilityType _type){
        this.isDismissible = _isDismissible;
        this.owner = _owner;
        this.type = _type;
    }
    //Метод повертає Effect з тих міркувань, що в межах dismiss() не буде змоги
    //видалити неактивний ефект з набору діючих ефектів, тому перевірка буде здійснюватись
    //на рівні Character.update() і вже потім будуть видалятись недійсні ефекти
    public Effect update(boolean acted){
        if (acted){
            this.timeLeft--;
            if (this.timeLeft>0) return this;
            else {
                this.dismiss();
                return null;
            }
        } else return this;
    }
    private void dismiss() {
        if (isDismissible) {
            for (Field field : owner.getClass().getDeclaredFields()) {
                if (changeValues.containsKey(field)) {
                    try {
                        //обрахування нового значення параметру
                        Double value = (Double)
                                PropertyUtils.getProperty(owner, field.getName()) - changeValues.get(field);
                        //зміна параметру
                        PropertyUtils.setProperty(owner, field.getName(), value);
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
    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Map<Field, Double> getChangeValues() {
        return changeValues;
    }

    public void setChangeValues(Map<Field, Double> changeValues) {
        this.changeValues = changeValues;
    }

    public AbilityType getType() {
        return type;
    }
}
