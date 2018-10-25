package spacetrader.game.cheat;

import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.CrewMemberId;
import spacetrader.game.enums.VeryRareEncounter;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.CheatCode;
import spacetrader.util.Util;

// TODO removes refs to gui...
@CheatCode
public class GameCheats {

    private final Game game;
    private boolean cheatMode = false;

    public GameCheats(Game game) {
        this.game = game;
    }

    public boolean isCheatMode() {
        return cheatMode;
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode = cheatMode;
    }

    /**
     * @return false if did anything, true if didn't recognized word (i.e., need to search for a system).
     */
    public boolean isConsiderCheat(String[] words, GameController controller) {
        Ship ship = game.getCommander().getShip();

        String first = words.length > 0 ? words[0] : "";
        String second = words.length > 1 ? words[1] : "";
        String third = words.length > 2 ? words[2] : "";
        int num1 = Functions.isInt(second) ? Integer.parseInt(second) : 0;
        int num2 = Functions.isInt(third) ? Integer.parseInt(third) : 0;

        if (cheatMode) {
            switch (SomeStringsForCheatSwitch.find(first)) {
                case Bazaar:
                    game.setChanceOfTradeInOrbit(Math.max(0, Math.min(1000, num1)));
                    break;
                case Cover:
                    if (num1 >= 0 && num1 < ship.getShields().length && num2 >= 0 && num2 < Consts.Shields.length)
                        ship.getShields()[num1] = (Shield) Consts.Shields[num2].clone();
                    break;
                case DeLorean:
                    game.getCommander().setDays(Math.max(0, num1));
                    break;
                case Diamond:
                    ship.setHullUpgraded(!ship.getHullUpgraded());
                    break;
                case Energize:
                    game.setCanSuperWarp(!game.getCanSuperWarp());
                    break;
                case Events:
                    if (second.equals("Reset")) {
                        game.resetVeryRareEncounters();
                    } else {
                        StringBuilder textBuilder = new StringBuilder();
                        for (VeryRareEncounter veryRareEncounter : game.getVeryRareEncounters()) {
                            textBuilder.append(Strings.VeryRareEncounters[veryRareEncounter.castToInt()]).append(Strings.newline);
                        }
                        String text = textBuilder.toString().trim();

                        GuiFacade.alert(AlertType.Alert, Strings.CheatsVeryRareEncountersRemaining, text);
                    }
                    break;
                case Fame:
                    game.getCommander().setReputationScore(Math.max(0, num1));
                    break;
                case Go:
                    game.setSelectedSystemByName(second);
                    if (game.getSelectedSystem().getName().toLowerCase().equals(second.toLowerCase())) {
                        controller.autoSaveOnDeparture();

                        game.warpDirect();

                        controller.autoSaveOnArrival();
                    }
                    break;
                case Ice: {
                    switch (SomeStringsForCheatSwitch.find(second)) {
                        case Pirate:
                            game.getCommander().setKillsPirate(Math.max(0, num2));
                            break;
                        case Police:
                            game.getCommander().setKillsPolice(Math.max(0, num2));
                            break;
                        case Trader:
                            game.getCommander().setKillsTrader(Math.max(0, num2));
                            break;
                    }
                }
                break;
                case Indemnity:
                    game.getCommander().setNoClaim(Math.max(0, num1));
                    break;
                case IOU:
                    game.getCommander().setDebt(Math.max(0, num1));
                    break;
                case Iron:
                    if (num1 >= 0 && num1 < ship.getWeapons().length && num2 >= 0 && num2 < Consts.Weapons.length) {
                        ship.getWeapons()[num1] = (Weapon) Consts.Weapons[num2].clone();
                    }
                    break;
                case Juice:
                    ship.setFuel(Math.max(0, Math.min(ship.getFuelTanks(), num1)));
                    break;
                case Knack:
                    if (num1 >= 0 && num1 < game.getMercenaries().length) {
                        String[] skills = third.split(",");
                        for (int i = 0; i < game.getMercenaries()[num1].getSkills().length && i < skills.length; i++) {
                            if (Functions.isInt(skills[i])) {
                                game.getMercenaries()[num1].getSkills()[i] = Math.max(1, Math.min(Consts.MaxSkill, Integer
                                        .parseInt(skills[i])));
                            }
                        }
                    }
                    break;
                case L_Engle:
                    game.setFabricRipProbability(Math.max(0, Math.min(Consts.FabricRipInitialProbability, num1)));
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
                    if (num1 > 0 && num1 < ship.getCrew().length && num2 > 0 && num2 < game.getMercenaries().length
                            && !Util.arrayContains(Consts.SpecialCrewMemberIds, (CrewMemberId.fromInt(num2)))) {
                        int skill = ship.getTrader();
                        ship.getCrew()[num1] = game.getMercenaries()[num2];
                        if (ship.getTrader() != skill) {
                            game.recalculateBuyPrices(game.getCommander().getCurrentSystem());
                        }
                    }
                    break;
                case RapSheet:
                    game.getCommander().setPoliceRecordScore(num1);
                    break;
                case Rarity:
                    game.setChanceOfVeryRareEncounter(Math.max(0, Math.min(1000, num1)));
                    break;
                case Scratch:
                    game.getCommander().setCash(Math.max(0, num1));
                    break;
                case Skin:
                    ship.setHull(Math.max(0, Math.min(ship.getHullStrength(), num1)));
                    break;
                case Status: {
                    switch (SomeStringsForCheatSwitch.find(second)) {
                        case Artifact:
                            game.setQuestStatusArtifact(Math.max(0, num2));
                            break;
                        case Dragonfly:
                            game.setQuestStatusDragonfly(Math.max(0, num2));
                            break;
                        case Experiment:
                            game.setQuestStatusExperiment(Math.max(0, num2));
                            break;
                        case Gemulon:
                            game.setQuestStatusGemulon(Math.max(0, num2));
                            break;
                        case Japori:
                            game.setQuestStatusJapori(Math.max(0, num2));
                            break;
                        case Jarek:
                            game.setQuestStatusJarek(Math.max(0, num2));
                            break;
                        case Moon:
                            game.setQuestStatusMoon(Math.max(0, num2));
                            break;
                        case Reactor:
                            game.setQuestStatusReactor(Math.max(0, num2));
                            break;
                        case Princess:
                            game.setQuestStatusPrincess(Math.max(0, num2));
                            break;
                        case Scarab:
                            game.setQuestStatusScarab(Math.max(0, num2));
                            break;
                        case Sculpture:
                            game.setQuestStatusSculpture(Math.max(0, num2));
                            break;
                        case SpaceMonster:
                            game.setQuestStatusSpaceMonster(Math.max(0, num2));
                            break;
                        case Wild:
                            game.setQuestStatusWild(Math.max(0, num2));
                            break;
                        default:
                            String text = Strings.CheatsArtifact + ": " + game.getQuestStatusArtifact() + Strings.newline
                                    + Strings.CheatsDragonfly + ": " + game.getQuestStatusDragonfly() + Strings.newline
                                    + Strings.CheatsExperiment + ": " + game.getQuestStatusExperiment() + Strings.newline
                                    + Strings.CheatsGemulon + ": " + game.getQuestStatusGemulon() + Strings.newline
                                    + Strings.CheatsJapori + ": " + game.getQuestStatusJapori() + Strings.newline
                                    + Strings.CheatsJarek + ": " + game.getQuestStatusJarek() + Strings.newline
                                    + Strings.CheatsMoon + ": " + game.getQuestStatusMoon() + Strings.newline
                                    + Strings.CheatsPrincess + ": " + game.getQuestStatusPrincess() + Strings.newline
                                    + Strings.CheatsReactor + ": " + game.getQuestStatusReactor() + Strings.newline
                                    + Strings.CheatsScarab + ": " + game.getQuestStatusScarab() + Strings.newline
                                    + Strings.CheatsSculpture + ": " + game.getQuestStatusSculpture() + Strings.newline
                                    + Strings.CheatsSpaceMonster + ": " + game.getQuestStatusSpaceMonster() + Strings.newline
                                    + Strings.CheatsWild + ": " + game.getQuestStatusWild();

                            GuiFacade.alert(AlertType.Alert, Strings.CheatsStatusOfQuests, text);
                            break;
                    }
                }
                break;
                case Swag:
                    if (num1 >= 0 && num1 < ship.getCargo().length) {
                        ship.getCargo()[num1] = Math.max(0, Math.min(ship.getFreeCargoBays() + ship.getCargo()[num1], num2));
                    }
                    break;
                case Test:
                    GuiFacade.performTestForm();
                    break;
                case Tool:
                    if (num1 >= 0 && num1 < ship.getGadgets().length && num2 >= 0 && num2 < Consts.Gadgets.length) {
                        ship.getGadgets()[num1] = (Gadget) Consts.Gadgets[num2].clone();
                    }
                    break;
                case Varmints:
                    ship.setTribbles(Math.max(0, num1));
                    break;
                case Yellow:
                    game.setEasyEncounters(true);
                    break;
                default:
                    return true;
            }
        } else {
            switch (SomeStringsForCheatSwitch.find(first)) {
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
}
