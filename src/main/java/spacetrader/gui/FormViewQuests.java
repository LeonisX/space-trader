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
//using System;
//using System.Drawing;
//using System.Collections;
//using System.ComponentModel;
//using System.Windows.Forms;
package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.game.enums.CrewMemberId;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.game.*;
import spacetrader.stub.ArrayList;
import spacetrader.util.Util;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class FormViewQuests extends SpaceTraderForm {
    // #region Control Declarations

    private spacetrader.controls.Button btnClose;
    private spacetrader.controls.LinkLabel lblQuests;
    private Container components = null;

    // #endregion

    // #region Methods

    public FormViewQuests() {
        initializeComponent();

        UpdateAll();
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        this.btnClose = new spacetrader.controls.Button();
        this.lblQuests = new spacetrader.controls.LinkLabel();
        this.suspendLayout();
        //
        // btnClose
        //
        this.btnClose.setDialogResult(DialogResult.CANCEL);
        this.btnClose.setLocation(new java.awt.Point(-32, -32));
        this.btnClose.setName("btnClose");
        this.btnClose.setSize(new spacetrader.controls.Size(32, 32));
        this.btnClose.setTabIndex(32);
        this.btnClose.setTabStop(false);
        this.btnClose.setText("X");
        //
        // lblQuests
        //
        this.lblQuests.LinkArea = new spacetrader.controls.LinkArea(0, 0);
        this.lblQuests.setLocation(new java.awt.Point(8, 8));
        this.lblQuests.setName("lblQuests");
        this.lblQuests.setSize(new spacetrader.controls.Size(368, 312));
        this.lblQuests.setTabIndex(44);
        this.lblQuests.setText("Kill the space monster at Acamar."
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
                + "Claim your moon at Utopia.");
        this.lblQuests.LinkClicked = new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            public void handle(Object sender,
                               spacetrader.controls.LinkLabelLinkClickedEventArgs e) {
                lblQuests_LinkClicked(sender, e);
            }
        };
        //
        // FormViewQuests
        //
        this.setAutoScaleBaseSize(new spacetrader.controls.Size(5, 13));
        this.setCancelButton(this.btnClose);
        this.setClientSize(new spacetrader.controls.Size(378, 325));
        this.Controls.addAll(Arrays.asList(this.btnClose, this.lblQuests));
        this.setFormBorderStyle(FormBorderStyle.FixedDialog);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setName("FormViewQuests");
        this.setShowInTaskbar(false);
        this.setStartPosition(FormStartPosition.CenterParent);
        this.setText("Quests");
    }

    // #endregion

    private String[] GetQuestStrings() {
        Game game = Game.CurrentGame();
        ArrayList quests = new ArrayList(12);

        if (game.getQuestStatusGemulon() > SpecialEvent.StatusGemulonNotStarted
                && game.getQuestStatusGemulon() < SpecialEvent.StatusGemulonDate) {
            if (game.getQuestStatusGemulon() == SpecialEvent.StatusGemulonDate - 1)
                quests.add(Strings.QuestGemulonInformTomorrow);
            else
                quests.add(Functions.StringVars(Strings.QuestGemulonInformDays,
                        Functions.Multiples(SpecialEvent.StatusGemulonDate
                                - game.getQuestStatusGemulon(), "day")));
        } else if (game.getQuestStatusGemulon() == SpecialEvent.StatusGemulonFuel)
            quests.add(Strings.QuestGemulonFuel);

        if (game.getQuestStatusExperiment() > SpecialEvent.StatusExperimentNotStarted
                && game.getQuestStatusExperiment() < SpecialEvent.StatusExperimentDate) {
            if (game.getQuestStatusExperiment() == SpecialEvent.StatusExperimentDate - 1)
                quests.add(Strings.QuestExperimentInformTomorrow);
            else
                quests.add(Functions.StringVars(
                        Strings.QuestExperimentInformDays, Functions.Multiples(
                                SpecialEvent.StatusExperimentDate
                                        - game.getQuestStatusExperiment(), "day")));
        }

        if (game.Commander().getShip().ReactorOnBoard()) {
            if (game.getQuestStatusReactor() == SpecialEvent.StatusReactorFuelOk)
                quests.add(Strings.QuestReactor);
            else
                quests.add(Strings.QuestReactorFuel);
        } else if (game.getQuestStatusReactor() == SpecialEvent.StatusReactorDelivered)
            quests.add(Strings.QuestReactorLaser);

        if (game.getQuestStatusSpaceMonster() == SpecialEvent.StatusSpaceMonsterAtAcamar)
            quests.add(Strings.QuestSpaceMonsterKill);

        if (game.getQuestStatusJapori() == SpecialEvent.StatusJaporiInTransit)
            quests.add(Strings.QuestJaporiDeliver);

        switch (game.getQuestStatusDragonfly()) {
            case SpecialEvent.StatusDragonflyFlyBaratas:
                quests.add(Strings.QuestDragonflyBaratas);
                break;
            case SpecialEvent.StatusDragonflyFlyMelina:
                quests.add(Strings.QuestDragonflyMelina);
                break;
            case SpecialEvent.StatusDragonflyFlyRegulas:
                quests.add(Strings.QuestDragonflyRegulas);
                break;
            case SpecialEvent.StatusDragonflyFlyZalkon:
                quests.add(Strings.QuestDragonflyZalkon);
                break;
            case SpecialEvent.StatusDragonflyDestroyed:
                quests.add(Strings.QuestDragonflyShield);
                break;
        }

        switch (game.getQuestStatusPrincess()) {
            case SpecialEvent.StatusPrincessFlyCentauri:
                quests.add(Strings.QuestPrincessCentauri);
                break;
            case SpecialEvent.StatusPrincessFlyInthara:
                quests.add(Strings.QuestPrincessInthara);
                break;
            case SpecialEvent.StatusPrincessFlyQonos:
                quests.add(Strings.QuestPrincessQonos);
                break;
            case SpecialEvent.StatusPrincessRescued:
                if (game.Commander().getShip().PrincessOnBoard()) {
                    if (game.getQuestStatusPrincess() == SpecialEvent.StatusPrincessImpatient)
                        quests.add(Functions.StringVars(
                                Strings.QuestPrincessReturningImpatient,
                                game.Mercenaries()[CrewMemberId.Princess
                                        .castToInt()].Name()));
                    else
                        quests.add(Functions.StringVars(
                                Strings.QuestPrincessReturning,
                                game.Mercenaries()[CrewMemberId.Princess
                                        .castToInt()].Name()));
                } else
                    quests.add(Functions.StringVars(Strings.QuestPrincessReturn,
                            game.Mercenaries()[CrewMemberId.Princess
                                    .castToInt()].Name()));
                break;
            case SpecialEvent.StatusPrincessReturned:
                quests.add(Strings.QuestPrincessQuantum);
                break;
        }

        if (game.getQuestStatusScarab() == SpecialEvent.StatusScarabHunting)
            quests.add(Strings.QuestScarabFind);
        else if (game.getQuestStatusScarab() == SpecialEvent.StatusScarabDestroyed) {
            if (Consts.SpecialEvents[SpecialEventType.ScarabUpgradeHull
                    .castToInt()].Location() == null)
                quests
                        .add(Functions
                                .StringVars(
                                        Strings.QuestScarabNotify,
                                        Consts.SpecialEvents[SpecialEventType.ScarabDestroyed
                                                .castToInt()].Location().Name()));
            else
                quests
                        .add(Functions
                                .StringVars(
                                        Strings.QuestScarabHull,
                                        Consts.SpecialEvents[SpecialEventType.ScarabUpgradeHull
                                                .castToInt()].Location().Name()));
        }

        if (game.Commander().getShip().SculptureOnBoard())
            quests.add(Strings.QuestSculpture);
        else if (game.getQuestStatusReactor() == SpecialEvent.StatusReactorDelivered)
            quests.add(Strings.QuestSculptureHiddenBays);

        if (game.getQuestStatusArtifact() == SpecialEvent.StatusArtifactOnBoard)
            quests.add(Strings.QuestArtifact);

        if (game.Commander().getShip().JarekOnBoard()) {
            if (game.getQuestStatusJarek() == SpecialEvent.StatusJarekImpatient)
                quests.add(Strings.QuestJarekImpatient);
            else
                quests.add(Strings.QuestJarek);
        }

        if (game.Commander().getShip().WildOnBoard()) {
            if (game.getQuestStatusWild() == SpecialEvent.StatusWildImpatient)
                quests.add(Strings.QuestWildImpatient);
            else
                quests.add(Strings.QuestWild);
        }

        if (game.Commander().getShip().getTribbles() > 0)
            quests.add(Strings.QuestTribbles);

        if (game.getQuestStatusMoon() == SpecialEvent.StatusMoonBought)
            quests.add(Strings.QuestMoon);

        return Functions.ArrayListtoStringArray(quests);
    }

    private void UpdateAll() {
        String[] quests = GetQuestStrings();
        if (quests.length == 0)
            lblQuests.setText(Strings.QuestNone);
        else {
            lblQuests.setText(Util.StringsJoin(Strings.newline + Strings.newline, quests));

            for (int i = 0; i < Strings.SystemNames.length; i++) {
                String systemName = Strings.SystemNames[i];
                int start = 0;
                int index = -1;

                while ((index = lblQuests.getText().indexOf(systemName, start)) >= 0) {
                    lblQuests.Links.add(index, systemName.length(), systemName);
                    start = index + systemName.length();
                }
            }
        }
    }

    // #endregion

    // #region Event Handlers

    private void lblQuests_LinkClicked(Object sender,
                                       spacetrader.controls.LinkLabelLinkClickedEventArgs e) {
        Game.CurrentGame().setSelectedSystemByName(e.Link.LinkData.toString());
        Game.CurrentGame().getParentWindow().updateAll();
        Close();
    }

    // #endregion
}
