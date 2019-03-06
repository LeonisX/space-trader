package spacetrader.game;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.*;
import spacetrader.game.quest.enums.EventName;
import spacetrader.game.quest.enums.QuestName;
import spacetrader.game.quest.quests.ArtifactQuest;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;

public class Encounter implements Serializable {

    static final long serialVersionUID = -535871984L;

    private Game game;
    private Commander commander;

    private boolean easyEncounters = false;

    //TODO need initialize??? test
    private Integer encounterType = EncounterType.values()[0].castToInt(); // Type of current encounter
    private List<Integer> encounters; // Encounters

    private int chanceOfVeryRareEncounter = 5; // Rare encounters not done yet.
    private List<Integer> veryRareEncounters = new ArrayList<>(); // Array of Very Options

    private Integer veryRareEncounterId; // current ID, used right after its generation

    private boolean rareEncountersFirst = false;

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
        this.commander = game.getCommander();

        initializeVeryRareEncounters();

        encounters = Arrays.stream(EncounterType.values()).map(EncounterType::castToInt).collect(Collectors.toList());
    }

    public boolean isDetermineEncounter() {
        // If there is a specific encounter that needs to happen, it will,
        // otherwise we'll generate a random encounter.
        return isDetermineNonRandomEncounter() || isDetermineRandomEncounter();
    }

    private boolean isDetermineNonRandomEncounter() {
        BooleanContainer showEncounter = new BooleanContainer(false);

        game.getQuestSystem().fireEvent(EventName.ENCOUNTER_DETERMINE_NON_RANDOM_ENCOUNTER, showEncounter);

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
        return encounterType == EncounterType.PIRATE_ATTACK.castToInt()
                || !(game.getOpponent().isCloaked() || game.getOptions().isAlwaysIgnorePirates());
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
                    .getRandom(12 - game.getDifficultyId()) < 1) || (commander
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
        return encounterType == EncounterType.POLICE_ATTACK.castToInt() || encounterType == EncounterType.POLICE_INSPECT.castToInt()
                || !(game.getOpponent().isCloaked() || game.getOptions().isAlwaysIgnorePolice());
    }

    private boolean isDetermineRandomEncounter() {
        if (rareEncountersFirst && getVeryRareEncounters().size() > 0 && isDetermineVeryRareEncounter()) {
            return true;
        }

        RandomEncounterContainer opponents = new RandomEncounterContainer();

        game.getQuestSystem().fireEvent(ON_DETERMINE_RANDOM_ENCOUNTER, opponents);

        ArtifactQuest artifactQuest = (ArtifactQuest) game.getQuestSystem().getQuest(QuestName.Artifact);

        // Check if it is time for an encounter
        int encounter = Functions.getRandom(44 - (2 * game.getDifficultyId()));
        int policeModifier = Math.max(1, 3 - PoliceRecord.getPoliceRecordFromScore().getType().castToInt());

        // encounters are half as likely if you're in a flea.
        if (commander.getShip().getType() == ShipType.FLEA) {
            encounter *= 2;
        }

        if (encounter < game.getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()) {
            // When you are already raided, other pirates have little to gain
            opponents.setPirate(!game.getRaided());
        } else if (encounter < game.getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()
                + game.getWarpSystem().getPoliticalSystem().getActivityPolice().castToInt() * policeModifier) {
            // policeModifier adapts itself to your criminal record: you'll
            // encounter more police if you are a hardened criminal.
            opponents.setPolice(true);
        } else if (encounter < game.getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()
                + game.getWarpSystem().getPoliticalSystem().getActivityPolice().castToInt() * policeModifier
                + game.getWarpSystem().getPoliticalSystem().getActivityTraders().castToInt()) {
            opponents.setTrader(true);
        } else if (artifactQuest.isArtifactOnBoard() && Functions.getRandom(20) <= 3) {
            opponents.setMantis(true);
        }

        if (opponents.isPolice()) {
            return isDeterminePoliceEncounter();
        } else if (opponents.isPirate() || opponents.isMantis()) {
            return isDeterminePirateEncounter(opponents.isMantis());
        } else if (opponents.isTrader()) {
            return isDetermineTraderEncounter();
        } else if (commander.getDays() > 10 && Functions.getRandom(1000) < getChanceOfVeryRareEncounter()
                && getVeryRareEncounters().size() > 0) {
            return isDetermineVeryRareEncounter();
        } else return false;
    }

    private boolean isDetermineTraderEncounter() {
        game.generateOpponent(OpponentType.TRADER);

        // If you are cloaked, they don't see you
        setEncounterType(EncounterType.TRADER_IGNORE);
        if (!commander.getShip().isCloaked()) {
            // If you're a criminal, traders tend to flee if you've got at least some reputation
            //TODO duplicated code?
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
                encounterType == EncounterType.TRADER_IGNORE.castToInt() || encounterType == EncounterType.TRADER_FLEE.castToInt()))
                && !((encounterType == EncounterType.TRADER_BUY.castToInt() || encounterType == EncounterType.TRADER_SELL.castToInt())
                && game.getOptions().isAlwaysIgnoreTradeInOrbit());
    }

    public boolean isDetermineVeryRareEncounter() {
        veryRareEncounterId = getVeryRareEncounters().get(Functions.getRandom(getVeryRareEncounters().size()));
        BooleanContainer happened = new BooleanContainer(false);

        game.getQuestSystem().fireEvent(ON_DETERMINE_VERY_RARE_ENCOUNTER, happened);

        return happened.getValue();
    }

    public void encounterBegin() {
        // Set up the encounter variables.
        setEncounterContinueFleeing(setEncounterContinueAttacking(game.setOpponentDisabled(false)));
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
        if (encounterType < 1000) {
            switch (EncounterType.fromInt(encounterType)) {
                case PIRATE_ATTACK:
                case POLICE_ATTACK:
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
        } else {
            game.getQuestSystem().fireEvent(ENCOUNTER_GET_EXECUTE_ACTION_FIRE_SHOTS);
        }

        // Determine whether someone gets destroyed
        if (commander.getShip().getHull() <= 0) {
            if (commander.getShip().getEscapePod()) {
                result = EncounterResult.ESCAPE_POD;
            } else {
                GuiFacade.alert((game.getOpponent().getHull() <= 0 ? AlertType.EncounterBothDestroyed
                        : AlertType.EncounterYouLose));

                game.getQuestSystem().fireEvent(ON_BEFORE_KILLED);

                result = EncounterResult.KILLED;
            }
        } else if (game.getOpponentDisabled()) {
            BooleanContainer specialShipDisabled = new BooleanContainer(false);

            game.getQuestSystem().fireEvent(ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, specialShipDisabled);

            if (specialShipDisabled.getValue()) {
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
                    && (game.getDifficulty() == Difficulty.BEGINNER || (Functions.getRandom(7) + commander
                    .getShip().getPilot() / 3) * 2 >= Functions.getRandom(game.getOpponent().getPilot())
                    * (2 + game.getDifficultyId()))) {
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
                int prevEncounter = encounterType;

                encounterUpdateEncounterType(prevCmdrHull, prevOppHull);

                // Update the opponent fleeing flag.
                setEncounterOppFleeing(false);
                if (encounterType < 1000) {
                    switch (EncounterType.fromInt(encounterType)) {
                        case PIRATE_FLEE:
                        case PIRATE_SURRENDER:
                        case POLICE_FLEE:
                        case TRADER_FLEE:
                        case TRADER_SURRENDER:
                            setEncounterOppFleeing(true);
                    }
                }

                if (game.getOptions().isContinuousAttack()
                        && (getEncounterCmdrFleeing() || !getEncounterOppFleeing() || game.getOptions().isContinuousAttackFleeing()
                        && (encounterType == prevEncounter || encounterType != EncounterType.PIRATE_SURRENDER.castToInt()
                        && encounterType != EncounterType.TRADER_SURRENDER.castToInt()))) {
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

    public boolean isEncounterExecuteAttack(Ship attacker, Ship defender, boolean fleeing) {
        boolean hit = false;

        // On beginner level, if you flee, you will escape unharmed.
        // Otherwise, Fighterskill attacker is pitted against pilotskill defender; if defender
        // is fleeing the attacker has a free shot, but the chance to hit is smaller
        // JAF - if the opponent is disabled and attacker has targeting system, they WILL be hit.
        if (!(game.getDifficulty() == Difficulty.BEGINNER && defender.isCommandersShip() && fleeing)
                && (attacker.isCommandersShip() && game.getOpponentDisabled()
                && attacker.hasGadget(GadgetType.TARGETING_SYSTEM) || Functions.getRandom(attacker.getFighter()
                + defender.getSize().castToInt()) >= (fleeing ? 2 : 1)
                * Functions.getRandom(5 + defender.getPilot() / 2))) {
            // If the defender is disabled, it only takes one shot to destroy it completely.
            if (attacker.isCommandersShip() && game.getOpponentDisabled()) {
                defender.setHull(0);
            } else {
                OpponentsContainer opponents = new OpponentsContainer(attacker, defender);
                opponents.setAttackerLasers(attacker.getWeaponStrength(WeaponType.PULSE_LASER, WeaponType.MORGANS_LASER));
                opponents.setAttackerDisruptors(attacker.getWeaponStrength(WeaponType.PHOTON_DISRUPTOR,
                        WeaponType.QUANTUM_DISRUPTOR));

                game.getQuestSystem().fireEvent(ENCOUNTER_IS_EXECUTE_ATTACK_GET_WEAPONS, opponents);

                int disrupt = 0;

                // Attempt to disable the opponent if they're not already disabled, their shields are down,
                // we have disabling weapons, and the option is checked.
                if (defender.isDisableable() && defender.getShieldCharge() == 0 && !game.getOpponentDisabled()
                        && game.getOptions().isDisableOpponents() && opponents.getAttackerDisruptors() > 0) {
                    disrupt = Functions.getRandom(opponents.getAttackerDisruptors() * (100 + 2 * attacker.getFighter()) / 100);
                } else {
                    IntContainer damage = new IntContainer((opponents.getAttackerWeapons() == 0)
                            ? 0 : Functions.getRandom(opponents.getAttackerWeapons() * (100 + 2 * attacker.getFighter()) / 100));

                    if (damage.getValue() > 0) {
                        hit = true;

                        game.getQuestSystem().fireEvent(ENCOUNTER_IS_EXECUTE_ATTACK_PRIMARY_DAMAGE, damage);

                        // First, shields are depleted
                        for (int i = 0; i < defender.getShields().length && defender.getShields()[i] != null && damage.getValue() > 0; i++) {
                            int applied = Math.min(defender.getShields()[i].getCharge(), damage.getValue());
                            defender.getShields()[i].setCharge(defender.getShields()[i].getCharge() - applied);
                            damage.subtract(applied);
                        }

                        // If there still is damage after the shields have been depleted, this is subtracted
                        // from the hull, modified by the engineering skill of the defender.
                        // JAF - If the player only has disabling weapons, no damage will be done to the hull.
                        if (damage.getValue() > 0) {
                            damage.setValue(Math.max(1, damage.getValue() - Functions.getRandom(defender.getEngineer())));

                            disrupt = damage.getValue() * opponents.getAttackerDisruptors() / opponents.getAttackerWeapons();

                            // Only that damage coming from Lasers will deplete the hull.
                            damage.subtract(disrupt);

                            // At least 2 shots on Normal level are needed to destroy the hull
                            // (3 on Easy, 4 on Beginner, 1 on Hard or Impossible). For opponents, it is always 2.
                            damage.setValue(Math.min(damage.getValue(), defender.getHullStrength()
                                    / (defender.isCommandersShip() ? Math.max(1, spacetrader.game.enums.Difficulty.IMPOSSIBLE
                                    .castToInt() - game.getDifficultyId()) : 2)));

                            game.getQuestSystem().fireEvent(ENCOUNTER_IS_EXECUTE_ATTACK_SECONDARY_DAMAGE, damage);

                            defender.setHull(Math.max(0, defender.getHull() - damage.getValue()));
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

                game.getQuestSystem().fireEvent(ENCOUNTER_EXECUTE_ATTACK_KEEP_SPECIAL_SHIP, defender);
            }
        }

        return hit;
    }

    public void encounterMeet() {
        game.getQuestSystem().fireEvent(ENCOUNTER_MEET);
    }

    public void encounterDrink() {
        game.getQuestSystem().fireEvent(ENCOUNTER_DRINK);
    }

    public void encounterPlunder() {
        GuiFacade.performPlundering();

        if (encounterType >= EncounterType.TRADER_ATTACK.castToInt()) {
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

    public void encounterTrade() {
        boolean buy = (encounterType == EncounterType.TRADER_BUY.castToInt());
        int item = (buy ? commander.getShip() : game.getOpponent()).getRandomTradeableItem();
        String alertStr = (buy ? Strings.CargoSelling : Strings.CargoBuying);

        int cash = commander.getCash();

        if (encounterType == EncounterType.TRADER_BUY.castToInt()) { //TODO if (buy) ???
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
            if (encounterType < 1000) {
                switch (EncounterType.fromInt(encounterType)) {
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
            } else {
                game.getQuestSystem().fireEvent(ENCOUNTER_UPDATE_ENCOUNTER_TYPE);
            }
        }
    }

    public boolean isEncounterVerifyAttack() {
        BooleanContainer attack = new BooleanContainer(true);

        if (commander.getShip().getWeaponStrength() == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoWeapons);
            attack.setValue(false);
        } else if (!game.getOpponent().isDisableable()
                && commander.getShip().getWeaponStrength(WeaponType.PULSE_LASER, WeaponType.MORGANS_LASER) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoLasers);
            attack.setValue(false);
        } else {
            if (encounterType < 1000) {
                switch (EncounterType.fromInt(encounterType)) {
                    case PIRATE_IGNORE:
                        setEncounterType(EncounterType.PIRATE_ATTACK);
                        break;

                    case POLICE_INSPECT:
                        if (!commander.getShip().isDetectableIllegalCargoOrPassengers()
                                && GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) != DialogResult.YES) {
                            attack.setValue(false);
                        }
                        if (!attack.getValue()) {
                            break;
                        }

                    case POLICE_FLEE:
                    case POLICE_IGNORE:
                    case POLICE_SURRENDER:
                        if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                                || GuiFacade.alert(AlertType.EncounterAttackPolice) == DialogResult.YES) {
                            if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                                commander.setPoliceRecordScore(Consts.PoliceRecordScoreCriminal);
                            }

                            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackPolice);

                            if (encounterType != EncounterType.POLICE_FLEE.castToInt()) {
                                setEncounterType(EncounterType.POLICE_ATTACK);
                            }
                        } else {
                            attack.setValue(false);
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
                            attack.setValue(false);
                        }
                        if (!attack.getValue()) {
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
                }
            } else {
                game.getQuestSystem().fireEvent(ENCOUNTER_VERIFY_ATTACK, attack);
            }

            // Make sure the fleeing flag isn't set if we're attacking.
            if (attack.getValue()) {
                setEncounterCmdrFleeing(false);
            }
        }

        return attack.getValue();
    }

    // Reaction on Board button. Maria Celeste only
    public boolean isEncounterVerifyBoard() {
        BooleanContainer board = new BooleanContainer(false);

        game.getQuestSystem().fireEvent(ENCOUNTER_VERIFY_BOARD, board);

        return board.getValue();
    }

    public boolean isEncounterVerifyBribe() {
        boolean bribed = false;

        BooleanContainer matched = new BooleanContainer(false);

        game.getQuestSystem().fireEvent(ENCOUNTER_VERIFY_BRIBE, matched);

        if (matched.getValue()) {
            return bribed;
        } else if (game.getWarpSystem().getPoliticalSystem().getBribeLevel() <= 0) {
            GuiFacade.alert(AlertType.EncounterPoliceBribeCant);
        } else if (commander.getShip().isDetectableIllegalCargoOrPassengers()
                || GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) == DialogResult.YES) {
            // Bribe depends on how easy it is to bribe the police and commander's current worth
            int diffMod = 10 + 5 * (Difficulty.IMPOSSIBLE.castToInt() - game.getDifficultyId());
            int passMod = commander.getShip().isIllegalSpecialCargo()
                    ? (game.getDifficultyId() <= Difficulty.NORMAL.castToInt() ? 2 : 3)
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


        //TODO test and may be simplify
        if (encounterType != EncounterType.POLICE_INSPECT.castToInt()
                || commander.getShip().isDetectableIllegalCargoOrPassengers()
                || GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) == DialogResult.YES) {
            setEncounterCmdrFleeing(true);

            BooleanContainer matched = new BooleanContainer(false);

            game.getQuestSystem().fireEvent(ENCOUNTER_VERIFY_FLEE, matched);

            if (!matched.getValue() && encounterType == EncounterType.POLICE_INSPECT.castToInt()) {
                int scoreMod = (encounterType == EncounterType.POLICE_INSPECT.castToInt()) ? Consts.ScoreFleePolice
                        : Consts.ScoreAttackPolice;
                int scoreMin = (encounterType == EncounterType.POLICE_INSPECT.castToInt()) ? Consts.PoliceRecordScoreDubious
                        - (game.getDifficultyId() < Difficulty.NORMAL.castToInt() ? 0 : 1)
                        : Consts.PoliceRecordScoreCriminal;

                setEncounterType(EncounterType.POLICE_ATTACK.castToInt());
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
                                    - game.getDifficultyId() + 2) * 10) / 50) * 50));
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
        SurrenderContainer surrenderContainer = new SurrenderContainer(EncounterResult.CONTINUE);

        game.getQuestSystem().fireEvent(ENCOUNTER_ON_VERIFY_SURRENDER, surrenderContainer);

        if (surrenderContainer.isMatch()) {
            return surrenderContainer.getResult();
        }

        if (encounterType == EncounterType.POLICE_ATTACK.castToInt() || encounterType == EncounterType.POLICE_SURRENDER.castToInt()) {
            if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScorePsychopath) {
                GuiFacade.alert(AlertType.EncounterSurrenderRefused);
            } else if (GuiFacade.alert(AlertType.EncounterPoliceSurrender, (new String[]{
                    commander.getShip().getIllegalSpecialCargoDescription(Strings.EncounterPoliceSurrenderCargo, true,
                            false), commander.getShip().getIllegalSpecialCargoActions()})) == DialogResult.YES) {
                surrenderContainer.setResult(EncounterResult.ARRESTED);
            }
        } else {
            game.setRaided(true);

            BooleanContainer allowRobbery = new BooleanContainer(true);
            game.getQuestSystem().fireEvent(ENCOUNTER_ON_SURRENDER_IF_RAIDED, allowRobbery);

            allowRobbery.setValue(allowRobbery.getValue() && game.getOpponent().getType() != ShipType.MANTIS);

            if (allowRobbery.getValue()) {

                List<String> precious = new java.util.ArrayList<>();

                game.getQuestSystem().fireEvent(ENCOUNTER_ON_ROBBERY, precious);

                if (!precious.isEmpty()) {
                    GuiFacade.alert(AlertType.PreciousHidden, Functions.stringVars(Strings.ListStrings[precious.size()],
                            precious.toArray(new String[0])));
                }

                ArrayList<Integer> cargoToSteal = commander.getShip().getStealableCargo();
                if (cargoToSteal.size() == 0) {
                    int blackmail = Math.min(25000, Math.max(500, commander.getWorth() / 20));
                    int cashPayment = Math.min(commander.getCash(), blackmail);
                    commander.setDebt(commander.getDebt() + (blackmail - cashPayment));
                    commander.setCash(commander.getCash() - cashPayment);
                    GuiFacade.alert(AlertType.EncounterPiratesFindNoCargo, Functions.plural(blackmail, Strings.MoneyUnit));
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
            }

            surrenderContainer.setResult(EncounterResult.NORMAL);
        }

        return surrenderContainer.getResult();
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

                game.getQuestSystem().fireEvent(ENCOUNTER_VERIFY_YIELD);

                result = EncounterResult.NORMAL;
            }
        }

        return result;
    }

    private void encounterWon() {
        if (encounterType >= EncounterType.PIRATE_ATTACK.castToInt()
                && encounterType <= EncounterType.PIRATE_DISABLED.castToInt()
                && game.getOpponent().getType() != ShipType.MANTIS
                && commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious) {
            GuiFacade.alert(AlertType.EncounterPiratesBounty, Strings.EncounterPiratesDestroyed, "", Functions
                    .plural(game.getOpponent().getBounty(), Strings.MoneyUnit));
        } else {
            GuiFacade.alert(AlertType.EncounterYouWin);
        }

        if (encounterType < 1000) {
            switch (EncounterType.fromInt(encounterType)) {
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
                case TRADER_ATTACK:
                case TRADER_FLEE:
                case TRADER_SURRENDER:
                    commander.setKillsTrader(commander.getKillsTrader() + 1);
                    commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillTrader);
                    encounterScoop();
                    break;
            }
        } else {
            game.getQuestSystem().fireEvent(ENCOUNTER_ON_ENCOUNTER_WON);
        }

        commander.setReputationScore(commander.getReputationScore() + (game.getOpponent().getType().castToInt() / 2 + 1));
    }

    private void encounterScoop() {
        // Chance 50% to pick something up on Normal level, 33% on Hard level, 25% on
        // Impossible level, and 100% on Easy or Beginner.
        if ((game.getDifficultyId() < Difficulty.NORMAL.castToInt()
                || Functions.getRandom(game.getDifficultyId()) == 0) && game.getOpponent().getFilledCargoBays() > 0) {
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

    public void initializeVeryRareEncounters() {
        veryRareEncounters = new ArrayList<>(game.getQuestSystem().getVeryRareEncounters());
    }

    public String getEncounterAction() {
        String action;

        if (game.getOpponentDisabled()) {
            action = Functions.stringVars(Strings.EncounterActionOppDisabled, getEncounterShipText());
        } else if (getEncounterOppFleeing()) {
            if (encounterType == EncounterType.PIRATE_SURRENDER.castToInt()
                    || encounterType == EncounterType.TRADER_SURRENDER.castToInt()) {
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
        StringContainer text = new StringContainer("");

        // Set up the fleeing variable initially.
        setEncounterOppFleeing(false);

        if (encounterType < 1000) {
            switch (EncounterType.fromInt(encounterType)) {
                case PIRATE_ATTACK:
                case POLICE_ATTACK:
                    text.setValue(Strings.EncounterTextOpponentAttack);
                    break;
                case PIRATE_IGNORE:
                case POLICE_IGNORE:
                case TRADER_IGNORE:
                    text.setValue(commander.getShip().isCloaked() ? Strings.EncounterTextOpponentNoNotice
                            : Strings.EncounterTextOpponentIgnore);
                    break;
                case PIRATE_FLEE:
                case POLICE_FLEE:
                case TRADER_FLEE:
                    text.setValue(Strings.EncounterTextOpponentFlee);
                    setEncounterOppFleeing(true);
                    break;
                case POLICE_INSPECT:
                    text.setValue(Strings.EncounterTextPoliceInspection);
                    break;
                case POLICE_SURRENDER:
                    text.setValue(Strings.EncounterTextPoliceSurrender);
                    break;
                case TRADER_BUY:
                case TRADER_SELL:
                    text.setValue(Strings.EncounterTextTrader);
                    break;
                case POLICE_DISABLED:
                case PIRATE_DISABLED:
                case PIRATE_SURRENDER:
                case TRADER_ATTACK:
                case TRADER_DISABLED:
                case TRADER_SURRENDER:
                    // These should never be the initial encounter type.
                    break;
            }
        } else {
            game.getQuestSystem().fireEvent(ENCOUNTER_GET_INTRODUCTORY_ACTION, text);
        }

        return text.getValue();
    }

    public int getEncounterImageIndex() {
        IntContainer encounterImage = new IntContainer(-1);

        if (encounterType < 1000) {
            switch (EncounterType.fromInt(encounterType)) {
                case POLICE_ATTACK:
                case POLICE_FLEE:
                case POLICE_IGNORE:
                case POLICE_INSPECT:
                case POLICE_SURRENDER:
                    encounterImage.setValue(Consts.EncounterImgPolice);
                    break;
                case PIRATE_ATTACK:
                case PIRATE_FLEE:
                case PIRATE_IGNORE:
                    if (game.getOpponent().getType() == ShipType.MANTIS) {
                        encounterImage.setValue(Consts.EncounterImgAlien);
                    } else {
                        encounterImage.setValue(Consts.EncounterImgPirate);
                    }
                    break;
                case TRADER_BUY:
                case TRADER_FLEE:
                case TRADER_IGNORE:
                case TRADER_SELL:
                    encounterImage.setValue(Consts.EncounterImgTrader);
                    break;
                case POLICE_DISABLED:
                case PIRATE_DISABLED:
                case PIRATE_SURRENDER:
                case TRADER_ATTACK:
                case TRADER_DISABLED:
                case TRADER_SURRENDER:
                    // These should never be the initial encounter type.
                    break;
            }
        } else {
            game.getQuestSystem().fireEvent(ENCOUNTER_GET_IMAGE_INDEX, encounterImage);
        }

        return encounterImage.getValue();
    }

    public String getEncounterShipText() {
        StringContainer shipText = new StringContainer(game.getOpponent().getName());

        if (encounterType < 1000) {
            switch (EncounterType.fromInt(encounterType)) {
                case PIRATE_ATTACK:
                case PIRATE_DISABLED:
                case PIRATE_FLEE:
                case PIRATE_SURRENDER:
                    shipText.setValue((game.getOpponent().getType() == ShipType.MANTIS)
                            ? Strings.EncounterShipMantis
                            : Strings.EncounterShipPirate);
                    break;
                case POLICE_ATTACK:
                case POLICE_DISABLED:
                case POLICE_FLEE:
                    shipText.setValue(Strings.EncounterShipPolice);
                    break;
                case TRADER_ATTACK:
                case TRADER_DISABLED:
                case TRADER_FLEE:
                case TRADER_SURRENDER:
                    shipText.setValue(Strings.EncounterShipTrader);
                    break;
                default:
            }
        } else {
            game.getQuestSystem().fireEvent(EventName.ENCOUNTER_GET_ENCOUNTER_SHIP_TEXT, shipText);
        }

        return shipText.getValue();
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

        if (encounterType < 1000) {
            switch (EncounterType.fromInt(encounterType)) {
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
                case TRADER_BUY:
                case TRADER_FLEE:
                case TRADER_IGNORE:
                case TRADER_SELL:
                    encounterPretext.setValue(Strings.EncounterPretextTrader);
                    break;
                case POLICE_DISABLED:
                case PIRATE_DISABLED:
                case PIRATE_SURRENDER:
                case TRADER_ATTACK:
                case TRADER_DISABLED:
                case TRADER_SURRENDER:
                    // These should never be the initial encounter type.
                    break;
            }
        } else {
            game.getQuestSystem().fireEvent(EventName.ENCOUNTER_GET_INTRODUCTORY_TEXT, encounterPretext);
        }

        String internal = Functions.stringVars(Functions.getPlural(encounterPretext.getValue(), 2), game.getOpponent().getName().toLowerCase());

        return Functions.stringVars(Strings.EncounterText, new String[]{
                Functions.plural(game.getClicks(), Strings.DistanceSubunit), game.getWarpSystem().getName(), internal});
    }

    public List<Integer> getVeryRareEncounters() {
        return veryRareEncounters;
    }

    private int getChanceOfVeryRareEncounter() {
        return chanceOfVeryRareEncounter;
    }

    public void setChanceOfVeryRareEncounter(int chanceOfVeryRareEncounter) {
        this.chanceOfVeryRareEncounter = chanceOfVeryRareEncounter;
    }

    public Integer getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(Integer encounterType) {
        this.encounterType = encounterType;
    }

    public void setEncounterType(EncounterType encounterType) {
        this.encounterType = encounterType.castToInt();
    }

    private boolean getEncounterOppHit() {
        return encounterOppHit;
    }

    public void setEncounterOppHit(boolean encounterOppHit) {
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

    public void setEncounterCmdrHit(boolean encounterCmdrHit) {
        this.encounterCmdrHit = encounterCmdrHit;
    }

    public boolean getEncounterCmdrFleeing() {
        return encounterCmdrFleeing;
    }

    public void setEncounterCmdrFleeing(boolean encounterCmdrFleeing) {
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

    public int getVeryRareEncounterId() {
        return veryRareEncounterId;
    }

    public boolean isRareEncountersFirst() {
        return rareEncountersFirst;
    }

    public void setRareEncountersFirst(boolean rareEncountersFirst) {
        this.rareEncountersFirst = rareEncountersFirst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encounter encounter = (Encounter) o;
        return easyEncounters == encounter.easyEncounters &&
                chanceOfVeryRareEncounter == encounter.chanceOfVeryRareEncounter &&
                rareEncountersFirst == encounter.rareEncountersFirst &&
                encounterContinueFleeing == encounter.encounterContinueFleeing &&
                encounterContinueAttacking == encounter.encounterContinueAttacking &&
                encounterCmdrFleeing == encounter.encounterCmdrFleeing &&
                encounterCmdrHit == encounter.encounterCmdrHit &&
                encounterOppFleeingPrev == encounter.encounterOppFleeingPrev &&
                encounterOppFleeing == encounter.encounterOppFleeing &&
                encounterOppHit == encounter.encounterOppHit &&
                Objects.equals(encounterType, encounter.encounterType) &&
                Objects.equals(encounters, encounter.encounters) &&
                Objects.equals(veryRareEncounters, encounter.veryRareEncounters) &&
                Objects.equals(veryRareEncounterId, encounter.veryRareEncounterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(easyEncounters, encounterType, encounters, chanceOfVeryRareEncounter, veryRareEncounters, veryRareEncounterId, rareEncountersFirst, encounterContinueFleeing, encounterContinueAttacking, encounterCmdrFleeing, encounterCmdrHit, encounterOppFleeingPrev, encounterOppFleeing, encounterOppHit);
    }
}
