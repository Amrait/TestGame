package kozak.abilities;

import kozak.Character;

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



    //Метод, який застосовує ло всіх цілей заданий ефект
    public abstract void apply(List<Character> targets);

    List<Field> getAffectedFields() {
        return affectedFields;
    }

    void setAffectedFields(List<Field> affectedFields) {
        this.affectedFields = affectedFields;
    }

}
