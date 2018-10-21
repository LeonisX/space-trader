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

// import java.util.ArrayList;

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
    // #region Member Declarations

    private CrewMemberId _id;
    private int[] _skills = new int[4];
    private StarSystemId _curSystemId = StarSystemId.NA;

    public CrewMember(CrewMemberId id, int pilot, int fighter, int trader, int engineer, StarSystemId curSystemId) {
        _id = id;
        Pilot(pilot);
        Fighter(fighter);
        Trader(trader);
        Engineer(engineer);
        _curSystemId = curSystemId;
    }

    public CrewMember(CrewMember baseCrewMember) {
        _id = baseCrewMember.Id();
        Pilot(baseCrewMember.Pilot());
        Fighter(baseCrewMember.Fighter());
        Trader(baseCrewMember.Trader());
        Engineer(baseCrewMember.Engineer());
        _curSystemId = baseCrewMember.getCurrentSystemId();
    }

    public CrewMember(Hashtable hash) {
        super();
        _id = CrewMemberId.fromInt(GetValueFromHash(hash, "_id", Integer.class));
        _skills = GetValueFromHash(hash, "_skills", _skills, int[].class);
        _curSystemId = StarSystemId.fromInt(GetValueFromHash(hash, "_curSystemId", _curSystemId, Integer.class));
    }

    private void ChangeRandomSkill(int amount) {
        ArrayList skillIdList = new ArrayList(4);
        for (int i = 0; i < Skills().length; i++) {
            if (Skills()[i] + amount > 0 && Skills()[i] + amount < Consts.MaxSkill)
                skillIdList.add(i);
        }

        if (skillIdList.size() > 0) {
            int skill = (Integer) skillIdList.get(Functions.getRandom(skillIdList.size()));

            int curTrader = Game.getCurrentGame().getCommander().getShip().Trader();
            Skills()[skill] += amount;
            if (Game.getCurrentGame().getCommander().getShip().Trader() != curTrader)
                Game.getCurrentGame().RecalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());
        }
    }

    // *************************************************************************
    // Increase one of the skills.
    // *************************************************************************
    public void IncreaseRandomSkill() {
        ChangeRandomSkill(1);
    }

    // *************************************************************************
    // NthLowest Skill. Returns skill with the nth lowest score
    // (i.e., 2 is the second worst skill). If there is a tie, it will return
    // in the order of Pilot, Fighter, Trader, Engineer.
    // JAF - rewrote this to be more efficient.
    // *************************************************************************
    public int NthLowestSkill(int n) {
        int[] skillIds = new int[]{0, 1, 2, 3};

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3 - j; i++) {
                if (Skills()[skillIds[i]] > Skills()[skillIds[i + 1]]) {
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

        hash.add("_id", _id);
        hash.add("_skills", _skills);
        hash.add("_curSystemId", _curSystemId);

        return hash;
    }

    // *************************************************************************
    // Randomly tweak the skills.
    // *************************************************************************
    public void TonicTweakRandomSkill() {
        int[] oldSkills = Arrays.copyOf(Skills(), Skills().length);

        if (Game.getCurrentGame().Difficulty().castToInt() < Difficulty.Hard.castToInt()) {
            // add one to a random skill, subtract one from a random skill
            while (Skills()[0] == oldSkills[0] && Skills()[1] == oldSkills[1] && Skills()[2] == oldSkills[2]
                    && Skills()[3] == oldSkills[3]) {
                ChangeRandomSkill(1);
                ChangeRandomSkill(-1);
            }
        } else {
            // add one to two random skills, subtract three from one random
            // skill
            ChangeRandomSkill(1);
            ChangeRandomSkill(1);
            ChangeRandomSkill(-3);
        }
    }

    public @Override
    String toString() {
        return Name();
    }

    // #endregion

    // #region Properties

    public StarSystem getCurrentSystem() {
        return _curSystemId == StarSystemId.NA ? null : Game.getCurrentGame().getUniverse()[_curSystemId.castToInt()];
    }

    public void setCurrentSystem(StarSystem value) {
        _curSystemId = value.Id();
    }

    public StarSystemId getCurrentSystemId() {
        return _curSystemId;
    }

    public void setCurrentSystemId(StarSystemId currentSystemId) {
        _curSystemId = currentSystemId;
    }

    public int Engineer() {
        return _skills[SkillType.Engineer.castToInt()];
    }

    public void Engineer(int value) {
        _skills[SkillType.Engineer.castToInt()] = value;
    }

    public int Fighter() {
        return _skills[SkillType.Fighter.castToInt()];
    }

    public void Fighter(int value) {
        _skills[SkillType.Fighter.castToInt()] = value;
    }

    public CrewMemberId Id() {
        return _id;
    }

    public String Name() {
        return Strings.CrewMemberNames[_id.castToInt()];
    }

    public int Pilot() {
        return _skills[SkillType.Pilot.castToInt()];
    }

    public void Pilot(int value) {
        _skills[SkillType.Pilot.castToInt()] = value;
    }

    public int Rate() {
        return Util.ArrayContains(Consts.SpecialCrewMemberIds, Id()) || Id() == CrewMemberId.Zeethibal ? 0 : (Pilot()
                + Fighter() + Trader() + Engineer()) * 3;
    }

    public int[] Skills() {
        return _skills;
    }

    public int Trader() {
        return _skills[SkillType.Trader.castToInt()];
    }

    public void Trader(int value) {
        _skills[SkillType.Trader.castToInt()] = value;
    }
}
