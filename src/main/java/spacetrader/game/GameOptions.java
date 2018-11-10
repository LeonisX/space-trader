package spacetrader.game;

import spacetrader.util.Functions;
import spacetrader.util.Hashtable;

public class GameOptions extends STSerializableObject {

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

    public GameOptions(boolean loadFromDefaults) {
        if (loadFromDefaults) {
            loadFromDefaults(false);
        }
    }

    GameOptions(Hashtable hash) {
        super();
        alwaysIgnorePirates = getValueFromHash(hash, "_alwaysIgnorePirates", alwaysIgnorePirates);
        alwaysIgnorePolice = getValueFromHash(hash, "_alwaysIgnorePolice", alwaysIgnorePolice);
        alwaysIgnoreTradeInOrbit = getValueFromHash(hash, "_alwaysIgnoreTradeInOrbit",
                alwaysIgnoreTradeInOrbit);
        alwaysIgnoreTraders = getValueFromHash(hash, "_alwaysIgnoreTraders", alwaysIgnoreTraders);
        autoFuel = getValueFromHash(hash, "_autoFuel", autoFuel);
        autoRepair = getValueFromHash(hash, "_autoRepair", autoRepair);
        continuousAttack = getValueFromHash(hash, "_continuousAttack", continuousAttack);
        continuousAttackFleeing = getValueFromHash(hash, "_continuousAttackFleeing", continuousAttackFleeing);
        disableOpponents = getValueFromHash(hash, "_disableOpponents", disableOpponents);
        newsAutoPay = getValueFromHash(hash, "_newsAutoPay", newsAutoPay);
        newsAutoShow = getValueFromHash(hash, "_newsAutoShow", newsAutoShow);
        remindLoans = getValueFromHash(hash, "_remindLoans", remindLoans);
        reserveMoney = getValueFromHash(hash, "_reserveMoney", reserveMoney);
        showTrackedRange = getValueFromHash(hash, "_showTrackedRange", showTrackedRange);
        trackAutoOff = getValueFromHash(hash, "_trackAutoOff", trackAutoOff);
        leaveEmpty = getValueFromHash(hash, "_leaveEmpty", leaveEmpty);
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

        Object obj = Functions.loadFile(Consts.DefaultSettingsFile, !errorIfFileNotFound);
        if (obj == null) {
            defaults = new GameOptions(false);
        } else {
            defaults = new GameOptions((Hashtable) obj);
        }

        copyValues(defaults);
    }

    public void saveAsDefaults() {
        Functions.saveFile(Consts.DefaultSettingsFile, serialize());
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_alwaysIgnorePirates", alwaysIgnorePirates);
        hash.add("_alwaysIgnorePolice", alwaysIgnorePolice);
        hash.add("_alwaysIgnoreTradeInOrbit", alwaysIgnoreTradeInOrbit);
        hash.add("_alwaysIgnoreTraders", alwaysIgnoreTraders);
        hash.add("_autoFuel", autoFuel);
        hash.add("_autoRepair", autoRepair);
        hash.add("_continuousAttack", continuousAttack);
        hash.add("_continuousAttackFleeing", continuousAttackFleeing);
        hash.add("_disableOpponents", disableOpponents);
        hash.add("_newsAutoPay", newsAutoPay);
        hash.add("_newsAutoShow", newsAutoShow);
        hash.add("_remindLoans", remindLoans);
        hash.add("_reserveMoney", reserveMoney);
        hash.add("_showTrackedRange", showTrackedRange);
        hash.add("_trackAutoOff", trackAutoOff);
        hash.add("_leaveEmpty", leaveEmpty);

        return hash;
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
}
