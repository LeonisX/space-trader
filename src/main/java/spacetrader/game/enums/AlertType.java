package spacetrader.game.enums;

public enum AlertType implements SpaceTraderEnum {

    Alert,
    AntidoteOnBoard,
    AntidoteDestroyed,
    AntidoteTaken,
    AppStart,
    ArrivalBuyNewspaper,
    ArrivalIFFuel,
    ArrivalIFFuelRepairs,
    ArrivalIFNewspaper,
    ArrivalIFRepairs,
    ArtifactLost,
    ArtifactRelinquished,
    CargoIF,
    CargoNoEmptyBays,
    CargoNoneAvailable,
    CargoNoneToSell,
    CargoNotInterested,
    CargoNotSold,
    ChartJump,
    ChartJumpCurrent,
    ChartJumpNoSystemSelected,
    ChartTrackSystem,
    ChartWormholeUnreachable,
    Cheater,
    CrewFireMercenary,
    CrewNoQuarters,
    DebtNoBuy,
    DebtNone,
    DebtReminder,
    DebtTooLargeGrounded,
    DebtTooLargeLoan,
    DebtTooLargeTrade,
    DebtWarning,
    Egg,
    EncounterAliensSurrender,
    EncounterArrested,
    EncounterAttackCaptain,
    EncounterAttackNoDisruptors,
    EncounterAttackNoLasers,
    EncounterAttackNoWeapons,
    EncounterAttackPolice,
    EncounterAttackTrader,
    EncounterBothDestroyed,
    EncounterDisabledOpponent,
    EncounterDrinkContents,
    EncounterDumpAll,
    EncounterDumpWarning,
    EncounterEscaped,
    EncounterEscapedHit,
    EncounterEscapePodActivated,
    EncounterLooting,
    EncounterMarieCeleste,
    EncounterMarieCelesteNoBribe,
    EncounterOpponentEscaped,
    EncounterPiratesBounty,
    EncounterPiratesFindNoCargo,
    EncounterPiratesTakeSculpture,
    EncounterPoliceBribe,
    EncounterPoliceBribeCant,
    EncounterPoliceBribeLowCash,
    EncounterPoliceFine,
    EncounterPoliceNothingFound,
    EncounterPoliceNothingIllegal,
    EncounterPoliceSubmit,
    EncounterPoliceSurrender,
    EncounterPostMarie,
    EncounterPostMarieFlee,
    EncounterScoop,
    EncounterScoopNoRoom,
    EncounterScoopNoScoop,
    EncounterSurrenderRefused,
    EncounterTonicConsumedGood,
    EncounterTonicConsumedStrange,
    EncounterTradeCompleted,
    EncounterYouLose,
    EncounterYouWin,
    EquipmentAlreadyOwn,
    EquipmentBuy,
    EquipmentEscapePod,
    EquipmentExtraBaysInUse,
    EquipmentFuelCompactor,
    EquipmentIF,
    EquipmentLightningShield,
    EquipmentNotEnoughSlots,
    EquipmentSell,
    FileErrorOpen,
    FileErrorSave,
    FleaBuilt,
    GameAbandonConfirm,
    GameClearHighScores,
    GameEndBoughtMoon,
    GameEndHighScoreAchieved,
    GameEndHighScoreCheat,
    GameEndHighScoreMissed,
    GameEndKilled,
    GameEndRetired,
    GameEndScore,
    GameRetire,
    InsuranceNoEscapePod,
    InsurancePayoff,
    InsuranceStop,
    JailConvicted,
    JailFleaReceived,
    JailIllegalGoodsImpounded,
    JailInsuranceLost,
    JailMercenariesLeave,
    JailShipSold,
    LeavingIFInsurance,
    LeavingIFMercenaries,
    LeavingIFWormholeTax,
    MeetCaptainAhab,
    MeetCaptainConrad,
    MeetCaptainHuie,
    NewGameConfirm,
    NewGameMoreSkillPoints,
    OptionsNoGame,
    PreciousHidden,
    RegistryError,
    ShipBuyConfirm,
    ShipBuyCrewQuarters,
    ShipBuyIF,
    ShipBuyIFTransfer,
    ShipBuyNoSlots,
    ShipBuyNotAvailable,
    ShipBuyNoTransfer,
    ShipBuyPassengerQuarters,
    ShipBuyTransfer,
    ShipDesignIF,
    ShipDesignThanks,
    ShipHullUpgraded,
    SpecialCleanRecord,
    SpecialExperimentPerformed,
    SpecialIF,
    SpecialMoonBought,
    SpecialNoQuarters,
    SpecialNotEnoughBays,
    SpecialPassengerOnBoard,
    SpecialSealedCanisters,
    SpecialSkillIncrease,
    SpecialTimespaceFabricRip,
    SpecialTrainingCompleted,
    TravelArrival,
    TravelUneventfulTrip,
    TribblesAllDied,
    TribblesAteFood,
    TribblesGone,
    TribblesHalfDied,
    TribblesKilled,
    TribblesMostDied,
    TribblesOwn,
    TribblesRemoved,
    TribblesInspector,
    TribblesSqueek,
    TribblesTradeIn,
    Quest; //TODO need???

    @Override
    public int castToInt() {
        return ordinal();
    }
}
