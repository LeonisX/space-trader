package spacetrader.game.quest.containers;

import spacetrader.game.Ship;

public class OpponentsContainer {

    private Ship attacker;
    private Ship defender;

    private int attackerLasers;
    private int attackerDisruptors;

    public OpponentsContainer(Ship attacker, Ship defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    public Ship getAttacker() {
        return attacker;
    }

    public Ship getDefender() {
        return defender;
    }

    public int getAttackerLasers() {
        return attackerLasers;
    }

    public void setAttackerLasers(int attackerLasers) {
        this.attackerLasers = attackerLasers;
    }

    public int getAttackerDisruptors() {
        return attackerDisruptors;
    }

    public void setAttackerDisruptors(int attackerDisruptors) {
        this.attackerDisruptors = attackerDisruptors;
    }

    public int getAttackerWeapons() {
        return attackerLasers + attackerDisruptors;
    }
}
