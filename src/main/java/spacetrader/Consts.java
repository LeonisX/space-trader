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
package spacetrader;

import jwinforms.Rectangle;
import spacetrader.enums.*;
import spacetrader.util.Environment;
import util.Path;

public class Consts
{
	// #region Individual Constants

	// Directory structure and File Constsants.
	public static String BaseDirectory = Environment.CurrentDirectory;
	public static String CustomDirectory = Path
			.Combine(BaseDirectory, "custom");
	public static String DataDirectory = Path.Combine(BaseDirectory, "data");
	public static String SaveDirectory = Path.Combine(BaseDirectory, "save");

	public static String CustomImagesDirectory = Path.Combine(CustomDirectory,
			"images");
	public static String CustomTemplatesDirectory = Path.Combine(
			CustomDirectory, "templates");

	public static String HighScoreFile = Path.Combine(DataDirectory,
			"HighScores.bin");
	public static String DefaultSettingsFile = Path.Combine(DataDirectory,
			"DefaultSettings.bin");

	public static final String CurrentVersion = "2.00";

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

	public static final int MaxSkill = 10;

	public static final int StartClicks = 20;
	public static final int MaxFuelTanks = 20;
	public static final int FuelCompactorTanks = 3;
	public static final int HullUpgrade = 50;
	public static final int MaxShip = 9;
	public static final int MaxSlots = 5;
	public static final int FleaConversionCost = 500;
	public static final int PodTransferCost = 200;

	public static final int ImagesPerShip = 4;
	public static final int ShipImgOffsetNormal = 0;
	public static final int ShipImgOffsetDamage = 1;
	public static final int ShipImgOffsetShield = 2;
	public static final int ShipImgOffsetSheildDamage = 3;
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

	public static final int MaxTribbles = 100000;

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
	public static final int ScoreCaughtWithWild = -4;
	public static final int ScoreFleePolice = -2;
	public static final int ScoreKillCaptain = 100;
	public static final int ScoreKillPirate = 1;
	public static final int ScoreKillPolice = -6;
	public static final int ScoreKillTrader = -4;
	public static final int ScorePlunderPirate = -1;
	public static final int ScorePlunderTrader = -2;
	public static final int ScoreTrafficking = -1;

	public static final String ShipTemplateSeparator = "----------------------------";
	// #endregion

	// #region Constant Arrays

	// TODO many of these can become enums.
	// #region Gadgets
	public static Gadget[] Gadgets = new Gadget[] {
			new Gadget(GadgetType.ExtraCargoBays, SkillType.NA, 2500,
					TechLevel.EarlyIndustrial, 35), // 5 extra holds
			new Gadget(GadgetType.AutoRepairSystem, SkillType.Engineer, 7500,
					TechLevel.Industrial, 20), // Increases engineer's
												// effectivity
			new Gadget(GadgetType.NavigatingSystem, SkillType.Pilot, 15000,
					TechLevel.PostIndustrial, 20), // Increases pilot's
													// effectivity
			new Gadget(GadgetType.TargetingSystem, SkillType.Fighter, 25000,
					TechLevel.PostIndustrial, 20), // Increases fighter's
													// effectivity
			new Gadget(GadgetType.CloakingDevice, SkillType.Pilot, 100000,
					TechLevel.HiTech, 5), // If you have a good engineer,
											// neither pirates nor police will
											// notice you
			// The gadgets below can't be bought
			new Gadget(GadgetType.FuelCompactor, SkillType.NA, 30000,
					TechLevel.Unavailable, 0),
			new Gadget(GadgetType.HiddenCargoBays, SkillType.NA, 60000,
					TechLevel.Unavailable, 0) };
	// #endregion

	// #region PoliceRecords
	public static PoliceRecord[] PoliceRecords = new PoliceRecord[] {
			new PoliceRecord(PoliceRecordType.Psychopath,
					PoliceRecordScorePsychopath),
			new PoliceRecord(PoliceRecordType.Villain, PoliceRecordScoreVillain),
			new PoliceRecord(PoliceRecordType.Criminal,
					PoliceRecordScoreCriminal),
			new PoliceRecord(PoliceRecordType.Crook, PoliceRecordScoreCrook),
			new PoliceRecord(PoliceRecordType.Dubious, PoliceRecordScoreDubious),
			new PoliceRecord(PoliceRecordType.Clean, PoliceRecordScoreClean),
			new PoliceRecord(PoliceRecordType.Lawful, PoliceRecordScoreLawful),
			new PoliceRecord(PoliceRecordType.Trusted, PoliceRecordScoreTrusted),
			new PoliceRecord(PoliceRecordType.Liked, PoliceRecordScoreLiked),
			new PoliceRecord(PoliceRecordType.Hero, PoliceRecordScoreHero) };
	// #endregion

	// #region PoliticalSystems
	public static PoliticalSystem[] PoliticalSystems = new PoliticalSystem[] {
			new PoliticalSystem(PoliticalSystemType.Anarchy, 0,
					Activity.Absent, Activity.Swarms, Activity.Minimal,
					TechLevel.PreAgricultural, TechLevel.Industrial, 7, true,
					true, TradeItemType.Food),
			new PoliticalSystem(PoliticalSystemType.Capitalist, 2,
					Activity.Some, Activity.Few, Activity.Swarms,
					TechLevel.EarlyIndustrial, TechLevel.HiTech, 1, true, true,
					TradeItemType.Ore),
			new PoliticalSystem(PoliticalSystemType.Communist, 6,
					Activity.Abundant, Activity.Moderate, Activity.Moderate,
					TechLevel.Agricultural, TechLevel.Industrial, 5, true,
					true, TradeItemType.NA),
			new PoliticalSystem(PoliticalSystemType.Confederacy, 5,
					Activity.Moderate, Activity.Some, Activity.Many,
					TechLevel.Agricultural, TechLevel.PostIndustrial, 3, true,
					true, TradeItemType.Games),
			new PoliticalSystem(PoliticalSystemType.Corporate, 2,
					Activity.Abundant, Activity.Few, Activity.Swarms,
					TechLevel.EarlyIndustrial, TechLevel.HiTech, 2, true, true,
					TradeItemType.Robots),
			new PoliticalSystem(PoliticalSystemType.Cybernetic, 0,
					Activity.Swarms, Activity.Swarms, Activity.Many,
					TechLevel.PostIndustrial, TechLevel.HiTech, 0, false,
					false, TradeItemType.Ore),
			new PoliticalSystem(PoliticalSystemType.Democracy, 4,
					Activity.Some, Activity.Few, Activity.Many,
					TechLevel.Renaissance, TechLevel.HiTech, 2, true, true,
					TradeItemType.Games),
			new PoliticalSystem(PoliticalSystemType.Dictatorship, 3,
					Activity.Moderate, Activity.Many, Activity.Some,
					TechLevel.PreAgricultural, TechLevel.HiTech, 2, true, true,
					TradeItemType.NA),
			new PoliticalSystem(PoliticalSystemType.Fascist, 7,
					Activity.Swarms, Activity.Swarms, Activity.Minimal,
					TechLevel.EarlyIndustrial, TechLevel.HiTech, 0, false,
					true, TradeItemType.Machines),
			new PoliticalSystem(PoliticalSystemType.Feudal, 1,
					Activity.Minimal, Activity.Abundant, Activity.Few,
					TechLevel.PreAgricultural, TechLevel.Renaissance, 6, true,
					true, TradeItemType.Firearms),
			new PoliticalSystem(PoliticalSystemType.Military, 7,
					Activity.Swarms, Activity.Absent, Activity.Abundant,
					TechLevel.Medieval, TechLevel.HiTech, 0, false, true,
					TradeItemType.Robots),
			new PoliticalSystem(PoliticalSystemType.Monarchy, 3,
					Activity.Moderate, Activity.Some, Activity.Moderate,
					TechLevel.PreAgricultural, TechLevel.Industrial, 4, true,
					true, TradeItemType.Medicine),
			new PoliticalSystem(PoliticalSystemType.Pacifist, 7, Activity.Few,
					Activity.Minimal, Activity.Many, TechLevel.PreAgricultural,
					TechLevel.Renaissance, 1, true, false, TradeItemType.NA),
			new PoliticalSystem(PoliticalSystemType.Socialist, 4, Activity.Few,
					Activity.Many, Activity.Some, TechLevel.PreAgricultural,
					TechLevel.Industrial, 6, true, true, TradeItemType.NA),
			new PoliticalSystem(PoliticalSystemType.Satori, 0,
					Activity.Minimal, Activity.Minimal, Activity.Minimal,
					TechLevel.PreAgricultural, TechLevel.Agricultural, 0,
					false, false, TradeItemType.NA),
			new PoliticalSystem(PoliticalSystemType.Technocracy, 1,
					Activity.Abundant, Activity.Some, Activity.Abundant,
					TechLevel.EarlyIndustrial, TechLevel.HiTech, 2, true, true,
					TradeItemType.Water),
			new PoliticalSystem(PoliticalSystemType.Theocracy, 5,
					Activity.Abundant, Activity.Minimal, Activity.Moderate,
					TechLevel.PreAgricultural, TechLevel.EarlyIndustrial, 0,
					true, true, TradeItemType.Narcotics) };
	// #endregion

	// #region Reputations
	public static Reputation[] Reputations = new Reputation[] {
			new Reputation(ReputationType.Harmless, ReputationScoreHarmless),
			new Reputation(ReputationType.MostlyHarmless,
					ReputationScoreMostlyHarmless),
			new Reputation(ReputationType.Poor, ReputationScorePoor),
			new Reputation(ReputationType.Average, ReputationScoreAverage),
			new Reputation(ReputationType.AboveAverage,
					ReputationScoreAboveAverage),
			new Reputation(ReputationType.Competent, ReputationScoreCompetent),
			new Reputation(ReputationType.Dangerous, ReputationScoreDangerous),
			new Reputation(ReputationType.Deadly, ReputationScoreDeadly),
			new Reputation(ReputationType.Elite, ReputationScoreElite) };
	// #endregion

	// #region Shields
	public static Shield[] Shields = new Shield[] {
			new Shield(ShieldType.Energy, 100, 5000, TechLevel.Industrial, 70),
			new Shield(ShieldType.Reflective, 200, 20000,
					TechLevel.PostIndustrial, 30),
			// The weapons below cannot be bought
			new Shield(ShieldType.Lightning, 350, 45000, TechLevel.Unavailable,
					0) };
	// #endregion

	// #region ShipImageOffsets
	public static Rectangle[] ShipImageOffsets = new Rectangle[] {
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
			new Rectangle(7, 0, 49, 0), // Space Monster
			new Rectangle(21, 0, 22, 0), // Dragonfly
			new Rectangle(15, 0, 34, 0), // Mantis
			new Rectangle(7, 0, 49, 0), // Scarab
			new Rectangle(9, 0, 46, 0), // Bottle
			new Rectangle(2, 0, 60, 0), // Custom
			new Rectangle(2, 0, 60, 0) // Scorpion
	};
	// #endregion

	// #region ShipSpecs
	public static ShipSpec[] ShipSpecs = new ShipSpec[] {
			// Type Size Bays W S G Cr F FC Hull RC Price % Police Pirates
			// Traders MinTechLevel
			new ShipSpec(ShipType.Flea, Size.Tiny, 10, 0, 0, 0, 1, 20, 1, 25,
					1, 2000, 2, Activity.NA, Activity.NA, Activity.Absent,
					TechLevel.EarlyIndustrial),
			new ShipSpec(ShipType.Gnat, Size.Small, 15, 1, 0, 1, 1, 14, 1, 100,
					2, 10000, 28, Activity.Absent, Activity.Absent,
					Activity.Absent, TechLevel.Industrial),
			new ShipSpec(ShipType.Firefly, Size.Small, 20, 1, 1, 1, 1, 17, 1,
					100, 3, 25000, 20, Activity.Absent, Activity.Absent,
					Activity.Absent, TechLevel.Industrial),
			new ShipSpec(ShipType.Mosquito, Size.Small, 15, 2, 1, 1, 1, 13, 1,
					100, 5, 30000, 20, Activity.Absent, Activity.Minimal,
					Activity.Absent, TechLevel.Industrial),
			new ShipSpec(ShipType.Bumblebee, Size.Medium, 25, 1, 2, 2, 2, 15,
					1, 100, 7, 60000, 15, Activity.Minimal, Activity.Minimal,
					Activity.Absent, TechLevel.Industrial),
			new ShipSpec(ShipType.Beetle, Size.Medium, 50, 0, 1, 1, 3, 14, 1,
					50, 10, 80000, 3, Activity.NA, Activity.NA,
					Activity.Absent, TechLevel.Industrial),
			new ShipSpec(ShipType.Hornet, Size.Large, 20, 3, 2, 1, 2, 16, 2,
					150, 15, 100000, 6, Activity.Few, Activity.Some,
					Activity.Minimal, TechLevel.PostIndustrial),
			new ShipSpec(ShipType.Grasshopper, Size.Large, 30, 2, 2, 3, 3, 15,
					3, 150, 15, 150000, 2, Activity.Some, Activity.Moderate,
					Activity.Few, TechLevel.PostIndustrial),
			new ShipSpec(ShipType.Termite, Size.Huge, 60, 1, 3, 2, 3, 13, 4,
					200, 20, 225000, 2, Activity.Moderate, Activity.Many,
					Activity.Some, TechLevel.HiTech),
			new ShipSpec(ShipType.Wasp, Size.Huge, 35, 3, 2, 2, 3, 14, 5, 200,
					20, 300000, 2, Activity.Many, Activity.Abundant,
					Activity.Moderate, TechLevel.HiTech),
			// The ships below can't be bought (mostly)
			new ShipSpec(ShipType.SpaceMonster, Size.Huge, 0, 3, 0, 0, 1, 1, 1,
					500, 1, 500000, 0, Activity.NA, Activity.NA, Activity.NA,
					TechLevel.Unavailable),
			new ShipSpec(ShipType.Dragonfly, Size.Small, 0, 2, 3, 2, 1, 1, 1,
					10, 1, 500000, 0, Activity.NA, Activity.NA, Activity.NA,
					TechLevel.Unavailable),
			new ShipSpec(ShipType.Mantis, Size.Medium, 0, 3, 1, 3, 3, 1, 1,
					300, 1, 500000, 0, Activity.NA, Activity.NA, Activity.NA,
					TechLevel.Unavailable),
			new ShipSpec(ShipType.Scarab, Size.Large, 20, 2, 0, 0, 2, 1, 1,
					400, 1, 500000, 0, Activity.NA, Activity.NA, Activity.NA,
					TechLevel.Unavailable),
			new ShipSpec(ShipType.Bottle, Size.Small, 0, 0, 0, 0, 0, 1, 1, 10,
					1, 100, 0, Activity.NA, Activity.NA, Activity.NA,
					TechLevel.Unavailable),
			new ShipSpec(ShipType.Custom, Size.Huge, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, Activity.NA, Activity.NA, Activity.NA,
					TechLevel.Unavailable),
			new ShipSpec(ShipType.Scorpion, Size.Huge, 30, 2, 2, 2, 2, 1, 1,
					300, 1, 500000, 0, Activity.NA, Activity.NA, Activity.NA,
					TechLevel.Unavailable) };
	// #endregion

	// #region Shipyards
	public static Shipyard[] Shipyards = new Shipyard[] {
			new Shipyard(ShipyardId.Corellian, Size.Large,
					ShipyardSkill.CrewQuarters),
			new Shipyard(ShipyardId.Incom, Size.Medium,
					ShipyardSkill.ShieldSlotUnits),
			new Shipyard(ShipyardId.Kuat, Size.Huge, ShipyardSkill.HullPerUnit),
			new Shipyard(ShipyardId.Sienar, Size.Tiny,
					ShipyardSkill.WeaponSlotUnits),
			new Shipyard(ShipyardId.Sorosuub, Size.Small,
					ShipyardSkill.FuelBase) };
	//TODO was ArrayList
	public static CrewMemberId[] SpecialCrewMemberIds =// new ArrayList(
			new CrewMemberId[] { CrewMemberId.Commander,
					CrewMemberId.Dragonfly, CrewMemberId.FamousCaptain,
					CrewMemberId.Jarek, CrewMemberId.Opponent,
					CrewMemberId.Princess, CrewMemberId.Scarab,
					CrewMemberId.Scorpion, CrewMemberId.SpaceMonster,
					CrewMemberId.Wild };

	public static SpecialEvent[] SpecialEvents = new SpecialEvent[] {
			new SpecialEvent(SpecialEventType.Artifact, 0, 1, false),
			new SpecialEvent(SpecialEventType.ArtifactDelivery, -20000, 0, true),
			new SpecialEvent(SpecialEventType.CargoForSale, 1000, 3, false),
			new SpecialEvent(SpecialEventType.Dragonfly, 0, 1, true),
			new SpecialEvent(SpecialEventType.DragonflyBaratas, 0, 0, true),
			new SpecialEvent(SpecialEventType.DragonflyDestroyed, 0, 0, true),
			new SpecialEvent(SpecialEventType.DragonflyMelina, 0, 0, true),
			new SpecialEvent(SpecialEventType.DragonflyRegulas, 0, 0, true),
			new SpecialEvent(SpecialEventType.DragonflyShield, 0, 0, false),
			new SpecialEvent(SpecialEventType.EraseRecord, 5000, 3, false),
			new SpecialEvent(SpecialEventType.Experiment, 0, 0, true),
			new SpecialEvent(SpecialEventType.ExperimentFailed, 0, 0, true),
			new SpecialEvent(SpecialEventType.ExperimentStopped, 0, 0, true),
			new SpecialEvent(SpecialEventType.Gemulon, 0, 0, true),
			new SpecialEvent(SpecialEventType.GemulonFuel, 0, 0, false),
			new SpecialEvent(SpecialEventType.GemulonInvaded, 0, 0, true),
			new SpecialEvent(SpecialEventType.GemulonRescued, 0, 0, true),
			new SpecialEvent(SpecialEventType.Japori, 0, 1, false),
			new SpecialEvent(SpecialEventType.JaporiDelivery, 0, 0, true),
			new SpecialEvent(SpecialEventType.Jarek, 0, 1, false),
			new SpecialEvent(SpecialEventType.JarekGetsOut, 0, 0, true),
			new SpecialEvent(SpecialEventType.Lottery, -1000, 0, true),
			new SpecialEvent(SpecialEventType.Moon, 500000, 4, false),
			new SpecialEvent(SpecialEventType.MoonRetirement, 0, 0, false),
			new SpecialEvent(SpecialEventType.Reactor, 0, 0, false),
			new SpecialEvent(SpecialEventType.ReactorDelivered, 0, 0, true),
			new SpecialEvent(SpecialEventType.ReactorLaser, 0, 0, false),
			new SpecialEvent(SpecialEventType.Scarab, 0, 1, true),
			new SpecialEvent(SpecialEventType.ScarabDestroyed, 0, 0, true),
			new SpecialEvent(SpecialEventType.ScarabUpgradeHull, 0, 0, false),
			new SpecialEvent(SpecialEventType.Skill, 3000, 3, false),
			new SpecialEvent(SpecialEventType.SpaceMonster, 0, 1, true),
			new SpecialEvent(SpecialEventType.SpaceMonsterKilled, -15000, 0,
					true),
			new SpecialEvent(SpecialEventType.Tribble, 1000, 1, false),
			new SpecialEvent(SpecialEventType.TribbleBuyer, 0, 3, false),
			new SpecialEvent(SpecialEventType.Wild, 0, 1, false),
			new SpecialEvent(SpecialEventType.WildGetsOut, 0, 0, true),
			new SpecialEvent(SpecialEventType.Sculpture, -2000, 0, false),
			new SpecialEvent(SpecialEventType.SculptureDelivered, 0, 0, true),
			new SpecialEvent(SpecialEventType.SculptureHiddenBays, 0, 0, false),
			new SpecialEvent(SpecialEventType.Princess, 0, 0, true),
			new SpecialEvent(SpecialEventType.PrincessCentauri, 0, 0, true),
			new SpecialEvent(SpecialEventType.PrincessInthara, 0, 0, true),
			new SpecialEvent(SpecialEventType.PrincessQonos, 0, 0, false),
			new SpecialEvent(SpecialEventType.PrincessQuantum, 0, 0, false),
			new SpecialEvent(SpecialEventType.PrincessReturned, 0, 0, true) };
	// #endregion

	// #region TradeItems
	public static TradeItem[] TradeItems = new TradeItem[] {
			new TradeItem(TradeItemType.Water, TechLevel.PreAgricultural,
					TechLevel.PreAgricultural, TechLevel.Medieval, 30, 3, 4,
					SystemPressure.Drought, SpecialResource.SweetOceans,
					SpecialResource.Desert, 30, 50, 1),
			new TradeItem(TradeItemType.Furs, TechLevel.PreAgricultural,
					TechLevel.PreAgricultural, TechLevel.PreAgricultural, 250,
					10, 10, SystemPressure.Cold, SpecialResource.RichFauna,
					SpecialResource.Lifeless, 230, 280, 5),
			new TradeItem(TradeItemType.Food, TechLevel.Agricultural,
					TechLevel.PreAgricultural, TechLevel.Agricultural, 100, 5,
					5, SystemPressure.CropFailure, SpecialResource.RichSoil,
					SpecialResource.PoorSoil, 90, 160, 5),
			new TradeItem(TradeItemType.Ore, TechLevel.Medieval,
					TechLevel.Medieval, TechLevel.Renaissance, 350, 20, 10,
					SystemPressure.War, SpecialResource.MineralRich,
					SpecialResource.MineralPoor, 350, 420, 10),
			new TradeItem(TradeItemType.Games, TechLevel.Renaissance,
					TechLevel.Agricultural, TechLevel.PostIndustrial, 250, -10,
					5, SystemPressure.Boredom, SpecialResource.Artistic,
					SpecialResource.NA, 160, 270, 5),
			new TradeItem(TradeItemType.Firearms, TechLevel.Renaissance,
					TechLevel.Agricultural, TechLevel.Industrial, 1250, -75,
					100, SystemPressure.War, SpecialResource.Warlike,
					SpecialResource.NA, 600, 1100, 25),
			new TradeItem(TradeItemType.Medicine, TechLevel.EarlyIndustrial,
					TechLevel.Agricultural, TechLevel.PostIndustrial, 650, -20,
					10, SystemPressure.Plague, SpecialResource.SpecialHerbs,
					SpecialResource.NA, 400, 700, 25),
			new TradeItem(TradeItemType.Machines, TechLevel.EarlyIndustrial,
					TechLevel.Renaissance, TechLevel.Industrial, 900, -30, 5,
					SystemPressure.Employment, SpecialResource.NA,
					SpecialResource.NA, 600, 800, 25),
			new TradeItem(TradeItemType.Narcotics, TechLevel.Industrial,
					TechLevel.PreAgricultural, TechLevel.Industrial, 3500,
					-125, 150, SystemPressure.Boredom,
					SpecialResource.WeirdMushrooms, SpecialResource.NA, 2000,
					3000, 50),
			new TradeItem(TradeItemType.Robots, TechLevel.PostIndustrial,
					TechLevel.EarlyIndustrial, TechLevel.HiTech, 5000, -150,
					100, SystemPressure.Employment, SpecialResource.NA,
					SpecialResource.NA, 3500, 5000, 100) };
	// #endregion

	// #region Weapons
	public static Weapon[] Weapons = new Weapon[] {
			new Weapon(WeaponType.PulseLaser, 15, false, 2000,
					TechLevel.Industrial, 50),
			new Weapon(WeaponType.BeamLaser, 25, false, 12500,
					TechLevel.PostIndustrial, 35),
			new Weapon(WeaponType.MilitaryLaser, 35, false, 35000,
					TechLevel.HiTech, 15),
			new Weapon(WeaponType.MorgansLaser, 85, false, 50000,
					TechLevel.Unavailable, 0),
			new Weapon(WeaponType.PhotonDisruptor, 20, true, 15000,
					TechLevel.PostIndustrial, 0),
			new Weapon(WeaponType.QuantumDistruptor, 60, true, 50000,
					TechLevel.Unavailable, 0) };
	// #endregion

	// #region EquipmentForSale (This comes at the end because it depends on
	// other Constant Arrays)
	public static Equipment[] EquipmentForSale = new Equipment[] {
			Weapons[WeaponType.PulseLaser.CastToInt()],
			Weapons[WeaponType.BeamLaser.CastToInt()],
			Weapons[WeaponType.MilitaryLaser.CastToInt()],
			Weapons[WeaponType.PhotonDisruptor.CastToInt()],
			Shields[ShieldType.Energy.CastToInt()],
			Shields[ShieldType.Reflective.CastToInt()],
			Gadgets[GadgetType.ExtraCargoBays.CastToInt()],
			Gadgets[GadgetType.AutoRepairSystem.CastToInt()],
			Gadgets[GadgetType.NavigatingSystem.CastToInt()],
			Gadgets[GadgetType.TargetingSystem.CastToInt()],
			Gadgets[GadgetType.CloakingDevice.CastToInt()] };
	// #endregion

	// #endregion
}
