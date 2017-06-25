package kozak.abilities;

import kozak.flags.AbilityType;
import kozak.units.Character;
import java.util.ArrayList;
import java.util.List;

public class RemoveEffectAbility extends Ability{
    private AbilityType targetType;
    public RemoveEffectAbility(AbilityType _targetType, String _name){
        super.setType(AbilityType.DISSPELL);
        this.targetType = _targetType;
        super.setName(_name);
    }
    @Override
    public void apply(List<Character> targets) {
        if (targets != null) {
            //на кожну ціль
            //створення даних в ефект
            for (Character character : targets
                    ) {
                List<Effect> newEffects = new ArrayList<>(10);
                for (Effect effect : character.getEffects()) {
                    if (!effect.getType().equals(this.targetType)) newEffects.add(effect);
                }
                character.setEffects(newEffects);
            }
        }
    }
    @Override
    public String printActionText(){
        return "applied " + super.getType()
                + " effect, removing all "
                + targetType + " from target.";
    }
}
