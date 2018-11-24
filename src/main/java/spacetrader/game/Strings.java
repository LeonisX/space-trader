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
    }};

    //TODO test FormMonster, ...
    public static String newline = "<BR>";//String.format("%n");

    public static String AlertsCancel = "Cancel";
    public static String AlertsNo = "No";
    public static String AlertsOk = "Ok";
    public static String AlertsYes = "Yes";

    public static String AlertsAlertTitle = "^1";
    public static String AlertsAlertMessage = "^2";
    public static String AlertsAntidoteOnBoardTitle = "Antidote";
    public static String AlertsAntidoteOnBoardMessage = "Ten of your cargo bays now contain antidote for the Japori system.";
    public static String AlertsAntidoteDestroyedTitle = "Antidote Destroyed";
    public static String AlertsAntidoteDestroyedMessage = "The antidote for the Japori system has been destroyed with your ship. You should return to ^1 and get some more.";
    public static String AlertsAntidoteTakenTitle = "Antidote Taken";
    public static String AlertsAntidoteTakenMessage = "The Space Corps removed the antidote for Japori from your ship and delivered it, fulfilling your assignment.";
    public static String AlertsAppStartTitle = "Space Trader for Windows";
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
    public static String AlertsArtifactLostTitle = "Artifact Lost";
    public static String AlertsArtifactLostMessage = "The alien artifact has been lost in the wreckage of your ship.";
    public static String AlertsArtifactRelinquishedTitle = "Artifact Relinquished";
    public static String AlertsArtifactRelinquishedMessage = "The aliens take the artifact from you.";
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
    public static String AlertsEggTitle = "Egg";
    public static String AlertsEggMessage = "Congratulations! An eccentric Easter Bunny decides to exchange your trade goods for a special present!";
    public static String AlertsEncounterAliensSurrenderTitle = "Surrender";
    public static String AlertsEncounterAliensSurrenderMessage = "If you surrender to the aliens, they will take the artifact. Are you sure you wish to do that?";
    public static String AlertsEncounterArrestedTitle = "Arrested";
    public static String AlertsEncounterArrestedMessage = "You are arrested and taken to the space station, where you are brought before a court of law.";
    public static String AlertsEncounterAttackCaptainTitle = "Really Attack?";
    public static String AlertsEncounterAttackCaptainMessage = "Famous Captains get famous by, among other things, destroying everyone who attacks them. Do you really want to attack?";
    public static String AlertsEncounterAttackCaptainAccept = "Really Attack";
    public static String AlertsEncounterAttackCaptainCancel = "OK, I Won't";
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
    public static String AlertsEncounterDrinkContentsTitle = "Drink Contents?";
    public static String AlertsEncounterDrinkContentsMessage = "You have come across an extremely rare bottle of Captain Marmoset's Amazing Skill Tonic! The \"use-by\" date is illegible, but might still be good.  Would you like to drink it?";
    public static String AlertsEncounterDrinkContentsAccept = "Yes, Drink It";
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
    public static String AlertsEncounterMarieCelesteTitle = "Engage Marie Celeste";
    public static String AlertsEncounterMarieCelesteMessage = "The ship is empty: there is nothing in the ship's log, but the crew has vanished, leaving food on the tables and cargo in the holds. Do you wish to offload the cargo to your own holds? ";
    public static String AlertsEncounterMarieCelesteAccept = "Yes, Take Cargo";
    public static String AlertsEncounterMarieCelesteNoBribeTitle = "No Bribe";
    public static String AlertsEncounterMarieCelesteNoBribeMessage = "We'd love to take your money, but Space Command already knows you've got illegal goods onboard.";
    public static String AlertsEncounterOpponentEscapedTitle = "Opponent Escaped";
    public static String AlertsEncounterOpponentEscapedMessage = "Your opponent has managed to escape.";
    public static String AlertsEncounterPiratesBountyTitle = "Bounty";
    public static String AlertsEncounterPiratesBountyMessage = "You ^1 the pirate ship^2 and earned a bounty of ^3.";
    public static String AlertsEncounterPiratesExamineReactorTitle = "Pirates Examine Reactor";
    public static String AlertsEncounterPiratesExamineReactorMessage = "The pirates poke around the Ion Reactor while trying to figure out if it's valuable. They finally conclude that the Reactor is worthless, not to mention dangerous, and leave it on your ship.";
    public static String AlertsEncounterPiratesFindNoCargoTitle = "Pirates Find No Cargo";
    public static String AlertsEncounterPiratesFindNoCargoMessage = "The pirates are very angry that they find no cargo on your ship. To stop them from destroying you, you have no choice but to pay them an amount equal to 5% of your current worth - ^1.";
    public static String AlertsEncounterPiratesSurrenderPrincessTitle = "You Have the Princess";
    public static String AlertsEncounterPiratesSurrenderPrincessMessage = "Pirates are not nice people, and there's no telling what they might do to the Princess. Better to die fighting than give her up to them!";
    public static String AlertsEncounterPiratesTakeSculptureTitle = "Pirates Take Sculpture";
    public static String AlertsEncounterPiratesTakeSculptureMessage = "As the pirates ransack your ship, they find the stolen sculpture. \"This is worth thousands!\" one pirate exclaims, as he stuffs it into his pack.";
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
    public static String AlertsEncounterPostMarieTitle = "Contraband Removed";
    public static String AlertsEncounterPostMarieMessage = "The Customs Police confiscated all of your illegal cargo, but since you were cooperative, you avoided stronger fines or penalties.";
    public static String AlertsEncounterPostMarieFleeTitle = "Criminal Act!";
    public static String AlertsEncounterPostMarieFleeMessage = "Are you sure you want to do that? The Customs Police know you have engaged in criminal activity, and will report it!";
    public static String AlertsEncounterPostMarieFleeAccept = "Yes, I still want to";
    public static String AlertsEncounterPostMarieFleeCancel = "OK, I won't";
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
    public static String AlertsEncounterTonicConsumedGoodTitle = "Tonic Consumed";
    public static String AlertsEncounterTonicConsumedGoodMessage = "Mmmmm. Captain Marmoset's Amazing Skill Tonic not only fills you with energy, but tastes like a fine single-malt." + Strings.newline;
    public static String AlertsEncounterTonicConsumedStrangeTitle = "Tonic Consumed";
    public static String AlertsEncounterTonicConsumedStrangeMessage = "While you don't know what it was supposed to taste like, you get the feeling that this dose of tonic was a bit off.";
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
    public static String AlertsEquipmentFuelCompactorTitle = "Fuel Compactor";
    public static String AlertsEquipmentFuelCompactorMessage = "You now have a fuel compactor installed on your ship.";
    public static String AlertsEquipmentHiddenCompartmentsTitle = "Hidden Compartments";
    public static String AlertsEquipmentHiddenCompartmentsMessage = "You now have hidden compartments equivalent to 5 extra cargo bays installed in your ship. Police won't find illegal cargo hidden in these compartments.";
    public static String AlertsEquipmentIFTitle = "Not Enough Money";
    public static String AlertsEquipmentIFMessage = "You don't have enough money to spend on this item.";
    public static String AlertsEquipmentLightningShieldTitle = "Lightning Shield";
    public static String AlertsEquipmentLightningShieldMessage = "You now have one lightning shield installed on your ship.";
    public static String AlertsEquipmentMorgansLaserTitle = "Morgan's Laser";
    public static String AlertsEquipmentMorgansLaserMessage = "You now have Henry Morgan's special laser installed on your ship.";
    public static String AlertsEquipmentNotEnoughSlotsTitle = "Not Enough Slots";
    public static String AlertsEquipmentNotEnoughSlotsMessage = "You have already filled all of your available slots for this type of item.";
    public static String AlertsEquipmentQuantumDisruptorTitle = "Quantum Disruptor";
    public static String AlertsEquipmentQuantumDisruptorMessage = "You now have one quantum disruptor installed on your ship.";
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
    public static String AlertsGameEndBoughtMoonTitle = "You Have Retired";
    public static String AlertsGameEndBoughtMoonGirlTitle = "You Have Retired with the Princess";
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
    public static String AlertsJailHiddenCargoBaysRemovedTitle = "Hidden Compartments Removed";
    public static String AlertsJailHiddenCargoBaysRemovedMessage = "When your ship is impounded, the police go over it with a fine-toothed comb. You hidden compartments are found and removed.";
    public static String AlertsJailIllegalGoodsImpoundedTitle = "Illegal Goods Impounded";
    public static String AlertsJailIllegalGoodsImpoundedMessage = "The police also impound all of the illegal goods you have on board.";
    public static String AlertsJailInsuranceLostTitle = "Insurance Lost";
    public static String AlertsJailInsuranceLostMessage = "Since you cannot pay your insurance while you're in prison, it is retracted.";
    public static String AlertsJailMercenariesLeaveTitle = "Mercenaries Leave";
    public static String AlertsJailMercenariesLeaveMessage = "Any mercenaries who were traveling with you have left.";
    public static String AlertsJailShipSoldTitle = "Ship Sold";
    public static String AlertsJailShipSoldMessage = "Because you don't have the credits to pay your fine, your ship is sold.";
    /*public static String AlertsJarekTakenHomeTitle = "Jarek Taken Home";
    public static String AlertsJarekTakenHomeMessage = "The Space Corps decides to give ambassador Jarek a lift home to Devidia.";*/
    public static String AlertsLeavingIFInsuranceTitle = "Not Enough Money";
    public static String AlertsLeavingIFInsuranceMessage = "You don't have enough cash to pay for your insurance.";
    public static String AlertsLeavingIFMercenariesTitle = "Pay Mercenaries";
    public static String AlertsLeavingIFMercenariesMessage = "You don't have enough cash to pay your mercenaries to come with you on this trip. Fire them or make sure you have enough cash.";
    public static String AlertsLeavingIFWormholeTaxTitle = "Wormhole Tax";
    public static String AlertsLeavingIFWormholeTaxMessage = "You don't have enough money to pay for the wormhole tax.";
    public static String AlertsMeetCaptainAhabTitle = "Meet Captain Ahab";
    public static String AlertsMeetCaptainAhabMessage = "Captain Ahab is in need of a spare shield for an upcoming mission. He offers to trade you some piloting lessons for your reflective shield. Do you wish to trade?";
    public static String AlertsMeetCaptainAhabAccept = "Yes, Trade Shield";
    public static String AlertsMeetCaptainConradTitle = "Meet Captain Conrad";
    public static String AlertsMeetCaptainConradMessage = "Captain Conrad is in need of a military laser. She offers to trade you some engineering training for your military laser. Do you wish to trade?";
    public static String AlertsMeetCaptainConradAccept = "Yes, Trade Laser";
    public static String AlertsMeetCaptainHuieTitle = "Meet Captain Huie";
    public static String AlertsMeetCaptainHuieMessage = "Captain Huie is in need of a military laser. She offers to exchange some bargaining training for your military laser. Do you wish to trade?";
    public static String AlertsMeetCaptainHuieAccept = "Yes, Trade Laser";
    public static String AlertsNewGameConfirmTitle = "New Game";
    public static String AlertsNewGameConfirmMessage = "Are you sure you wish to start a new game?";
    public static String AlertsNewGameMoreSkillPointsTitle = "More Skill Points";
    public static String AlertsNewGameMoreSkillPointsMessage = "You haven't awarded all 20 skill points yet.";
    public static String AlertsOptionsNoGameTitle = "No Game Active";
    public static String AlertsOptionsNoGameMessage = "You don't have a game open, so you can only change the default options.";
    public static String AlertsPreciousHiddenTitle = "Precious Cargo Hidden";
    public static String AlertsPreciousHiddenMessage = "You quickly hide ^1 in your hidden cargo bays before the pirates board your ship. This would never work with the police, but pirates are usually in more of a hurry.";
    public static String AlertsPrincessTakenHomeTitle = "Princess Taken Home";
    public static String AlertsPrincessTakenHomeMessage = "The Space Corps decides to give the Princess a ride home to Galvon since you obviously weren't up to the task.";
    public static String AlertsReactorConfiscatedTitle = "Police Confiscate Reactor";
    public static String AlertsReactorConfiscatedMessage = "The Police confiscate the Ion reactor as evidence of your dealings with unsavory characters.";
    public static String AlertsReactorDestroyedTitle = "Reactor Destroyed";
    public static String AlertsReactorDestroyedMessage = "The destruction of your ship was made much more spectacular by the added explosion of the Ion Reactor.";
    public static String AlertsReactorOnBoardTitle = "Reactor";
    public static String AlertsReactorOnBoardMessage = "Five of your cargo bays now contain the unstable Ion Reactor, and ten of your bays contain enriched fuel.";
    public static String AlertsReactorMeltdownTitle = "Reactor Meltdown!";
    public static String AlertsReactorMeltdownMessage = "Just as you approach the docking bay, the reactor explodes into a huge radioactive fireball!";
    public static String AlertsReactorWarningFuelTitle = "Reactor Warning";
    public static String AlertsReactorWarningFuelMessage = "You notice the Ion Reactor has begun to consume fuel rapidly. In a single day, it has burned up nearly half a bay of fuel!";
    public static String AlertsReactorWarningFuelGoneTitle = "Reactor Warning";
    public static String AlertsReactorWarningFuelGoneMessage = "The Ion Reactor is emitting a shrill whine, and it's shaking. The display indicates that it is suffering from fuel starvation.";
    public static String AlertsReactorWarningTempTitle = "Reactor Warning";
    public static String AlertsReactorWarningTempMessage = "The Ion Reactor is smoking and making loud noises. The display warns that the core is close to the melting temperature.";
    public static String AlertsRegistryErrorTitle = "Error...";
    public static String AlertsRegistryErrorMessage = "Error accessing the Registry: ^1";
    public static String AlertsSculptureConfiscatedTitle = "Police Confiscate Sculpture";
    public static String AlertsSculptureConfiscatedMessage = "The Police confiscate the stolen sculpture and return it to its rightful owner.";
    public static String AlertsSculptureSavedTitle = "Sculpture Saved";
    public static String AlertsSculptureSavedMessage = "On your way to the escape pod, you grab the stolen sculpture. Oh well, at least you saved something.";
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
    public static String AlertsShipBuyReactorTitle = "Shipyard Engineer";
    public static String AlertsShipBuyReactorMessage = "Sorry! We can't take your ship as a trade-in. That Ion Reactor looks dangerous, and we have no way of removing it. Come back when you've gotten rid of it.";
    public static String AlertsShipBuyTransferTitle = "Transfer ^1";
    public static String AlertsShipBuyTransferMessage = "I'll transfer your ^2 to your new ship for ^3.";
    public static String AlertsShipBuyTransferAccept = "Do it!";
    public static String AlertsShipBuyTransferCancel = "No thanks";
    public static String AlertsShipDesignIFTitle = "Not Enough Money";
    public static String AlertsShipDesignIFMessage = "You don't have enough money to create this design.";
    public static String AlertsShipDesignThanksTitle = "Thank you!";
    public static String AlertsShipDesignThanksMessage = "^1 thanks you for your business!";
    public static String AlertsShipHullUpgradedTitle = "Hull Upgraded";
    public static String AlertsShipHullUpgradedMessage = "Technicians spend the day retrofitting the hull of your ship.";
    public static String AlertsSpecialCleanRecordTitle = "Clean Record";
    public static String AlertsSpecialCleanRecordMessage = "The hacker resets your police record to Clean.";
    public static String AlertsSpecialExperimentPerformedTitle = "Experiment Performed";
    public static String AlertsSpecialExperimentPerformedMessage = "The galaxy is abuzz with news of a terrible malfunction in Dr. Fehler's laboratory. Evidently, he was not warned in time and he performed his experiment... with disastrous results!";
    public static String AlertsSpecialIFTitle = "Not Enough Money";
    public static String AlertsSpecialIFMessage = "You don't have enough cash to spend to accept this offer.";
    public static String AlertsSpecialMoonBoughtTitle = "Moon Bought";
    public static String AlertsSpecialMoonBoughtMessage = "You bought a moon in the Utopia system. Go there to claim it.";
    public static String AlertsSpecialNoQuartersTitle = "No Free Quarters";
    public static String AlertsSpecialNoQuartersMessage = "There are currently no free crew quarters on your ship.";
    public static String AlertsSpecialNotEnoughBaysTitle = "Not Enough Bays";
    public static String AlertsSpecialNotEnoughBaysMessage = "You don't have enough empty cargo bays at the moment.";
    /*public static String AlertsSpecialPassengerConcernedJarekTitle = "Ship's Comm.";
    public static String AlertsSpecialPassengerConcernedJarekMessage = "Commander? Jarek here. Do you require any assistance in charting a course to Devidia?";*/
    public static String AlertsSpecialPassengerConcernedPrincessTitle = "Ship's Comm.";
    public static String AlertsSpecialPassengerConcernedPrincessMessage = "[Ziyal] Oh Captain? (giggles) Would it help if I got out and pushed?";
    public static String AlertsSpecialPassengerConcernedWildTitle = "Ship's Comm.";
    public static String AlertsSpecialPassengerConcernedWildMessage = "Bridge? This is Jonathan. Are we there yet? Heh, heh. Sorry, I couldn't resist.";
    /*public static String AlertsSpecialPassengerImpatientJarekTitle = "Ship's Comm.";
    public static String AlertsSpecialPassengerImpatientJarekMessage = "Captain! This is the Ambassador speaking. We should have been there by now?!";*/
    public static String AlertsSpecialPassengerImpatientPrincessTitle = "Ship's Comm.";
    public static String AlertsSpecialPassengerImpatientPrincessMessage = "Sir! Are you taking me home or merely taking the place of my previous captors?!";
    public static String AlertsSpecialPassengerImpatientWildTitle = "Ship's Comm.";
    public static String AlertsSpecialPassengerImpatientWildMessage = "Commander! Wild here. What's taking us so long?!";
    public static String AlertsSpecialPassengerOnBoardTitle = "Passenger On Board";
    public static String AlertsSpecialPassengerOnBoardMessage = "You have taken ^1 on board. While on board ^1 will lend you expertise, but may stop helping if the journey takes too long.";
    public static String AlertsSpecialSealedCanistersTitle = "Sealed Canisters";
    public static String AlertsSpecialSealedCanistersMessage = "You bought the sealed canisters and put them in your cargo bays.";
    public static String AlertsSpecialSkillIncreaseTitle = "Skill Increase";
    public static String AlertsSpecialSkillIncreaseMessage = "The alien increases one of your skills. ";
    public static String AlertsSpecialTimespaceFabricRipTitle = "Timespace Fabric Rip";
    public static String AlertsSpecialTimespaceFabricRipMessage = "You have flown through a tear in the timespace continuum caused by Dr. Fehler's failed experiment. You may not have reached" + Strings.newline + "your planned destination!";
    public static String AlertsSpecialTrainingCompletedTitle = "Training Completed";
    public static String AlertsSpecialTrainingCompletedMessage = "After a few hours of training with a top expert, you feel your abilities have improved significantly.";
    public static String AlertsTravelArrivalTitle = "Arrival";
    public static String AlertsTravelArrivalMessage = "You arrive at your destination.";
    public static String AlertsTravelUneventfulTripTitle = "Uneventful Trip";
    public static String AlertsTravelUneventfulTripMessage = "After an uneventful trip, you arrive at your destination.";
    public static String AlertsTribblesAllDiedTitle = "All The Tribbles Died";
    public static String AlertsTribblesAllDiedMessage = "The radiation from the Ion Reactor is deadly to Tribbles. All of the Tribbles on board your ship have died.";
    public static String AlertsTribblesAteFoodTitle = "Tribbles Ate Food";
    public static String AlertsTribblesAteFoodMessage = "You find that, instead of food, some of your cargo bays contain only Tribbles!";
    public static String AlertsTribblesGoneTitle = "No More Tribbles";
    public static String AlertsTribblesGoneMessage = "The alien uses his alien technology to beam over your whole collection of Tribbles to his ship.";
    public static String AlertsTribblesHalfDiedTitle = "Half The Tribbles Died";
    public static String AlertsTribblesHalfDiedMessage = "The radiation from the Ion Reactor seems to be deadly to Tribbles. Half the Tribbles on board died.";
    public static String AlertsTribblesKilledTitle = "Tribbles Killed";
    public static String AlertsTribblesKilledMessage = "Your Tribbles all died in the explosion.";
    public static String AlertsTribblesMostDiedTitle = "Most Tribbles Died";
    public static String AlertsTribblesMostDiedMessage = "You find that, instead of narcotics, some of your cargo bays contain only dead Tribbles!";
    public static String AlertsTribblesOwnTitle = "A Tribble";
    public static String AlertsTribblesOwnMessage = "You are now the proud owner of a little, cute, furry tribble.";
    public static String AlertsTribblesRemovedTitle = "Tribbles Removed";
    public static String AlertsTribblesRemovedMessage = "The Tribbles were sold with your ship.";
    public static String AlertsTribblesInspectorTitle = "Space Port Inspector";
    public static String AlertsTribblesInspectorMessage = "Our scan reports you have ^1 Tribbles on board your ship. Tribbles are pests worse than locusts! You are running the risk of getting a hefty fine!";
    public static String AlertsTribblesSqueekTitle = "A Tribble";
    public static String AlertsTribblesSqueekMessage = "Squeek!";
    public static String AlertsTribblesTradeInTitle = "You've Got Tribbles";
    public static String AlertsTribblesTradeInMessage = "Hm. I see you got a Tribble infestation on your current ship. I'm sorry, but that severely reduces the trade-in price.";
    public static String AlertsWildArrestedTitle = "Wild Arrested";
    public static String AlertsWildArrestedMessage = "Jonathan Wild is arrested, and taken away to stand trial.";
    public static String AlertsWildChatsPiratesTitle = "Wild Chats With Pirates";
    public static String AlertsWildChatsPiratesMessage = "The Pirate Captain turns out to be an old associate of Jonathan Wild's. They talk about old times, and you get the feeling that Wild would switch ships if the Pirates had any quarters available.";
    public static String AlertsWildGoesPiratesTitle = "Wild Goes With Pirates";
    public static String AlertsWildGoesPiratesMessage = "The Pirate Captain turns out to be an old associate of Jonathan Wild's, and invites him to go to Kravat aboard the Pirate ship. Wild accepts the offer and thanks you for the ride.";
    public static String AlertsWildLeavesShipTitle = "Wild Leaves Ship";
    public static String AlertsWildLeavesShipMessage = "Jonathan Wild leaves your ship, and goes into hiding on ^1.";
    public static String AlertsWildSculptureTitle = "Wild Eyes Sculpture";
    public static String AlertsWildSculptureMessage = "Jonathan Wild sees the stolen sculpture. \"Wow, I only know of one of these left in the whole Universe!\" he exclaims, \"Geurge Locas must be beside himself with it being stolen.\" He seems very impressed with you, which makes you feel much better about the item your delivering.";
    public static String AlertsWildWontBoardLaserTitle = "Wild Won't Board Ship";
    public static String AlertsWildWontBoardLaserMessage = "Jonathan Wild isn't willing to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here." + Strings.newline;
    public static String AlertsWildWontBoardReactorTitle = "Wild Won't Board Ship";
    public static String AlertsWildWontBoardReactorMessage = "Jonathan Wild doesn't like the looks of that Ion Reactor. He thinks it's too dangerous, and won't get on board.";
    public static String AlertsWildWontStayAboardLaserTitle = "Wild Won't Stay Aboard";
    public static String AlertsWildWontStayAboardLaserMessage = "Jonathan Wild isn't about to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here on ^1." + Strings.newline;
    public static String AlertsWildWontStayAboardLaserAccept = "Say Goodbye to Wild";
    public static String AlertsWildWontStayAboardReactorTitle = "Wild Won't Stay Aboard";
    public static String AlertsWildWontStayAboardReactorMessage = "Jonathan Wild isn't willing to go with you if you bring that Reactor on board. He'd rather take his chances hiding out here on ^1." + Strings.newline;
    public static String AlertsWildWontStayAboardReactorAccept = "Say Goodbye to Wild";

    //Units
    public static String CargoBay="Cargo Bay";
    public static String CargoUnit = "unit";
    public static String DistanceUnit = "parsec";
    public static String DistanceSubunit = "click";
    public static String MoneyUnit = "credit";
    public static String TimeUnit = "day";

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
    public static String CheatsArtifact = "Artifact";
    public static String CheatsDragonfly = "Dragonfly";
    public static String CheatsExperiment = "Experiment";
    public static String CheatsGemulon = "Gemulon";
    public static String CheatsJapori = "Japori";
    /*public static String CheatsJarek = "Jarek";*/
    public static String CheatsMoon = "Moon";
    public static String CheatsPrincess = "Princess";
    public static String CheatsReactor = "Reactor";
    public static String CheatsScarab = "Scarab";
    public static String CheatsSculpture = "Sculpture";
    public static String CheatsSpaceMonster = "SpaceMonster";
    public static String CheatsWild = "Wild";
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
    public static String EncounterHidePrincess = "the Princess";
    public static String EncounterHideSculpture = "the stolen sculpture";
    public static String EncounterHullStrength = "Hull at ^1%";
    public static String EncounterPiratesDestroyed = "destroyed";
    public static String EncounterPiratesDisabled = "disabled";
    public static String EncounterPiratesLocation = "(informing the police of the pirate's location)";
    public static String EncounterPoliceSubmitArrested = "You will be arrested!";
    public static String EncounterPoliceSubmitGoods = "illegal goods";
    public static String EncounterPoliceSubmitReactor = "an illegal Ion Reactor";
    public static String EncounterPoliceSubmitSculpture = "a stolen sculpture";
    public static String EncounterPoliceSubmitWild = "Jonathan Wild";
    public static String EncounterPoliceSurrenderCargo = "You have ^1 on board!";
    public static String EncounterPoliceSurrenderAction = "They will ^1. ";
    public static String EncounterPoliceSurrenderReactor = "destroy the reactor";
    public static String EncounterPoliceSurrenderSculpt = "confiscate the sculpture";
    public static String EncounterPoliceSurrenderWild = "arrest Wild, too";
    public static String EncounterPretextAlien = "an alien ^1";
    public static String EncounterPretextBottle = "a floating ^1";
    public static String EncounterPretextCaptainAhab = "the famous Captain Ahab in a ^1";
    public static String EncounterPretextCaptainConrad = "the famous Captain Conrad in a ^1";
    public static String EncounterPretextCaptainHuie = "the famous Captain Huie in a ^1";
    public static String EncounterPretextMarie = "a drifting ^1";
    //TODO
    public static String EncounterPretextMariePolice = "the Customs Police in a ^1";
    public static String EncounterPretextPirate = "a pirate ^1";
    public static String EncounterPretextPolice = "a police ^1";
    public static String EncounterPretextScorpion = "the kidnappers in a ^1";
    public static String EncounterPretextSpaceMonster = "a horrifying ^1";
    public static String EncounterPretextStolen = "a stolen ^1";
    public static String EncounterPretextTrader = "a trader ^1";
    public static String EncounterPrincessRescued = newline + newline
            + "You land your ship near where the Space Corps has landed with the Scorpion in tow. The Princess is revived from hibernation and you get to see her for the first time. Instead of the spoiled child you were expecting, Ziyal is possible the most beautiful woman you've ever seen. \"What took you so long?\" she demands. You notice a twinkle in her eye, and then she smiles. Not only is she beautiful, but she's got a sense of humor. She says, \"Thank you for freeing me. I am in your debt.\" With that she give you a kiss on the cheek, then leaves. You hear her mumble, \"Now about a ride home.\"";
    public static String EncounterShieldStrength = "Shields at ^1%";
    public static String EncounterShieldNone = "No Shields";
    public static String EncounterShipCaptain = "Captain";
    public static String EncounterShipMantis = "alien ship";
    public static String EncounterShipPirate = "pirate ship";
    public static String EncounterShipPolice = "police ship";
    public static String EncounterShipTrader = "trader ship";
    public static String EncounterText = "At ^1 from ^2 you encounter ^3.";
    public static String EncounterTextBottle = "It appears to be a rare bottle of Captain Marmoset's Skill Tonic!";
    public static String EncounterTextFamousCaptain = "The Captain requests a brief meeting with you.";
    public static String EncounterTextMarieCeleste = "The Marie Celeste appears to be completely abandoned.";
    public static String EncounterTextOpponentAttack = "Your opponent attacks.";
    public static String EncounterTextOpponentFlee = "Your opponent is fleeing.";
    public static String EncounterTextOpponentIgnore = "It ignores you.";
    public static String EncounterTextOpponentNoNotice = "It doesn't notice you.";
    public static String EncounterTextPoliceInspection = "The police summon you to submit to an inspection.";
    public static String EncounterTextPolicePostMarie = "\"We know you removed illegal goods from the Marie Celeste. You must give them up at once!\"";
    public static String EncounterTextPoliceSurrender = "The police hail they want you to surrender.";
    public static String EncounterTextTrader = "You are hailed with an offer to trade goods.";

    public static String EquipmentFreeSlot = "FREE SLOT";
    public static String EquipmentNoneForSale = "None for sale";
    public static String EquipmentNoSlots = "No slots";

    public static String FileFormatBad = "The file is not a Space Trader for Windows file, or is the wrong version or has been corrupted.";
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

    public static String NewsMoonForSale = "Seller in ^1 System has Utopian Moon available.";
    public static String NewsShipyard = "Shipyard in ^1 System offers to design custom ships.";
    public static String NewsTribbleBuyer = "Collector in ^1 System seeks to purchase Tribbles.";

    public static String OptionsKeepEmptyCargoBays = "to leave empty when buying goods in-system";

    public static String PersonnelVacancy = "Vacancy";
    public static String PointsRemaining = "remaining: ^1.";

    public static String QuestNone = "There are no open quests.";
    public static String QuestArtifact = "Deliver the alien artifact to Professor Berger at some hi-tech system.";
    public static String QuestDragonflyBaratas = "Follow the Dragonfly to Baratas.";
    public static String QuestDragonflyMelina = "Follow the Dragonfly to Melina.";
    public static String QuestDragonflyRegulas = "Follow the Dragonfly to Regulas.";
    public static String QuestDragonflyShield = "Get your lightning shield at Zalkon.";
    public static String QuestDragonflyZalkon = "Follow the Dragonfly to Zalkon.";
    public static String QuestExperimentInformDays = "Stop Dr. Fehler's experiment at Daled within ^1.";
    public static String QuestExperimentInformTomorrow = "Stop Dr. Fehler's experiment at Daled by tomorrow.";
    public static String QuestGemulonFuel = "Get your fuel compactor at Gemulon.";
    public static String QuestGemulonInformDays = "Inform Gemulon about alien invasion within ^1.";
    public static String QuestGemulonInformTomorrow = "Inform Gemulon about alien invasion by tomorrow.";
    /*public static String QuestJarek = "Take ambassador Jarek to Devidia.";
    public static String QuestJarekImpatient = QuestJarek + newline
            + "Jarek is wondering why the journey is taking so long, and is no longer of much help in negotiating trades.";*/
    public static String QuestJaporiDeliver = "Deliver antidote to Japori.";
    public static String QuestMoon = "Claim your moon at Utopia.";
    public static String QuestPrincessCentauri = "Follow the Scorpion to Centauri.";
    public static String QuestPrincessInthara = "Follow the Scorpion to Inthara.";
    public static String QuestPrincessQonos = "Follow the Scorpion to Qonos.";
    public static String QuestPrincessQuantum = "Get your Quantum Disruptor at Galvon.";
    public static String QuestPrincessReturn = "Transport ^1 from Qonos to Galvon.";
    public static String QuestPrincessReturning = "Return ^1 to Galvon.";
    public static String QuestPrincessReturningImpatient = QuestPrincessReturning + newline
            + "She is becoming anxious to arrive at home, and is no longer of any help in engineering functions.";
    public static String QuestReactor = "Deliver the unstable reactor to Nix for Henry Morgan.";
    public static String QuestReactorFuel = "Deliver the unstable reactor to Nix before it consumes all its fuel.";
    public static String QuestReactorLaser = "Get your special laser at Nix.";
    public static String QuestScarabFind = "Find and destroy the Scarab (which is hiding at the exit to a wormhole).";
    public static String QuestScarabHull = "Get your hull upgraded at ^1.";
    public static String QuestScarabNotify = "Notify the authorities at ^1 that the Scarab has been destroyed.";
    public static String QuestSculpture = "Deliver the stolen sculpture to Endor.";
    public static String QuestSculptureHiddenBays = "Have hidden compartments installed at Endor.";
    public static String QuestSpaceMonsterKill = "Kill the space monster at Acamar.";
    public static String QuestTribbles = "Get rid of those pesky tribbles.";
    public static String QuestWild = "Smuggle Jonathan Wild to Kravat.";
    public static String QuestWildImpatient = QuestWild + newline
            + "Wild is getting impatient, and will no longer aid your crew along the way.";

    public static String ShipBuyGotOne = "got one";
    public static String ShipBuyTransfer = "and transfer your unique equipment to the new ship";

    public static String ShipBay = "bay";
    public static String ShipEquipment = "Equipment:";
    public static String ShipGadgetSlot = "gadget slot";
    public static String ShipHardened = "Hardened";
    public static String ShipHull = "Hull:";
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

    public static String SpecialCargoArtifact = "An alien artifact.";
    public static String SpecialCargoExperiment = "A portable singularity.";
    public static String SpecialCargoJapori = "10 bays of antidote.";
    /*public static String SpecialCargoJarek = "A haggling computer.";*/
    public static String SpecialCargoNone = "No special items.";
    public static String SpecialCargoReactor = "An unstable reactor taking up 5 bays.";
    public static String SpecialCargoSculpture = "A stolen plastic sculpture of a man holding some kind of light sword.";
    public static String SpecialCargoReactorBays = "of enriched fuel.";
    public static String SpecialCargoTribblesInfest = "An infestation of tribbles.";
    public static String SpecialCargoTribbleCute = "Cute, furry tribble.";
    public static String SpecialCargoTribblesCute = "Cute, furry tribbles.";

    public static String StatusBarBays = "Bays:";
    public static String StatusBarCash = "Cash:";
    public static String StatusBarCurrentCosts = "Current Costs:";
    public static String StatusBarNoGameLoaded = "No Game Loaded.";

    public static String TribbleDangerousNumber = "a dangerous number of";

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
            "Wesley", "Wonton", "Yorvick", "Zeethibal", // anagram for Elizabeth

            // The rest are mercenaries added JAF
            "Opponent", // crew of opponent mantis, pirate, police, and trader ships
            "Wild", // now earns his keep!
            //"Jarek", // now earns his keep!
            "Captain", // crew of famous captain ships
            "Dragonfly", // dummy crew member used in opponent ship
            "Scarab", // dummy crew member used in opponent ship
            "SpaceMonster", // dummy crew member used in opponent ship
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
            "Ziyal", // From ST: Deep Space 9
            "Scorpion" // dummy crew member used in opponent ship
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

    public static String[] GameCompletionTypes = new String[]{"Was killed", "Retired", "Claimed moon"};

    public static String[] ListStrings = new String[]{"", "^1", "^1 and ^2", "^1, ^2, and ^3", "^1, ^2, ^3, and ^4"};

    /*
     * In News Events, the following variables can be used: ^1 Commander Name ^2
     * Current System ^3 Commander's Ship Type
     */
    public static String[] NewsEvent = new String[]{
            "Scientist Adds Alien Artifact to Museum Collection.",
            "Police Trace Orbiting Space Litter to ^1.",
            "Experimental Craft Stolen! Critics Demand Security Review.",
            "Investigators Report Strange Craft.",
            "Spectacular Display as Stolen Ship Destroyed in Fierce Space Battle.",
            "Rumors Continue: Melina Orbitted by Odd Starcraft.",
            "Strange Ship Observed in Regulas Orbit.",
            "Unidentified Ship: A Threat to Zalkon?",
            "Huge Explosion Reported at Research Facility.",
            "Travelers Report Timespace Damage, Warp Problems!",
            "Scientists cancel High-profile Test! Committee to Investigate Design.",
            "Travelers Claim Sighting of Ship Materializing in Orbit!",
            "Editorial: Who Will Warn Gemulon?",
            "Alien Invasion Devastates Planet!",
            "Invasion Imminent! Plans in Place to Repel Hostile Invaders.",
            "Thug Assaults Captain Ahab!",
            "Destruction of Captain Ahab's Ship Causes Anger!",
            "Captain Conrad Comes Under Attack By Criminal!",
            "Captain Conrad's Ship Destroyed by Villain!",
            "Famed Captain Huie Attacked by Brigand!",
            "Citizens Mourn Destruction of Captain Huie's Ship!",
            "Editorial: We Must Help Japori!",
            "Disease Antidotes Arrive! Health Officials Optimistic.",
            //"Ambassador Jarek Returns from Crisis.",
            "Security Scandal: Test Craft Confirmed Stolen.",
            "Wormhole Traffic Delayed as Stolen Craft Destroyed.",
            "Wormhole Travelers Harassed by Unusual Ship!",
            "Space Monster Threatens Homeworld!",
            "Hero Slays Space Monster! Parade, Honors Planned for Today.",
            "Notorious Criminal Jonathan Wild Arrested!",
            "Rumors Suggest Known Criminal J. Wild May Come to Kravat!",
            "Priceless collector's item stolen from home of Geurge Locas!",
            "Space Corps follows ^3 with alleged stolen sculpture to ^2.",
            "Member of Royal Family kidnapped!",
            "Aggressive Ship Seen in Orbit Around Centauri",
            "Dangerous Scorpion Damages Several Other Ships Near Inthara",
            "Kidnappers Holding Out at Qonos",
            "Scorpion Defeated! Kidnapped Member of Galvon Royal Family Freed!",
            "Beloved Royal Returns Home!"};

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

    //TODO remove unneeded
    public static String[] QuestStates = new String[]{"Inactive", "Scheduled", "Subscribed", "Active", "Suspended",
            "Failed", "Finished", "Unknown"};

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
            "Hornet", "Grasshopper", "Termite", "Wasp", "Space Monster", "Dragonfly", "Mantis", "Scarab", "Bottle",
            ShipNameCustomShip, "Scorpion"};

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

    public static String[] SpecialEventStrings = new String[]{
            "This alien artifact should be delivered to professor Berger, who is currently traveling. You can probably find him at a hi-tech solar system. The alien race which produced this artifact seems keen on getting it back, however, and may hinder the carrier. Are you, for a price, willing to deliver it?",
            "This is professor Berger. I thank you for delivering the alien artifact to me. I hope the aliens weren't too much of a nuisance. I have transferred 20000 credits to your account, which I assume compensates for your troubles.",
            "A trader in second-hand goods offers you 3 sealed cargo canisters for the sum of 1000 credits. It could be a good deal: they could contain robots. Then again, it might just be water. Do you want the canisters?",
            "This is Colonel Jackson of the Space Corps. An experimental ship, code-named \"Dragonfly\", has been stolen. It is equipped with very special, almost indestructible shields. It shouldn't fall into the wrong hands and we will reward you if you destroy it. It has been last seen in the Baratas system.",
            "A small ship of a weird design docked here recently for repairs. The engineer who worked on it said that it had a weak hull, but incredibly strong shields. I heard it took off in the direction of the Melina system.",
            "Hello, Commander. This is Colonel Jackson again. On behalf of the Space Corps, I thank you for your valuable assistance in destroying the Dragonfly. As a reward, we will install one of the experimental shields on your ship. Return here for that when you're ready.",
            "A ship with shields that seemed to be like lightning recently fought many other ships in our system. I have never seen anything like it before. After it left, I heard it went to the Regulas system.",
            "A small ship with shields like I have never seen before was here a few days ago. It destroyed at least ten police ships! Last thing I heard was that it went to the Zalkon system.",
            "Colonel Jackson here. Do you want us to install a lightning shield on your current ship?",
            "A hacker conveys to you that he has cracked the passwords to the galaxy-wide police computer network, and that he can erase your police record for the sum of 5000 credits. Do you want him to do that?",
            "While reviewing the plans for Dr. Fehler's new space-warping drive, Dr. Lowenstam discovered a critical error. If you don't go to Daled and stop the experiment within ten days, the time-space continuum itself could be damaged!",
            "Dr. Fehler can't understand why the experiment failed. But the failure has had a dramatic and disastrous effect on the fabric of space-time itself. It seems that Dr. Fehler won't be getting tenure any time soon... and you may have trouble when you warp!",
            "Upon your warning, Dr. Fehler calls off the experiment. As your  reward, you are given a Portable Singularity. This device will, for one time only, instantaneously transport you to any system in the galaxy. The Singularity can be accessed  by clicking the \"J\" (Jump) button on the Galactic Chart.",
            "We received word that aliens will invade Gemulon seven days from now. We know exactly at which coordinates they will arrive, but we can't warn Gemulon because an ion storm disturbs all forms of communication. We need someone, anyone, to deliver this info to Gemulon within six days.",
            "Do you wish us to install the fuel compactor on your current ship? (You need a free gadget slot)",
            "Alas, Gemulon has been invaded by aliens, which has thrown us back to pre-agricultural times. If only we had known the exact coordinates where they first arrived at our system, we might have prevented this tragedy from happening.",
            "This information of the arrival of the alien invasion force allows us to prepare a defense. You have saved our way of life. As a reward, we have a fuel compactor gadget for you, which will increase the travel distance by 3 parsecs for any ship. Return here to get it installed.",
            "A strange disease has invaded the Japori system. We would like you to deliver these ten canisters of special antidote to Japori. Note that, if you accept, ten of your cargo bays will remain in use on your way to Japori. Do you accept this mission?",
            "Thank you for delivering the medicine to us. We don't have any money to reward you, but we do have an alien fast-learning machine with which we will increase your skills.",
            //"A recent change in the political climate of this solar system has forced Ambassador Jarek to flee back to his home system, Devidia. Would you be willing to give him a lift?",
            //"Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment.",
            //"You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account.",
            "There is a small but habitable moon for sale in the Utopia system, for the very reasonable sum of half a million credits. If you accept it, you can retire to it and live a peaceful, happy, and wealthy life. Do you wish to buy it?",
            "Welcome to the Utopia system. Your own moon is available for you to retire to it, if you feel inclined to do that. Are you ready to retire and lead a happy, peaceful, and wealthy life?",
            "Galactic criminal Henry Morgan wants this illegal ion reactor delivered to Nix. It's a very dangerous mission! The reactor and its fuel are bulky, taking up 15 bays. Worse, it's not stable -- its resonant energy will weaken your shields and hull strength while it's aboard your ship. Are you willing to deliver it?",
            "Henry Morgan takes delivery of the reactor with great glee. His men immediately set about stabilizing the fuel system. As a reward, Morgan offers you a special, high-powered laser that he designed. Return with an empty weapon slot when you want them to install it.",
            "Morgan's technicians are standing by with something that looks a lot like a military laser -- if you ignore the additional cooling vents and anodized ducts. Do you want them to install Morgan's special laser?",
            "Captain Renwick developed a new organic hull material for his ship which cannot be damaged except by Pulse lasers. While he was celebrating this success, pirates boarded and stole the craft, which they have named the Scarab. Rumors suggest it's being hidden at the exit to a wormhole. Destroy the ship for a reward!",
            "Space Corps is indebted to you for destroying the Scarab and the pirates who stole it. As a reward, we can have Captain Renwick upgrade the hull of your ship. Note that his upgrades won't be transferable if you buy a new ship! Come back with the ship you wish to upgrade.",
            "The organic hull used in the Scarab is still not ready for day-to-day use. But Captain Renwick can certainly upgrade your hull with some of his retrofit technology. It's light stuff, and won't reduce your ship's range. Should he upgrade your ship?",
            "An alien with a fast-learning machine offers to increase one of your skills for the reasonable sum of 3000 credits. You won't be able to pick that skill, though. Do you accept his offer?",
            "A space monster has invaded the Acamar system and is disturbing the trade routes. You'll be rewarded handsomely if you manage to destroy it.",
            "We thank you for destroying the space monster that circled our system for so long. Please accept 15000 credits as reward for your heroic deed.",
            "A merchant prince offers you a very special and wondrous item for the sum of 1000 credits. Do you accept?",
            "An eccentric alien billionaire wants to buy your collection of tribbles and offers half a credit for each of them. Do you accept his offer?",
            "Law Enforcement is closing in on notorious criminal kingpin Jonathan Wild. He would reward you handsomely for smuggling him home to Kravat. You'd have to avoid capture by the Police on the way. Are you willing to give him a berth?",
            "Jonathan Wild is most grateful to you for spiriting him to safety. As a reward, he has one of his Cyber Criminals hack into the Police Database, and clean up your record. He also offers you the opportunity to take his talented nephew Zeethibal along as a Mercenary with no pay.",
            "A hooded figure approaches you and asks if you'd be willing to deliver some recently acquired merchandise to Endor. He's holding a small sculpture of a man holding some kind of light sword that you strongly suspect was stolen. It appears to be made of plastic and not very valuable. \"I'll pay you 2,000 credits now, plus 15,000 on delivery,\" the figure says. After seeing the look on your face he adds, \"It's a collector's item. Will you deliver it or not?\"",
            "Yet another dark, hooded figure approaches. \"Do you have the action fig- umm, the sculpture?\" You hand it over and hear what sounds very much like a giggle from under the hood. \"I know you were promised 15,000 credits on delivery, but I'm strapped for cash right now. However, I have something better for you. I have an acquaintance who can install hidden compartments in your ship.\" Return with an empty gadget slot when you're ready to have it installed.",
            "You're taken to a warehouse and whisked through the door. A grubby alien of some humanoid species - you're not sure which one - approaches. \"So you're the being who needs Hidden Compartments. Should I install them in your ship?\" (It requires a free gadget slot.)",
            "A member of the Royal Family of Galvon has been kidnapped! Princess Ziyal was abducted by men while travelling across the planet. They escaped in a hi-tech ship called the Scorpion. Please rescue her! (You'll need to equip your ship with disruptors to be able to defeat the Scorpion without destroying it.) A ship bristling with weapons was blasting out of the system. It's trajectory before going to warp indicates that its destination was Centauri.",
            "A ship had its shields upgraded to Lighting Shields just two days ago. A shipyard worker overheard one of the crew saying they were headed to Inthara.",
            "Just yesterday a ship was seen in docking bay 327. A trader sold goods to a member of the crew, who was a native of Qonos. It's possible that's where they were going next.",
            "The Galvonian Ambassador to Qonos approaches you. The Princess needs a ride home. Will you take her? I don't think she'll feel safe with anyone else.",
            "His Majesty's Shipyard: Do you want us to install a quantum disruptor on your current ship?",
            "The King and Queen are extremely grateful to you for returning their daughter to them. The King says, \"Ziyal is priceless to us, but we feel we must offer you something as a reward. Visit my shipyard captain and he'll install one of our new Quantum Disruptors.\""};

    public static String[] SpecialEventTitles = new String[]{
            "Alien Artifact", "Artifact Delivery", "Cargo For Sale",
            "Dragonfly", "Dragonfly Destroyed", "Weird Ship", "Lightning Ship",
            "Lightning Shield", "Strange Ship", "Erase Record",
            "Dangerous Experiment", "Experiment Failed", "Disaster Averted",
            "Alien Invasion", "Fuel Compactor", "Gemulon Invaded",
            "Gemulon Rescued", "Japori Disease", "Medicine Delivery",
            /*"Ambassador Jarek", "Jarek Gets Out", "Lottery Winner",*/
            "Moon For Sale", "Retirement", "Morgan's Reactor",
            "Reactor Delivered", "Install Morgan's Laser", "Scarab Stolen",
            "Scarab Destroyed", "Upgrade Hull", "Skill Increase",
            "Space Monster", "Monster Killed", "Merchant Prince",
            "Tribble Buyer", "Jonathan Wild", "Wild Gets Out",
            "Stolen Sculpture", "Sculpture Delivered",
            "Install Hidden Compartments", "Kidnapped", "Aggressive Ship",
            "Dangerous Scorpion", "Royal Rescue", "Quantum Disruptor",
            "Royal Return",
            //TODO delete
    "ASSIGNED"};

    public static String[] SpecialResources = new String[]{"Nothing Special",
            "Mineral Rich", "Mineral Poor", "Desert", "Sweetwater Oceans",
            "Rich Soil", "Poor Soil", "Rich Fauna", "Lifeless",
            "Weird Mushrooms", "Special Herbs", "Artistic Populace",
            "Warlike Populace"};

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

    public static String[] VeryRareEncounters = new String[]{"Marie Celeste", "Captain Ahab", "Captain Conrad",
            "Captain Huie", "Dated Tonic", "Good Tonic"};

    public static String[] WeaponNames = new String[]{"Pulse Laser", "Beam Laser", "Military Laser", "Morgan's Laser",
            "Photon Disruptor", "Quantum Disruptor"};
}
