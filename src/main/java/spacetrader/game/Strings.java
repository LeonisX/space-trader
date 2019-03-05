package spacetrader.game;

import java.util.HashMap;
import java.util.Map;

public class Strings {

    public static Map<String, String> pluralMap = new HashMap<String, String>() {{
        put("Cargo Bay2", "Cargo Bays");
        put("unit2", "units");
        put("click", "clicks");
        put("parsec2", "parsecs");
        put("credit2", "credits");
        put("day2", "days");
        put("container2", "containers");
    }};

    //TODO test FormMonster, ...
    public static String newline = "<BR>";//String.format("%n");

    public static String AlertsCancel = "Cancel";
    public static String AlertsNo = "No";
    public static String AlertsOk = "Ok";
    public static String AlertsYes = "Yes";
    public static String AlertsAlertTitle = "^1";
    public static String AlertsAlertMessage = "^2";
    public static String AlertsAppStartTitle = "Space Trader for Java 8";
    public static String AlertsArrivalBuyNewspaperTitle = "Buy Newspaper?";
    public static String AlertsArrivalBuyNewspaperMessage = "The local newspaper costs ^1. Do you wish to buy a copy?";
    public static String AlertsArrivalBuyNewspaperAccept = "Buy Newspaper";
    public static String AlertsArrivalIFFuelTitle = "No Full Tanks";
    public static String AlertsArrivalIFFuelMessage = "You do not have enough money to buy full tanks.";
    public static String AlertsArrivalIFFuelRepairsTitle = "Not Enough Money";
    public static String AlertsArrivalIFFuelRepairsMessage = "You don't have enough money to get a full tank or full hull repairs.";
    public static String AlertsArrivalIFNewspaperTitle = "Can't Afford it!";
    public static String AlertsArrivalIFNewspaperMessage = "Sorry! A newspaper costs ^1 in this system. You don't have enough money!";
    public static String AlertsArrivalIFRepairsTitle = "No Full Repairs";
    public static String AlertsArrivalIFRepairsMessage = "You don't have enough money to get your hull fully repaired.";
    public static String AlertsCargoIFTitle = "Not Enough Money";
    public static String AlertsCargoIFMessage = "You don't have enough money to spend on any of these goods.";
    public static String AlertsCargoNoEmptyBaysTitle = "No Empty Bays";
    public static String AlertsCargoNoEmptyBaysMessage = "You don't have any empty cargo holds available at the moment";
    public static String AlertsCargoNoneAvailableTitle = "Nothing Available";
    public static String AlertsCargoNoneAvailableMessage = "None of these goods are available.";
    public static String AlertsCargoNoneToSellTitle = "None To ^1";
    public static String AlertsCargoNoneToSellMessage = "You have none of these goods in your cargo bays.";
    public static String AlertsCargoNotInterestedTitle = "Not Interested";
    public static String AlertsCargoNotInterestedMessage = "Nobody in this system is interested in buying these goods.";
    public static String AlertsCargoNotSoldTitle = "Not Available";
    public static String AlertsCargoNotSoldMessage = "That item is not available in this system.";
    public static String AlertsChartJumpTitle = "Use Singularity?";
    public static String AlertsChartJumpMessage = "Do you wish to use the Portable Singularity to transport immediately to ^1?";
    public static String AlertsChartJumpAccept = "Use Singularity";
    public static String AlertsChartJumpCancel = "Don't use it";
    public static String AlertsChartJumpCurrentTitle = "Cannot Jump";
    public static String AlertsChartJumpCurrentMessage = "You are tracking the system where you are currently located. It's useless to jump to your current location.";
    public static String AlertsChartJumpNoSystemSelectedTitle = "No System Selected";
    public static String AlertsChartJumpNoSystemSelectedMessage = "To use the Portable Singularity, track a system before clicking on this button.";
    public static String AlertsChartTrackSystemTitle = "Track System?";
    public static String AlertsChartTrackSystemMessage = "^1?";
    public static String AlertsChartWormholeUnreachableTitle = "Wormhole Unreachable";
    public static String AlertsChartWormholeUnreachableMessage = "The wormhole to ^1 is only accessible from ^2.";
    public static String AlertsCheaterTitle = "Cheater!";
    public static String AlertsCheaterMessage = "Cheaters never prosper!  (Well, not with that command, anyway.)";
    public static String AlertsCrewFireMercenaryTitle = "Fire Mercenary";
    public static String AlertsCrewFireMercenaryMessage = "Are you sure you wish to fire ^1?";
    public static String AlertsCrewNoQuartersTitle = "No Quarters Available";
    public static String AlertsCrewNoQuartersMessage = "You don't have any crew quarters available for ^1.";
    public static String AlertsDebtNoBuyTitle = "You Have A Debt";
    public static String AlertsDebtNoBuyMessage = "You can't buy that as long as you have debts.";
    public static String AlertsDebtNoneTitle = "No Debt";
    public static String AlertsDebtNoneMessage = "You have no debts.";
    public static String AlertsDebtReminderTitle = "Loan Notification";
    public static String AlertsDebtReminderMessage = "The Bank's  Loan Officer reminds you that your debt continues to accrue interest. You currently owe ^1.";
    public static String AlertsDebtTooLargeGroundedTitle = "Large Debt";
    public static String AlertsDebtTooLargeGroundedMessage = "Your debt is too large.  You are not allowed to leave this system until your debt is lowered.";
    public static String AlertsDebtTooLargeLoanTitle = "Debt Too High";
    public static String AlertsDebtTooLargeLoanMessage = "Your debt is too high to get another loan.";
    public static String AlertsDebtTooLargeTradeTitle = "Large Debt";
    public static String AlertsDebtTooLargeTradeMessage = "Your debt is too large.  Nobody will trade with you.";
    public static String AlertsDebtWarningTitle = "Warning: Large Debt";
    public static String AlertsDebtWarningMessage = "Your debt is getting too large. Reduce it quickly or your ship will be put on a chain!";
    public static String AlertsEncounterArrestedTitle = "Arrested";
    public static String AlertsEncounterArrestedMessage = "You are arrested and taken to the space station, where you are brought before a court of law.";
    public static String AlertsEncounterAttackNoDisruptorsTitle = "No Disabling Weapons";
    public static String AlertsEncounterAttackNoDisruptorsMessage = "You have no disabling weapons! You would only be able to destroy your opponent, which would defeat the purpose of your quest.";
    public static String AlertsEncounterAttackNoLasersTitle = "No Hull-Damaging Weapons";
    public static String AlertsEncounterAttackNoLasersMessage = "You only have disabling weapons, but your opponent cannot be disabled!";
    public static String AlertsEncounterAttackNoWeaponsTitle = "No Weapons";
    public static String AlertsEncounterAttackNoWeaponsMessage = "You can't attack without weapons!";
    public static String AlertsEncounterAttackPoliceTitle = "Attack Police";
    public static String AlertsEncounterAttackPoliceMessage = "Are you sure you wish to attack the police? This will turn you into a criminal!";
    public static String AlertsEncounterAttackTraderTitle = "Attack Trader";
    public static String AlertsEncounterAttackTraderMessage = "Are you sure you wish to attack the trader? This will immediately set your police record to dubious!";
    public static String AlertsEncounterBothDestroyedTitle = "Both Destroyed";
    public static String AlertsEncounterBothDestroyedMessage = "You and your opponent have managed to destroy each other.";
    public static String AlertsEncounterDisabledOpponentTitle = "Opponent Disabled";
    public static String AlertsEncounterDisabledOpponentMessage = "You have disabled your opponent. Without life support they'll have to hibernate. You notify Space Corps, and they come and tow the ^1 to the planet, where the crew is revived and then arrested. ^2";
    public static String AlertsEncounterDumpAllTitle = "Dump All?";
    public static String AlertsEncounterDumpAllMessage = "You paid ^1 for these items. Are you sure you want to just dump them?";
    public static String AlertsEncounterDumpWarningTitle = "Space Littering";
    public static String AlertsEncounterDumpWarningMessage = "Dumping cargo in space is considered littering. If the police find your dumped goods and track them to you, this will influence your record. Do you really wish to dump?";
    public static String AlertsEncounterEscapedTitle = "Escaped";
    public static String AlertsEncounterEscapedMessage = "You have managed to escape your opponent.";
    public static String AlertsEncounterEscapedHitTitle = "You Escaped";
    public static String AlertsEncounterEscapedHitMessage = "You got hit, but still managed to escape.";
    public static String AlertsEncounterEscapePodActivatedTitle = "Escape Pod Activated";
    public static String AlertsEncounterEscapePodActivatedMessage = "Just before the final demise of your ship, your escape pod gets activated and ejects you. After a few days, the Space Corps picks you up and drops you off at a nearby space port.";
    public static String AlertsEncounterLootingTitle = "Looting";
    public static String AlertsEncounterLootingMessage = "The pirates board your ship and transfer as much of your cargo to their own ship as their cargo bays can hold.";
    public static String AlertsEncounterOpponentEscapedTitle = "Opponent Escaped";
    public static String AlertsEncounterOpponentEscapedMessage = "Your opponent has managed to escape.";
    public static String AlertsEncounterPiratesBountyTitle = "Bounty";
    public static String AlertsEncounterPiratesBountyMessage = "You ^1 the pirate ship^2 and earned a bounty of ^3.";
    public static String AlertsEncounterPiratesFindNoCargoTitle = "Pirates Find No Cargo";
    public static String AlertsEncounterPiratesFindNoCargoMessage = "The pirates are very angry that they find no cargo on your ship. To stop them from destroying you, you have no choice but to pay them an amount equal to 5% of your current worth - ^1.";
    public static String AlertsEncounterPoliceBribeTitle = "Bribe";
    public static String AlertsEncounterPoliceBribeMessage = "These police officers are willing to forego inspection for the amount of ^1.";
    public static String AlertsEncounterPoliceBribeAccept = "Offer Bribe";
    public static String AlertsEncounterPoliceBribeCancel = "Forget It";
    public static String AlertsEncounterPoliceBribeCantTitle = "No Bribe";
    public static String AlertsEncounterPoliceBribeCantMessage = "These police officers can't be bribed.";
    public static String AlertsEncounterPoliceBribeLowCashTitle = "Not Enough Cash";
    public static String AlertsEncounterPoliceBribeLowCashMessage = "You don't have enough cash for a bribe.";
    public static String AlertsEncounterPoliceFineTitle = "Caught";
    public static String AlertsEncounterPoliceFineMessage = "The police discovers illegal goods in your cargo holds. These goods impounded and you are fined ^1.";
    public static String AlertsEncounterPoliceNothingFoundTitle = "Nothing Found";
    public static String AlertsEncounterPoliceNothingFoundMessage = "The police find nothing illegal in your cargo holds, and apologize for the inconvenience.";
    public static String AlertsEncounterPoliceNothingIllegalTitle = "You Have Nothing Illegal";
    public static String AlertsEncounterPoliceNothingIllegalMessage = "Are you sure you want to do that? You are not carrying illegal goods, so you have nothing to fear!";
    public static String AlertsEncounterPoliceNothingIllegalAccept = "Yes, I still want to";
    public static String AlertsEncounterPoliceNothingIllegalCancel = "OK, I won't";
    public static String AlertsEncounterPoliceSubmitTitle = "You Have Illegal Goods";
    public static String AlertsEncounterPoliceSubmitMessage = "Are you sure you want to let the police search you? You are carrying ^1! ^2";
    public static String AlertsEncounterPoliceSubmitAccept = "Yes, let them";
    public static String AlertsEncounterPoliceSurrenderTitle = "Surrender";
    public static String AlertsEncounterPoliceSurrenderMessage = "^1If you surrender, you will spend some time in prison and will have to pay a hefty fine. ^2Are you sure you want to do that?";
    public static String AlertsEncounterScoopTitle = "Scoop Canister";
    public static String AlertsEncounterScoopMessage = "A canister from the destroyed ship, labeled ^1, drifts within range of your scoops.";
    public static String AlertsEncounterScoopAccept = "Pick It Up";
    public static String AlertsEncounterScoopCancel = "Let It Go";
    public static String AlertsEncounterScoopNoRoomTitle = "No Room To Scoop";
    public static String AlertsEncounterScoopNoRoomMessage = "You don't have any room in your cargo holds. Do you wish to jettison goods to make room, or just let it go.";
    public static String AlertsEncounterScoopNoRoomAccept = "Make Room";
    public static String AlertsEncounterScoopNoRoomCancel = "Let it go";
    public static String AlertsEncounterScoopNoScoopTitle = "No Scoop";
    public static String AlertsEncounterScoopNoScoopMessage = "You regret finding nothing in your holds that can be dumped, and let the canister go.";
    public static String AlertsEncounterSurrenderRefusedTitle = "To The Death!";
    public static String AlertsEncounterSurrenderRefusedMessage = "Surrender? Hah! We want your HEAD!";
    public static String AlertsEncounterTradeCompletedTitle = "Trade Completed";
    public static String AlertsEncounterTradeCompletedMessage = "Thanks for ^1 the ^2. It's been a pleasure doing business with you.";
    public static String AlertsEncounterYouLoseTitle = "You Lose";
    public static String AlertsEncounterYouLoseMessage = "Your ship has been destroyed by your opponent.";
    public static String AlertsEncounterYouWinTitle = "You Win";
    public static String AlertsEncounterYouWinMessage = "You have destroyed your opponent.";
    public static String AlertsEquipmentAlreadyOwnTitle = "You Already Have One";
    public static String AlertsEquipmentAlreadyOwnMessage = "It's not useful to buy more than one of this item.";
    public static String AlertsEquipmentBuyTitle = "Buy ^1";
    public static String AlertsEquipmentBuyMessage = "Do you wish to buy this item for ^2?";
    public static String AlertsEquipmentEscapePodTitle = "Escape Pod";
    public static String AlertsEquipmentEscapePodMessage = "Do you want to buy an escape pod for 2000 credits?";
    public static String AlertsEquipmentExtraBaysInUseTitle = "Cargo Bays Full";
    public static String AlertsEquipmentExtraBaysInUseMessage = "The extra cargo bays are still filled with goods. You can only sell them when they're empty.";
    public static String AlertsEquipmentIFTitle = "Not Enough Money";
    public static String AlertsEquipmentIFMessage = "You don't have enough money to spend on this item.";
    public static String AlertsEquipmentNotEnoughSlotsTitle = "Not Enough Slots";
    public static String AlertsEquipmentNotEnoughSlotsMessage = "You have already filled all of your available slots for this type of item.";
    public static String AlertsEquipmentSellTitle = "Sell Item";
    public static String AlertsEquipmentSellMessage = "Are you sure you want to sell this item?";
    public static String AlertsFileErrorOpenTitle = "Error";
    public static String AlertsFileErrorOpenMessage = "An error occurred while trying to open ^1." + Strings.newline + Strings.newline + "^2";
    public static String AlertsFileErrorSaveTitle = "Error";
    public static String AlertsFileErrorSaveMessage = "An error occurred while trying to save ^1." + Strings.newline + Strings.newline + "^2";
    public static String AlertsFleaBuiltTitle = "Flea Built";
    public static String AlertsFleaBuiltMessage = "In 3 days and with 500 credits, you manage to convert your pod into a Flea.";
    public static String AlertsGameAbandonConfirmTitle = "Are You Sure?";
    public static String AlertsGameAbandonConfirmMessage = "Are you sure you want to abandon your current game?";
    public static String AlertsGameClearHighScoresTitle = "Clear High Scores";
    public static String AlertsGameClearHighScoresMessage = "Are you sure you wish to clear the high score table?";
    public static String AlertsGameEndHighScoreAchievedTitle = "Congratulations!";
    public static String AlertsGameEndHighScoreAchievedMessage = "You have made the high-score list!";
    public static String AlertsGameEndHighScoreCheatTitle = "Naughty, Naughty!";
    public static String AlertsGameEndHighScoreCheatMessage = "You would have made the high-score list if you weren't a Cheat!.";
    public static String AlertsGameEndHighScoreMissedTitle = "Sorry";
    public static String AlertsGameEndHighScoreMissedMessage = "Alas! This is not enough to enter the high-score list.";
    public static String AlertsGameEndKilledTitle = "You Are Dead";
    public static String AlertsGameEndRetiredTitle = "You Have Retired";
    public static String AlertsGameEndScoreTitle = "Score";
    public static String AlertsGameEndScoreMessage = "You achieved a score of ^1.^2%.";
    public static String AlertsGameRetireTitle = "Retire";
    public static String AlertsGameRetireMessage = "Are you sure you wish to retire?";
    public static String AlertsInsuranceNoEscapePodTitle = "No Escape Pod";
    public static String AlertsInsuranceNoEscapePodMessage = "Insurance isn't useful for you, since you don't have an escape pod.";
    public static String AlertsInsurancePayoffTitle = "Insurance";
    public static String AlertsInsurancePayoffMessage = "Since your ship was insured, the bank pays you the total worth of the destroyed ship.";
    public static String AlertsInsuranceStopTitle = "Stop Insurance";
    public static String AlertsInsuranceStopMessage = "Do you really wish to stop your insurance and lose your no-claim?";
    public static String AlertsJailConvictedTitle = "Convicted";
    public static String AlertsJailConvictedMessage = "You are convicted to ^1 in prison and a fine of ^2.";
    public static String AlertsJailFleaReceivedTitle = "Flea Received";
    public static String AlertsJailFleaReceivedMessage = "When you leave prison, the police have left a second-hand Flea for you so you can continue your travels.";
    public static String AlertsJailIllegalGoodsImpoundedTitle = "Illegal Goods Impounded";
    public static String AlertsJailIllegalGoodsImpoundedMessage = "The police also impound all of the illegal goods you have on board.";
    public static String AlertsJailInsuranceLostTitle = "Insurance Lost";
    public static String AlertsJailInsuranceLostMessage = "Since you cannot pay your insurance while you're in prison, it is retracted.";
    public static String AlertsJailMercenariesLeaveTitle = "Mercenaries Leave";
    public static String AlertsJailMercenariesLeaveMessage = "Any mercenaries who were traveling with you have left.";
    public static String AlertsJailShipSoldTitle = "Ship Sold";
    public static String AlertsJailShipSoldMessage = "Because you don't have the credits to pay your fine, your ship is sold.";
    public static String AlertsLeavingIFInsuranceTitle = "Not Enough Money";
    public static String AlertsLeavingIFInsuranceMessage = "You don't have enough cash to pay for your insurance.";
    public static String AlertsLeavingIFMercenariesTitle = "Pay Mercenaries";
    public static String AlertsLeavingIFMercenariesMessage = "You don't have enough cash to pay your mercenaries to come with you on this trip. Fire them or make sure you have enough cash.";
    public static String AlertsLeavingIFWormholeTaxTitle = "Wormhole Tax";
    public static String AlertsLeavingIFWormholeTaxMessage = "You don't have enough money to pay for the wormhole tax.";
    public static String AlertsNewGameConfirmTitle = "New Game";
    public static String AlertsNewGameConfirmMessage = "Are you sure you wish to start a new game?";
    public static String AlertsNewGameMoreSkillPointsTitle = "More Skill Points";
    public static String AlertsNewGameMoreSkillPointsMessage = "You haven't awarded all 20 skill points yet.";
    public static String AlertsOptionsNoGameTitle = "No Game Active";
    public static String AlertsOptionsNoGameMessage = "You don't have a game open, so you can only change the default options.";
    public static String AlertsPreciousHiddenTitle = "Precious Cargo Hidden";
    public static String AlertsPreciousHiddenMessage = "You quickly hide ^1 in your hidden cargo bays before the pirates board your ship. This would never work with the police, but pirates are usually in more of a hurry.";
    public static String AlertsRegistryErrorTitle = "Error...";
    public static String AlertsRegistryErrorMessage = "Error accessing the Registry: ^1";
    public static String AlertsShipBuyConfirmTitle = "Buy New Ship";
    public static String AlertsShipBuyConfirmMessage = "Are you sure you wish to trade in your ^1 for a new ^2^3?";
    public static String AlertsShipBuyCrewQuartersTitle = "Too Many Crew Members";
    public static String AlertsShipBuyCrewQuartersMessage = "The new ship you picked doesn't have enough quarters for all of your crew members. First you will have to fire one or more of them.";
    public static String AlertsShipBuyIFTitle = "Not Enough Money";
    public static String AlertsShipBuyIFMessage = "You don't have enough money to buy this ship.";
    public static String AlertsShipBuyIFTransferTitle = "Not Enough Money";
    public static String AlertsShipBuyIFTransferMessage = "You won't have enough money to buy this ship and pay the cost to transfer all of your unique equipment. You should choose carefully which items you wish to transfer!";
    public static String AlertsShipBuyNoSlotsTitle = "Can't Transfer Item";
    public static String AlertsShipBuyNoSlotsMessage = "If you trade your ship in for a ^1, you won't be able to transfer your ^2 because the new ship has insufficient ^3 slots!";
    public static String AlertsShipBuyNotAvailableTitle = "Ship Not Available";
    public static String AlertsShipBuyNotAvailableMessage = "That type of ship is not available in the current system.";
    public static String AlertsShipBuyNoTransferTitle = "Can't Transfer Item";
    public static String AlertsShipBuyNoTransferMessage = "Unfortunately, if you make this trade, you won't be able to afford to transfer your ^1 to the new ship!";
    public static String AlertsShipBuyPassengerQuartersTitle = "Passenger Needs Quarters";
    public static String AlertsShipBuyPassengerQuartersMessage = "You must get a ship with enough crew quarters so that ^1 can stay on board.";
    public static String AlertsShipBuyTransferTitle = "Transfer ^1";
    public static String AlertsShipBuyTransferMessage = "I'll transfer your ^2 to your new ship for ^3.";
    public static String AlertsShipBuyTransferAccept = "Do it!";
    public static String AlertsShipBuyTransferCancel = "No thanks";
    public static String AlertsShipDesignIFTitle = "Not Enough Money";
    public static String AlertsShipDesignIFMessage = "You don't have enough money to create this design.";
    public static String AlertsShipDesignThanksTitle = "Thank you!";
    public static String AlertsShipDesignThanksMessage = "^1 thanks you for your business!";
    public static String AlertsSpecialIFTitle = "Not Enough Money";
    public static String AlertsSpecialIFMessage = "You don't have enough cash to spend to accept this offer.";
    public static String AlertsSpecialNoQuartersTitle = "No Free Quarters";
    public static String AlertsSpecialNoQuartersMessage = "There are currently no free crew quarters on your ship.";
    public static String AlertsSpecialNotEnoughBaysTitle = "Not Enough Bays";
    public static String AlertsSpecialNotEnoughBaysMessage = "You don't have enough empty cargo bays at the moment.";
    public static String AlertsSpecialPassengerOnBoardTitle = "Passenger On Board";
    public static String AlertsSpecialPassengerOnBoardMessage = "You have taken ^1 on board. While on board ^1 will lend you expertise, but may stop helping if the journey takes too long.";
    public static String AlertsTravelArrivalTitle = "Arrival";
    public static String AlertsTravelArrivalMessage = "You arrive at your destination.";
    public static String AlertsTravelUneventfulTripTitle = "Uneventful Trip";
    public static String AlertsTravelUneventfulTripMessage = "After an uneventful trip, you arrive at your destination.";

    //Units
    public static String CargoBay="Cargo Bay";
    public static String CargoUnit = "unit";
    public static String ContainerUnit = "container";
    public static String DistanceUnit = "parsec";
    public static String DistanceSubunit = "click";
    public static String MoneyUnit = "credit";
    public static String TimeUnit = "day";

    //Genitive (for English, the meaning does not change)
    public static String ContainerUnitGen = "container";
    public static String TimeUnitGen = "day";

    public static String BankInsuranceButtonBuy = "Buy Insurance";
    public static String BankInsuranceButtonStop = "Stop Insurance";
    public static String BankLoanStatementBorrow = "You can borrow up to ^1.";
    public static String BankLoanStatementDebt = "You have a debt of ^1.";

    public static String CargoAll = "All";
    public static String CargoBuyAfford = "You can afford to buy ^1.";
    public static String CargoBuyAvailable = "The trader has ^1 for sale.";
    public static String CargoBuying = "buying";
    public static String CargoBuyNA = "not sold";
    public static String CargoBuyQuestion = "How many do you want to ^1?";
    public static String CargoBuyStatement = "At ^1 each, you can buy up to ^2.";
    public static String CargoBuyStatementSteal = "Your victim has ^1 of these goods.";
    public static String CargoBuyStatementTrader = "The trader wants to sell ^1 for the price of ^2 each.";
    public static String CargoCredit = "cr.";
    public static String CargoDump = "Dump";
    public static String CargoLoss = "Your loss";
    public static String CargoMax = "Max";
    public static String CargoProfit = "Your profit";
    public static String CargoSellDumpCost = "It costs ^1 per unit for disposal.";
    public static String CargoSelling = "selling";
    public static String CargoSellNA = "no trade";
    public static String CargoSellPaid = "You paid about ^1 per unit.";
    public static String CargoSellPaidTrader = "You paid about ^1 per unit, and can sell ^2.";
    public static String CargoSellProfitPerUnit = "^1 per unit is ^2";
    public static String CargoSellQuestion = "How many do you want to ^1?";
    public static String CargoSellStatement = "You can sell up to ^1 at ^2 each.";
    public static String CargoSellStatementDump = "You can ^1 up to ^2.";
    public static String CargoSellStatementTrader = "The trader wants to buy ^1 and offers ^2 each.";
    public static String CargoTitle = "^1 ^2";

    public static String ChartDistance = "^1 to ^2.";

    public static String CheatsVeryRareEncountersRemaining = "Remaining Very Rare Encounters";
    public static String CheatsStatusOfQuests = "Status of Quests";

    public static String CommanderAnd = "and";
    public static String CommanderAngryKingpins = "Angry kingpins:";
    public static String CommanderBountyOffered = "Bounty offered:";

    public static String DockFuelCost = "A full tank costs ^1";
    public static String DockFuelFull = "Your tank is full.";
    public static String DockFuelStatus = "You have fuel to fly ^1.";
    public static String DockHullCost = "Full repairs will cost ^1";
    public static String DockHullFull = "No repairs are needed.";
    public static String DockHullStatus = "Your hull strength is at ^1%.";

    public static String EncounterActionCmdrChased = "The ^1 is still following you.";
    public static String EncounterActionCmdrHit = "The ^1 hits you.";
    public static String EncounterActionCmdrMissed = "The ^1 missed you.";
    public static String EncounterActionOppAttacks = "The ^1 attacks.";
    public static String EncounterActionOppChased = "The ^1 didn't get away.";
    public static String EncounterActionOppDisabled = "The ^1 has been disabled.";
    public static String EncounterActionOppFleeing = "The ^1 is fleeing.";
    public static String EncounterActionOppHit = "You hit the ^1.";
    public static String EncounterActionOppMissed = "You missed the ^1.";
    public static String EncounterActionOppSurrender = "The ^1 hails that they wish to surrender to you.";
    public static String EncounterHullStrength = "Hull at ^1%";
    public static String EncounterPiratesDestroyed = "destroyed";
    public static String EncounterPiratesDisabled = "disabled";
    public static String EncounterPiratesLocation = "(informing the police of the pirate's location)";
    public static String EncounterPoliceSubmitArrested = "You will be arrested!";
    public static String EncounterPoliceSubmitGoods = "illegal goods";
    public static String EncounterPoliceSurrenderCargo = "You have ^1 on board!";
    public static String EncounterPoliceSurrenderAction = "They will ^1. ";
    public static String EncounterPretextAlien = "an alien ^1";
    public static String EncounterPretextPirate = "a pirate ^1";
    public static String EncounterPretextPolice = "a police ^1";
    public static String EncounterPretextTrader = "a trader ^1";
    public static String EncounterShieldStrength = "Shields at ^1%";
    public static String EncounterShieldNone = "No Shields";
    public static String EncounterShipMantis = "alien ship";
    public static String EncounterShipPirate = "pirate ship";
    public static String EncounterShipPolice = "police ship";
    public static String EncounterShipTrader = "trader ship";
    public static String EncounterText = "At ^1 from ^2 you encounter ^3.";
    public static String EncounterTextOpponentAttack = "Your opponent attacks.";
    public static String EncounterTextOpponentFlee = "Your opponent is fleeing.";
    public static String EncounterTextOpponentIgnore = "It ignores you.";
    public static String EncounterTextOpponentNoNotice = "It doesn't notice you.";
    public static String EncounterTextPoliceInspection = "The police summon you to submit to an inspection.";
    public static String EncounterTextPoliceSurrender = "The police hail they want you to surrender.";
    public static String EncounterTextTrader = "You are hailed with an offer to trade goods.";

    public static String EquipmentFreeSlot = "FREE SLOT";
    public static String EquipmentNoneForSale = "None for sale";
    public static String EquipmentNoSlots = "No slots";
    public static String EquipmentNotForSale = "not for sale";

    public static String FileFormatBad = "The file is not a Space Trader for Java 8 file, or is the wrong version or has been corrupted.";
    public static String FileFutureVersion = "The version of the file is greater than the current version. You should upgrade to the latest version of Space Trader for Windows.";

    public static String HighScoreEmpty = "Empty";
    public static String HighScoreStatus = "^1 on ^2 day, worth ^3 on ^4 level.";   //Was killed

    public static String JettisonAll = "All";
    public static String JettisonBays = "Bays: ^1";

    public static String MercOnBoard = "Member of Crew (^1)";

    public static String Mercenaries = "mercenaries";
    public static String MercenariesForHire = "^1 available for hire.";
    public static String MercenaryFire = "Fire";
    public static String MercenaryHire = "Hire";

    public static String MoneyRateSuffix = "^1 daily";

    public static String NA = "N/A";

    public static String NewsShipyard = "Shipyard in ^1 System offers to design custom ships.";

    public static String OptionsKeepEmptyCargoBays = "to leave empty when buying goods in-system";

    public static String PersonnelVacancy = "Vacancy";
    public static String PointsRemaining = "remaining: ^1.";

    public static String QuestNone = "There are no open quests.";

    public static String ShipBuyGotOne = "got one";
    public static String ShipBuyTransfer = "and transfer your unique equipment to the new ship";

    public static String ShipBay = "bay";
    public static String ShipEquipment = "Equipment:";
    public static String ShipGadgetSlot = "gadget slot";
    public static String ShipShiedSlot = "shield slot";
    public static String ShipWeaponSlot = "weapon slot";
    public static String ShipUnfilled = "Unfilled:";

    public static String ShipInfoEscapePod = "Escape Pod";

    public static String ShipListBuy = "Buy";
    public static String ShipListInfo = "Info";

    public static String ShipNameCurrentShip = "<current ship>";
    public static String ShipNameCustomShip = "Custom Ship";
    public static String ShipNameModified = "<modified>";
    public static String ShipNameTemplateSuffixDefault = "(Default)";
    public static String ShipNameTemplateSuffixMinimum = "(Minimum)";

    public static String ShipyardEquipForSale = "There is equipment for sale.";
    public static String ShipyardEquipNoSale = "No equipment for sale.";
    public static String ShipyardPodCost = "You can buy an escape pod for 2,000 cr.";
    public static String ShipyardPodIF = "You need 2,000 cr. to buy an escape pod.";
    public static String ShipyardPodInstalled = "You have an escape pod installed.";
    public static String ShipyardPodNoSale = "No escape pods for sale.";
    public static String ShipyardShipForSale = "There are ships for sale.";
    public static String ShipyardShipNoSale = "No ships for sale.";
    public static String ShipyardSizeItem = "^1 (Max ^2)";
    public static String ShipyardTitle = "Ship Design at ^1 Shipyards";
    public static String ShipyardUnit = "Units";
    public static String ShipyardWarning = "Bear in mind that getting too close to the maximum number of units will result in a \"Crowding Penalty\" due to the engineering difficulty of squeezing everything in.  There is a modest penalty at 80%, and a more severe one at 90%.";
    public static String ShipyardWelcome = "Welcome to ^1 Shipyards! Our best engineer, ^2, is at your service.";

    public static String SpecialCargoNone = "No special items.";

    public static String StatusBarBays = "Bays:";
    public static String StatusBarCash = "Cash:";
    public static String StatusBarCurrentCosts = "Current Costs:";
    public static String StatusBarNoGameLoaded = "No Game Loaded.";

    public static String Unknown = "Unknown";

    // String Arrays

    public static String[] ActivityLevels = new String[]{"Absent", "Minimal",
            "Few", "Some", "Moderate", "Many", "Abundant", "Swarms"};

    public static String[] CargoBuyOps = new String[]{"Buy", "Buy", "Steal"};

    public static String[] CargoSellOps = new String[]{"Sell", "Sell", "Dump", "Jettison"};

    public static String[] CrewMemberNames = new String[]{"Commander",
            "Alyssa", "Armatur", "Bentos", "C2U2", "Chi'Ti", "Crystal", "Dane",
            "Deirdre", "Doc", "Draco", "Iranda", "Jeremiah", "Jujubal",
            "Krydon", "Luis", "Mercedez", "Milete", "Muri-L", "Mystyc",
            "Nandi", "Orestes", "Pancho", "PS37", "Quarck", "Sosumi", "Uma",
            "Wesley", "Wonton", "Yorvick", /*"Zeethibal",*/ // anagram for Elizabeth

            // The rest are mercenaries added JAF
            "Opponent", // crew of opponent mantis, pirate, police, and trader ships
            //"Wild", // now earns his keep!
            //"Jarek", // now earns his keep!
            //"Captain", // crew of famous captain ships
            //"Dragonfly", // dummy crew member used in opponent ship
            //"Scarab", // dummy crew member used in opponent ship
            "Aragorn", // My first son's middle name, and from Lord of the Rings
            "Brady", // My third son's middle name, and QB of the New England Patriots
            "Eight of Nine", // From Star Trek - Seven's younger sibling ;)
            "Fangorn", // From Lord of the Rings
            "Gagarin", // The first man in space
            "Hoshi", // From ST: Enterprise
            "Jackson", // From Stargate - and my nephew's first name
            "Kaylee", // From FireFly
            "Marcus", // My second son's middle name
            "O'Neill", // From Stargate
            "Ripley", // From the Alien series
            "Stilgar", // From Dune
            "Taggart", // From Galaxy Quest
            "Vansen", // From Space: Above and Beyond
            "Xizor", // From Star Wars: Shadows of the Empire
            //"Ziyal", // From ST: Deep Space 9
    };

    public static String[] DifficultyLevels = new String[]{"Beginner", "Easy", "Normal", "Hard", "Impossible"};

    public static String[][] EquipmentDescriptions = new String[][]{
            new String[]{
                    "The Pulse Laser is the weakest weapon available. It's small size allows only enough energy to build up to emit pulses of light.",
                    "The Beam Laser is larger than the Pulse Laser, so can build up enough charge to power what are essentially two Pulse Lasers. The resulting effect appears more like a ant beam.",
                    "The Military Laser is the largest commercially available weapon. It can build up enough charge to power three Pulse Lasers in series, resulting in a more dense and concentrated beam.",
                    "Morgan's Laser has been constructed from a Beam Laser, which has been attached to an Ion Reactor that builds up an immense charge, resulting in the strongest weapon known to exist.",
                    "The Photon Disruptor is a relatively weak weapon, but has the ability to disable an opponent's electrical systems, rendering them helpless.",
                    "The Quantum Disruptor is a very powerful disabling weapon. Once an opponent's sheilds are down it will usually require only a single shot with the Quantum Disruptor to disable them."},
            new String[]{
                    "The Energy Shield is a very basic deflector shield. Its operating principle is to absorb the energy directed at it.",
                    "The Reflective Shield is twice as powerful as the Energy Shield. It works by reflecting the energy directed at it instead of absorbing that energy.",
                    "The Lightning Shield is the most powerful shield known to exist. It features a Reflective Shield operating on a rotating frequency, which causes what looks like lightning to play across the shield barrier."},
            new String[]{
                    "Extra Cargo Bays to store anything your ship can take on as cargo.",
                    "The Auto-Repair System works to reduce the damage your ship sustains in battle, and repairs some damage in between encounters. It also boosts all other engineering functions.",
                    "The Navigating System increases the overall Pilot skill of the ship, making it harder to hit in battle, and making it easier to flee an encounter.",
                    "The Targeting System increases the overall Fighter skill of the ship, which increases the amount of damage done to an opponent in battle.",
                    "The Cloaking Device can enable your ship to evade detection by an opponent, but only if the Engineer skill of your ship is greater than that of your opponent. It also makes your ship harder to hit in battle.",
                    "The Fuel Compactor that you got as a reward for warning Gemulon of the invasion will increase the range of your ship by 3 parsecs.",
                    "These extra bays will not be detected during routine police searches. They may be detected if you are arrested and the police perform a more thorough search."}};

    public static String[] EquipmentTypes = new String[]{"Weapon", "Shield", "Gadget"};

    public static String[] GadgetNames = new String[]{"5 Extra Cargo Bays", "Auto-Repair System", "Navigating System",
            "Targeting System", "Cloaking Device", "Fuel Compactor", "5 Hidden Cargo Bays"};

    public static String[] GameCompletionTypes = new String[]{"Was killed", "Retired"};

    public static String[] ListStrings = new String[]{"", "^1", "^1 and ^2", "^1, ^2, and ^3", "^1, ^2, ^3, and ^4"};

    /*
     * In News Events, the following variables can be used: ^1 Commander Name ^2
     * Current System ^3 Commander's Ship Type
     */
    //TODO make stationar news
    public static String[] NewsEvent = new String[]{
            "Police Trace Orbiting Space Litter to ^1.",
            "Travelers Claim Sighting of Ship Materializing in Orbit!"
    };

    public static String[][] NewsHeadlines = new String[][]{
            new String[]{"Riots, Looting Mar Factional Negotiations.",
                    "Communities Seek Consensus.",
                    "Successful Bakunin Day Rally!",
                    "Major Faction Conflict Expected for the Weekend!"},
            new String[]{"Editorial: Taxes Too High!",
                    "Market Indices Read Record Levels!",
                    "Corporate Profits Up!",
                    "Restrictions on Corporate Freedom Abolished by Courts!"},
            new String[]{"Party Reports Productivity Increase.",
                    "Counter-Revolutionary Bureaucrats Purged from Party!",
                    "Party: Bold New Future Predicted!",
                    "Politburo Approves New 5-Year Plan!"},
            new String[]{
                    "States Dispute Natural Resource Rights!",
                    "States Denied Federal Funds over Local Laws!",
                    "Southern States Resist Federal Taxation for Capital Projects!",
                    "States Request Federal Intervention in Citrus Conflict!"},
            new String[]{"Robot Shortages Predicted for Q4.",
                    "Profitable Quarter Predicted.",
                    "CEO: Corporate Rebranding Progressing.",
                    "Advertising Budgets to Increase."},
            new String[]{"Olympics: Software Beats Wetware in All Events!",
                    "New Network Protocols To Be Deployed.",
                    "Storage Banks to be Upgraded!",
                    "System Backup Rescheduled."},
            new String[]{"Local Elections on Schedule!",
                    "Polls: Voter Satisfaction High!",
                    "Campaign Spending Aids Economy!",
                    "Police, Politicians Vow Improvements."},
            new String[]{"New Palace Planned; Taxes Increase.",
                    "Future Presents More Opportunities for Sacrifice!",
                    "Insurrection Crushed: Rebels Executed!",
                    "Police Powers to Increase!"},
            new String[]{
                    "Drug Smugglers Sentenced to Death!",
                    "Aliens Required to Carry Visible Identification at All Times!",
                    "Foreign Sabotage Suspected.",
                    "Stricter Immigration Laws Installed."},
            new String[]{"Farmers Drafted to Defend Lord's Castle!",
                    "Report: Kingdoms Near Flashpoint!",
                    "Baron Ignores Ultimatum!", "War of Succession Threatens!"},
            new String[]{"Court-Martials Up 2% This Year.",
                    "Editorial: Why Wait to Invade?",
                    "HQ: Invasion Plans Reviewed.",
                    "Weapons Research Increases Kill-Ratio!"},
            new String[]{"King to Attend Celebrations.",
                    "Queen's Birthday Celebration Ends in Riots!",
                    "King Commissions New Artworks.",
                    "Prince Exiled for Palace Plot!"},
            new String[]{"Dialog Averts Eastern Conflict!",
                    "Universal Peace: Is it Possible?",
                    "Editorial: Life in Harmony.",
                    "Polls: Happiness Quotient High!"},
            new String[]{"Government Promises Increased Welfare Benefits!",
                    "State Denies Food Rationing Required to Prevent Famine.",
                    "'Welfare Actually Boosts Economy,' Minister Says.",
                    "Hoarder Lynched by Angry Mob!"},
            new String[]{"Millions at Peace.", "Sun Rises.",
                    "Countless Hearts Awaken.", "Serenity Reigns."},
            new String[]{"New Processor Hits 10 ZettaHerz!",
                    "Nanobot Output Exceeds Expectation.",
                    "Last Human Judge Retires.",
                    "Software Bug Causes Mass Confusion."},
            new String[]{"High Priest to Hold Special Services.",
                    "Temple Restoration Fund at 81%.",
                    "Sacred Texts on Public Display.",
                    "Dozen Blasphemers Excommunicated!"}};

    public static String[][] NewsMastheads = new String[][]{
            new String[]{"The ^1 Arsenal", "The Grassroot", "Kick It!"},
            new String[]{"The Objectivist", "The ^1 Market", "The Invisible Hand"},
            new String[]{"The Daily Worker", "The People's Voice", "The ^1 Proletariat"},
            new String[]{"Planet News", "The ^1 Times", "Interstate Update"},
            new String[]{"^1 Memo", "News From The Board", "Status Report"},
            new String[]{"Pulses", "Binary Stream", "The System Clock"},
            new String[]{"The Daily Planet", "The ^1 Majority", "Unanimity"},
            new String[]{"The Command", "Leader's Voice", "The ^1 Mandate"},
            new String[]{"State Tribune", "Motherland News", "Homeland Report"},
            new String[]{"News from the Keep", "The Town Crier", "The ^1 Herald"},
            new String[]{"General Report", "^1 Dispatch", "The ^1 Sentry"},
            new String[]{"Royal Times", "The Loyal Subject", "The Fanfare"},
            new String[]{"Pax Humani", "Principle", "The ^1 Chorus"},
            new String[]{"All for One", "Brotherhood", "The People's Syndicate"},
            new String[]{"The Daily Koan", "Haiku", "One Hand Clapping"},
            new String[]{"The Future", "Hardware Dispatch", "TechNews"},
            new String[]{"The Spiritual Advisor", "Church Tidings", "The Temple Tribune"}};

    public static String[] NewsPoliceRecordHero = new String[]{
            "Locals Welcome Visiting Hero ^1!",
            "Famed Hero ^1 to Visit System!",
            "Large Turnout At Spaceport to Welcome ^1!"};

    public static String[] NewsPoliceRecordPsychopath = new String[]{
            "Police Warning: ^1 Will Dock At ^2!",
            "Notorious Criminal ^1 Sighted in ^2!",
            "Locals Rally to Deny Spaceport Access to ^1!",
            "Terror Strikes Locals on Arrival of ^1!"};

    public static String[] NewsPressureExternal = new String[]{
            "Reports of ^1 in the ^2 System.", "News of ^1 in the ^2 System.",
            "New Rumors of ^1 in the ^2 System.",
            "Sources report ^1 in the ^2 System.",
            "Notice: ^1 in the ^2 System.",
            "Evidence Suggests ^1 in the ^2 System."};

    public static String[] NewsPressureExternalPressures = new String[]{"", "Strife and War", "Plague Outbreaks",
            "Severe Drought", "Terrible Boredom", "Cold Weather", "Crop Failures", "Labor Shortages"};

    public static String[] NewsPressureInternal = new String[]{"",
            "War News: Offensives Continue!", "Plague Spreads! Outlook Grim.",
            "No Rain in Sight!", "Editors: Won't Someone Entertain Us?",
            "Cold Snap Continues!", "Serious Crop Failure! Must We Ration?",
            "Jobless Rate at All-Time Low!"};

    public static String[] SpecialResources = new String[]{"Nothing Special",
            "Mineral Rich", "Mineral Poor", "Desert", "Sweetwater Oceans",
            "Rich Soil", "Poor Soil", "Rich Fauna", "Lifeless",
            "Weird Mushrooms", "Special Herbs", "Artistic Populace",
            "Warlike Populace"};

    //TODO remove unneeded
    public static String[] QuestStates = new String[]{"Inactive", "Scheduled", "Subscribed", "Active", "Suspended",
            "Failed", "Finished"};

    public static String[] PoliceRecordNames = new String[]{"Psychopath", "Villain", "Criminal", "Crook", "Dubious",
            "Clean", "Lawful", "Trusted", "Liked", "Hero"};

    public static String[] PoliticalSystemNames = new String[]{"Anarchy",
            "Capitalist State", "Communist State", "Confederacy",
            "Corporate State", "Cybernetic State", "Democracy", "Dictatorship",
            "Fascist State", "Feudal State", "Military State", "Monarchy",
            "Pacifist State", "Socialist State", "State of Satori",
            "Technocracy", "Theocracy"};

    public static String[] ReputationNames = new String[]{"Harmless", "Mostly harmless", "Poor", "Average",
            "Above average", "Competent", "Dangerous", "Deadly", "Elite"};

    public static String[] ShieldNames = new String[]{"Energy Shield", "Reflective Shield", "Lightning Shield"};

    public static String[] ShipNames = new String[]{"Flea", "Gnat", "Firefly", "Mosquito", "Bumblebee", "Beetle",
            "Hornet", "Grasshopper", "Termite", "Wasp", "Mantis", ShipNameCustomShip};

    public static String[] ShipyardEngineers = new String[]{"Wedge", "Luke", "Lando", "Mara", "Obi-Wan"};

    public static String[] ShipyardNames = new String[]{
            "Corellian Engineering", "Incom Corporation", "Kuat Drive Yards",
            "Sienar Fleet Systems", "Sorosuub Engineering"};

    public static String[] ShipyardSkillDescriptions = new String[]{
            "All ships constructed at this shipyard use 2 fewer units per crew quarter.",
            "All ships constructed at this shipyard have 2 extra base fuel tanks.",
            "All ships constructed at this shipyard have the hull points increment by 5 more than usual.",
            "All ships constructed at this shipyard get shield slots for 2 fewer units.",
            "All ships constructed at this shipyard get weapon slots for 2 fewer units."};

    public static String[] ShipyardSkills = new String[]{"Crew Quartering", "Fuel Efficiency", "Hull Strength",
            "Shielding", "Weaponry"};

    public static String[] Sizes = new String[]{"Tiny", "Small", "Medium", "Large", "Huge", "Gargantuan"};

    // *************************************************************************
    // Many of these names are from Star Trek: The Next Generation, or are small
    // changes to names of this series. A few have different origins.
    // JAF - Except where noted these comments are the previous author's.
    // *************************************************************************
    public static String[] SystemNames = new String[]{"Acamar", // JAF - TNG "The Vengeance Factor (Acamar III)"
            "Adahn", // The alternate personality for The Nameless One in "Planescape: Torment"
            "Aldea", // JAF - TNG "When the Bough Breaks"
            "Andevian", // JAF - ST Andoria?
            "Antedi", // JAF - TNG "Manhunt" (Antede III)
            "Balosnee", "Baratas", // JAF - TNG "The Emissary" (Barradas III)
            "Brax", // One of the heroes in Master of Magic
            "Bretel", // This is a Dutch device for keeping your pants up.
            "Calondia", // JAF - TNG "The Price" (Caldonia)
            "Campor", // JAF - TNG "Bloodlines" (Camor V) or DS9 "Defiant" (Campa III)
            "Capelle", // The city I lived in while programming this game
            "Carzon", // JAF - Character from DS9 (Kurzon)?
            "Castor", // A Greek demi-god
            "Cestus", // JAF - several ST episodes (Cestus III)
            "Cheron", // JAF - TOS "Let That Be Your Last Battlefield"
            "Courteney", // After Courteney Cox...
            "Daled", // JAF - TNG "The Dauphin" (Daled IV)
            "Damast", "Davlos", // JAF - DS9 "Time's Orphan" (Davlos Prime) or DS9 "Visionary" (Davlos III)
            "Deneb", // JAF - TOS "Wolf in the Fold" (Deneb II) or TOS "Where No Man Has Gone Before" and TNG "Encounter at Farpoint" (Deneb IV)
            "Deneva", // JAF - TOS "Operation -- Annihilate!"
            "Devidia", // JAF - TNG "Time's Arrow" (Devidia II)
            "Draylon", // JAF - DS9 "Sanctuary" (Draylon II)
            "Drema", // JAF - TNG "Pen Pals" (Drema IV)
            "Endor", // JAF - From Return of the Jedi
            "Esmee", // One of the witches in Pratchett's Discworld
            "Exo", // JAF - TOS "What Are Little Girls Made Of?" (Exo III)
            "Ferris", // Iron
            "Festen", // A great Scandinavian movie
            "Fourmi", // An ant, in French
            "Frolix", // A solar system in one of Philip K. Dick's novels
            "Gemulon", // JAF - TNG " Mission" (Gamalon V) or DS9 "Paradise" (Germulon V)
            "Guinifer", // One way of writing the name of king Arthur's wife
            "Hades", // The underworld
            "Hamlet", // From Shakespeare
            "Helena", // Of Troy
            "Hulst", // A Dutch plant
            "Iodine", // An element
            "Iralius", "Janus", // A seldom encountered Dutch boy's name
            "Japori", // JAF - DS9 "Improbable Cause" (Jaforay II)?
            "Jarada", // JAF - DS9 "Progress" (Jarido)?
            "Jason", // A Greek hero
            "Kaylon", // JAF - TNG "Half a Life" (Kalon II)
            "Khefka", // JAF - DS9 "Invasive Procedures" (Kafka IV)
            "Kira", // My dog's name
            "Klaatu", // From a classic SF movie
            "Klaestron", // JAF - DS9 "Dax" (Klaestron IV)
            "Korma", // An Indian sauce
            "Kravat", // Interesting spelling of the French word for "tie"
            "Krios", // JAF - TNG "The Mind's Eye"
            "Laertes", // A king in a Greek tragedy
            "Largo", // JAF - DS9 "Babel" (Largo V)
            "Lave", // The starting system in Elite
            "Ligon", // JAF - TNG "Code of Honor" (Ligon II)
            "Lowry", // The name of the "hero" in Terry Gilliam's "Brazil"
            "Magrat", // The second of the witches in Pratchett's Discworld
            "Malcoria", // JAF - "Star Trek: First Contact" (Malkor III)?
            "Melina", // JAF - TNG "Silicon Avatar" (Malona IV)?
            "Mentar", // The Psilon home system in Master of Orion
            "Merik", // JAF - TOS "The Cloud Minders" (Merak II)
            "Mintaka", // JAF - TNG "Who Watches the Watchers" (Mintaka III)
            "Montor", // A city in Ultima III and Ultima VII part 2
            "Mordan", // JAF - TNG "Too Short a Season" (Mordan IV)
            "Myrthe", // The name of my daughter
            "Nelvana", // JAF - TNG "The Defector" (Nelvana III)
            "Nix", // An interesting spelling of a word meaning "nothing" in Dutch
            "Nyle", // An interesting spelling of the great river
            "Odet", "Og", // The last of the witches in Pratchett's Discworld
            "Omega", // The end of it all
            "Omphalos", // Greek for navel
            "Orias", "Othello", // From Shakespeare
            "Parade", // This word means the same in Dutch and in English
            "Penthara", // JAF - TNG "A Matter of Time" (Penthara IV)
            "Picard", // The enigmatic captain from ST:TNG
            "Pollux", // Brother of Castor
            "Quator", // JAF - TNG "Unification: Part I" (Qualar II)?
            "Rakhar", // JAF - DS9 "Vortex"
            "Ran", // A film by Akira Kurosawa
            "Regulas", // JAF - "Star Trek II: The Wrath of Khan" (Regula) or DS9 "Fascination" (Regulus III) or TOS "Amok Time" (Regulus V)
            "Relva", // JAF - TNG "Coming of Age" (Relva VII)
            "Rhymus", "Rochani", // JAF - DS9 "Dramatis Personae" (Rochanie III)
            "Rubicum", // The river Ceasar crossed to get into Rome
            "Rutia", // JAF - TNG "The High Ground" (Ruteeya IV)
            "Sarpeidon", // JAF - DS9 "Tacking into the Wind" (Sarpeidon V) or TOS "All Our Yesterdays" (Sarpeidon)
            "Sefalla", "Seltrice", "Sigma", "Sol", // That's our own solar system
            "Somari", "Stakoron", "Styris", // JAF - TNG "Code of Honor" (Styrus IV)
            "Talani", // JAF - DS9 "Armageddon Game" (T'Lani III and T'Lani Prime)
            "Tamus", "Tantalos", // A king from a Greek tragedy
            "Tanuga", "Tarchannen", "Terosa", // JAF - DS9 "Second Sight" (Terosa Prime)
            "Thera", // A seldom encountered Dutch girl's name
            "Titan", // The largest moon of Jupiter
            "Torin", // A hero from Master of Magic
            "Triacus", // JAF - TOS "And the Children Shall Lead"
            "Turkana", // JAF - TNG "Legacy" (Turkana IV)
            "Tyrus", "Umberlee", // A god from AD&D, which has a prominent role in Baldur's Gate
            "Utopia", // The ultimate goal
            "Vadera", "Vagra", // JAF - TNG "Skin of Evil" (Vagra II)
            "Vandor", // JAF - TNG "We'll Always Have Paris" (Vando VI)?
            "Ventax", // JAF - TNG "Devil's Due" (Ventax II)
            "Xenon", "Xerxes", // A Greek hero
            "Yew", // A city which is in almost all of the Ultima games
            "Yojimbo", // A film by Akira Kurosawa
            "Zalkon", // TNG "Transfigurations" (Zalcon)
            "Zuul", // From the first Ghostbusters movie

            // The rest are systems added JAF
            "Centauri", // As in Alpha Centauri - the closest star outside our solar system
            "Galvon", // Star Trek: The Next Generation "Data's Day"
            "Inthara", // Star Trek: Voyager "Retrospect"
            "Meridian", // Star Trek: Deep Space Nine "Meridian"
            "Qonos", // Star Trek - Klinon Homeworld (QonoS - Kronos)
            "Rae", // My wife's middle name
            "Weytahn", // Star Trek: Enterprise "Cease Fire"
            "Zonama" // From the Star Wars: New Jedi Order series (and Rogue Planet)
    };

    public static String[] SystemPressures = new String[]{
            "under no particular pressure", // Uneventful
            "at war", // Ore and Weapons in demand
            "ravaged by a plague", // Medicine in demand
            "suffering from a drought", // Water in demand
            "suffering from extreme boredom", // Games and Narcotics in demand
            "suffering from a cold spell", // Furs in demand
            "suffering from a crop failure", // Food in demand
            "lacking enough workers" // Machinery and Robots in demand
    };

    public static String[] TechLevelNames = new String[]{"Pre-Agricultural", "Agricultural", "Medieval", "Renaissance",
            "Early Industrial", "Industrial", "Post-Industrial", "Hi-Tech"};

    public static String[] TradeItemNames = new String[]{"Water", "Furs", "Food", "Ore", "Games", "Firearms",
            "Medicine", "Machines", "Narcotics", "Robots"};

    public static String[] WeaponNames = new String[]{"Pulse Laser", "Beam Laser", "Military Laser", "Morgan's Laser",
            "Photon Disruptor", "Quantum Disruptor"};
}
