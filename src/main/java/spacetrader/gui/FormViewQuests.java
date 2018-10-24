/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron
 * Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to
 * http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/

package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.game.*;
import spacetrader.game.enums.CrewMemberId;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.gui.debug.Launcher;
import spacetrader.stub.ArrayList;
import spacetrader.util.ReflectionUtils;

import java.util.List;

public class FormViewQuests extends SpaceTraderForm {

    Game game = Game.getCurrentGame();

    private Button closeButton = new Button();
    private LinkLabel questsLabelValue = new LinkLabel();

    public static void main(String[] args) {
        new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        Launcher.runForm(new FormViewQuests());
    }

    public FormViewQuests() {
        initializeComponent();
        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formViewQuests");
        setText("Quests");
        setAutoScaleBaseSize(5, 13);
        setClientSize(378, 325);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);

        suspendLayout();

        questsLabelValue.setLinkArea(new LinkArea(0, 0));
        questsLabelValue.setLocation(8, 8);
        questsLabelValue.setSize(368, 312);
        questsLabelValue.setTabIndex(44);
        /*questsLabelValue.setText("Kill the space monster at Acamar."
                + "\n\n"
                + "Get your lightning shield at Zalkon."
                + "\n\n"
                + "Deliver antidote to Japori."
                + "\n\n"
                + "Deliver the alien artifact to Professor Berger at some hi-tech system."
                + "\n\n"
                + "Bring ambassador Jarek to Devidia.  Jarek is wondering why the journey is taking so long, and is no longer of much help in negotiating trades."
                + "\n\n"
                + "Inform Gemulon about alien invasion within 8 days."
                + "\n\n"
                + "Stop Dr. Fehler's experiment at Daled within 8 days."
                + "\n\n"
                + "Deliver the unstable reactor to Nix before it consumes all its fuel."
                + "\n\n"
                + "Find and destroy the Scarab (which is hiding at the exit to a wormhole)."
                + "\n\n"
                + "Smuggle Jonathan Wild to Kravat.  Wild is getting impatient, and will no longer aid your crew along the way."
                + "\n\n" + "Get rid of those pesky tribbles." + "\n\n"
                + "Claim your moon at Utopia.");*/
        questsLabelValue.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                questsLabelValueClicked(e);
            }
        });

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        //closeButton.setText("X");

        controls.addAll(questsLabelValue, closeButton);

        ReflectionUtils.loadControlsDimensions(asSwingObject(), getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(asSwingObject(), getName(), GlobalAssets.getStrings());
    }

    private void updateAll() {
        List<String> quests = getQuestStrings();
        if (quests.isEmpty()) {
            questsLabelValue.setText(Strings.QuestNone);
        } else {
            questsLabelValue.setText(String.join(Strings.newline + Strings.newline, quests));

            for (int i = 0; i < Strings.SystemNames.length; i++) {
                String systemName = Strings.SystemNames[i];
                int start = 0;
                int index;

                //TODO simplify
                while ((index = questsLabelValue.getText().indexOf(systemName, start)) >= 0) {
                    questsLabelValue.getLinks().add(index, systemName.length(), systemName);
                    start = index + systemName.length();
                }
            }
        }
    }

    private List<String> getQuestStrings() {
        ArrayList<String> quests = new ArrayList<>(12);

        if (game.getQuestStatusGemulon() > SpecialEvent.STATUS_GEMULON_NOT_STARTED
                && game.getQuestStatusGemulon() < SpecialEvent.STATUS_GEMULON_DATE) {
            if (game.getQuestStatusGemulon() == SpecialEvent.STATUS_GEMULON_DATE - 1) {
                quests.add(Strings.QuestGemulonInformTomorrow);
            } else {
                quests.add(Functions.stringVars(Strings.QuestGemulonInformDays,
                        Functions.multiples(SpecialEvent.STATUS_GEMULON_DATE
                                - game.getQuestStatusGemulon(), Strings.TimeUnit)));
            }
        } else if (game.getQuestStatusGemulon() == SpecialEvent.STATUS_GEMULON_FUEL) {
            quests.add(Strings.QuestGemulonFuel);
        }

        if (game.getQuestStatusExperiment() > SpecialEvent.STATUS_EXPERIMENT_NOT_STARTED
                && game.getQuestStatusExperiment() < SpecialEvent.STATUS_EXPERIMENT_DATE) {
            if (game.getQuestStatusExperiment() == SpecialEvent.STATUS_EXPERIMENT_DATE - 1) {
                quests.add(Strings.QuestExperimentInformTomorrow);
            } else {
                quests.add(Functions.stringVars(
                        Strings.QuestExperimentInformDays, Functions.multiples(
                                SpecialEvent.STATUS_EXPERIMENT_DATE
                                        - game.getQuestStatusExperiment(), Strings.TimeUnit)));
            }
        }

        if (game.getCommander().getShip().isReactorOnBoard()) {
            if (game.getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_FUEL_OK) {
                quests.add(Strings.QuestReactor);
            } else {
                quests.add(Strings.QuestReactorFuel);
            }
        } else if (game.getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_DELIVERED) {
            quests.add(Strings.QuestReactorLaser);
        }

        if (game.getQuestStatusSpaceMonster() == SpecialEvent.STATUS_SPACE_MONSTER_AT_ACAMAR) {
            quests.add(Strings.QuestSpaceMonsterKill);
        }

        if (game.getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT) {
            quests.add(Strings.QuestJaporiDeliver);
        }

        switch (game.getQuestStatusDragonfly()) {
            case SpecialEvent.STATUS_DRAGONFLY_FLY_BARATAS:
                quests.add(Strings.QuestDragonflyBaratas);
                break;
            case SpecialEvent.STATUS_DRAGONFLY_FLY_MELINA:
                quests.add(Strings.QuestDragonflyMelina);
                break;
            case SpecialEvent.STATUS_DRAGONFLY_FLY_REGULAS:
                quests.add(Strings.QuestDragonflyRegulas);
                break;
            case SpecialEvent.STATUS_DRAGONFLY_FLY_ZALKON:
                quests.add(Strings.QuestDragonflyZalkon);
                break;
            case SpecialEvent.STATUS_DRAGONFLY_DESTROYED:
                quests.add(Strings.QuestDragonflyShield);
                break;
        }

        switch (game.getQuestStatusPrincess()) {
            case SpecialEvent.STATUS_PRINCESS_FLY_CENTAURI:
                quests.add(Strings.QuestPrincessCentauri);
                break;
            case SpecialEvent.STATUS_PRINCESS_FLY_INTHARA:
                quests.add(Strings.QuestPrincessInthara);
                break;
            case SpecialEvent.STATUS_PRINCESS_FLY_QONOS:
                quests.add(Strings.QuestPrincessQonos);
                break;
            case SpecialEvent.STATUS_PRINCESS_RESCUED:
                if (game.getCommander().getShip().isPrincessOnBoard()) {
                    if (game.getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_IMPATIENT) {
                        quests.add(Functions.stringVars(
                                Strings.QuestPrincessReturningImpatient,
                                game.getMercenaries()[CrewMemberId.PRINCESS.castToInt()].getName()));
                    } else {
                        quests.add(Functions.stringVars(
                                Strings.QuestPrincessReturning,
                                game.getMercenaries()[CrewMemberId.PRINCESS.castToInt()].getName()));
                    }
                } else {
                    quests.add(Functions.stringVars(Strings.QuestPrincessReturn,
                            game.getMercenaries()[CrewMemberId.PRINCESS.castToInt()].getName()));
                }
                break;
            case SpecialEvent.STATUS_PRINCESS_RETURNED:
                quests.add(Strings.QuestPrincessQuantum);
                break;
        }

        if (game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_HUNTING) {
            quests.add(Strings.QuestScarabFind);
        } else if (game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_DESTROYED) {
            if (Consts.SpecialEvents[SpecialEventType.ScarabUpgradeHull.castToInt()].getLocation() == null) {
                quests.add(Functions.stringVars(Strings.QuestScarabNotify,
                        Consts.SpecialEvents[SpecialEventType.ScarabDestroyed.castToInt()].getLocation().getName()));
            } else {
                quests.add(Functions.stringVars(Strings.QuestScarabHull,
                        Consts.SpecialEvents[SpecialEventType.ScarabUpgradeHull.castToInt()].getLocation().getName()));
            }
        }

        if (game.getCommander().getShip().isSculptureOnBoard()) {
            quests.add(Strings.QuestSculpture);
        } else if (game.getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_DELIVERED) {
            quests.add(Strings.QuestSculptureHiddenBays);
        }

        if (game.getQuestStatusArtifact() == SpecialEvent.STATUS_ARTIFACT_ON_BOARD) {
            quests.add(Strings.QuestArtifact);
        }

        if (game.getCommander().getShip().isJarekOnBoard()) {
            if (game.getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_IMPATIENT) {
                quests.add(Strings.QuestJarekImpatient);
            } else {
                quests.add(Strings.QuestJarek);
            }
        }

        if (game.getCommander().getShip().isWildOnBoard()) {
            if (game.getQuestStatusWild() == SpecialEvent.STATUS_WILD_IMPATIENT) {
                quests.add(Strings.QuestWildImpatient);
            } else {
                quests.add(Strings.QuestWild);
            }
        }

        if (game.getCommander().getShip().getTribbles() > 0) {
            quests.add(Strings.QuestTribbles);
        }

        if (game.getQuestStatusMoon() == SpecialEvent.STATUS_MOON_BOUGHT) {
            quests.add(Strings.QuestMoon);
        }

        return quests;
    }

    private void questsLabelValueClicked(LinkLabelLinkClickedEventArgs e) {
        game.setSelectedSystemByName(e.getLink().getLinkData().toString());
        game.getParentWindow().updateAll();
        close();
    }
}
