package spacetrader.game.cheat;

import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.VeryRareEncounter;
import spacetrader.game.quest.enums.EventName;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

// TODO removes refs to gui...
@CheatCode
public class GameCheats implements Serializable {

    private boolean cheatMode = false;

    public boolean isCheatMode() {
        return cheatMode;
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode = cheatMode;
    }

    /**
     * @return false if did anything, true if didn't recognized word (i.e., need to search for a system).
     */
    public boolean isConsiderCheat(String[] wordsArray, GameController controller) {
        Game game = Game.getCurrentGame();
        Ship ship = Game.getShip();

        CheatWords words = CheatWords.parseWords(wordsArray);

        if (cheatMode) {
            if (words.isCheat()) {
                return true;
            }

            switch (SomeStringsForCheatSwitch.find(words.getFirst())) {
                case Bazaar:
                    game.setChanceOfTradeInOrbit(Math.max(0, Math.min(1000, words.getNum1())));
                    break;
                case Cover:
                    if (words.getNum1() >= 0 && words.getNum1() < ship.getShields().length && words.getNum2() >= 0 && words.getNum2() < Consts.Shields.length)
                        ship.getShields()[words.getNum1()] = (Shield) Consts.Shields[words.getNum2()].clone();
                    break;
                case DeLorean:
                    Game.getCommander().setDays(Math.max(0, words.getNum1()));
                    break;
                case Energize:
                    game.setCanSuperWarp(!game.getCanSuperWarp());
                    break;
                case Events:
                    if (words.getSecond().equals("Reset")) {
                        game.getEncounter().resetVeryRareEncounters();
                    } else {
                        StringBuilder textBuilder = new StringBuilder();
                        for (VeryRareEncounter veryRareEncounter : game.getEncounter().getVeryRareEncounters()) {
                            textBuilder.append(Strings.VeryRareEncounters[veryRareEncounter.castToInt()]).append(Strings.newline);
                        }
                        String text = textBuilder.toString().trim();

                        GuiFacade.alert(AlertType.Alert, Strings.CheatsVeryRareEncountersRemaining, text);
                    }
                    break;
                case Fame:
                    Game.getCommander().setReputationScore(Math.max(0, words.getNum1()));
                    break;
                case Go:
                    game.setSelectedSystemByName(words.getSecond());
                    if (game.getSelectedSystem() != null && game.getSelectedSystem().getName().toLowerCase().equals(words.getSecond().toLowerCase())) {
                        controller.autoSaveOnDeparture();

                        game.warpDirect();

                        controller.autoSaveOnArrival();
                    }
                    break;
                case Ice: {
                    switch (SomeStringsForCheatSwitch.find(words.getSecond())) {
                        case Pirate:
                            Game.getCommander().setKillsPirate(Math.max(0, words.getNum2()));
                            break;
                        case Police:
                            Game.getCommander().setKillsPolice(Math.max(0, words.getNum2()));
                            break;
                        case Trader:
                            Game.getCommander().setKillsTrader(Math.max(0, words.getNum2()));
                            break;
                    }
                }
                break;
                case Indemnity:
                    Game.getCommander().setNoClaim(Math.max(0, words.getNum1()));
                    break;
                case IOU:
                    Game.getCommander().setDebt(Math.max(0, words.getNum1()));
                    break;
                case Iron:
                    if (words.getNum1() >= 0 && words.getNum1() < ship.getWeapons().length && words.getNum2() >= 0 && words.getNum2() < Consts.Weapons.length) {
                        ship.getWeapons()[words.getNum1()] = (Weapon) Consts.Weapons[words.getNum2()].clone();
                    }
                    break;
                case Juice:
                    ship.setFuel(Math.max(0, Math.min(ship.getFuelTanks(), words.getNum1())));
                    break;
                case Knack:
                    if (words.getNum1() >= 0 && words.getNum1() < game.getMercenaries().size()) {
                        String[] skills = words.getThird().split(",");
                        for (int i = 0; i < game.getMercenaries().get(words.getNum1()).getSkills().length && i < skills.length; i++) {
                            if (Functions.isInt(skills[i])) {
                                game.getMercenaries().get(words.getNum1()).getSkills()[i] = Math.max(1, Math.min(Consts.MaxSkill, Integer
                                        .parseInt(skills[i])));
                            }
                        }
                    }
                    break;
                case L_Engle:
                    game.setFabricRipProbability(Math.max(0, Math.min(Consts.FabricRipInitialProbability, words.getNum1())));
                    break;
                case LifeBoat:
                    ship.setEscapePod(!ship.getEscapePod());
                    break;
                case MonsterCom:
                    GuiFacade.performMonsterCom();
                    break;
                case PlanB:
                    game.setAutoSave(true);
                    break;
                case Posse:
                    CrewMember mercenary = game.getMercenaries().get(words.getNum2());
                    if (words.getNum1() > 0 && words.getNum1() < ship.getCrew().length && mercenary != null && mercenary.isMercenary()) {
                        int skill = ship.getTrader();
                        ship.getCrew()[words.getNum1()] = game.getMercenaries().get(words.getNum2());
                        if (ship.getTrader() != skill) {
                            game.recalculateBuyPrices(Game.getCommander().getCurrentSystem());
                        }
                    }
                    break;
                case RapSheet:
                    Game.getCommander().setPoliceRecordScore(words.getNum1());
                    break;
                case Rarity:
                    game.getEncounter().setChanceOfVeryRareEncounter(Math.max(0, Math.min(1000, words.getNum1())));
                    break;
                case Scratch:
                    Game.getCommander().setCash(Math.max(0, words.getNum1()));
                    break;
                case Skin:
                    ship.setHull(Math.max(0, Math.min(ship.getHullStrength(), words.getNum1())));
                    break;
                case Status: {
                    game.getQuestSystem().fireEvent(EventName.IS_CONSIDER_STATUS_CHEAT, words);

                    switch (SomeStringsForCheatSwitch.find(words.getSecond())) {
                        case Artifact:
                            game.setQuestStatusArtifact(Math.max(0, words.getNum2()));
                            break;
                        case Dragonfly:
                            game.setQuestStatusDragonfly(Math.max(0, words.getNum2()));
                            break;
                        case Experiment:
                            game.setQuestStatusExperiment(Math.max(0, words.getNum2()));
                            break;
                        case Gemulon:
                            game.setQuestStatusGemulon(Math.max(0, words.getNum2()));
                            break;
                        case Japori:
                            game.setQuestStatusJapori(Math.max(0, words.getNum2()));
                            break;
                        case Moon:
                            game.setQuestStatusMoon(Math.max(0, words.getNum2()));
                            break;

                        default:

                            String text = Strings.CheatsArtifact + ": " + game.getQuestStatusArtifact() + Strings.newline
                                    + Strings.CheatsDragonfly + ": " + game.getQuestStatusDragonfly() + Strings.newline
                                    + Strings.CheatsExperiment + ": " + game.getQuestStatusExperiment() + Strings.newline
                                    + Strings.CheatsGemulon + ": " + game.getQuestStatusGemulon() + Strings.newline
                                    + Strings.CheatsJapori + ": " + game.getQuestStatusJapori() + Strings.newline
                                    + Strings.CheatsMoon + ": " + game.getQuestStatusMoon() + Strings.newline;

                            Map<String, Integer> strings = new HashMap<>();
                            game.getQuestSystem().fireEvent(EventName.IS_CONSIDER_STATUS_DEFAULT_CHEAT, strings);

                            text = text + strings.entrySet().stream()
                                    .map(e -> e.getKey() + ": " + e.getValue()).collect(joining(Strings.newline));

                            GuiFacade.alert(AlertType.Alert, Strings.CheatsStatusOfQuests, text);
                            break;
                    }
                }
                break;
                case Swag:
                    if (words.getNum1() >= 0 && words.getNum1() < ship.getCargo().length) {
                        ship.getCargo()[words.getNum1()] = Math.max(0, Math.min(ship.getFreeCargoBays() + ship.getCargo()[words.getNum1()], words.getNum2()));
                    }
                    break;
                case Test:
                    GuiFacade.performTestForm();
                    break;
                case Tool:
                    if (words.getNum1() >= 0 && words.getNum1() < ship.getGadgets().length && words.getNum2() >= 0 && words.getNum2() < Consts.Gadgets.length) {
                        ship.getGadgets()[words.getNum1()] = (Gadget) Consts.Gadgets[words.getNum2()].clone();
                    }
                    break;
                case Yellow:
                    game.getEncounter().setEasyEncounters(true);
                    break;
                default:
                    game.getQuestSystem().fireEvent(EventName.IS_CONSIDER_CHEAT, words);
                    return !words.isCheat();
            }
        } else {
            switch (SomeStringsForCheatSwitch.find(words.getFirst())) {
                case Cheetah:
                    GuiFacade.alert(AlertType.Cheater);
                    cheatMode = true;
                    break;
                default:
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameCheats)) return false;
        GameCheats that = (GameCheats) o;
        return cheatMode == that.cheatMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cheatMode);
    }
}
