package kozak.abilities;

import kozak.flags.AbilityType;
import kozak.units.Character;

import java.lang.reflect.Field;
import java.util.List;

//клас оголошено абстрактним, аби в майбутньому можна було створити
//класи-спадкоємці з різною механікою дії, однак в усі методи передавати
//як параметр базовий клас.
public abstract class Ability {
    //Список полів персонажа, на які буде здійснено вплив
    //В майбутньому, є сенс передавати HashMap<Field, Modifier>,
    //аби окремі поля можна було змінювати по-своєму
    private List<Field> affectedFields;

    //Назва вміння
    private String name;

    //Тип вміння. Дозволяє передавати цей флаг ефекту, який накладатиметься на ціль.
    //Наявність цього флагу дозволяє:
    //1. Знімати з цілі ефекти лише певного типу, як-от debuff/buff
    //2. Блокувати у власника вмінь певні навички, наприклад, блокувати здатність накладати прокляття чи абощо
    private AbilityType type = AbilityType.NOT_SPECIFIED;

    //Метод, який застосовує ло всіх цілей заданий ефект
    public abstract void apply(List<Character> targets);

    public abstract String printActionText();

    public List<Field> getAffectedFields() {
        return affectedFields;
    }

    public void setAffectedFields(List<Field> affectedFields) {
        this.affectedFields = affectedFields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbilityType getType() {
        return type;
    }

    public void setType(AbilityType type) {
        this.type = type;
    }
}
