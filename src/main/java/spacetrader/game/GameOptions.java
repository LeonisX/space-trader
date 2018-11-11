package spacetrader.game;

import spacetrader.util.IOUtils;

import java.io.Serializable;
import java.util.Objects;

public class GameOptions implements Serializable {

    /**
     * Automatically ignores pirates when it is safe to do so
     */
    private boolean alwaysIgnorePirates = false;
    /**
     * Automatically ignores police when it is safe to do so
     */
    private boolean alwaysIgnorePolice = false;
    /**
     * Automatically ignores Trade in Orbit when it is safe to do so
     */
    private boolean alwaysIgnoreTradeInOrbit = false;
    /**
     * Automatically ignores traders when it is safe to do so
     */
    private boolean alwaysIgnoreTraders = true;
    /**
     * Automatically get a full tank when arriving in a new system
     */
    private boolean autoFuel = false;
    /**
     * Automatically get a full hull repair when arriving in a new system
     */
    private boolean autoRepair = false;
    /**
     * Continuous attack/flee mode
     */
    private boolean continuousAttack = false;
    /**
     * Continue attack on fleeing ship
     */
    private boolean continuousAttackFleeing = false;
    /**
     * Disable opponents when possible (when you have disabling weapons and the opponent is a pirate, trader, or mantis)
     */
    private boolean disableOpponents = false;
    /**
     * by default, ask each time someone buys a newspaper
     */
    private boolean newsAutoPay = false;
    /**
     * by default, don't show newspaper
     */
    private boolean newsAutoShow = false;
    /**
     * remind you every five days about outstanding loan balances
     */
    private boolean remindLoans = true;
    /**
     * Keep enough money for insurance and mercenaries
     */
    private boolean reserveMoney = false;
    /**
     * display range when tracking a system on Short Range Chart
     */
    private boolean showTrackedRange = true;
    /**
     * Automatically stop tracking a system when you get to it?
     */
    private boolean trackAutoOff = true;
    /**
     * Number of cargo bays to leave empty when buying goods
     */
    private int leaveEmpty = 0;

    private GameOptions() {
        // need for tests
    }

    public GameOptions(boolean loadFromDefaults) {
        if (loadFromDefaults) {
            loadFromDefaults(false);
        }
    }

    public void copyValues(GameOptions source) {
        setAlwaysIgnorePirates(source.isAlwaysIgnorePirates());
        setAlwaysIgnorePolice(source.isAlwaysIgnorePolice());
        setAlwaysIgnoreTradeInOrbit(source.isAlwaysIgnoreTradeInOrbit());
        setAlwaysIgnoreTraders(source.isAlwaysIgnoreTraders());
        setAutoFuel(source.isAutoFuel());
        setAutoRepair(source.isAutoRepair());
        setContinuousAttack(source.isContinuousAttack());
        setContinuousAttackFleeing(source.isContinuousAttackFleeing());
        setDisableOpponents(source.isDisableOpponents());
        setNewsAutoPay(source.isNewsAutoPay());
        setNewsAutoShow(source.isNewsAutoShow());
        setRemindLoans(source.isRemindLoans());
        setReserveMoney(source.isReserveMoney());
        setShowTrackedRange(source.isShowTrackedRange());
        setTrackAutoOff(source.isTrackAutoOff());
        setLeaveEmpty(source.getLeaveEmpty());
    }

    public void loadFromDefaults(boolean errorIfFileNotFound) {
        GameOptions defaults;

        Object obj = IOUtils.readObjectFromFile(Consts.DefaultSettingsFile, !errorIfFileNotFound).orElse(null);
        if (obj == null) {
            defaults = new GameOptions(false);
        } else {
            defaults = (GameOptions) obj;
        }

        copyValues(defaults);
    }

    public void saveAsDefaults() {
        IOUtils.writeObjectToFile(Consts.DefaultSettingsFile, this);
    }

    public boolean isAlwaysIgnorePirates() {
        return alwaysIgnorePirates;
    }

    public void setAlwaysIgnorePirates(boolean alwaysIgnorePirates) {
        this.alwaysIgnorePirates = alwaysIgnorePirates;
    }

    public boolean isAlwaysIgnorePolice() {
        return alwaysIgnorePolice;
    }

    public void setAlwaysIgnorePolice(boolean alwaysIgnorePolice) {
        this.alwaysIgnorePolice = alwaysIgnorePolice;
    }

    public boolean isAlwaysIgnoreTradeInOrbit() {
        return alwaysIgnoreTradeInOrbit;
    }

    public void setAlwaysIgnoreTradeInOrbit(boolean alwaysIgnoreTradeInOrbit) {
        this.alwaysIgnoreTradeInOrbit = alwaysIgnoreTradeInOrbit;
    }

    public boolean isAlwaysIgnoreTraders() {
        return alwaysIgnoreTraders;
    }

    public void setAlwaysIgnoreTraders(boolean alwaysIgnoreTraders) {
        this.alwaysIgnoreTraders = alwaysIgnoreTraders;
    }

    public boolean isAutoFuel() {
        return autoFuel;
    }

    public void setAutoFuel(boolean autoFuel) {
        this.autoFuel = autoFuel;
    }

    public boolean isAutoRepair() {
        return autoRepair;
    }

    public void setAutoRepair(boolean autoRepair) {
        this.autoRepair = autoRepair;
    }

    public boolean isContinuousAttack() {
        return continuousAttack;
    }

    public void setContinuousAttack(boolean continuousAttack) {
        this.continuousAttack = continuousAttack;
    }

    public boolean isContinuousAttackFleeing() {
        return continuousAttackFleeing;
    }

    public void setContinuousAttackFleeing(boolean continuousAttackFleeing) {
        this.continuousAttackFleeing = continuousAttackFleeing;
    }

    public boolean isDisableOpponents() {
        return disableOpponents;
    }

    public void setDisableOpponents(boolean disableOpponents) {
        this.disableOpponents = disableOpponents;
    }

    public boolean isNewsAutoPay() {
        return newsAutoPay;
    }

    public void setNewsAutoPay(boolean newsAutoPay) {
        this.newsAutoPay = newsAutoPay;
    }

    public boolean isNewsAutoShow() {
        return newsAutoShow;
    }

    public void setNewsAutoShow(boolean newsAutoShow) {
        this.newsAutoShow = newsAutoShow;
    }

    public boolean isRemindLoans() {
        return remindLoans;
    }

    public void setRemindLoans(boolean remindLoans) {
        this.remindLoans = remindLoans;
    }

    public boolean isReserveMoney() {
        return reserveMoney;
    }

    public void setReserveMoney(boolean reserveMoney) {
        this.reserveMoney = reserveMoney;
    }

    public boolean isShowTrackedRange() {
        return showTrackedRange;
    }

    public void setShowTrackedRange(boolean showTrackedRange) {
        this.showTrackedRange = showTrackedRange;
    }

    public boolean isTrackAutoOff() {
        return trackAutoOff;
    }

    public void setTrackAutoOff(boolean trackAutoOff) {
        this.trackAutoOff = trackAutoOff;
    }

    public int getLeaveEmpty() {
        return leaveEmpty;
    }

    public void setLeaveEmpty(int leaveEmpty) {
        this.leaveEmpty = leaveEmpty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameOptions)) return false;
        GameOptions that = (GameOptions) o;
        return alwaysIgnorePirates == that.alwaysIgnorePirates &&
                alwaysIgnorePolice == that.alwaysIgnorePolice &&
                alwaysIgnoreTradeInOrbit == that.alwaysIgnoreTradeInOrbit &&
                alwaysIgnoreTraders == that.alwaysIgnoreTraders &&
                autoFuel == that.autoFuel &&
                autoRepair == that.autoRepair &&
                continuousAttack == that.continuousAttack &&
                continuousAttackFleeing == that.continuousAttackFleeing &&
                disableOpponents == that.disableOpponents &&
                newsAutoPay == that.newsAutoPay &&
                newsAutoShow == that.newsAutoShow &&
                remindLoans == that.remindLoans &&
                reserveMoney == that.reserveMoney &&
                showTrackedRange == that.showTrackedRange &&
                trackAutoOff == that.trackAutoOff &&
                leaveEmpty == that.leaveEmpty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alwaysIgnorePirates, alwaysIgnorePolice, alwaysIgnoreTradeInOrbit, alwaysIgnoreTraders, autoFuel, autoRepair, continuousAttack, continuousAttackFleeing, disableOpponents, newsAutoPay, newsAutoShow, remindLoans, reserveMoney, showTrackedRange, trackAutoOff, leaveEmpty);
    }
}
