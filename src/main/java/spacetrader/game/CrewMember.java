package spacetrader.game;

import spacetrader.game.enums.CrewMemberId;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.SkillType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class CrewMember implements Serializable {

    static final long serialVersionUID = 15L;

    private int id;
    //TODO remove in future
    private CrewMemberId crewMemberId;
    private int[] skills = new int[4];
    private boolean mercenary;
    private boolean volunteer;
    private StarSystemId currentSystemId; // StarSystemId.NA

    CrewMember() {
        // need for tests
    }

    public CrewMember(CrewMemberId crewMemberId, int pilot, int fighter, int trader, int engineer, boolean mercenary, StarSystemId curSystemId) {
        this(crewMemberId, crewMemberId.castToInt(), pilot, fighter, trader, engineer, mercenary, curSystemId);
    }

    public CrewMember(CrewMemberId crewMemberId, int id, int pilot, int fighter, int trader, int engineer, boolean mercenary, StarSystemId curSystemId) {
        this.id = id;
        this.crewMemberId = crewMemberId;
        setPilot(pilot);
        setFighter(fighter);
        setTrader(trader);
        setEngineer(engineer);
        setMercenary(mercenary);
        setVolunteer(false);
        currentSystemId = curSystemId;
    }

    public static CrewMember specialCrewMember(int id, int pilot, int fighter, int trader, int engineer, boolean mercenary) {
        return new CrewMember(CrewMemberId.NA, id, pilot, fighter, trader,  engineer, mercenary, StarSystemId.NA);
    }

    CrewMember(CrewMember baseCrewMember) {
        this.id = baseCrewMember.getId();
        this.crewMemberId = baseCrewMember.getCrewMemberId();
        setPilot(baseCrewMember.getPilot());
        setFighter(baseCrewMember.getFighter());
        setTrader(baseCrewMember.getTrader());
        setEngineer(baseCrewMember.getEngineer());
        setMercenary(mercenary);
        setVolunteer(false);
        currentSystemId = baseCrewMember.getCurrentSystemId();
        skills = baseCrewMember.getSkills();
    }

    public void changeRandomSkill(int amount) {
        ArrayList<Integer> skillIdList = new ArrayList<>();
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] + amount >= 0 && skills[i] + amount < Consts.MaxSkill) {
                skillIdList.add(i);
            }
        }

        if (skillIdList.size() > 0) {
            int skillId = skillIdList.get(Functions.getRandom(skillIdList.size()));

            int curTrader = Game.getCurrentGame().getShip().getTrader();
            skills[skillId] += amount;
            if (Game.getCurrentGame().getShip().getTrader() != curTrader) {
                Game.getCurrentGame().recalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());
            }
        }
    }

    // *************************************************************************
    // Increase one of the skills.
    // *************************************************************************
    public void increaseRandomSkill() {
        changeRandomSkill(1);
    }

    // *************************************************************************
    // NthLowest Skill. Returns skill with the nth lowest score
    // (i.e., 2 is the second worst skill). If there is a tie, it will return
    // in the order of Pilot, Fighter, Trader, Engineer.
    // JAF - rewrote this to be more efficient.
    // *************************************************************************
    public int nthLowestSkill(int n) {
        int[] skillIds = new int[]{0, 1, 2, 3};

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3 - j; i++) {
                if (skills[skillIds[i]] > skills[skillIds[i + 1]]) {
                    int temp = skillIds[i];
                    skillIds[i] = skillIds[i + 1];
                    skillIds[i + 1] = temp;
                }
            }
        }

        return skillIds[n - 1];
    }

    public @Override
    String toString() {
        return getName();
    }

    public StarSystem getCurrentSystem() {
        return (currentSystemId == StarSystemId.NA) ? null : Game.getCurrentGame().getStarSystem(currentSystemId);
    }

    public void setCurrentSystem(StarSystem value) {
        currentSystemId = value.getId();
    }

    public StarSystemId getCurrentSystemId() {
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

    public boolean isMercenary() {
        return mercenary;
    }

    public void setMercenary(boolean mercenary) {
        this.mercenary = mercenary;
    }

    public boolean isVolunteer() {
        return volunteer;
    }

    public void setVolunteer(boolean volunteer) {
        this.volunteer = volunteer;
    }

    /*public CrewMemberId getId() {
        return id;
    }*/

    public String getName() {
        return (id >= 1000) ? Game.getCurrentGame().getQuestSystem().getCrewMemberName(id) : Strings.CrewMemberNames[id];
    }

    public int getPilot() {
        return skills[SkillType.PILOT.castToInt()];
    }

    public void setPilot(int value) {
        skills[SkillType.PILOT.castToInt()] = value;
    }

    public int getRate() {
        return isMercenary() && !isVolunteer() ? (getPilot() + getFighter() + getTrader() + getEngineer()) * 3 : 0;
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

    public void setSkills(int[] skills) {
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CrewMemberId getCrewMemberId() {
        return crewMemberId;
    }

    public void setCrewMemberId(CrewMemberId crewMemberId) {
        this.crewMemberId = crewMemberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CrewMember)) return false;
        CrewMember that = (CrewMember) o;
        return id == that.id &&
                Arrays.equals(skills, that.skills) &&
                currentSystemId == that.currentSystemId;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, currentSystemId);
        result = 31 * result + Arrays.hashCode(skills);
        return result;
    }
}
