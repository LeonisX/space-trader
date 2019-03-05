package spacetrader.game;

import spacetrader.controls.Rectangle;
import spacetrader.game.enums.*;
import spacetrader.util.Path;

public class Consts {

    // Price paid by government for each negative PoliceScore point
    public static final int BountyModifier = 1000;
    public static final int GalaxyWidth = 154;
    public static final int GalaxyHeight = 110;
    public static final int MinDistance = 7;
    public static final int CloseDistance = 13;
    public static final int MaxRange = 20;
    public static final int WormDist = 25;
    public static final int DebtWarning = 75000;
    public static final int DebtTooLarge = 100000;
    public static final double InsRate = 0.0025;
    public static final double IntRate = 0.1;
    public static final int MaxNoClaim = 90;
    public static final int SkillBonus = 3;
    public static final int CloakBonus = 2;
    public static final int MaximalSkillPointsOnStart = 20;
    public static final int AvailableSkillPointsOnStart = 16;
    public static final int MaxSkill = 10;
    public static final int StartClicks = 20;
    public static final int MaxFuelTanks = 20;
    public static final int FuelCompactorTanks = 3;
    public static final int MaxShip = 9;
    public static final int MaxSlots = 5;
    public static final int FleaConversionCost = 500;
    public static final int PodTransferCost = 200;

    public static final int ImagesPerShip = 4;
    public static final int ShipImgOffsetNormal = 0;
    public static final int ShipImgOffsetDamage = 1;
    public static final int ShipImgOffsetShield = 2;
    public static final int ShipImgOffsetShieldDamage = 3;
    public static final int ShipImgUseDefault = -1;

    public static final int EncounterImgAlien = 0;
    public static final int EncounterImgPirate = 1;
    public static final int EncounterImgPolice = 2;
    public static final int EncounterImgSpecial = 3;
    public static final int EncounterImgTrader = 4;

    public static final int StoryProbability = 50 / 8;
    public static final int FabricRipInitialProbability = 25;

    public static final int DirectionUp = 0;
    public static final int DirectionDown = 1;
    public static final int DirectionLeft = 2;
    public static final int DirectionRight = 3;

    public static final int DisruptorSystemsMultiplier = 3;

    public static final int PoliceRecordScorePsychopath = -100;
    public static final int PoliceRecordScoreVillain = -70;
    public static final int PoliceRecordScoreCriminal = -30;
    public static final int PoliceRecordScoreCrook = -10;
    public static final int PoliceRecordScoreDubious = -5;
    public static final int PoliceRecordScoreClean = 0;
    public static final int PoliceRecordScoreLawful = 5;
    public static final int PoliceRecordScoreTrusted = 10;
    public static final int PoliceRecordScoreLiked = 25;
    public static final int PoliceRecordScoreHero = 75;

    public static final int ReputationScoreHarmless = 0;
    public static final int ReputationScoreMostlyHarmless = 10;
    public static final int ReputationScorePoor = 20;
    public static final int ReputationScoreAverage = 40;
    public static final int ReputationScoreAboveAverage = 80;
    public static final int ReputationScoreCompetent = 150;
    public static final int ReputationScoreDangerous = 300;
    public static final int ReputationScoreDeadly = 600;
    public static final int ReputationScoreElite = 1500;

    public static final int ScoreAttackPirate = 0;
    public static final int ScoreAttackPolice = -3;
    public static final int ScoreAttackTrader = -2;

    //public static final int ScoreCaughtWithWild = -4;
    public static final int ScoreFleePolice = -2;
    public static final int ScoreKillPirate = 1;
    public static final int ScoreKillPolice = -6;
    public static final int ScoreKillTrader = -4;
    public static final int ScorePlunderPirate = -1;
    public static final int ScorePlunderTrader = -2;
    public static final int ScoreTrafficking = -1;

    public static final String ShipTemplateSeparator = "----------------------------";

    // Directory structure and File Constants.
    public static String BaseDirectory = System.getProperty("user.dir");
    public static String CustomDirectory = Path.combine(BaseDirectory, "custom");
    public static String DataDirectory = Path.combine(BaseDirectory, "data");
    public static String SaveDirectory = Path.combine(BaseDirectory, "save");
    public static String CustomImagesDirectory = Path.combine(CustomDirectory, "images");
    public static String CustomTemplatesDirectory = Path.combine(CustomDirectory, "templates");
    public static String HighScoreFile = Path.combine(DataDirectory, "HighScores.bin");
    public static String DefaultSettingsFile = Path.combine(DataDirectory, "DefaultSettings.bin");

    // TODO many of these can become enums.
    public static Gadget[] Gadgets = new Gadget[]{
            // 5 extra holds
            new Gadget(GadgetType.EXTRA_CARGO_BAYS, SkillType.NA, 2500, TechLevel.EARLY_INDUSTRIAL, 35),
            // Increases engineer's efficiency
            new Gadget(GadgetType.AUTO_REPAIR_SYSTEM, SkillType.ENGINEER, 7500, TechLevel.INDUSTRIAL, 20),
            // Increases pilot's efficiency
            new Gadget(GadgetType.NAVIGATING_SYSTEM, SkillType.PILOT, 15000, TechLevel.POST_INDUSTRIAL, 20),
            // Increases fighter's efficiency
            new Gadget(GadgetType.TARGETING_SYSTEM, SkillType.FIGHTER, 25000, TechLevel.POST_INDUSTRIAL, 20),
            // If you have a good engineer, neither pirates nor police will notice you
            new Gadget(GadgetType.CLOAKING_DEVICE, SkillType.PILOT, 100000, TechLevel.HI_TECH, 5),
            // The gadgets below can't be bought
            new Gadget(GadgetType.FUEL_COMPACTOR, SkillType.NA, 30000, TechLevel.UNAVAILABLE, 0),
            new Gadget(GadgetType.HIDDEN_CARGO_BAYS, SkillType.NA, 60000, TechLevel.UNAVAILABLE, 0)
    };

    static PoliceRecord[] PoliceRecords = new PoliceRecord[]{
            new PoliceRecord(PoliceRecordType.PSYCHOPATH, PoliceRecordScorePsychopath),
            new PoliceRecord(PoliceRecordType.VILLAIN, PoliceRecordScoreVillain),
            new PoliceRecord(PoliceRecordType.CRIMINAL, PoliceRecordScoreCriminal),
            new PoliceRecord(PoliceRecordType.CROOK, PoliceRecordScoreCrook),
            new PoliceRecord(PoliceRecordType.DUBIOUS, PoliceRecordScoreDubious),
            new PoliceRecord(PoliceRecordType.CLEAN, PoliceRecordScoreClean),
            new PoliceRecord(PoliceRecordType.LAWFUL, PoliceRecordScoreLawful),
            new PoliceRecord(PoliceRecordType.TRUSTED, PoliceRecordScoreTrusted),
            new PoliceRecord(PoliceRecordType.LIKED, PoliceRecordScoreLiked),
            new PoliceRecord(PoliceRecordType.HERO, PoliceRecordScoreHero)
    };

    public static PoliticalSystem[] PoliticalSystems = new PoliticalSystem[]{
            new PoliticalSystem(PoliticalSystemType.ANARCHY, 0, Activity.ABSENT, Activity.SWARMS, Activity.MINIMAL,
                    TechLevel.PRE_AGRICULTURAL, TechLevel.INDUSTRIAL, 7, true, true, TradeItemType.FOOD),
            new PoliticalSystem(PoliticalSystemType.CAPITALIST, 2, Activity.SOME, Activity.FEW, Activity.SWARMS,
                    TechLevel.EARLY_INDUSTRIAL, TechLevel.HI_TECH, 1, true, true, TradeItemType.ORE),
            new PoliticalSystem(PoliticalSystemType.COMMUNIST, 6, Activity.ABUNDANT, Activity.MODERATE,
                    Activity.MODERATE, TechLevel.AGRICULTURAL, TechLevel.INDUSTRIAL, 5, true, true, TradeItemType.NA),
            new PoliticalSystem(PoliticalSystemType.CONFEDERACY, 5, Activity.MODERATE, Activity.SOME, Activity.MANY,
                    TechLevel.AGRICULTURAL, TechLevel.POST_INDUSTRIAL, 3, true, true, TradeItemType.Games),
            new PoliticalSystem(PoliticalSystemType.CORPORATE, 2, Activity.ABUNDANT, Activity.FEW, Activity.SWARMS,
                    TechLevel.EARLY_INDUSTRIAL, TechLevel.HI_TECH, 2, true, true, TradeItemType.ROBOTS),
            new PoliticalSystem(PoliticalSystemType.CYBERNETIC, 0, Activity.SWARMS, Activity.SWARMS, Activity.MANY,
                    TechLevel.POST_INDUSTRIAL, TechLevel.HI_TECH, 0, false, false, TradeItemType.ORE),
            new PoliticalSystem(PoliticalSystemType.DEMOCRACY, 4, Activity.SOME, Activity.FEW, Activity.MANY,
                    TechLevel.RENAISSANCE, TechLevel.HI_TECH, 2, true, true, TradeItemType.Games),
            new PoliticalSystem(PoliticalSystemType.DICTATORSHIP, 3, Activity.MODERATE, Activity.MANY, Activity.SOME,
                    TechLevel.PRE_AGRICULTURAL, TechLevel.HI_TECH, 2, true, true, TradeItemType.NA),
            new PoliticalSystem(PoliticalSystemType.FASCIST, 7, Activity.SWARMS, Activity.SWARMS, Activity.MINIMAL,
                    TechLevel.EARLY_INDUSTRIAL, TechLevel.HI_TECH, 0, false, true, TradeItemType.MACHINES),
            new PoliticalSystem(PoliticalSystemType.FEUDAL, 1, Activity.MINIMAL, Activity.ABUNDANT, Activity.FEW,
                    TechLevel.PRE_AGRICULTURAL, TechLevel.RENAISSANCE, 6, true, true, TradeItemType.FIREARMS),
            new PoliticalSystem(PoliticalSystemType.MILITARY, 7, Activity.SWARMS, Activity.ABSENT, Activity.ABUNDANT,
                    TechLevel.MEDIEVAL, TechLevel.HI_TECH, 0, false, true, TradeItemType.ROBOTS),
            new PoliticalSystem(PoliticalSystemType.MONARCHY, 3, Activity.MODERATE, Activity.SOME, Activity.MODERATE,
                    TechLevel.PRE_AGRICULTURAL, TechLevel.INDUSTRIAL, 4, true, true, TradeItemType.MEDICINE),
            new PoliticalSystem(PoliticalSystemType.PACIFIST, 7, Activity.FEW, Activity.MINIMAL, Activity.MANY,
                    TechLevel.PRE_AGRICULTURAL, TechLevel.RENAISSANCE, 1, true, false, TradeItemType.NA),
            new PoliticalSystem(PoliticalSystemType.SOCIALIST, 4, Activity.FEW, Activity.MANY, Activity.SOME,
                    TechLevel.PRE_AGRICULTURAL, TechLevel.INDUSTRIAL, 6, true, true, TradeItemType.NA),
            new PoliticalSystem(PoliticalSystemType.SATORI, 0, Activity.MINIMAL, Activity.MINIMAL, Activity.MINIMAL,
                    TechLevel.PRE_AGRICULTURAL, TechLevel.AGRICULTURAL, 0, false, false, TradeItemType.NA),
            new PoliticalSystem(PoliticalSystemType.TECHNOCRACY, 1, Activity.ABUNDANT, Activity.SOME, Activity.ABUNDANT,
                    TechLevel.EARLY_INDUSTRIAL, TechLevel.HI_TECH, 2, true, true, TradeItemType.WATER),
            new PoliticalSystem(PoliticalSystemType.THEOCRACY, 5, Activity.ABUNDANT, Activity.MINIMAL,
                    Activity.MODERATE, TechLevel.PRE_AGRICULTURAL, TechLevel.EARLY_INDUSTRIAL, 0, true, true,
                    TradeItemType.NARCOTICS)
    };

    static Reputation[] Reputations = new Reputation[]{
            new Reputation(ReputationType.HARMLESS, ReputationScoreHarmless),
            new Reputation(ReputationType.MOSTLY_HARMLESS, ReputationScoreMostlyHarmless),
            new Reputation(ReputationType.POOR, ReputationScorePoor),
            new Reputation(ReputationType.AVERAGE, ReputationScoreAverage),
            new Reputation(ReputationType.ABOVE_AVERAGE, ReputationScoreAboveAverage),
            new Reputation(ReputationType.COMPETENT, ReputationScoreCompetent),
            new Reputation(ReputationType.DANGEROUS, ReputationScoreDangerous),
            new Reputation(ReputationType.DEADLY, ReputationScoreDeadly),
            new Reputation(ReputationType.ELITE, ReputationScoreElite)
    };

    public static Shield[] Shields = new Shield[]{
            new Shield(ShieldType.ENERGY, 100, 5000, TechLevel.INDUSTRIAL, 70),
            new Shield(ShieldType.REFLECTIVE, 200, 20000, TechLevel.POST_INDUSTRIAL, 30),
            // The shields below cannot be bought
            new Shield(ShieldType.LIGHTNING, 350, 45000, TechLevel.UNAVAILABLE, 0)
    };

    public static Rectangle[] ShipImageOffsets = new Rectangle[]{
            // We only care about X and Width, so set Y and Height to 0.
            new Rectangle(22, 0, 19, 0), // Flea
            new Rectangle(18, 0, 27, 0), // Gnat
            new Rectangle(18, 0, 27, 0), // Firefly
            new Rectangle(18, 0, 27, 0), // Mosquito
            new Rectangle(12, 0, 40, 0), // Bumblebee
            new Rectangle(12, 0, 40, 0), // Beetle
            new Rectangle(7, 0, 50, 0), // Hornet
            new Rectangle(7, 0, 50, 0), // Grasshopper
            new Rectangle(2, 0, 60, 0), // Termite
            new Rectangle(2, 0, 60, 0), // Wasp
            new Rectangle(15, 0, 34, 0), // Mantis
            new Rectangle(2, 0, 60, 0) // Custom
    };

    public static ShipSpec[] ShipSpecs = new ShipSpec[]{
            // Type Size Bays W S G Cr F FC Hull RC Price % Police Pirates
            // Traders MinTechLevel
            new ShipSpec(ShipType.FLEA, Size.TINY, 10, 0, 0, 0, 1, 20, 1, 25, 1, 2000, 2, Activity.NA, Activity.NA,
                    Activity.ABSENT, TechLevel.EARLY_INDUSTRIAL),
            new ShipSpec(ShipType.GNAT, Size.SMALL, 15, 1, 0, 1, 1, 14, 1, 100, 2, 10000, 28, Activity.ABSENT,
                    Activity.ABSENT, Activity.ABSENT, TechLevel.INDUSTRIAL),
            new ShipSpec(ShipType.FIREFLY, Size.SMALL, 20, 1, 1, 1, 1, 17, 1, 100, 3, 25000, 20, Activity.ABSENT,
                    Activity.ABSENT, Activity.ABSENT, TechLevel.INDUSTRIAL),
            new ShipSpec(ShipType.MOSQUITO, Size.SMALL, 15, 2, 1, 1, 1, 13, 1, 100, 5, 30000, 20, Activity.ABSENT,
                    Activity.MINIMAL, Activity.ABSENT, TechLevel.INDUSTRIAL),
            new ShipSpec(ShipType.BUMBLEBEE, Size.MEDIUM, 25, 1, 2, 2, 2, 15, 1, 100, 7, 60000, 15, Activity.MINIMAL,
                    Activity.MINIMAL, Activity.ABSENT, TechLevel.INDUSTRIAL),
            new ShipSpec(ShipType.BEETLE, Size.MEDIUM, 50, 0, 1, 1, 3, 14, 1, 50, 10, 80000, 3, Activity.NA,
                    Activity.NA, Activity.ABSENT, TechLevel.INDUSTRIAL),
            new ShipSpec(ShipType.HORNET, Size.LARGE, 20, 3, 2, 1, 2, 16, 2, 150, 15, 100000, 6, Activity.FEW,
                    Activity.SOME, Activity.MINIMAL, TechLevel.POST_INDUSTRIAL),
            new ShipSpec(ShipType.GRASSHOPPER, Size.LARGE, 30, 2, 2, 3, 3, 15, 3, 150, 15, 150000, 2, Activity.SOME,
                    Activity.MODERATE, Activity.FEW, TechLevel.POST_INDUSTRIAL),
            new ShipSpec(ShipType.TERMITE, Size.HUGE, 60, 1, 3, 2, 3, 13, 4, 200, 20, 225000, 2, Activity.MODERATE,
                    Activity.MANY, Activity.SOME, TechLevel.HI_TECH),
            new ShipSpec(ShipType.WASP, Size.HUGE, 35, 3, 2, 2, 3, 14, 5, 200, 20, 300000, 2, Activity.MANY,
                    Activity.ABUNDANT, Activity.MODERATE, TechLevel.HI_TECH),
            // The ships below can't be bought (mostly)
            new ShipSpec(ShipType.MANTIS, Size.MEDIUM, 0, 3, 1, 3, 3, 1, 1, 300, 1, 500000, 0, Activity.NA, Activity.NA,
                    Activity.NA, TechLevel.UNAVAILABLE),
            new ShipSpec(ShipType.CUSTOM, Size.HUGE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Activity.NA, Activity.NA,
                    Activity.NA, TechLevel.UNAVAILABLE),
            new ShipSpec(ShipType.QUEST, Size.HUGE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Activity.NA, Activity.NA,
                    Activity.NA, TechLevel.UNAVAILABLE)
    };

    public static Shipyard[] Shipyards = new Shipyard[]{
            new Shipyard(ShipyardId.CORELLIAN, Size.LARGE, ShipyardSkill.CREW_QUARTERS),
            new Shipyard(ShipyardId.INCOM, Size.MEDIUM, ShipyardSkill.SHIELD_SLOT_UNITS),
            new Shipyard(ShipyardId.KUAT, Size.HUGE, ShipyardSkill.HULL_PER_UNIT),
            new Shipyard(ShipyardId.SIENAR, Size.TINY, ShipyardSkill.WEAPON_SLOT_UNITS),
            new Shipyard(ShipyardId.SOROSUUB, Size.SMALL, ShipyardSkill.FUEL_BASE)
    };

    public static TradeItem[] TradeItems = new TradeItem[]{
            new TradeItem(TradeItemType.WATER, TechLevel.PRE_AGRICULTURAL, TechLevel.PRE_AGRICULTURAL,
                    TechLevel.MEDIEVAL, 30, 3, 4, SystemPressure.DROUGHT, SpecialResource.SWEET_OCEANS,
                    SpecialResource.DESERT, 30, 50, 1),
            new TradeItem(TradeItemType.FURS, TechLevel.PRE_AGRICULTURAL, TechLevel.PRE_AGRICULTURAL,
                    TechLevel.PRE_AGRICULTURAL, 250, 10, 10, SystemPressure.COLD, SpecialResource.RICH_FAUNA,
                    SpecialResource.LIFELESS, 230, 280, 5),
            new TradeItem(TradeItemType.FOOD, TechLevel.AGRICULTURAL, TechLevel.PRE_AGRICULTURAL,
                    TechLevel.AGRICULTURAL, 100, 5, 5, SystemPressure.CROP_FAILURE, SpecialResource.RICH_SOIL,
                    SpecialResource.POOR_SOIL, 90, 160, 5),
            new TradeItem(TradeItemType.ORE, TechLevel.MEDIEVAL, TechLevel.MEDIEVAL, TechLevel.RENAISSANCE, 350, 20, 10,
                    SystemPressure.WAR, SpecialResource.MINERAL_RICH, SpecialResource.MINERAL_POOR, 350, 420, 10),
            new TradeItem(TradeItemType.Games, TechLevel.RENAISSANCE, TechLevel.AGRICULTURAL, TechLevel.POST_INDUSTRIAL,
                    250, -10, 5, SystemPressure.BOREDOM, SpecialResource.ARTISTIC, SpecialResource.NA, 160, 270, 5),
            new TradeItem(TradeItemType.FIREARMS, TechLevel.RENAISSANCE, TechLevel.AGRICULTURAL, TechLevel.INDUSTRIAL,
                    1250, -75, 100, SystemPressure.WAR, SpecialResource.WARLIKE, SpecialResource.NA, 600, 1100, 25),
            new TradeItem(TradeItemType.MEDICINE, TechLevel.EARLY_INDUSTRIAL, TechLevel.AGRICULTURAL,
                    TechLevel.POST_INDUSTRIAL, 650, -20, 10, SystemPressure.PLAGUE, SpecialResource.SPECIAL_HERBS,
                    SpecialResource.NA, 400, 700, 25),
            new TradeItem(TradeItemType.MACHINES, TechLevel.EARLY_INDUSTRIAL, TechLevel.RENAISSANCE,
                    TechLevel.INDUSTRIAL, 900, -30, 5, SystemPressure.EMPLOYMENT, SpecialResource.NA,
                    SpecialResource.NA, 600, 800, 25),
            new TradeItem(TradeItemType.NARCOTICS, TechLevel.INDUSTRIAL, TechLevel.PRE_AGRICULTURAL,
                    TechLevel.INDUSTRIAL, 3500, -125, 150, SystemPressure.BOREDOM, SpecialResource.WEIRD_MUSHROOMS,
                    SpecialResource.NA, 2000, 3000, 50),
            new TradeItem(TradeItemType.ROBOTS, TechLevel.POST_INDUSTRIAL, TechLevel.EARLY_INDUSTRIAL,
                    TechLevel.HI_TECH, 5000, -150, 100, SystemPressure.EMPLOYMENT, SpecialResource.NA,
                    SpecialResource.NA, 3500, 5000, 100)
    };

    public static Weapon[] Weapons = new Weapon[]{
            new Weapon(WeaponType.PULSE_LASER, 15, false, 2000, TechLevel.INDUSTRIAL, 50),
            new Weapon(WeaponType.BEAM_LASER, 25, false, 12500, TechLevel.POST_INDUSTRIAL, 35),
            new Weapon(WeaponType.MILITARY_LASER, 35, false, 35000, TechLevel.HI_TECH, 15),
            new Weapon(WeaponType.MORGANS_LASER, 85, false, 50000, TechLevel.UNAVAILABLE, 0),
            new Weapon(WeaponType.PHOTON_DISRUPTOR, 20, true, 15000, TechLevel.POST_INDUSTRIAL, 0),
            new Weapon(WeaponType.QUANTUM_DISRUPTOR, 60, true, 50000, TechLevel.UNAVAILABLE, 0)
    };

    // This comes at the end because it depends on other Constant Arrays
    public static Equipment[] EquipmentForSale = new Equipment[]{Weapons[WeaponType.PULSE_LASER.castToInt()],
            Weapons[WeaponType.BEAM_LASER.castToInt()], Weapons[WeaponType.MILITARY_LASER.castToInt()],
            Weapons[WeaponType.PHOTON_DISRUPTOR.castToInt()], Shields[ShieldType.ENERGY.castToInt()],
            Shields[ShieldType.REFLECTIVE.castToInt()], Gadgets[GadgetType.EXTRA_CARGO_BAYS.castToInt()],
            Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()], Gadgets[GadgetType.NAVIGATING_SYSTEM.castToInt()],
            Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()], Gadgets[GadgetType.CLOAKING_DEVICE.castToInt()]
    };
}
