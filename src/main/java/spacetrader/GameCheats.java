package spacetrader;

import java.util.Iterator;

import spacetrader.enums.AlertType;
import spacetrader.enums.CrewMemberId;
import spacetrader.enums.VeryRareEncounter;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.CheatCode;
import spacetrader.util.Util;

// TODO removes refs to gui...
@CheatCode
public class GameCheats
{
	private final Game game;
	boolean cheatMode = false;

	public GameCheats(Game game)
	{
		this.game = game;
	}

	/**
	 * @return false if did anything, true if didn't recognized word (i.e., need to search for a system).
	 */
	public boolean ConsiderCheat(String[] words, GameController controller)
	{
		Ship ship = game.Commander().getShip();

		String first = words.length > 0 ? words[0] : "";
		String second = words.length > 1 ? words[1] : "";
		String third = words.length > 2 ? words[2] : "";
		int num1 = Functions.IsInt(second) ? Integer.parseInt(second) : 0;
		int num2 = Functions.IsInt(third) ? Integer.parseInt(third) : 0;

		if (cheatMode)
		{

			switch (SomeStringsForCheatSwitch.find(first))
			{
			case Bazaar:
				game.setChanceOfTradeInOrbit(Math.max(0, Math.min(1000, num1)));
				break;
			case Cover:
				if (num1 >= 0 && num1 < ship.Shields().length && num2 >= 0 && num2 < Consts.Shields.length)
					ship.Shields()[num1] = (Shield)Consts.Shields[num2].Clone();
				break;
			case DeLorean:
				game.Commander().setDays(Math.max(0, num1));
				break;
			case Diamond:
				ship.setHullUpgraded(!ship.getHullUpgraded());
				break;
			case Energize:
				game.setCanSuperWarp(!game.getCanSuperWarp());
				break;
			case Events:
				if (second == "Reset")
					game.ResetVeryRareEncounters();
				else
				{
					String text = "";
					for (Iterator<VeryRareEncounter> list = game.VeryRareEncounters().iterator(); list.hasNext();)
						text += Strings.VeryRareEncounters[list.next().CastToInt()] + Strings.newline;
					text = text.trim();

					GuiFacade.alert(AlertType.Alert, "Remaining Very Rare Encounters", text);
				}
				break;
			case Fame:
				game.Commander().setReputationScore(Math.max(0, num1));
				break;
			case Go:
				game.setSelectedSystemByName(second);
				if (game.SelectedSystem().Name().toLowerCase().equals(second.toLowerCase()))
				{
					controller.autoSave_depart();

					game.WarpDirect();

					controller.autoSave_arive();
				}
				break;
			case Ice:
			{
				switch (SomeStringsForCheatSwitch.find(second))
				{
				case Pirate:
					game.Commander().setKillsPirate(Math.max(0, num2));
					break;
				case Police:
					game.Commander().setKillsPolice(Math.max(0, num2));
					break;
				case Trader:
					game.Commander().setKillsTrader(Math.max(0, num2));
					break;
				}
			}
				break;
			case Indemnity:
				game.Commander().NoClaim(Math.max(0, num1));
				break;
			case IOU:
				game.Commander().setDebt(Math.max(0, num1));
				break;
			case Iron:
				if (num1 >= 0 && num1 < ship.Weapons().length && num2 >= 0 && num2 < Consts.Weapons.length)
					ship.Weapons()[num1] = (Weapon)Consts.Weapons[num2].Clone();
				break;
			case Juice:
				ship.setFuel(Math.max(0, Math.min(ship.FuelTanks(), num1)));
				break;
			case Knack:
				if (num1 >= 0 && num1 < game.Mercenaries().length)
				{
					String[] skills = third.split(",");
					for (int i = 0; i < game.Mercenaries()[num1].Skills().length && i < skills.length; i++)
					{
						if (Functions.IsInt(skills[i]))
							game.Mercenaries()[num1].Skills()[i] = Math.max(1, Math.min(Consts.MaxSkill, Integer
									.parseInt(skills[i])));
					}
				}
				break;
			case L_Engle:
				game.setFabricRipProbability(Math.max(0, Math.min(Consts.FabricRipInitialProbability, num1)));
				break;
			case LifeBoat:
				ship.setEscapePod(!ship.getEscapePod());
				break;
			case MonsterCom:
				GuiFacade.performMonsterCom();
				break;
			case PlanB:
				game.setAutoSave(true);
				break;
			case Posse:
				if (num1 > 0 && num1 < ship.Crew().length && num2 > 0 && num2 < game.Mercenaries().length
						&& !Util.ArrayContains(Consts.SpecialCrewMemberIds, (CrewMemberId.FromInt(num2))))
				{
					int skill = ship.Trader();
					ship.Crew()[num1] = game.Mercenaries()[num2];
					if (ship.Trader() != skill)
						game.RecalculateBuyPrices(game.Commander().getCurrentSystem());
				}
				break;
			case RapSheet:
				game.Commander().setPoliceRecordScore(num1);
				break;
			case Rarity:
				game.setChanceOfVeryRareEncounter(Math.max(0, Math.min(1000, num1)));
				break;
			case Scratch:
				game.Commander().setCash(Math.max(0, num1));
				break;
			case Skin:
				ship.setHull(Math.max(0, Math.min(ship.HullStrength(), num1)));
				break;
			case Status:
			{
				switch (SomeStringsForCheatSwitch.find(second))
				{
				case Artifact:
					game.setQuestStatusArtifact(Math.max(0, num2));
					break;
				case Dragonfly:
					game.setQuestStatusDragonfly(Math.max(0, num2));
					break;
				case Experiment:
					game.setQuestStatusExperiment(Math.max(0, num2));
					break;
				case Gemulon:
					game.setQuestStatusGemulon(Math.max(0, num2));
					break;
				case Japori:
					game.setQuestStatusJapori(Math.max(0, num2));
					break;
				case Jarek:
					game.setQuestStatusJarek(Math.max(0, num2));
					break;
				case Moon:
					game.setQuestStatusMoon(Math.max(0, num2));
					break;
				case Reactor:
					game.setQuestStatusReactor(Math.max(0, num2));
					break;
				case Princess:
					game.setQuestStatusPrincess(Math.max(0, num2));
					break;
				case Scarab:
					game.setQuestStatusScarab(Math.max(0, num2));
					break;
				case Sculpture:
					game.setQuestStatusSculpture(Math.max(0, num2));
					break;
				case SpaceMonster:
					game.setQuestStatusSpaceMonster(Math.max(0, num2));
					break;
				case Wild:
					game.setQuestStatusWild(Math.max(0, num2));
					break;
				default:
					String text = "Artifact: " + game.getQuestStatusArtifact() + Strings.newline + "Dragonfly: "
							+ game.getQuestStatusDragonfly() + Strings.newline + "Experiment: "
							+ game.getQuestStatusExperiment() + Strings.newline + "Gemulon: "
							+ game.getQuestStatusGemulon() + Strings.newline + "Japori: " + game.getQuestStatusJapori()
							+ Strings.newline + "Jarek: " + game.getQuestStatusJarek() + Strings.newline + "Moon: "
							+ game.getQuestStatusMoon() + Strings.newline + "Princess: "
							+ game.getQuestStatusPrincess() + Strings.newline + "Reactor: "
							+ game.getQuestStatusReactor() + Strings.newline + "Scarab: " + game.getQuestStatusScarab()
							+ Strings.newline + "Sculpture: " + game.getQuestStatusSculpture() + Strings.newline
							+ "SpaceMonster: " + game.getQuestStatusSpaceMonster() + Strings.newline + "Wild: "
							+ game.getQuestStatusWild();

				GuiFacade.alert(AlertType.Alert, "Status of Quests", text);
					break;
				}
			}
				break;
			case Swag:
				if (num1 >= 0 && num1 < ship.Cargo().length)
					ship.Cargo()[num1] = Math.max(0, Math.min(ship.FreeCargoBays() + ship.Cargo()[num1], num2));
				break;
			case Test:
				GuiFacade.performTestForm();
				break;
			case Tool:
				if (num1 >= 0 && num1 < ship.Gadgets().length && num2 >= 0 && num2 < Consts.Gadgets.length)
					ship.Gadgets()[num1] = (Gadget)Consts.Gadgets[num2].Clone();
				break;
			case Varmints:
				ship.setTribbles(Math.max(0, num1));
				break;
			case Yellow:
				game.setEasyEncounters(true);
				break;
			default:
				return true;
			}
		} else
		{
			switch (SomeStringsForCheatSwitch.find(first))
			{
			case Cheetah:
				GuiFacade.alert(AlertType.Cheater);
				cheatMode = true;
				break;
			default:
				return true;
			}
		}
		return false;
	}
}
