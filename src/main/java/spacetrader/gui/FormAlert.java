package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.enums.ColorDepth;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.Game;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.GameEndType;
import spacetrader.guifacade.Facaded;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.awt.*;

import static spacetrader.game.Strings.*;

@Facaded
public class FormAlert extends SpaceTraderForm {

    private static final String DA_80_CHARS = "01234567890123456789012345678901234567890123456789012345678901234567890123456789";

    private static final int SPLASH_INDEX = 4;

    private Label messageLabel = new Label();
    private Button acceptButton = new Button();
    private Button cancelButton = new Button();

    private ImageList ilImages = new ImageList();
    private Timer timer = new Timer();

    public FormAlert(String title, String text, String acceptButtonText, DialogResult acceptButtonResult,
                     String cancelButtonText, DialogResult cancelButtonResult, String[] args) {

        initializeComponent();

        // Replace any variables.
        if (args != null) {
            title = Functions.stringVars(title, args);
            text = Functions.stringVars(text, args);
        }

        FontMetrics metrics = messageLabel.asSwingObject().getFontMetrics(messageLabel.asSwingObject().getFont());

        messageLabel.setWidth(metrics.stringWidth((text.length() > 80) ? DA_80_CHARS : text));
        messageLabel.setText(text);
        messageLabel.setWidth(messageLabel.getMaxLineWidth());
        messageLabel.setHeight(metrics.getHeight() * messageLabel.getLinesCount());

        // Size the buttons.
        int btnWidth;
        acceptButton.setAutoWidth(true);
        acceptButton.setText(acceptButtonText);
        acceptButton.setDialogResult(acceptButtonResult);
        btnWidth = acceptButton.getWidth();
        if (cancelButtonText != null) {
            cancelButton.setAutoWidth(true);
            cancelButton.setText(cancelButtonText);
            cancelButton.setVisible(true);
            cancelButton.setDialogResult(cancelButtonResult);
            btnWidth += cancelButton.getWidth() + 6;
        }

        // Size the form.
        setWidth(Math.max(btnWidth, messageLabel.getWidth()) + 16);
        setHeight(messageLabel.getHeight() + 52);

        // Locate the 
        messageLabel.setLeft((this.getWidth() - messageLabel.getWidth()) / 2);
        acceptButton.setTop(messageLabel.getHeight() + 18);
        acceptButton.setLeft((this.getWidth() - btnWidth) / 2);
        cancelButton.setTop(acceptButton.getTop());
        cancelButton.setLeft(acceptButton.getLeft() + acceptButton.getWidth() + 6);

        // Set the title.
        this.setText(title);
    }

    public FormAlert(String title, int imageIndex) {
        initializeComponent();
        // Make sure the extra spacetrader.controls are hidden.
        messageLabel.setVisible(false);
        cancelButton.setVisible(false);

        // Move acceptButton off-screen.
        acceptButton.setLeft(-acceptButton.getWidth());
        acceptButton.setTop(-acceptButton.getHeight());
        setAcceptButton(acceptButton);
        setCancelButton(acceptButton);

        // Set the background image.
        setBackgroundImage(ilImages.getImages()[imageIndex]);
        setClientSize(getBackgroundImage().getWidth(), getBackgroundImage().getHeight());

        // Set the title.
        setText(title);

        // If this is the splash screen, get rid of the title bar and start the timer.
        if (imageIndex == SPLASH_INDEX) {
            this.setFormBorderStyle(FormBorderStyle.NONE);
            timer.start();
        }
    }

    @Facaded
    public static DialogResult alert(AlertType type) {
        return alert(type, new String[]{});
    }

    @Facaded
    public static DialogResult alert(AlertType type, String... args) {
        if (null == Game.getCurrentGame()) {
            return makeDialog(type, args).showDialog();
        } else {
            return alert(type, (WinformPane) Game.getCurrentGame().getParentWindow(), args);
        }
    }

    @Facaded
    public static DialogResult alert(AlertType type, WinformPane owner, String... args) {
        return makeDialog(type, args).showDialog(owner);
    }

    public static FormAlert makeDialog(AlertType type, String[] args) {
        switch (type) {
            case Alert:
                return new FormAlert(AlertsAlertTitle, AlertsAlertMessage, AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case AppStart:
                return new FormAlert(AlertsAppStartTitle, SPLASH_INDEX);
            case ArrivalBuyNewspaper:
                return new FormAlert(AlertsArrivalBuyNewspaperTitle, AlertsArrivalBuyNewspaperMessage,
                        AlertsArrivalBuyNewspaperAccept, DialogResult.YES, AlertsCancel, DialogResult.NO, args);
            case ArrivalIFFuel:
                return new FormAlert(AlertsArrivalIFFuelTitle, AlertsArrivalIFFuelMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case ArrivalIFFuelRepairs:
                return new FormAlert(AlertsArrivalIFFuelRepairsTitle, AlertsArrivalIFFuelRepairsMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case ArrivalIFNewspaper:
                return new FormAlert(AlertsArrivalIFNewspaperTitle, AlertsArrivalIFNewspaperMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case ArrivalIFRepairs:
                return new FormAlert(AlertsArrivalIFRepairsTitle, AlertsArrivalIFRepairsMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case CargoIF:
                return new FormAlert(AlertsCargoIFTitle, AlertsCargoIFMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case CargoNoEmptyBays:
                return new FormAlert(AlertsCargoNoEmptyBaysTitle, AlertsCargoNoEmptyBaysMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case CargoNoneAvailable:
                return new FormAlert(AlertsCargoNoneAvailableTitle, AlertsCargoNoneAvailableMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case CargoNoneToSell:
                return new FormAlert(AlertsCargoNoneToSellTitle, AlertsCargoNoneToSellMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case CargoNotInterested:
                return new FormAlert(AlertsCargoNotInterestedTitle, AlertsCargoNotInterestedMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case CargoNotSold:
                return new FormAlert(AlertsCargoNotSoldTitle, AlertsCargoNotSoldMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case ChartJump:
                return new FormAlert(AlertsChartJumpTitle, AlertsChartJumpMessage, AlertsChartJumpAccept,
                        DialogResult.YES, AlertsChartJumpCancel, DialogResult.NO, args);
            case ChartJumpCurrent:
                return new FormAlert(AlertsChartJumpCurrentTitle, AlertsChartJumpCurrentMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ChartJumpNoSystemSelected:
                return new FormAlert(AlertsChartJumpNoSystemSelectedTitle, AlertsChartJumpNoSystemSelectedMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            //TODO unused???
            case ChartTrackSystem:
                return new FormAlert(AlertsChartTrackSystemTitle, AlertsChartTrackSystemMessage, AlertsYes, DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case ChartWormholeUnreachable:
                return new FormAlert(AlertsChartWormholeUnreachableTitle, AlertsChartWormholeUnreachableMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case Cheater:
                return new FormAlert(AlertsCheaterTitle, AlertsCheaterMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case CrewFireMercenary:
                return new FormAlert(AlertsCrewFireMercenaryTitle, AlertsCrewFireMercenaryMessage, AlertsYes, DialogResult.YES, AlertsNo,
                        DialogResult.NO, args);
            case CrewNoQuarters:
                return new FormAlert(AlertsCrewNoQuartersTitle, AlertsCrewNoQuartersMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case DebtNoBuy:
                return new FormAlert(AlertsDebtNoBuyTitle, AlertsDebtNoBuyMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case DebtNone:
                return new FormAlert(AlertsDebtNoneTitle, AlertsDebtNoneMessage, AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case DebtReminder:
                return new FormAlert(AlertsDebtReminderTitle, AlertsDebtReminderMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case DebtTooLargeGrounded:
                return new FormAlert(AlertsDebtTooLargeGroundedTitle, AlertsDebtTooLargeGroundedMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case DebtTooLargeLoan:
                return new FormAlert(AlertsDebtTooLargeLoanTitle, AlertsDebtTooLargeLoanMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case DebtTooLargeTrade:
                return new FormAlert(AlertsDebtTooLargeTradeTitle, AlertsDebtTooLargeTradeMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case DebtWarning:
                return new FormAlert(AlertsDebtWarningTitle, AlertsDebtWarningMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterArrested:
                return new FormAlert(AlertsEncounterArrestedTitle, AlertsEncounterArrestedMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterAttackNoDisruptors:
                return new FormAlert(AlertsEncounterAttackNoDisruptorsTitle, AlertsEncounterAttackNoDisruptorsMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterAttackNoLasers:
                return new FormAlert(AlertsEncounterAttackNoLasersTitle, AlertsEncounterAttackNoLasersMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case EncounterAttackNoWeapons:
                return new FormAlert(AlertsEncounterAttackNoWeaponsTitle, AlertsEncounterAttackNoWeaponsMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case EncounterAttackPolice:
                return new FormAlert(AlertsEncounterAttackPoliceTitle, AlertsEncounterAttackPoliceMessage, AlertsYes,
                        DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case EncounterAttackTrader:
                return new FormAlert(AlertsEncounterAttackTraderTitle, AlertsEncounterAttackTraderMessage,
                        AlertsYes, DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case EncounterBothDestroyed:
                return new FormAlert(AlertsEncounterBothDestroyedTitle, AlertsEncounterBothDestroyedMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterDisabledOpponent:
                return new FormAlert(AlertsEncounterDisabledOpponentTitle, AlertsEncounterDisabledOpponentMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            //TODO unused???
            case EncounterDumpAll:
                //TODO credit(s)
                return new FormAlert(AlertsEncounterDumpAllTitle, AlertsEncounterDumpAllMessage, AlertsYes,
                        DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case EncounterDumpWarning:
                if (Game.getCurrentGame() != null) {
                    Game.getCurrentGame().setLitterWarning(true);
                }
                return new FormAlert(AlertsEncounterDumpWarningTitle, AlertsEncounterDumpWarningMessage,
                        AlertsYes, DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case EncounterEscaped:
                return new FormAlert(AlertsEncounterEscapedTitle, AlertsEncounterEscapedMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case EncounterEscapedHit:
                return new FormAlert(AlertsEncounterEscapedHitTitle, AlertsEncounterEscapedHitMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case EncounterEscapePodActivated:
                return new FormAlert(AlertsEncounterEscapePodActivatedTitle, AlertsEncounterEscapePodActivatedMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterLooting:
                return new FormAlert(AlertsEncounterLootingTitle, AlertsEncounterLootingMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterOpponentEscaped:
                return new FormAlert(AlertsEncounterOpponentEscapedTitle, AlertsEncounterOpponentEscapedMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case EncounterPiratesBounty:
                return new FormAlert(AlertsEncounterPiratesBountyTitle, AlertsEncounterPiratesBountyMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterPiratesFindNoCargo:
                return new FormAlert(AlertsEncounterPiratesFindNoCargoTitle, AlertsEncounterPiratesFindNoCargoMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterPoliceBribe:
                return new FormAlert(AlertsEncounterPoliceBribeTitle, AlertsEncounterPoliceBribeMessage, AlertsEncounterPoliceBribeAccept,
                        DialogResult.YES, AlertsEncounterPoliceBribeCancel, DialogResult.NO, args);
            case EncounterPoliceBribeCant:
                return new FormAlert(AlertsEncounterPoliceBribeCantTitle, AlertsEncounterPoliceBribeCantMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case EncounterPoliceBribeLowCash:
                return new FormAlert(AlertsEncounterPoliceBribeLowCashTitle, AlertsEncounterPoliceBribeLowCashMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case EncounterPoliceFine:
                return new FormAlert(AlertsEncounterPoliceFineTitle, AlertsEncounterPoliceFineMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterPoliceNothingFound:
                return new FormAlert(AlertsEncounterPoliceNothingFoundTitle, AlertsEncounterPoliceNothingFoundMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterPoliceNothingIllegal:
                return new FormAlert(AlertsEncounterPoliceNothingIllegalTitle, AlertsEncounterPoliceNothingIllegalMessage,
                        AlertsEncounterPoliceNothingIllegalAccept, DialogResult.YES, AlertsEncounterPoliceNothingIllegalCancel, DialogResult.NO, args);
            case EncounterPoliceSubmit:
                return new FormAlert(AlertsEncounterPoliceSubmitTitle, AlertsEncounterPoliceSubmitMessage, AlertsEncounterPoliceSubmitAccept,
                        DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case EncounterPoliceSurrender:
                return new FormAlert(AlertsEncounterPoliceSurrenderTitle, AlertsEncounterPoliceSurrenderMessage,
                        AlertsYes, DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case EncounterScoop:
                return new FormAlert(AlertsEncounterScoopTitle, AlertsEncounterScoopMessage,
                        AlertsEncounterScoopAccept, DialogResult.YES, AlertsEncounterScoopCancel, DialogResult.NO, args);
            case EncounterScoopNoRoom:
                return new FormAlert(AlertsEncounterScoopNoRoomTitle, AlertsEncounterScoopNoRoomMessage,
                        AlertsEncounterScoopNoRoomAccept, DialogResult.YES, AlertsEncounterScoopNoRoomCancel, DialogResult.NO, args);
            case EncounterScoopNoScoop:
                return new FormAlert(AlertsEncounterScoopNoScoopTitle, AlertsEncounterScoopNoScoopMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterSurrenderRefused:
                return new FormAlert(AlertsEncounterSurrenderRefusedTitle, AlertsEncounterSurrenderRefusedMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);

            case EncounterTradeCompleted:
                return new FormAlert(AlertsEncounterTradeCompletedTitle, AlertsEncounterTradeCompletedMessage,
                        AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case EncounterYouLose:
                return new FormAlert(AlertsEncounterYouLoseTitle, AlertsEncounterYouLoseMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case EncounterYouWin:
                return new FormAlert(AlertsEncounterYouWinTitle, AlertsEncounterYouWinMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case EquipmentAlreadyOwn:
                return new FormAlert(AlertsEquipmentAlreadyOwnTitle, AlertsEquipmentAlreadyOwnMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case EquipmentBuy:
                return new FormAlert(AlertsEquipmentBuyTitle, AlertsEquipmentBuyMessage, AlertsYes, DialogResult.YES,
                        AlertsNo, DialogResult.NO, args);
            case EquipmentEscapePod:
                return new FormAlert(AlertsEquipmentEscapePodTitle, AlertsEquipmentEscapePodMessage, AlertsYes,
                        DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case EquipmentExtraBaysInUse:
                return new FormAlert(AlertsEquipmentExtraBaysInUseTitle, AlertsEquipmentExtraBaysInUseMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EquipmentIF:
                return new FormAlert(AlertsEquipmentIFTitle, AlertsEquipmentIFMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case EquipmentNotEnoughSlots:
                return new FormAlert(AlertsEquipmentNotEnoughSlotsTitle, AlertsEquipmentNotEnoughSlotsMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case EquipmentSell:
                return new FormAlert(AlertsEquipmentSellTitle, AlertsEquipmentSellMessage, AlertsYes, DialogResult.YES,
                        AlertsNo, DialogResult.NO, args);
            case FileErrorOpen:
                return new FormAlert(AlertsFileErrorOpenTitle, AlertsFileErrorOpenMessage, AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case FileErrorSave:
                return new FormAlert(AlertsFileErrorSaveTitle, AlertsFileErrorSaveMessage, AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case FleaBuilt:
                return new FormAlert(AlertsFleaBuiltTitle, AlertsFleaBuiltMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case GameAbandonConfirm:
                return new FormAlert(AlertsGameAbandonConfirmTitle, AlertsGameAbandonConfirmMessage, AlertsYes,
                        DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case GameClearHighScores:
                return new FormAlert(AlertsGameClearHighScoresTitle, AlertsGameClearHighScoresMessage, AlertsYes,
                        DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case GameEndHighScoreAchieved:
                return new FormAlert(AlertsGameEndHighScoreAchievedTitle, AlertsGameEndHighScoreAchievedMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case GameEndHighScoreCheat:
                return new FormAlert(AlertsGameEndHighScoreCheatTitle, AlertsGameEndHighScoreCheatMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case GameEndHighScoreMissed:
                return new FormAlert(AlertsGameEndHighScoreMissedTitle, AlertsGameEndHighScoreMissedMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case GameEndKilled:
                return new FormAlert(AlertsGameEndKilledTitle, GameEndType.KILLED.castToInt());
            case GameEndRetired:
                return new FormAlert(AlertsGameEndRetiredTitle, GameEndType.RETIRED.castToInt());
            case GameEndScore:
                return new FormAlert(AlertsGameEndScoreTitle, AlertsGameEndScoreMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case GameRetire:
                return new FormAlert(AlertsGameRetireTitle, AlertsGameRetireMessage, AlertsYes, DialogResult.YES, AlertsNo,
                        DialogResult.NO, args);
            case InsuranceNoEscapePod:
                return new FormAlert(AlertsInsuranceNoEscapePodTitle, AlertsInsuranceNoEscapePodMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case InsurancePayoff:
                return new FormAlert(AlertsInsurancePayoffTitle, AlertsInsurancePayoffMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case InsuranceStop:
                return new FormAlert(AlertsInsuranceStopTitle, AlertsInsuranceStopMessage, AlertsYes,
                        DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case JailConvicted:
                return new FormAlert(AlertsJailConvictedTitle, AlertsJailConvictedMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case JailFleaReceived:
                return new FormAlert(AlertsJailFleaReceivedTitle, AlertsJailFleaReceivedMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case JailIllegalGoodsImpounded:
                return new FormAlert(AlertsJailIllegalGoodsImpoundedTitle, AlertsJailIllegalGoodsImpoundedMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case JailInsuranceLost:
                return new FormAlert(AlertsJailInsuranceLostTitle, AlertsJailInsuranceLostMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case JailMercenariesLeave:
                return new FormAlert(AlertsJailMercenariesLeaveTitle, AlertsJailMercenariesLeaveMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case JailShipSold:
                return new FormAlert(AlertsJailShipSoldTitle, AlertsJailShipSoldMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case LeavingIFInsurance:
                return new FormAlert(AlertsLeavingIFInsuranceTitle, AlertsLeavingIFInsuranceMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case LeavingIFMercenaries:
                return new FormAlert(AlertsLeavingIFMercenariesTitle, AlertsLeavingIFMercenariesMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case LeavingIFWormholeTax:
                return new FormAlert(AlertsLeavingIFWormholeTaxTitle, AlertsLeavingIFWormholeTaxMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case NewGameConfirm:
                return new FormAlert(AlertsNewGameConfirmTitle, AlertsNewGameConfirmMessage, AlertsYes, DialogResult.YES,
                        AlertsNo, DialogResult.NO, args);
            //TODO unused???
            case NewGameMoreSkillPoints:
                return new FormAlert(AlertsNewGameMoreSkillPointsTitle, AlertsNewGameMoreSkillPointsMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case OptionsNoGame:
                return new FormAlert(AlertsOptionsNoGameTitle, AlertsOptionsNoGameMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case PreciousHidden:
                return new FormAlert(AlertsPreciousHiddenTitle, AlertsPreciousHiddenMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case RegistryError:
                return new FormAlert(AlertsRegistryErrorTitle, AlertsRegistryErrorMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case ShipBuyConfirm:
                return new FormAlert(AlertsShipBuyConfirmTitle, AlertsShipBuyConfirmMessage, AlertsYes,
                        DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case ShipBuyCrewQuarters:
                return new FormAlert(AlertsShipBuyCrewQuartersTitle, AlertsShipBuyCrewQuartersMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ShipBuyIF:
                return new FormAlert(AlertsShipBuyIFTitle, AlertsShipBuyIFMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case ShipBuyIFTransfer:
                return new FormAlert(AlertsShipBuyIFTransferTitle, AlertsShipBuyIFTransferMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ShipBuyNoSlots:
                return new FormAlert(AlertsShipBuyNoSlotsTitle, AlertsShipBuyNoSlotsMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ShipBuyNotAvailable:
                return new FormAlert(AlertsShipBuyNotAvailableTitle, AlertsShipBuyNotAvailableMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ShipBuyNoTransfer:
                return new FormAlert(AlertsShipBuyNoTransferTitle, AlertsShipBuyNoTransferMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ShipBuyPassengerQuarters:
                return new FormAlert(AlertsShipBuyPassengerQuartersTitle, AlertsShipBuyPassengerQuartersMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case ShipBuyTransfer:
                return new FormAlert(AlertsShipBuyTransferTitle, AlertsShipBuyTransferMessage, AlertsShipBuyTransferAccept,
                        DialogResult.YES, AlertsShipBuyTransferCancel, DialogResult.NO, args);
            case ShipDesignIF:
                return new FormAlert(AlertsShipDesignIFTitle, AlertsShipDesignIFMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case ShipDesignThanks:
                return new FormAlert(AlertsShipDesignThanksTitle, AlertsShipDesignThanksMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case SpecialIF:
                return new FormAlert(AlertsSpecialIFTitle, AlertsSpecialIFMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case SpecialNoQuarters:
                return new FormAlert(AlertsSpecialNoQuartersTitle, AlertsSpecialNoQuartersMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case SpecialNotEnoughBays:
                return new FormAlert(AlertsSpecialNotEnoughBaysTitle, AlertsSpecialNotEnoughBaysMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            case SpecialPassengerOnBoard:
                return new FormAlert(AlertsSpecialPassengerOnBoardTitle, AlertsSpecialPassengerOnBoardMessage,
                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case TravelArrival:
                return new FormAlert(AlertsTravelArrivalTitle, AlertsTravelArrivalMessage, AlertsOk, DialogResult.OK, null,
                        DialogResult.NONE, args);
            case TravelUneventfulTrip:
                return new FormAlert(AlertsTravelUneventfulTripTitle, AlertsTravelUneventfulTripMessage, AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, args);
            default:
                throw new IllegalArgumentException("Unknown AlertType: " + type);
        }
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        ResourceManager resources = new ResourceManager(FormAlert.class);

        ilImages = new ImageList();
        timer = new Timer();

        setName("formAlert");
        //setText("Title");
        //setAutoScaleBaseSize(5, 13);
        setClientSize(270, 63);
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setShowInTaskbar(false);
        setControlBox(false);

        setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                // If the button is off-screen, this is an image and can be clicked away.
                if (acceptButton.getLeft() < 0) {
                    close();
                }
            }
        });

        //TODO delete all sizes and locations
        messageLabel.setFont(FontCollection.regular825);
        messageLabel.setLocation(8, 8);
        // this.messageLabel.setSize(12, 13);
        //messageLabel.setText("X");


        acceptButton.setDialogResult(DialogResult.OK);
        //acceptButton.setControlBinding(ControlBinding.CENTER);
        acceptButton.setAutoWidth(true);
        //acceptButton.setLocation(115, 32);
        acceptButton.setSize(40, 22);
        acceptButton.setTabIndex(1);
        //acceptButton.setText("Ok");
        acceptButton.setFont(FontCollection.regular825);

        cancelButton.setDialogResult(DialogResult.NO);
        cancelButton.setAutoWidth(true);
        //cancelButton.setControlBinding(ControlBinding.CENTER);
        //cancelButton.setLocation(200, 32);
        cancelButton.setSize(40, 22);
        cancelButton.setTabIndex(2);
        cancelButton.setText("No");
        cancelButton.setVisible(false);
        cancelButton.setFont(FontCollection.regular825);

        ilImages.setColorDepth(ColorDepth.DEPTH_24_BIT);
        ilImages.setImageSize(160, 160);
        ilImages.setImageStream((ImageListStreamer) (resources.getObject("ilImages.ImageStream")));
        ilImages.setTransparentColor(null);

        timer.setInterval(4000);
        timer.setTick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                FormAlert.this.close();
            }
        });

        controls.addAll(messageLabel, acceptButton, cancelButton);

        ReflectionUtils.loadControlsData(this);
    }
}
