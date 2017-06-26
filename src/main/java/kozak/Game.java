package kozak;

import kozak.abilities.Ability;
import kozak.abilities.Effect;
import kozak.flags.AbilityType;
import kozak.flags.Factions;
import kozak.units.Character;
import kozak.units.Team;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

public class Game {
    private Map<Factions, List<Factions>> relations;
    private static final Logger logger = Logger.getRootLogger();

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
        try {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
            logger.info(sdf.format(date) + " - The Battle Has Began!");
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
            logger.info("----- Battle has ended -----");
            logger.info("----- First team survivors -----");
            for (Character chara: firstTeam.getMembers()
                    ) {
                logger.info(chara.toString());
            }
            logger.info("----- Second team survivors -----");
            for (Character chara: secondTeam.getMembers()
                    ) {
                logger.info(chara.toString());
            }
        }
        catch (Exception e){
            logger.error(e.toString());
            logger.error("----- Stack Trace -----");
            for (StackTraceElement ste: e.getStackTrace()
                    ) {
                logger.error(ste);
            }
            logger.error("----- Stack Trace Ended-----");
        }

    }

    private void takeTurn(Team actingTeam, Team oppositeTeam) {
        try {
            logger.info("----- Team " + actingTeam.getFaction() + " turn -----");
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
            logger.info(character.toString() + " used ability " + ability.getName()
                    + " on target " + target.toString() + " and " + ability.printActionText()
                    + target.toString() + " has " + target.getCurrentHealth()
                    + " hit points left.");
            if (!target.isAlive())
                logger.info(target.toString() + " was killed as a result.");
        }
        catch (Exception e){
            logger.error(e.toString());
            logger.error("----- Stack Trace -----");
            for (StackTraceElement ste: e.getStackTrace()
                    ) {
                logger.error(ste);
            }
            logger.error("----- Stack Trace Ended-----");
        }

    }

    private Character pickActingChar(Team team) {
        Character actingChar = null;
        try{
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
        }
        catch (Exception e){
            logger.error(e.toString());
            logger.error("----- Stack Trace -----");
            for (StackTraceElement ste: e.getStackTrace()
                 ) {
                logger.error(ste);
            }
            logger.error("----- Stack Trace Ended-----");
        }
        return actingChar;
    }
}
