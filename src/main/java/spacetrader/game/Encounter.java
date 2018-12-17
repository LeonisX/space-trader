package spacetrader.game;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.StringContainer;
import spacetrader.game.quest.enums.EventName;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.EventName.ON_ENCOUNTER_VERIFY_SURRENDER_HIDDEN;

public class Encounter implements Serializable {
    
    private Game game;
    private Commander commander;
    
    private boolean easyEncounters = false;

    //TODO need initialize??? test
    private EncounterType encounterType = EncounterType.values()[0]; // Type of current encounter
    private Map<Integer, EncounterType> encounters; // Encounters by ID

    private int chanceOfVeryRareEncounter = 5; // Rare encounters not done yet.
    private ArrayList<VeryRareEncounter> veryRareEncounters = new ArrayList<>(6); // Array of Very Options

    // The rest of the member variables are not saved between games.
    private boolean encounterContinueFleeing = false;
    private boolean encounterContinueAttacking = false;
    private boolean encounterCmdrFleeing = false;
    private boolean encounterCmdrHit = false;
    private boolean encounterOppFleeingPrev = false;
    private boolean encounterOppFleeing = false;
    private boolean encounterOppHit = false;

    public Encounter(Game game) {
        this.game = game;
        this.commander = Game.getCommander();
        
        resetVeryRareEncounters();

        encounters = Arrays.stream(EncounterType.values()).collect(Collectors.toMap(EncounterType::castToInt, e -> e));
    }

    public boolean isDetermineEncounter() {
        // If there is a specific encounter that needs to happen, it will,
        // otherwise we'll generate a random encounter.
        return isDetermineNonRandomEncounter() || isDetermineRandomEncounter();
    }

    private boolean isDetermineNonRandomEncounter() {
        BooleanContainer showEncounter = new BooleanContainer(false);

        // Encounter with space monster
        if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Acamar
                && game.getQuestStatusSpaceMonster() == SpecialEvent.STATUS_SPACE_MONSTER_AT_ACAMAR) {
            game.setOpponent(game.spaceMonster);
            setEncounterType(commander.getShip().isCloaked() ? EncounterType.SPACE_MONSTER_IGNORE
                    : EncounterType.SPACE_MONSTER_ATTACK);
            showEncounter.setValue(true);
        }
        // Encounter with the stolen Scarab
        else if (game.getArrivedViaWormhole() && game.getClicks() == 20 && game.getWarpSystem().getSpecialEventType() != SpecialEventType.NA
                && game.getWarpSystem().specialEvent().getType() == SpecialEventType.ScarabDestroyed
                && game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_HUNTING) {
            game.setOpponent(game.getScarab());
            setEncounterType(commander.getShip().isCloaked() ? EncounterType.SCARAB_IGNORE
                    : EncounterType.SCARAB_ATTACK);
            showEncounter.setValue(true);
        }
        // Encounter with stolen Dragonfly
        else if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Zalkon
                && game.getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_ZALKON) {
            game.setOpponent(game.getDragonfly());
            setEncounterType(commander.getShip().isCloaked() ? EncounterType.DRAGONFLY_IGNORE
                    : EncounterType.DRAGONFLY_ATTACK);
            showEncounter.setValue(true);
        }
        // Encounter with kidnappers in the Scorpion
        /*else if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Qonos
                && getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_FLY_QONOS) {
            setOpponent(scorpion);
            setEncounterType(commander.getShip().isCloaked() ? EncounterType.SCORPION_IGNORE
                    : EncounterType.SCORPION_ATTACK);
            showEncounter.setValue(true);
        }*/
        // ah, just when you thought you were gonna get away with it...
        else if (game.getClicks() == 1 && game.getJustLootedMarie()) {
            game.generateOpponent(OpponentType.POLICE);
            setEncounterType(EncounterType.MARIE_CELESTE_POLICE);
            game.setJustLootedMarie(false);

            showEncounter.setValue(true);
        }

        game.getQuestSystem().fireEvent(EventName.ON_DETERMINE_NON_RANDOM_ENCOUNTER, showEncounter);

        return showEncounter.getValue();
    }

    private boolean isDeterminePirateEncounter(boolean mantis) {
        if (mantis) {
            game.generateOpponent(OpponentType.MANTIS);
            setEncounterType(EncounterType.PIRATE_ATTACK);
        } else {
            game.generateOpponent(OpponentType.PIRATE);

            // If you have a cloak, they don't see you
            if (commander.getShip().isCloaked()) {
                setEncounterType(EncounterType.PIRATE_IGNORE);
                // Pirates will mostly attack, but they are cowardly: if your rep is
                // too high, they tend to flee
                // if Pirates are in a better ship, they won't flee, even if you
                // have a very scary
                // reputation.
            } else if (game.getOpponent().getType().castToInt() > commander.getShip().getType().castToInt()
                    || game.getOpponent().getType().castToInt() >= ShipType.GRASSHOPPER.castToInt()
                    || Functions.getRandom(Consts.ReputationScoreElite) > (commander.getReputationScore() * 4)
                    / (1 + game.getOpponent().getType().castToInt())) {
                setEncounterType(EncounterType.PIRATE_ATTACK);
            } else {
                setEncounterType(EncounterType.PIRATE_FLEE);
            }
        }

        // If they ignore you or flee and you can't see them, the encounter
        // doesn't take place
        // If you automatically don't want to confront someone who ignores you,
        // the
        // encounter may not take place
        return getEncounterType() == EncounterType.PIRATE_ATTACK || !(game.getOpponent().isCloaked() || game.getOptions()
                .isAlwaysIgnorePirates());
    }

    private boolean isDeterminePoliceEncounter() {
        game.generateOpponent(OpponentType.POLICE);

        // If you are cloaked, they don't see you
        setEncounterType(EncounterType.POLICE_IGNORE);
        if (!commander.getShip().isCloaked()) {
            if (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreDubious) {
                // If you're a criminal, the police will tend to attack
                // JAF - fixed this; there was code that didn't do anything.
                // if you're suddenly stuck in a lousy ship, Police won't flee
                // even if you
                // have a fearsome reputation.
                if (game.getOpponent().getWeaponStrength() > 0
                        && (commander.getReputationScore() < Consts.ReputationScoreAverage || Functions
                        .getRandom(Consts.ReputationScoreElite) > (commander.getReputationScore() / (1 + game.getOpponent()
                        .getType().castToInt())))
                        || game.getOpponent().getType().castToInt() > commander.getShip().getType().castToInt()) {
                    if (commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreCriminal) {
                        setEncounterType(EncounterType.POLICE_SURRENDER);
                    } else {
                        setEncounterType(EncounterType.POLICE_ATTACK);
                    }
                } else if (game.getOpponent().isCloaked()) {
                    setEncounterType(EncounterType.POLICE_IGNORE);
                } else {
                    setEncounterType(EncounterType.POLICE_FLEE);
                }
            } else if (!game.getInspected()
                    && (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreClean
                    || (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreLawful && Functions
                    .getRandom(12 - Game.getDifficultyId()) < 1) || (commander
                    .getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful && Functions.getRandom(40) == 0))) {
                // If you're reputation is dubious, the police will inspect you
                // If your record is clean, the police will inspect you with a
                // chance of 10% on Normal
                // If your record indicates you are a lawful trader, the chance
                // on inspection drops to 2.5%
                setEncounterType(EncounterType.POLICE_INSPECT);
                game.setInspected(true);
            }
        }

        // If they ignore you or flee and you can't see them, the encounter
        // doesn't take place
        // If you automatically don't want to confront someone who ignores you,
        // the
        // encounter may not take place. Otherwise it will - JAF
        return getEncounterType() == EncounterType.POLICE_ATTACK || getEncounterType() == EncounterType.POLICE_INSPECT
                || !(game.getOpponent().isCloaked() || game.getOptions().isAlwaysIgnorePolice());
    }

    private boolean isDetermineRandomEncounter() {
        boolean mantis = false;
        boolean pirate = false;
        boolean police = false;
        boolean trader = false;

        if (game.getWarpSystem().getId() == StarSystemId.Gemulon && game.getQuestStatusGemulon() == SpecialEvent.STATUS_GEMULON_TOO_LATE) {
            if (Functions.getRandom(10) > 4) {
                mantis = true;
            }
        } else {
            // Check if it is time for an encounter
            int encounter = Functions.getRandom(44 - (2 * Game.getDifficultyId()));
            int policeModifier = Math.max(1, 3 - PoliceRecord.getPoliceRecordFromScore().getType().castToInt());

            // encounters are half as likely if you're in a flea.
            if (commander.getShip().getType() == ShipType.FLEA) {
                encounter *= 2;
            }

            if (encounter < game.getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()) {
                // When you are already raided, other pirates have little to gain
                pirate = !game.getRaided();
            } else if (encounter < game.getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()
                    + game.getWarpSystem().getPoliticalSystem().getActivityPolice().castToInt() * policeModifier) {
                // policeModifier adapts itself to your criminal record: you'll
                // encounter more police if you are a hardened criminal.
                police = true;
            } else if (encounter < game.getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()
                    + game.getWarpSystem().getPoliticalSystem().getActivityPolice().castToInt() * policeModifier
                    + game.getWarpSystem().getPoliticalSystem().getActivityTraders().castToInt()) {
                trader = true;
            } else if (commander.getShip().isWildOnBoard() && game.getWarpSystem().getId() == StarSystemId.Kravat) {
                // if you're coming in to Kravat & you have Wild onboard, there'll be swarms o' cops.
                police = Functions.getRandom(100) < 100 / Math.max(2, Math.min(4, 5 - Game.getDifficultyId()));
            } else if (commander.getShip().isArtifactOnBoard() && Functions.getRandom(20) <= 3) {
                mantis = true;
            }
        }

        if (police) {
            return isDeterminePoliceEncounter();
        } else if (pirate || mantis) {
            return isDeterminePirateEncounter(mantis);
        } else if (trader) {
            return isDetermineTraderEncounter();
        } else if (commander.getDays() > 10 && Functions.getRandom(1000) < getChanceOfVeryRareEncounter()
                && getVeryRareEncounters().size() > 0) {
            return isDetermineVeryRareEncounter();
        } else {
            return false;
        }
    }

    private boolean isDetermineTraderEncounter() {
        game.generateOpponent(OpponentType.TRADER);

        // If you are cloaked, they don't see you
        setEncounterType(EncounterType.TRADER_IGNORE);
        if (!commander.getShip().isCloaked()) {
            // If you're a criminal, traders tend to flee if you've got at least some reputation
            if (!commander.getShip().isCloaked()
                    && commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                    && Functions.getRandom(Consts.ReputationScoreElite) <= (commander.getReputationScore() * 10)
                    / (1 + game.getOpponent().getType().castToInt())) {
                setEncounterType(EncounterType.TRADER_FLEE);
                // Will there be trade in orbit?
            } else if (Functions.getRandom(1000) < game.getChanceOfTradeInOrbit()) {
                if (commander.getShip().getFreeCargoBays() > 0 && game.getOpponent().hasTradeableItems()) {
                    setEncounterType(EncounterType.TRADER_SELL);
                    // we fudge on whether the trader has capacity to carry the stuff he's buying.
                } else if (commander.getShip().hasTradeableItems()) {
                    setEncounterType(EncounterType.TRADER_BUY);
                }
            }
        }

        // If they ignore you or flee and you can't see them, the encounter doesn't take place
        // If you automatically don't want to confront someone who ignores you, the
        // encounter may not take place; otherwise it will.
        return !game.getOpponent().isCloaked() && !(game.getOptions().isAlwaysIgnoreTraders() && (
                getEncounterType() == EncounterType.TRADER_IGNORE || getEncounterType() == EncounterType.TRADER_FLEE))
                && !((getEncounterType() == EncounterType.TRADER_BUY || getEncounterType() == EncounterType.TRADER_SELL)
                && game.getOptions().isAlwaysIgnoreTradeInOrbit());
    }

    public boolean isDetermineVeryRareEncounter() {
        // Very Rare Random Events:
        // 1. Encounter the abandoned Marie Celeste, which you may loot.
        // 2. Captain Ahab will trade your Reflective Shield for skill points in Piloting.
        // 3. Captain Conrad will trade your Military Laser for skill points in Engineering.
        // 4. Captain Huie will trade your Military Laser for points in Trading.
        // 5. Encounter an out-of-date bottle of Captain Marmoset's Skill Tonic.
        // This will affect skills depending on game difficulty level.
        // 6. Encounter a good bottle of Captain Marmoset's Skill Tonic, which will invoke
        // IncreaseRandomSkill one or two times, depending on game difficulty.
        switch (getVeryRareEncounters().get(Functions.getRandom(getVeryRareEncounters().size()))) {
            case MARIE_CELESTE:
                // Marie Celeste cannot be at Acamar, Qonos, or Zalkon as it may
                // cause problems with the Space Monster, Scorpion, or Dragonfly
                if (game.getClicks() > 1 && commander.getCurrentSystemId() != StarSystemId.Acamar
                        && commander.getCurrentSystemId() != StarSystemId.Zalkon
                        && commander.getCurrentSystemId() != StarSystemId.Qonos) {
                    getVeryRareEncounters().remove(VeryRareEncounter.MARIE_CELESTE);
                    setEncounterType(EncounterType.MARIE_CELESTE);
                    game.generateOpponent(OpponentType.TRADER);
                    for (int i = 0; i < game.getOpponent().getCargo().length; i++) {
                        game.getOpponent().getCargo()[i] = 0;
                    }
                    game.getOpponent().getCargo()[TradeItemType.NARCOTICS
                            .castToInt()] = Math.min(game.getOpponent().getCargoBays(), 5);
                    return true;
                }
                break;

            case CAPTAIN_AHAB:
                if (commander.getShip().hasShield(ShieldType.REFLECTIVE) && commander.getPilot() < 10
                        && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_AHAB);
                    setEncounterType(EncounterType.CAPTAIN_AHAB);
                    game.generateOpponent(OpponentType.FAMOUS_CAPTAIN);
                    return true;
                }
                break;

            case CAPTAIN_CONRAD:
                if (commander.getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && commander.getEngineer() < 10
                        && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_CONRAD);
                    setEncounterType(EncounterType.CAPTAIN_CONRAD);
                    game.generateOpponent(OpponentType.FAMOUS_CAPTAIN);
                    return true;
                }
                break;

            case CAPTAIN_HUIE:
                if (commander.getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && commander.getTrader() < 10
                        && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_HUIE);
                    setEncounterType(EncounterType.CAPTAIN_HUIE);
                    game.generateOpponent(OpponentType.FAMOUS_CAPTAIN);
                    return true;
                }
                break;

            case BOTTLE_OLD:
                getVeryRareEncounters().remove(VeryRareEncounter.BOTTLE_OLD);
                setEncounterType(EncounterType.BOTTLE_OLD);
                game.generateOpponent(OpponentType.BOTTLE);
                return true;

            case BOTTLE_GOOD:
                getVeryRareEncounters().remove(VeryRareEncounter.BOTTLE_GOOD);
                setEncounterType(EncounterType.BOTTLE_GOOD);
                game.generateOpponent(OpponentType.BOTTLE);
                return true;
        }

        return false;
    }

    public void encounterBegin() {
        // Set up the encounter variables.
        setEncounterContinueFleeing(setEncounterContinueAttacking(game.setOpponentDisabled(false)));
    }

    private void encounterDefeatDragonfly() {
        commander.setKillsPirate(commander.getKillsPirate() + 1);
        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
        game.setQuestStatusDragonfly(SpecialEvent.STATUS_DRAGONFLY_DESTROYED);
    }

    private void encounterDefeatScarab() {
        commander.setKillsPirate(commander.getKillsPirate() + 1);
        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
        game.setQuestStatusScarab(SpecialEvent.STATUS_SCARAB_DESTROYED);
    }

    /*private void encounterDefeatScorpion() {
        commander.setKillsPirate(commander.getKillsPirate() + 1);
        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
        setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_RESCUED);
    }*/

    public void encounterDrink() {
        if (GuiFacade.alert(AlertType.EncounterDrinkContents) == DialogResult.YES) {
            if (getEncounterType() == EncounterType.BOTTLE_GOOD) {
                // two points if you're on beginner-normal, one otherwise
                commander.increaseRandomSkill();
                if (Game.getDifficultyId() <= Difficulty.NORMAL.castToInt()) {
                    commander.increaseRandomSkill();
                }
                GuiFacade.alert(AlertType.EncounterTonicConsumedGood);
            } else {
                commander.tonicTweakRandomSkill();
                GuiFacade.alert(AlertType.EncounterTonicConsumedStrange);
            }
        }
    }

    public EncounterResult getEncounterExecuteAction() {
        EncounterResult result = EncounterResult.CONTINUE;
        int prevCmdrHull = commander.getShip().getHull();
        int prevOppHull = game.getOpponent().getHull();

        setEncounterCmdrHit(false);
        setEncounterOppHit(false);
        setEncounterOppFleeingPrev(getEncounterOppFleeing());
        setEncounterOppFleeing(false);

        // Fire shots
        switch (getEncounterType()) {
            case DRAGONFLY_ATTACK:
            case FAMOUS_CAPTAIN_ATTACK:
            case MARIE_CELESTE_POLICE:
            case PIRATE_ATTACK:
            case POLICE_ATTACK:
            case SCARAB_ATTACK:
            case QUEST_ATTACK:
            case SPACE_MONSTER_ATTACK:
            case TRADER_ATTACK:
                setEncounterCmdrHit(isEncounterExecuteAttack(game.getOpponent(), commander.getShip(), getEncounterCmdrFleeing()));
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && isEncounterExecuteAttack(commander.getShip(), game.getOpponent(), false));
                break;
            case PIRATE_FLEE:
            case PIRATE_SURRENDER:
            case POLICE_FLEE:
            case TRADER_FLEE:
            case TRADER_SURRENDER:
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && isEncounterExecuteAttack(commander.getShip(), game.getOpponent(), true));
                setEncounterOppFleeing(true);
                break;
            default:
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && isEncounterExecuteAttack(commander.getShip(), game.getOpponent(), false));
                break;
        }

        // Determine whether someone gets destroyed
        if (commander.getShip().getHull() <= 0) {
            if (commander.getShip().getEscapePod()) {
                result = EncounterResult.ESCAPE_POD;
            } else {
                GuiFacade.alert((game.getOpponent().getHull() <= 0 ? AlertType.EncounterBothDestroyed
                        : AlertType.EncounterYouLose));

                result = EncounterResult.KILLED;
            }
        } else if (game.getOpponentDisabled()) {
            BooleanContainer specialShipDisabled = new BooleanContainer(false);

            game.getQuestSystem().fireEvent(ON_ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, specialShipDisabled);

            if (specialShipDisabled.getValue()) {
                result = EncounterResult.NORMAL;
            } else if (game.getOpponent().getType() == ShipType.DRAGONFLY || game.getOpponent().getType() == ShipType.SCARAB) {

                switch (game.getOpponent().getType()) {
                    case DRAGONFLY:
                        encounterDefeatDragonfly();
                        break;
                    case SCARAB:
                        encounterDefeatScarab();
                        break;
                    /*case SCORPION:
                        str2 = Strings.EncounterPrincessRescued;
                        encounterDefeatScorpion();
                        break;*/
                }

                GuiFacade.alert(AlertType.EncounterDisabledOpponent, getEncounterShipText(), "");

                commander.setReputationScore(
                        commander.getReputationScore() + (game.getOpponent().getType().castToInt() / 2 + 1));
                result = EncounterResult.NORMAL;
            } else {
                encounterUpdateEncounterType(prevCmdrHull, prevOppHull);
                setEncounterOppFleeing(false);
            }
        } else if (game.getOpponent().getHull() <= 0) {
            encounterWon();

            result = EncounterResult.NORMAL;
        } else {
            boolean escaped = false;

            // Determine whether someone gets away.
            if (getEncounterCmdrFleeing()
                    && (Game.getDifficulty() == Difficulty.BEGINNER || (Functions.getRandom(7) + commander
                    .getShip().getPilot() / 3) * 2 >= Functions.getRandom(game.getOpponent().getPilot())
                    * (2 + Game.getDifficultyId()))) {
                GuiFacade.alert((getEncounterCmdrHit() ? AlertType.EncounterEscapedHit : AlertType.EncounterEscaped));
                escaped = true;
            } else if (getEncounterOppFleeing()
                    && Functions.getRandom(commander.getShip().getPilot()) * 4 <= Functions.getRandom(7 + game.getOpponent()
                    .getPilot() / 3) * 2) {
                GuiFacade.alert(AlertType.EncounterOpponentEscaped);
                escaped = true;
            }

            if (escaped) {
                result = EncounterResult.NORMAL;
            } else {
                // Determine whether the opponent's actions must be changed
                EncounterType prevEncounter = getEncounterType();

                encounterUpdateEncounterType(prevCmdrHull, prevOppHull);

                // Update the opponent fleeing flag.
                switch (getEncounterType()) {
                    case PIRATE_FLEE:
                    case PIRATE_SURRENDER:
                    case POLICE_FLEE:
                    case TRADER_FLEE:
                    case TRADER_SURRENDER:
                        setEncounterOppFleeing(true);
                        break;
                    default:
                        setEncounterOppFleeing(false);
                }

                if (game.getOptions().isContinuousAttack()
                        && (getEncounterCmdrFleeing() || !getEncounterOppFleeing() || game.getOptions()
                        .isContinuousAttackFleeing()
                        && (getEncounterType() == prevEncounter || getEncounterType() != EncounterType.PIRATE_SURRENDER
                        && getEncounterType() != EncounterType.TRADER_SURRENDER))) {
                    if (getEncounterCmdrFleeing()) {
                        setEncounterContinueFleeing(true);
                    } else {
                        setEncounterContinueAttacking(true);
                    }
                }
            }
        }

        return result;
    }

    private boolean isEncounterExecuteAttack(Ship attacker, Ship defender, boolean fleeing) {
        boolean hit = false;

        // On beginner level, if you flee, you will escape unharmed.
        // Otherwise, Fighterskill attacker is pitted against pilotskill
        // defender; if defender
        // is fleeing the attacker has a free shot, but the chance to hit is smaller
        // JAF - if the opponent is disabled and attacker has targeting system, they WILL be hit.
        if (!(Game.getDifficulty() == Difficulty.BEGINNER && defender.isCommandersShip() && fleeing)
                && (attacker.isCommandersShip() && game.getOpponentDisabled()
                && attacker.hasGadget(GadgetType.TARGETING_SYSTEM) || Functions.getRandom(attacker.getFighter()
                + defender.getSize().castToInt()) >= (fleeing ? 2 : 1)
                * Functions.getRandom(5 + defender.getPilot() / 2))) {
            // If the defender is disabled, it only takes one shot to destroy it
            // completely.
            if (attacker.isCommandersShip() && game.getOpponentDisabled()) {
                defender.setHull(0);
            } else {
                int attackerLasers = attacker.getWeaponStrength(WeaponType.PULSE_LASER, WeaponType.MORGANS_LASER);
                int attackerDisruptors = attacker.getWeaponStrength(WeaponType.PHOTON_DISRUPTOR,
                        WeaponType.QUANTUM_DISRUPTOR);

                if (defender.getType() == ShipType.SCARAB) {
                    attackerLasers -= attacker.getWeaponStrength(WeaponType.BEAM_LASER, WeaponType.MILITARY_LASER);
                    attackerDisruptors -= attacker.getWeaponStrength(WeaponType.PHOTON_DISRUPTOR,
                            WeaponType.PHOTON_DISRUPTOR);
                }

                int attackerWeapons = attackerLasers + attackerDisruptors;

                int disrupt = 0;

                // Attempt to disable the opponent if they're not already disabled, their shields are down,
                // we have disabling weapons, and the option is checked.
                if (defender.isDisableable() && defender.getShieldCharge() == 0 && !game.getOpponentDisabled()
                        && game.getOptions().isDisableOpponents() && attackerDisruptors > 0) {
                    disrupt = Functions.getRandom(attackerDisruptors * (100 + 2 * attacker.getFighter()) / 100);
                } else {
                    int damage = (attackerWeapons == 0)
                            ? 0 : Functions.getRandom(attackerWeapons * (100 + 2 * attacker.getFighter()) / 100);

                    if (damage > 0) {
                        hit = true;

                        // Reactor on board -- damage is boosted!
                        if (defender.isReactorOnBoard()) {
                            damage *= (int) (1 + (Game.getDifficultyId() + 1)
                                    * (Game.getDifficultyId() < Difficulty.NORMAL.castToInt() ? 0.25 : 0.33));
                        }

                        // First, shields are depleted
                        for (int i = 0; i < defender.getShields().length && defender.getShields()[i] != null && damage > 0; i++) {
                            int applied = Math.min(defender.getShields()[i].getCharge(), damage);
                            defender.getShields()[i].setCharge(defender.getShields()[i].getCharge() - applied);
                            damage -= applied;
                        }

                        // If there still is damage after the shields have been depleted,
                        // this is subtracted from the hull, modified by the engineering skill
                        // of the defender.
                        // JAF - If the player only has disabling weapons, no damage will be done to the hull.
                        if (damage > 0) {
                            damage = Math.max(1, damage - Functions.getRandom(defender.getEngineer()));

                            disrupt = damage * attackerDisruptors / attackerWeapons;

                            // Only that damage coming from Lasers will deplete the hull.
                            damage -= disrupt;

                            // At least 2 shots on Normal level are needed to destroy the hull
                            // (3 on Easy, 4 on Beginner, 1 on Hard or
                            // Impossible). For opponents,
                            // it is always 2.
                            damage = Math.min(damage, defender.getHullStrength()
                                    / (defender.isCommandersShip() ? Math.max(1, spacetrader.game.enums.Difficulty.IMPOSSIBLE
                                    .castToInt() - Game.getDifficultyId()) : 2));

                            // If the hull is hardened, damage is halved.
                            if (game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_DONE) {
                                damage /= 2;
                            }

                            defender.setHull(Math.max(0, defender.getHull() - damage));
                        }
                    }
                }

                // Did the opponent get disabled? (Disruptors are 3 times more
                // effective against the ship's systems than they are against the shields).
                if (defender.getHull() > 0 && defender.isDisableable()
                        && Functions.getRandom(100) < disrupt * Consts.DisruptorSystemsMultiplier * 100
                        / defender.getHull()) {
                    game.setOpponentDisabled(true);
                }

                game.getQuestSystem().fireEvent(ON_ENCOUNTER_EXECUTE_ATTACK_KEEP_SPECIAL_SHIP, defender);

                // Make sure the Scorpion doesn't get destroyed.
                /*if (defender.getType() == ShipType.SCORPION && defender.getHull() == 0) {
                    defender.setHull(1);
                    setOpponentDisabled(true);
                }*/
            }
        }

        return hit;
    }

    public void encounterMeet() {
        AlertType initialAlert = AlertType.Alert;
        int skill = 0;
        EquipmentType equipType = EquipmentType.GADGET;
        Object equipSubType = null;

        switch (getEncounterType()) {
            case CAPTAIN_AHAB:
                // Trade a reflective shield for skill points in piloting?
                initialAlert = AlertType.MeetCaptainAhab;
                equipType = EquipmentType.SHIELD;
                equipSubType = ShieldType.REFLECTIVE;
                skill = SkillType.PILOT.castToInt();
                break;

            case CAPTAIN_CONRAD:
                // Trade a military laser for skill points in engineering?
                initialAlert = AlertType.MeetCaptainConrad;
                equipType = EquipmentType.WEAPON;
                equipSubType = WeaponType.MILITARY_LASER;
                skill = SkillType.ENGINEER.castToInt();
                break;

            case CAPTAIN_HUIE:
                // Trade a military laser for skill points in trading?
                initialAlert = AlertType.MeetCaptainHuie;
                equipType = EquipmentType.WEAPON;
                equipSubType = WeaponType.MILITARY_LASER;
                skill = SkillType.TRADER.castToInt();
                break;
        }

        if (GuiFacade.alert(initialAlert) == DialogResult.YES) {
            // Remove the equipment we're trading.
            commander.getShip().removeEquipment(equipType, equipSubType);

            // Add points to the appropriate skill - two points if
            // beginner-normal, one otherwise.
            commander.getSkills()[skill] = Math.min(Consts.MaxSkill, commander.getSkills()[skill]
                    + (Game.getDifficultyId() <= Difficulty.NORMAL.castToInt() ? 2 : 1));

            GuiFacade.alert(AlertType.SpecialTrainingCompleted);
        }
    }

    public void encounterPlunder() {
        GuiFacade.performPlundering();

        if (getEncounterType().castToInt() >= EncounterType.TRADER_ATTACK.castToInt()) {
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScorePlunderTrader);

            if (game.getOpponentDisabled()) {
                commander.setKillsTrader(commander.getKillsTrader() + 1);
            }
        } else if (game.getOpponentDisabled()) {
            if (commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious) {
                GuiFacade.alert(AlertType.EncounterPiratesBounty, Strings.EncounterPiratesDisabled,
                        " " + Strings.EncounterPiratesLocation, Functions.plural(game.getOpponent().getBounty(), Strings.MoneyUnit));

                commander.setCash(commander.getCash() + game.getOpponent().getBounty());
            }

            commander.setKillsPirate(commander.getKillsPirate() + 1);
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
        } else
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScorePlunderPirate);

        commander.setReputationScore(commander.getReputationScore() + (game.getOpponent().getType().castToInt() / 2 + 1));
    }

    private void encounterScoop() {
        // Chance 50% to pick something up on Normal level, 33% on Hard level, 25% on
        // Impossible level, and 100% on Easy or Beginner.
        if ((Game.getDifficultyId() < Difficulty.NORMAL.castToInt()
                || Functions.getRandom(Game.getDifficultyId()) == 0) && game.getOpponent().getFilledCargoBays() > 0) {
            // Changed this to actually pick a good that was in the opponent's cargo hold - JAF.
            int index = Functions.getRandom(game.getOpponent().getFilledCargoBays());
            int tradeItem = -1;
            for (int sum = 0; sum <= index; sum += game.getOpponent().getCargo()[++tradeItem]) {
            }

            if (GuiFacade.alert(AlertType.EncounterScoop, Consts.TradeItems[tradeItem].getName()) == DialogResult.YES) {
                boolean jettisoned = false;

                if (commander.getShip().getFreeCargoBays() == 0
                        && GuiFacade.alert(AlertType.EncounterScoopNoRoom) == DialogResult.YES) {
                    GuiFacade.performJettison();
                    jettisoned = true;
                }

                if (commander.getShip().getFreeCargoBays() > 0) {
                    commander.getShip().getCargo()[tradeItem]++;
                } else if (jettisoned) {
                    GuiFacade.alert(AlertType.EncounterScoopNoScoop);
                }
            }
        }
    }

    public void encounterTrade() {
        boolean buy = (getEncounterType() == EncounterType.TRADER_BUY);
        int item = (buy ? commander.getShip() : game.getOpponent()).getRandomTradeableItem();
        String alertStr = (buy ? Strings.CargoSelling : Strings.CargoBuying);

        int cash = commander.getCash();

        if (getEncounterType() == EncounterType.TRADER_BUY) {
            game.cargoSellTrader(item);
        } else {
            // EncounterType.TRADER_SELL
            game.cargoBuyTrader(item);
        }

        if (commander.getCash() != cash) {
            GuiFacade.alert(AlertType.EncounterTradeCompleted, alertStr, Consts.TradeItems[item].getName());
        }
    }

    private void encounterUpdateEncounterType(int prevCmdrHull, int prevOppHull) {
        int chance = Functions.getRandom(100);

        if (game.getOpponent().getHull() < prevOppHull || game.getOpponentDisabled()) {
            switch (getEncounterType()) {
                case FAMOUS_CAPTAIN_ATTACK:
                    if (game.getOpponentDisabled()) {
                        setEncounterType(EncounterType.FAMOUS_CAPT_DISABLED);
                    }
                    break;
                case PIRATE_ATTACK:
                case PIRATE_FLEE:
                case PIRATE_SURRENDER:
                    if (game.getOpponentDisabled()) {
                        setEncounterType(EncounterType.PIRATE_DISABLED);
                    } else if (game.getOpponent().getHull() < (prevOppHull * 2) / 3) {
                        if (commander.getShip().getHull() < (prevCmdrHull * 2) / 3) {
                            if (chance < 60) {
                                setEncounterType(EncounterType.PIRATE_FLEE);
                            }
                        } else {
                            if (chance < 10 && game.getOpponent().getType() != ShipType.MANTIS) {
                                setEncounterType(EncounterType.PIRATE_SURRENDER);
                            } else {
                                setEncounterType(EncounterType.PIRATE_FLEE);
                            }
                        }
                    }
                    break;
                case POLICE_ATTACK:
                case POLICE_FLEE:
                    if (game.getOpponentDisabled()) {
                        setEncounterType(EncounterType.POLICE_DISABLED);
                    } else if (game.getOpponent().getHull() < prevOppHull / 2
                            && (commander.getShip().getHull() >= prevCmdrHull / 2 || chance < 40)) {
                        setEncounterType(EncounterType.POLICE_FLEE);
                    }
                    break;
                case TRADER_ATTACK:
                case TRADER_FLEE:
                case TRADER_SURRENDER:
                    if (game.getOpponentDisabled()) {
                        setEncounterType(EncounterType.TRADER_DISABLED);
                    } else if (game.getOpponent().getHull() < (prevOppHull * 2) / 3) {
                        if (chance < 60) {
                            setEncounterType(EncounterType.TRADER_SURRENDER);
                        } else {
                            setEncounterType(EncounterType.TRADER_FLEE);
                        }
                    }
                    // If you get damaged a lot, the trader tends to keep shooting;
                    // if you get damaged a little, the trader may keep shooting;
                    // if you get damaged very little or not at all, the trader will flee.
                    else if (game.getOpponent().getHull() < (prevOppHull * 9) / 10
                            && (commander.getShip().getHull() < (prevCmdrHull * 2) / 3 && chance < 20
                            || commander.getShip().getHull() < (prevCmdrHull * 9) / 10 && chance < 60 || commander
                            .getShip().getHull() >= (prevCmdrHull * 9) / 10)) {
                        setEncounterType(EncounterType.TRADER_FLEE);
                    }
            }
        }
    }

    public boolean isEncounterVerifyAttack() {
        boolean attack = true;

        BooleanContainer cantAttackScorpion = new BooleanContainer(false);
        game.getQuestSystem().fireEvent(ON_ENCOUNTER_VERIFY_ATTACK, cantAttackScorpion);

        if (cantAttackScorpion.getValue()) {
            attack = false;
        } else if (commander.getShip().getWeaponStrength() == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoWeapons);
            attack = false;
        } else if (!game.getOpponent().isDisableable()
                && commander.getShip().getWeaponStrength(WeaponType.PULSE_LASER, WeaponType.MORGANS_LASER) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoLasers);
            attack = false;
        } else {
            switch (getEncounterType()) {
                case DRAGONFLY_IGNORE:
                case PIRATE_IGNORE:
                case SCARAB_IGNORE:
                case QUEST_IGNORE:
                case SPACE_MONSTER_IGNORE:
                    setEncounterType(getEncounterType().getPreviousEncounter());
                    break;

                case POLICE_INSPECT:
                    if (!commander.getShip().isDetectableIllegalCargoOrPassengers()
                            && GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) != DialogResult.YES) {
                        attack = false;
                    }

                    // Fall through...
                    if (attack) {
                    }// goto case POLICE_IGNORE;
                    else {
                        break;
                    }

                case MARIE_CELESTE_POLICE:
                case POLICE_FLEE:
                case POLICE_IGNORE:
                case POLICE_SURRENDER:
                    if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                            || GuiFacade.alert(AlertType.EncounterAttackPolice) == DialogResult.YES) {
                        if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                            commander.setPoliceRecordScore(Consts.PoliceRecordScoreCriminal);
                        }

                        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackPolice);

                        if (getEncounterType() != EncounterType.POLICE_FLEE) {
                            setEncounterType(EncounterType.POLICE_ATTACK);
                        }
                    } else {
                        attack = false;
                    }
                    break;

                case TRADER_BUY:
                case TRADER_IGNORE:
                case TRADER_SELL:
                    if (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreClean) {
                        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackTrader);
                    } else if (GuiFacade.alert(AlertType.EncounterAttackTrader) == DialogResult.YES) {
                        commander.setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
                    } else {
                        attack = false;
                    }
                    if (!attack) {
                        break;
                    }

                case TRADER_ATTACK:
                case TRADER_SURRENDER:
                    if (Functions.getRandom(Consts.ReputationScoreElite) <= commander.getReputationScore() * 10
                            / (game.getOpponent().getType().castToInt() + 1)
                            || game.getOpponent().getWeaponStrength() == 0) {
                        setEncounterType(EncounterType.TRADER_FLEE);
                    } else {
                        setEncounterType(EncounterType.TRADER_ATTACK);
                    }
                    break;

                case CAPTAIN_AHAB:
                case CAPTAIN_CONRAD:
                case CAPTAIN_HUIE:
                    if (GuiFacade.alert(AlertType.EncounterAttackCaptain) == DialogResult.YES) {
                        if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreVillain) {
                            commander.setPoliceRecordScore(Consts.PoliceRecordScoreVillain);
                        }

                        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackTrader);

                        switch (getEncounterType()) {
                            case CAPTAIN_AHAB:
                                Game.getNews().addEvent(NewsEvent.CaptAhabAttacked);
                                break;
                            case CAPTAIN_CONRAD:
                                Game.getNews().addEvent(NewsEvent.CaptConradAttacked);
                                break;
                            case CAPTAIN_HUIE:
                                Game.getNews().addEvent(NewsEvent.CaptHuieAttacked);
                                break;
                        }

                        setEncounterType(EncounterType.FAMOUS_CAPTAIN_ATTACK);
                    } else {
                        attack = false;
                    }
                    break;
            }

            // Make sure the fleeing flag isn't set if we're attacking.
            if (attack) {
                setEncounterCmdrFleeing(false);
            }
        }

        return attack;
    }

    public boolean isEncounterVerifyBoard() {
        boolean board = false;

        if (GuiFacade.alert(AlertType.EncounterMarieCeleste) == DialogResult.YES) {
            board = true;

            int narcs = commander.getShip().getCargo()[TradeItemType.NARCOTICS.castToInt()];

            GuiFacade.performPlundering();

            if (commander.getShip().getCargo()[TradeItemType.NARCOTICS.castToInt()] > narcs) {
                game.setJustLootedMarie(true);
            }
        }

        return board;
    }

    public boolean isEncounterVerifyBribe() {
        boolean bribed = false;

        if (getEncounterType() == EncounterType.MARIE_CELESTE_POLICE) {
            GuiFacade.alert(AlertType.EncounterMarieCelesteNoBribe);
        } else if (game.getWarpSystem().getPoliticalSystem().getBribeLevel() <= 0) {
            GuiFacade.alert(AlertType.EncounterPoliceBribeCant);
        } else if (commander.getShip().isDetectableIllegalCargoOrPassengers()
                || GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) == DialogResult.YES) {
            // Bribe depends on how easy it is to bribe the police and commander's current worth
            int diffMod = 10 + 5 * (Difficulty.IMPOSSIBLE.castToInt() - Game.getDifficultyId());
            int passMod = commander.getShip().isIllegalSpecialCargo()
                    ? (Game.getDifficultyId() <= Difficulty.NORMAL.castToInt() ? 2 : 3)
                    : 1;

            int bribe = Math.max(100, Math.min(10000, (int) Math.ceil((double) commander.getWorth()
                    / game.getWarpSystem().getPoliticalSystem().getBribeLevel() / diffMod / 100) * 100 * passMod));

            if (GuiFacade.alert(AlertType.EncounterPoliceBribe, Functions.plural(bribe, Strings.MoneyUnit)) == DialogResult.YES) {
                if (commander.getCash() >= bribe) {
                    commander.setCash(commander.getCash() - bribe);
                    bribed = true;
                } else {
                    GuiFacade.alert(AlertType.EncounterPoliceBribeLowCash);
                }
            }
        }

        return bribed;
    }

    public boolean isEncounterVerifyFlee() {
        setEncounterCmdrFleeing(false);

        if (getEncounterType() != EncounterType.POLICE_INSPECT
                || commander.getShip().isDetectableIllegalCargoOrPassengers()
                || GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) == DialogResult.YES) {
            setEncounterCmdrFleeing(true);

            if (getEncounterType() == EncounterType.MARIE_CELESTE_POLICE
                    && GuiFacade.alert(AlertType.EncounterPostMarieFlee) == DialogResult.NO) {
                setEncounterCmdrFleeing(false);
            } else if (getEncounterType() == EncounterType.POLICE_INSPECT
                    || getEncounterType() == EncounterType.MARIE_CELESTE_POLICE) {
                int scoreMod = (getEncounterType() == EncounterType.POLICE_INSPECT) ? Consts.ScoreFleePolice
                        : Consts.ScoreAttackPolice;
                int scoreMin = (getEncounterType() == EncounterType.POLICE_INSPECT) ? Consts.PoliceRecordScoreDubious
                        - (Game.getDifficultyId() < Difficulty.NORMAL.castToInt() ? 0 : 1)
                        : Consts.PoliceRecordScoreCriminal;

                setEncounterType(EncounterType.POLICE_ATTACK);
                commander.setPoliceRecordScore(Math.min(commander.getPoliceRecordScore() + scoreMod, scoreMin));
            }
        }

        return getEncounterCmdrFleeing();
    }

    public boolean isEncounterVerifySubmit() {
        boolean submit = false;

        if (commander.getShip().isDetectableIllegalCargoOrPassengers()) {
            String str1 = commander.getShip().getIllegalSpecialCargoDescription("", true, true);
            String str2 = commander.getShip().isIllegalSpecialCargo() ? Strings.EncounterPoliceSubmitArrested : "";

            if (GuiFacade.alert(AlertType.EncounterPoliceSubmit, str1, str2) == DialogResult.YES) {
                submit = true;

                // If you carry illegal goods, they are impounded and you are fined
                if (commander.getShip().isDetectableIllegalCargo()) {
                    commander.getShip().removeIllegalGoods();

                    int fine = (int) Math.max(100, Math.min(10000,
                            Math.ceil((double) commander.getWorth()
                                    / ((Difficulty.IMPOSSIBLE.castToInt()
                                    - Game.getDifficultyId() + 2) * 10) / 50) * 50));
                    int cashPayment = Math.min(commander.getCash(), fine);
                    commander.setDebt(commander.getDebt() + (fine - cashPayment));
                    commander.setCash(commander.getCash() - cashPayment);

                    GuiFacade.alert(AlertType.EncounterPoliceFine, Functions.plural(fine, Strings.MoneyUnit));

                    commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreTrafficking);
                }
            }
        } else {
            submit = true;

            // If you aren't carrying illegal cargo or passengers, the police
            // will increase your lawfulness record
            GuiFacade.alert(AlertType.EncounterPoliceNothingFound);
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() - Consts.ScoreTrafficking);
        }

        return submit;
    }

    public EncounterResult getEncounterVerifySurrender() {
        EncounterResult result = EncounterResult.CONTINUE;

        BooleanContainer container = new BooleanContainer(false);

        game.getQuestSystem().fireEvent(ON_ENCOUNTER_VERIFY_SURRENDER_NO_HIDDEN, container);

        if (container.getValue()) {
            // If princess on board and no hidden cargo bays - continue fight to die.
        } else if (game.getOpponent().getType() == ShipType.MANTIS) {
            if (commander.getShip().isArtifactOnBoard()) {
                if (GuiFacade.alert(AlertType.EncounterAliensSurrender) == DialogResult.YES) {
                    GuiFacade.alert(AlertType.ArtifactRelinquished);
                    game.setQuestStatusArtifact(SpecialEvent.STATUS_ARTIFACT_NOT_STARTED);

                    result = EncounterResult.NORMAL;
                }
            } else {
                GuiFacade.alert(AlertType.EncounterSurrenderRefused);
            }
        } else if (getEncounterType() == EncounterType.POLICE_ATTACK || getEncounterType() == EncounterType.POLICE_SURRENDER) {
            if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScorePsychopath) {
                GuiFacade.alert(AlertType.EncounterSurrenderRefused);
            } else if (GuiFacade.alert(AlertType.EncounterPoliceSurrender, (new String[]{
                    commander.getShip().getIllegalSpecialCargoDescription(Strings.EncounterPoliceSurrenderCargo, true,
                            false), commander.getShip().getIllegalSpecialCargoActions()})) == DialogResult.YES) {
                result = EncounterResult.ARRESTED;
            }
        /*} else if (commander.getShip().isPrincessOnBoard() && !commander.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            GuiFacade.alert(AlertType.EncounterPiratesSurrenderPrincess);*/
        } else {
            game.setRaided(true);

            if (commander.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
                ArrayList<String> precious = new ArrayList<>();
                /*if (commander.getShip().isPrincessOnBoard()) {
                    precious.add(Strings.EncounterHidePrincess);
                }*/
                game.getQuestSystem().fireEvent(ON_ENCOUNTER_VERIFY_SURRENDER_HIDDEN, precious);
                if (commander.getShip().isSculptureOnBoard()) {
                    precious.add(Strings.EncounterHideSculpture);
                }

                GuiFacade.alert(AlertType.PreciousHidden, Functions.stringVars(Strings.ListStrings[precious.size()],
                        precious.toArray(new String[0])));
            } else if (commander.getShip().isSculptureOnBoard()) {
                game.setQuestStatusSculpture(SpecialEvent.STATUS_SCULPTURE_NOT_STARTED);
                GuiFacade.alert(AlertType.EncounterPiratesTakeSculpture);
            }

            ArrayList<Integer> cargoToSteal = commander.getShip().getStealableCargo();
            if (cargoToSteal.size() == 0) {
                int blackmail = Math.min(25000, Math.max(500, commander.getWorth() / 20));
                int cashPayment = Math.min(commander.getCash(), blackmail);
                commander.setDebt(commander.getDebt() + (blackmail - cashPayment));
                commander.setCash(commander.getCash() - cashPayment);
                GuiFacade.alert(AlertType.EncounterPiratesFindNoCargo, Functions
                        .plural(blackmail, Strings.MoneyUnit));
            } else {
                GuiFacade.alert(AlertType.EncounterLooting);

                // Pirates steal as much as they have room for, which could be everything - JAF.
                // Take most high-priced items - JAF.
                while (game.getOpponent().getFreeCargoBays() > 0 && cargoToSteal.size() > 0) {
                    int item = cargoToSteal.get(0);

                    commander.getPriceCargo()[item] -= commander.getPriceCargo()[item]
                            / commander.getShip().getCargo()[item];
                    commander.getShip().getCargo()[item]--;
                    game.getOpponent().getCargo()[item]++;

                    cargoToSteal.remove(0);
                }
            }

            if (commander.getShip().isWildOnBoard()) {
                if (game.getOpponent().getCrewQuarters() > 1) {
                    // Wild hops onto Pirate Ship
                    game.setQuestStatusWild(SpecialEvent.STATUS_WILD_NOT_STARTED);
                    GuiFacade.alert(AlertType.WildGoesPirates);
                } else {
                    GuiFacade.alert(AlertType.WildChatsPirates);
                }
            }

            // pirates puzzled by reactor
            if (commander.getShip().isReactorOnBoard()) {
                GuiFacade.alert(AlertType.EncounterPiratesExamineReactor);
            }

            result = EncounterResult.NORMAL;
        }

        return result;
    }

    public EncounterResult getEncounterVerifyYield() {
        EncounterResult result = EncounterResult.CONTINUE;

        if (commander.getShip().isIllegalSpecialCargo()) {
            if (GuiFacade.alert(AlertType.EncounterPoliceSurrender, (new String[]{
                    commander.getShip().getIllegalSpecialCargoDescription(Strings.EncounterPoliceSurrenderCargo, true,
                            true), commander.getShip().getIllegalSpecialCargoActions()})) == DialogResult.YES) {
                result = EncounterResult.ARRESTED;
            }
        } else {
            String str1 = commander.getShip().getIllegalSpecialCargoDescription("", false, true);

            if (GuiFacade.alert(AlertType.EncounterPoliceSubmit, str1, "") == DialogResult.YES) {
                // Police Record becomes dubious, if it wasn't already.
                if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreDubious) {
                    commander.setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
                }

                commander.getShip().removeIllegalGoods();

                result = EncounterResult.NORMAL;
            }
        }

        return result;
    }

    private void encounterWon() {
        if (getEncounterType().castToInt() >= EncounterType.PIRATE_ATTACK.castToInt()
                && getEncounterType().castToInt() <= EncounterType.PIRATE_DISABLED.castToInt()
                && game.getOpponent().getType() != ShipType.MANTIS
                && commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious) {
            GuiFacade.alert(AlertType.EncounterPiratesBounty, Strings.EncounterPiratesDestroyed, "", Functions
                    .plural(game.getOpponent().getBounty(), Strings.MoneyUnit));
        } else {
            GuiFacade.alert(AlertType.EncounterYouWin);
        }

        switch (getEncounterType()) {
            case FAMOUS_CAPTAIN_ATTACK:
                commander.setKillsTrader(commander.getKillsTrader() + 1);
                if (commander.getReputationScore() < Consts.ReputationScoreDangerous) {
                    commander.setReputationScore(Consts.ReputationScoreDangerous);
                } else {
                    commander.setReputationScore(commander.getReputationScore() + Consts.ScoreKillCaptain);
                }

                // bump news flag from attacked to ship destroyed
                Game.getNews().replaceLastAttackedEventWithDestroyedEvent();
                break;
            case DRAGONFLY_ATTACK:
                encounterDefeatDragonfly();
                break;
            case PIRATE_ATTACK:
            case PIRATE_FLEE:
            case PIRATE_SURRENDER:
                commander.setKillsPirate(commander.getKillsPirate() + 1);
                if (game.getOpponent().getType() != ShipType.MANTIS) {
                    if (commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious) {
                        commander.setCash(commander.getCash() + game.getOpponent().getBounty());
                    }
                    commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
                    encounterScoop();
                }
                break;
            case POLICE_ATTACK:
            case POLICE_FLEE:
                commander.setKillsPolice(commander.getKillsPolice() + 1);
                commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPolice);
                break;
            case SCARAB_ATTACK:
                encounterDefeatScarab();
                break;
            case SPACE_MONSTER_ATTACK:
                commander.setKillsPirate(commander.getKillsPirate() + 1);
                commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
                game.setQuestStatusSpaceMonster(SpecialEvent.STATUS_SPACE_MONSTER_DESTROYED);
                break;
            case TRADER_ATTACK:
            case TRADER_FLEE:
            case TRADER_SURRENDER:
                commander.setKillsTrader(commander.getKillsTrader() + 1);
                commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillTrader);
                encounterScoop();
                break;
        }

        commander.setReputationScore(commander.getReputationScore() + (game.getOpponent().getType().castToInt() / 2 + 1));
    }


    public void resetVeryRareEncounters() {
        getVeryRareEncounters().clear();
        getVeryRareEncounters().add(VeryRareEncounter.MARIE_CELESTE);
        getVeryRareEncounters().add(VeryRareEncounter.CAPTAIN_AHAB);
        getVeryRareEncounters().add(VeryRareEncounter.CAPTAIN_CONRAD);
        getVeryRareEncounters().add(VeryRareEncounter.CAPTAIN_HUIE);
        getVeryRareEncounters().add(VeryRareEncounter.BOTTLE_OLD);
        getVeryRareEncounters().add(VeryRareEncounter.BOTTLE_GOOD);
    }




    public String getEncounterAction() {
        String action;

        if (game.getOpponentDisabled()) {
            action = Functions.stringVars(Strings.EncounterActionOppDisabled, getEncounterShipText());
        } else if (getEncounterOppFleeing()) {
            if (getEncounterType() == EncounterType.PIRATE_SURRENDER
                    || getEncounterType() == EncounterType.TRADER_SURRENDER) {
                action = Functions.stringVars(Strings.EncounterActionOppSurrender, getEncounterShipText());
            } else {
                action = Functions.stringVars(Strings.EncounterActionOppFleeing, getEncounterShipText());
            }
        } else {
            action = Functions.stringVars(Strings.EncounterActionOppAttacks, getEncounterShipText());
        }

        return action;
    }

    public String getEncounterActionInitial() {
        String text = "";

        // Set up the fleeing variable initially.
        setEncounterOppFleeing(false);

        switch (getEncounterType()) {
            case BOTTLE_GOOD:
            case BOTTLE_OLD:
                text = Strings.EncounterTextBottle;
                break;
            case CAPTAIN_AHAB:
            case CAPTAIN_CONRAD:
            case CAPTAIN_HUIE:
                text = Strings.EncounterTextFamousCaptain;
                break;
            case DRAGONFLY_ATTACK:
            case PIRATE_ATTACK:
            case POLICE_ATTACK:
            case SCARAB_ATTACK:
            case QUEST_ATTACK:
            case SPACE_MONSTER_ATTACK:
                text = Strings.EncounterTextOpponentAttack;
                break;
            case DRAGONFLY_IGNORE:
            case PIRATE_IGNORE:
            case POLICE_IGNORE:
            case SCARAB_IGNORE:
            case QUEST_IGNORE:
            case SPACE_MONSTER_IGNORE:
            case TRADER_IGNORE:
                text = commander.getShip().isCloaked() ? Strings.EncounterTextOpponentNoNotice
                        : Strings.EncounterTextOpponentIgnore;
                break;
            case MARIE_CELESTE:
                text = Strings.EncounterTextMarieCeleste;
                break;
            case MARIE_CELESTE_POLICE:
                text = Strings.EncounterTextPolicePostMarie;
                break;
            case PIRATE_FLEE:
            case POLICE_FLEE:
            case TRADER_FLEE:
                text = Strings.EncounterTextOpponentFlee;
                setEncounterOppFleeing(true);
                break;
            case POLICE_INSPECT:
                text = Strings.EncounterTextPoliceInspection;
                break;
            case POLICE_SURRENDER:
                text = Strings.EncounterTextPoliceSurrender;
                break;
            case TRADER_BUY:
            case TRADER_SELL:
                text = Strings.EncounterTextTrader;
                break;
            case FAMOUS_CAPTAIN_ATTACK:
            case FAMOUS_CAPT_DISABLED:
            case POLICE_DISABLED:
            case PIRATE_DISABLED:
            case PIRATE_SURRENDER:
            case TRADER_ATTACK:
            case TRADER_DISABLED:
            case TRADER_SURRENDER:
                // These should never be the initial encounter type.
                break;
        }

        return text;
    }

    public int getEncounterImageIndex() {
        int encounterImage = -1;

        switch (getEncounterType()) {
            case BOTTLE_GOOD:
            case BOTTLE_OLD:
            case CAPTAIN_AHAB:
            case CAPTAIN_CONRAD:
            case CAPTAIN_HUIE:
            case MARIE_CELESTE:
                encounterImage = Consts.EncounterImgSpecial;
                break;
            case DRAGONFLY_ATTACK:
            case DRAGONFLY_IGNORE:
            case SCARAB_ATTACK:
            case SCARAB_IGNORE:
            case QUEST_ATTACK:
            case QUEST_IGNORE:
                encounterImage = Consts.EncounterImgPirate;
                break;
            case MARIE_CELESTE_POLICE:
            case POLICE_ATTACK:
            case POLICE_FLEE:
            case POLICE_IGNORE:
            case POLICE_INSPECT:
            case POLICE_SURRENDER:
                encounterImage = Consts.EncounterImgPolice;
                break;
            case PIRATE_ATTACK:
            case PIRATE_FLEE:
            case PIRATE_IGNORE:
                if (game.getOpponent().getType() == ShipType.MANTIS) {
                    encounterImage = Consts.EncounterImgAlien;
                } else {
                    encounterImage = Consts.EncounterImgPirate;
                }
                break;
            case SPACE_MONSTER_ATTACK:
            case SPACE_MONSTER_IGNORE:
                encounterImage = Consts.EncounterImgAlien;
                break;
            case TRADER_BUY:
            case TRADER_FLEE:
            case TRADER_IGNORE:
            case TRADER_SELL:
                encounterImage = Consts.EncounterImgTrader;
                break;
            case FAMOUS_CAPTAIN_ATTACK:
            case FAMOUS_CAPT_DISABLED:
            case POLICE_DISABLED:
            case PIRATE_DISABLED:
            case PIRATE_SURRENDER:
            case TRADER_ATTACK:
            case TRADER_DISABLED:
            case TRADER_SURRENDER:
                // These should never be the initial encounter type.
                break;
        }

        return encounterImage;
    }

    public String getEncounterShipText() {

        String shipText = game.getOpponent().getName();

        switch (getEncounterType()) {
            case FAMOUS_CAPTAIN_ATTACK:
            case FAMOUS_CAPT_DISABLED:
                shipText = Strings.EncounterShipCaptain;
                break;
            case PIRATE_ATTACK:
            case PIRATE_DISABLED:
            case PIRATE_FLEE:
            case PIRATE_SURRENDER:
                shipText = (game.getOpponent().getType() == ShipType.MANTIS)
                        ? Strings.EncounterShipMantis
                        : Strings.EncounterShipPirate;
                break;
            case POLICE_ATTACK:
            case POLICE_DISABLED:
            case POLICE_FLEE:
                shipText = Strings.EncounterShipPolice;
                break;
            case TRADER_ATTACK:
            case TRADER_DISABLED:
            case TRADER_FLEE:
            case TRADER_SURRENDER:
                shipText = Strings.EncounterShipTrader;
                break;
        }

        return shipText;
    }

    public String getEncounterText() {
        String cmdrStatus;
        String oppStatus;

        if (getEncounterCmdrFleeing()) {
            cmdrStatus = Functions.stringVars(Strings.EncounterActionCmdrChased, getEncounterShipText());
        } else if (getEncounterOppHit()) {
            cmdrStatus = Functions.stringVars(Strings.EncounterActionOppHit, getEncounterShipText());
        } else {
            cmdrStatus = Functions.stringVars(Strings.EncounterActionOppMissed, getEncounterShipText());
        }

        if (getEncounterOppFleeingPrev()) {
            oppStatus = Functions.stringVars(Strings.EncounterActionOppChased, getEncounterShipText());
        } else if (getEncounterCmdrHit()) {
            oppStatus = Functions.stringVars(Strings.EncounterActionCmdrHit, getEncounterShipText());
        } else {
            oppStatus = Functions.stringVars(Strings.EncounterActionCmdrMissed, getEncounterShipText());
        }

        return cmdrStatus + Strings.newline + oppStatus;
    }

    public String getEncounterTextInitial() {
        StringContainer encounterPretext = new StringContainer("");

        switch (getEncounterType()) {
            case BOTTLE_GOOD:
            case BOTTLE_OLD:
                encounterPretext.setValue(Strings.EncounterPretextBottle);
                break;
            case DRAGONFLY_ATTACK:
            case DRAGONFLY_IGNORE:
            case SCARAB_ATTACK:
            case SCARAB_IGNORE:
                encounterPretext.setValue(Strings.EncounterPretextStolen);
                break;
            case CAPTAIN_AHAB:
                encounterPretext.setValue(Strings.EncounterPretextCaptainAhab);
                break;
            case CAPTAIN_CONRAD:
                encounterPretext.setValue(Strings.EncounterPretextCaptainConrad);
                break;
            case CAPTAIN_HUIE:
                encounterPretext.setValue(Strings.EncounterPretextCaptainHuie);
                break;
            case MARIE_CELESTE:
                encounterPretext.setValue(Strings.EncounterPretextMarie);
                break;
            case MARIE_CELESTE_POLICE:
            case POLICE_ATTACK:
            case POLICE_FLEE:
            case POLICE_IGNORE:
            case POLICE_INSPECT:
            case POLICE_SURRENDER:
                encounterPretext.setValue(Strings.EncounterPretextPolice);
                break;
            case PIRATE_ATTACK:
            case PIRATE_FLEE:
            case PIRATE_IGNORE:
                if (game.getOpponent().getType() == ShipType.MANTIS) {
                    encounterPretext.setValue(Strings.EncounterPretextAlien);
                } else {
                    encounterPretext.setValue(Strings.EncounterPretextPirate);
                }
                break;
            /*case QUEST_ATTACK:
            case QUEST_IGNORE:
                encounterPretext = Strings.EncounterPretextScorpion);
                break;*/
            case SPACE_MONSTER_ATTACK:
            case SPACE_MONSTER_IGNORE:
                encounterPretext.setValue(Strings.EncounterPretextSpaceMonster);
                break;
            case TRADER_BUY:
            case TRADER_FLEE:
            case TRADER_IGNORE:
            case TRADER_SELL:
                encounterPretext.setValue(Strings.EncounterPretextTrader);
                break;
            case FAMOUS_CAPTAIN_ATTACK:
            case FAMOUS_CAPT_DISABLED:
            case POLICE_DISABLED:
            case PIRATE_DISABLED:
            case PIRATE_SURRENDER:
            case TRADER_ATTACK:
            case TRADER_DISABLED:
            case TRADER_SURRENDER:
                // These should never be the initial encounter type.
                break;
            default:
                game.getQuestSystem().fireEvent(EventName.ON_GET_ENCOUNTER_TEXT_INITIAL, encounterPretext);

        }

        String internal = Functions.stringVars(encounterPretext.getValue(), game.getOpponent().getName().toLowerCase());

        return Functions.stringVars(Strings.EncounterText, new String[]{
                Functions.plural(game.getClicks(), Strings.DistanceSubunit), game.getWarpSystem().getName(), internal});
    }

    public ArrayList<VeryRareEncounter> getVeryRareEncounters() {
        return veryRareEncounters;
    }

    private int getChanceOfVeryRareEncounter() {
        return chanceOfVeryRareEncounter;
    }

    public void setChanceOfVeryRareEncounter(int chanceOfVeryRareEncounter) {
        this.chanceOfVeryRareEncounter = chanceOfVeryRareEncounter;
    }

    public EncounterType getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(EncounterType encounterType) {
        this.encounterType = encounterType;
    }

    private boolean getEncounterOppHit() {
        return encounterOppHit;
    }

    private void setEncounterOppHit(boolean encounterOppHit) {
        this.encounterOppHit = encounterOppHit;
    }

    private boolean getEncounterOppFleeingPrev() {
        return encounterOppFleeingPrev;
    }

    private void setEncounterOppFleeingPrev(boolean encounterOppFleeingPrev) {
        this.encounterOppFleeingPrev = encounterOppFleeingPrev;
    }

    private boolean getEncounterOppFleeing() {
        return encounterOppFleeing;
    }

    private void setEncounterOppFleeing(boolean encounterOppFleeing) {
        this.encounterOppFleeing = encounterOppFleeing;
    }

    public boolean setEncounterContinueAttacking(boolean encounterContinueAttacking) {
        this.encounterContinueAttacking = encounterContinueAttacking;
        return encounterContinueAttacking;
    }

    public boolean getEncounterContinueAttacking() {
        return encounterContinueAttacking;
    }

    private boolean getEncounterCmdrHit() {
        return encounterCmdrHit;
    }

    private void setEncounterCmdrHit(boolean encounterCmdrHit) {
        this.encounterCmdrHit = encounterCmdrHit;
    }

    private boolean getEncounterCmdrFleeing() {
        return encounterCmdrFleeing;
    }

    private void setEncounterCmdrFleeing(boolean encounterCmdrFleeing) {
        this.encounterCmdrFleeing = encounterCmdrFleeing;
    }

    public boolean getEncounterContinueFleeing() {
        return encounterContinueFleeing;
    }

    public void setEncounterContinueFleeing(boolean encounterContinueFleeing) {
        this.encounterContinueFleeing = encounterContinueFleeing;
    }

    public boolean isEasyEncounters() {
        return easyEncounters;
    }

    public void setEasyEncounters(boolean easyEncounters) {
        this.easyEncounters = easyEncounters;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Encounter)) return false;
        Encounter encounter = (Encounter) object;
        return easyEncounters == encounter.easyEncounters &&
                chanceOfVeryRareEncounter == encounter.chanceOfVeryRareEncounter &&
                encounterContinueFleeing == encounter.encounterContinueFleeing &&
                encounterContinueAttacking == encounter.encounterContinueAttacking &&
                encounterCmdrFleeing == encounter.encounterCmdrFleeing &&
                encounterCmdrHit == encounter.encounterCmdrHit &&
                encounterOppFleeingPrev == encounter.encounterOppFleeingPrev &&
                encounterOppFleeing == encounter.encounterOppFleeing &&
                encounterOppHit == encounter.encounterOppHit &&
                encounterType == encounter.encounterType &&
                Objects.equals(encounters, encounter.encounters) &&
                Objects.equals(veryRareEncounters, encounter.veryRareEncounters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(easyEncounters, encounterType, encounters, chanceOfVeryRareEncounter, veryRareEncounters, encounterContinueFleeing, encounterContinueAttacking, encounterCmdrFleeing, encounterCmdrHit, encounterOppFleeingPrev, encounterOppFleeing, encounterOppHit);
    }
}
