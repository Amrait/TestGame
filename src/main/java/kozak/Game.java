package kozak;

import kozak.abilities.Ability;
import kozak.abilities.Effect;
import kozak.flags.AbilityType;
import kozak.flags.Factions;
import kozak.units.Character;
import kozak.units.Team;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    private Map<Factions, List<Factions>> relations;

    private Game() {
        relations = new HashMap<>(5);
        ArrayList<Factions> goodSideEnemies = new ArrayList<>(Stream.of
                (Factions.Ork, Factions.Undead).collect(Collectors.toList()));
        ArrayList<Factions> badSideEnemies = new ArrayList<>(Stream.of
                (Factions.Elf, Factions.Human).collect(Collectors.toList()));
        relations.put(Factions.Elf, goodSideEnemies);
        relations.put(Factions.Human, goodSideEnemies);
        relations.put(Factions.Ork, badSideEnemies);
        relations.put(Factions.Undead, badSideEnemies);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    private void run() {
        Team firstTeam = new Team(Factions.values()[ThreadLocalRandom.current()
                .nextInt(0, 4)]);
        Team secondTeam = new Team(relations.get(firstTeam.getFaction())
                .get(ThreadLocalRandom.current()
                        .nextInt(0, 2)));
        while (firstTeam.getSize() > 0 && secondTeam.getSize() > 0) {
            int coinFlip = ThreadLocalRandom.current().nextInt(1, 3);
            switch (coinFlip) {
                case 1: {
                    takeTurn(firstTeam, secondTeam);
                    firstTeam.update(true);
                    secondTeam.update(false);
                    break;
                }
                case 2: {
                    takeTurn(secondTeam, firstTeam);
                    secondTeam.update(true);
                    firstTeam.update(false);
                    break;
                }
            }
        }
        System.out.println("Survivors:\nFirst team:");
        for (Character chara: firstTeam.getMembers()
             ) {
            System.out.println(chara);
        }
        System.out.println("Survivors:\nSecond team:");
        for (Character chara: secondTeam.getMembers()
             ) {
            System.out.println(chara);
        }
    }

    private void takeTurn(Team actingTeam, Team oppositeTeam) {
        System.out.println("----- Team " + actingTeam.getFaction() + " turn -----");
        Character character = pickActingChar(actingTeam);
        Character target;
        Ability ability = character.getAbilities()
                            .get(ThreadLocalRandom.current()
                            .nextInt(0,character.getAbilities().size()));
        if (ability.getType()!=AbilityType.BLESSING){
            target = oppositeTeam.getMembers()
                            .get(ThreadLocalRandom.current()
                            .nextInt(0, oppositeTeam.getMembers()
                            .size()));
        }
        else {target = pickActingChar(actingTeam);}
        ability.apply(Stream.of(target).collect(Collectors.toList()));
        System.out.println(character.toString() + " used ability " + ability.getName()
        + " on target " + target.toString() + " and " + ability.printActionText());
        if (!target.isAlive())
            System.out.println(target.toString() + " was killed as a result.");
    }

    private Character pickActingChar(Team team) {
        Character actingChar = null;
        for (Character chara : team.getMembers()
                ) {
            for (Effect effect : chara.getEffects()
                    ) {
                if (effect.getType().equals(AbilityType.BLESSING)) {
                    actingChar = chara;
                    break;
                }
            }
        }
        if (actingChar == null)
            actingChar = team.getMembers()
                            .get(ThreadLocalRandom.current()
                            .nextInt(0, team.getMembers()
                            .size()));
        return actingChar;
    }
}
