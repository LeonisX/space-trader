package spacetrader.game;

import spacetrader.controls.Graphics;
import spacetrader.controls.Image;
import spacetrader.game.enums.*;
import spacetrader.guifacade.GuiEngine;

import java.io.Serializable;
import java.util.Objects;

public class ShipSpec implements Serializable {

    static final long serialVersionUID = 151L;

    private ShipType type = ShipType.CUSTOM;
    private Size size = Size.TINY;
    private int cargoBays = 0;
    private int weaponSlots = 0;
    private int shieldSlots = 0;
    private int gadgetSlots = 0;
    private int crewQuarters = 0;
    private int fuelTanks = 0;
    private int fuelCost = 0;
    private int hullStrength = 0;
    private int repairCost = 0;
    private int price = 0;
    private int occurrence = 0;
    private Activity police = Activity.NA;
    private Activity pirates = Activity.NA;
    private Activity traders = Activity.NA;
    private TechLevel minTech = TechLevel.UNAVAILABLE;
    private boolean hullUpgraded = false;
    private int imageIndex = Consts.ShipImgUseDefault;

    ShipSpec() {
    }

    ShipSpec(ShipType type, Size size, int cargoBays, int weaponSlots, int shieldSlots, int gadgetSlots,
             int crewQuarters, int fuelTanks, int fuelCost, int hullStrength, int repairCost, int price, int occurrence,
             Activity police, Activity pirates, Activity traders, TechLevel minTechLevel) {
        this.type = type;
        this.size = size;
        this.cargoBays = cargoBays;
        this.weaponSlots = weaponSlots;
        this.shieldSlots = shieldSlots;
        this.gadgetSlots = gadgetSlots;
        this.crewQuarters = crewQuarters;
        this.fuelTanks = fuelTanks;
        this.fuelCost = fuelCost;
        this.hullStrength = hullStrength;
        this.repairCost = repairCost;
        this.price = price;
        this.occurrence = occurrence;
        this.police = police;
        this.pirates = pirates;
        this.traders = traders;
        minTech = minTechLevel;
    }

    protected void setValues(ShipType type) {
        int typeInt = type.castToInt();

        this.type = type;
        size = Consts.ShipSpecs[typeInt].size;
        cargoBays = Consts.ShipSpecs[typeInt].cargoBays;
        weaponSlots = Consts.ShipSpecs[typeInt].weaponSlots;
        shieldSlots = Consts.ShipSpecs[typeInt].shieldSlots;
        gadgetSlots = Consts.ShipSpecs[typeInt].gadgetSlots;
        crewQuarters = Consts.ShipSpecs[typeInt].crewQuarters;
        fuelTanks = Consts.ShipSpecs[typeInt].fuelTanks;
        fuelCost = Consts.ShipSpecs[typeInt].fuelCost;
        hullStrength = Consts.ShipSpecs[typeInt].hullStrength;
        repairCost = Consts.ShipSpecs[typeInt].repairCost;
        price = Consts.ShipSpecs[typeInt].price;
        occurrence = Consts.ShipSpecs[typeInt].occurrence;
        police = Consts.ShipSpecs[typeInt].police;
        pirates = Consts.ShipSpecs[typeInt].pirates;
        traders = Consts.ShipSpecs[typeInt].traders;
        minTech = Consts.ShipSpecs[typeInt].minTech;
        hullUpgraded = Consts.ShipSpecs[typeInt].hullUpgraded;
        imageIndex = Consts.ShipSpecs[typeInt].imageIndex;
    }

    int getSlotsCount(EquipmentType type) {
        switch (type) {
            case WEAPON: return getWeaponSlots();
            case SHIELD: return getShieldSlots();
            case GADGET: return getGadgetSlots();
            default: return 0;
        }
    }

    public void updateCustomImageOffsetConstants() {
        Image image = GuiEngine.getImageProvider().getCustomShipImages()[0];
        int custIndex = ShipType.CUSTOM.castToInt();

        // Find the first column of pixels that has a non-white pixel for the X
        // value, and the last column for the width.
        int x = Graphics.getColumnOfFirstNonWhitePixel(image, 1);
        int width = Graphics.getColumnOfFirstNonWhitePixel(image, -1) - x + 1;
        Consts.ShipImageOffsets[custIndex].setX(Math.max(2, x));
        Consts.ShipImageOffsets[custIndex].setWidth(Math.min(62 - Consts.ShipImageOffsets[custIndex].getX(), width));
    }

    public int getCargoBays() {
        return cargoBays;
    }

    public void setCargoBays(int cargoBays) {
        this.cargoBays = cargoBays;
    }

    public int getFuelTanks() {
        return fuelTanks;
    }

    public void setFuelTanks(int fuelTanks) {
        this.fuelTanks = fuelTanks;
    }

    public int getWeaponSlots() {
        return weaponSlots;
    }

    public void setWeaponSlots(int weaponSlots) {
        this.weaponSlots = weaponSlots;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getShieldSlots() {
        return shieldSlots;
    }

    public void setShieldSlots(int shieldSlots) {
        this.shieldSlots = shieldSlots;
    }

    public int getRepairCost() {
        return repairCost;
    }

    void setRepairCost(int repairCost) {
        this.repairCost = repairCost;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getHullUpgraded() {
        return hullUpgraded;
    }

    public void setHullUpgraded(boolean hullUpgraded) {
        this.hullUpgraded = hullUpgraded;
    }

    public int getGadgetSlots() {
        return gadgetSlots;
    }

    public void setGadgetSlots(int gadgetSlots) {
        this.gadgetSlots = gadgetSlots;
    }

    public int getFuelCost() {
        return fuelCost;
    }

    void setFuelCost(int fuelCost) {
        this.fuelCost = fuelCost;
    }

    public int getCrewQuarters() {
        return crewQuarters;
    }

    public void setCrewQuarters(int crewQuarters) {
        this.crewQuarters = crewQuarters;
    }

    public int getHullStrength() {
        return hullStrength + (getHullUpgraded() ? Consts.HullUpgrade : 0);
    }

    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
    }

    public Image getImage() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetNormal];
    }

    public Image getImageDamaged() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetDamage];
    }

    public Image getImageDamagedWithShields() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetShieldDamage];
    }

    public int getImageIndex() {
        return (imageIndex == Consts.ShipImgUseDefault ? getType().castToInt() : imageIndex);
    }

    public void setImageIndex(int value) {
        this.imageIndex = (value == getType().castToInt() ? Consts.ShipImgUseDefault : value);
    }

    public Image getImageWithShields() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetShield];
    }

    public TechLevel getMinimumTechLevel() {
        return minTech;
    }

    public String getName() {
        return Strings.ShipNames[getType().castToInt()];
    }

    int getOccurrence() {
        return occurrence;
    }

    public Activity getPolice() {
        return police;
    }

    public Activity getPirates() {
        return pirates;
    }

    Activity getTraders() {
        return traders;
    }

    public ShipType getType() {
        return type;
    }

    public void setType(ShipType type) {
        this.type = type;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public void setPolice(Activity police) {
        this.police = police;
    }

    public void setPirates(Activity pirates) {
        this.pirates = pirates;
    }

    public void setTraders(Activity traders) {
        this.traders = traders;
    }

    public TechLevel getMinTech() {
        return minTech;
    }

    public void setMinTech(TechLevel minTech) {
        this.minTech = minTech;
    }

    public boolean isHullUpgraded() {
        return hullUpgraded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShipSpec)) return false;
        ShipSpec shipSpec = (ShipSpec) o;
        return cargoBays == shipSpec.cargoBays &&
                weaponSlots == shipSpec.weaponSlots &&
                shieldSlots == shipSpec.shieldSlots &&
                gadgetSlots == shipSpec.gadgetSlots &&
                crewQuarters == shipSpec.crewQuarters &&
                fuelTanks == shipSpec.fuelTanks &&
                fuelCost == shipSpec.fuelCost &&
                hullStrength == shipSpec.hullStrength &&
                repairCost == shipSpec.repairCost &&
                price == shipSpec.price &&
                occurrence == shipSpec.occurrence &&
                hullUpgraded == shipSpec.hullUpgraded &&
                imageIndex == shipSpec.imageIndex &&
                type == shipSpec.type &&
                size == shipSpec.size &&
                police == shipSpec.police &&
                pirates == shipSpec.pirates &&
                traders == shipSpec.traders &&
                minTech == shipSpec.minTech;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, size, cargoBays, weaponSlots, shieldSlots, gadgetSlots, crewQuarters, fuelTanks,
                fuelCost, hullStrength, repairCost, price, occurrence, police, pirates, traders, minTech, hullUpgraded, imageIndex);
    }
}
