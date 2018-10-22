/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
// using System;
// using System.Collections;
package spacetrader.game;

import spacetrader.game.enums.CrewMemberId;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.SkillType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.stub.ArrayList;
import spacetrader.util.Hashtable;
import spacetrader.util.Util;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class CrewMember extends STSerializableObject {

    private CrewMemberId id;
    private int[] skills = new int[4];
    private StarSystemId currentSystemId = StarSystemId.NA;

    CrewMember(CrewMemberId id, int pilot, int fighter, int trader, int engineer, StarSystemId curSystemId) {
        this.id = id;
        setPilot(pilot);
        setFighter(fighter);
        setTrader(trader);
        setEngineer(engineer);
        currentSystemId = curSystemId;
    }

    CrewMember(CrewMember baseCrewMember) {
        id = baseCrewMember.getId();
        setPilot(baseCrewMember.getPilot());
        setFighter(baseCrewMember.getFighter());
        setTrader(baseCrewMember.getTrader());
        setEngineer(baseCrewMember.getEngineer());
        currentSystemId = baseCrewMember.getCurrentSystemId();
        skills = baseCrewMember.getSkills();
    }

    CrewMember(Hashtable hash) {
        super();
        id = CrewMemberId.fromInt(getValueFromHash(hash, "_id", Integer.class));
        skills = getValueFromHash(hash, "_skills", skills, int[].class);
        currentSystemId = StarSystemId.fromInt(getValueFromHash(hash, "_curSystemId", currentSystemId, Integer.class));
    }

    private void changeRandomSkill(int amount) {
        ArrayList skillIdList = new ArrayList(4);
        for (int i = 0; i < getSkills().length; i++) {
            if (getSkills()[i] + amount > 0 && getSkills()[i] + amount < Consts.MaxSkill) {
                skillIdList.add(i);
            }
        }

        if (skillIdList.size() > 0) {
            int skill = (Integer) skillIdList.get(Functions.getRandom(skillIdList.size()));

            int curTrader = Game.getCurrentGame().getCommander().getShip().getTrader();
            getSkills()[skill] += amount;
            if (Game.getCurrentGame().getCommander().getShip().getTrader() != curTrader)
                Game.getCurrentGame().recalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());
        }
    }

    // *************************************************************************
    // Increase one of the skills.
    // *************************************************************************
    void increaseRandomSkill() {
        changeRandomSkill(1);
    }

    // *************************************************************************
    // NthLowest Skill. Returns skill with the nth lowest score
    // (i.e., 2 is the second worst skill). If there is a tie, it will return
    // in the order of Pilot, Fighter, Trader, Engineer.
    // JAF - rewrote this to be more efficient.
    // *************************************************************************
    int nthLowestSkill(int n) {
        int[] skillIds = new int[]{0, 1, 2, 3};

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3 - j; i++) {
                if (getSkills()[skillIds[i]] > getSkills()[skillIds[i + 1]]) {
                    int temp = skillIds[i];
                    skillIds[i] = skillIds[i + 1];
                    skillIds[i + 1] = temp;
                }
            }
        }

        return skillIds[n - 1];
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_id", id);
        hash.add("_skills", skills);
        hash.add("_curSystemId", currentSystemId);

        return hash;
    }

    // *************************************************************************
    // Randomly tweak the skills.
    // *************************************************************************
    void tonicTweakRandomSkill() {
        int[] oldSkills = Arrays.copyOf(getSkills(), getSkills().length);

        if (Game.getCurrentGame().getDifficulty().castToInt() < Difficulty.HARD.castToInt()) {
            // add one to a random skill, subtract one from a random skill
            while (getSkills()[0] == oldSkills[0] && getSkills()[1] == oldSkills[1] && getSkills()[2] == oldSkills[2]
                    && getSkills()[3] == oldSkills[3]) {
                changeRandomSkill(1);
                changeRandomSkill(-1);
            }
        } else {
            // add one to two random skills, subtract three from one random skill
            changeRandomSkill(1);
            changeRandomSkill(1);
            changeRandomSkill(-3);
        }
    }

    public @Override
    String toString() {
        return getName();
    }

    public StarSystem getCurrentSystem() {
        return currentSystemId == StarSystemId.NA ? null : Game.getCurrentGame().getUniverse()[currentSystemId.castToInt()];
    }

    void setCurrentSystem(StarSystem value) {
        currentSystemId = value.getId();
    }

    StarSystemId getCurrentSystemId() {
        return currentSystemId;
    }

    void setCurrentSystemId(StarSystemId currentSystemId) {
        this.currentSystemId = currentSystemId;
    }

    public int getEngineer() {
        return skills[SkillType.ENGINEER.castToInt()];
    }

    public void setEngineer(int value) {
        skills[SkillType.ENGINEER.castToInt()] = value;
    }

    public int getFighter() {
        return skills[SkillType.FIGHTER.castToInt()];
    }

    public void setFighter(int value) {
        skills[SkillType.FIGHTER.castToInt()] = value;
    }

    public CrewMemberId getId() {
        return id;
    }

    public String getName() {
        return Strings.CrewMemberNames[id.castToInt()];
    }

    public int getPilot() {
        return skills[SkillType.PILOT.castToInt()];
    }

    public void setPilot(int value) {
        skills[SkillType.PILOT.castToInt()] = value;
    }

    public int getRate() {
        return Util.arrayContains(Consts.SpecialCrewMemberIds, getId()) || getId() == CrewMemberId.ZEETHIBAL ? 0 : (getPilot()
                + getFighter() + getTrader() + getEngineer()) * 3;
    }

    public int[] getSkills() {
        return skills;
    }

    public int getTrader() {
        return skills[SkillType.TRADER.castToInt()];
    }

    public void setTrader(int value) {
        skills[SkillType.TRADER.castToInt()] = value;
    }
}
