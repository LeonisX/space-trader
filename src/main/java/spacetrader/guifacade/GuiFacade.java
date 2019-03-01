package spacetrader.guifacade;

import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.WinformPane;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.CargoBuyOp;
import spacetrader.game.enums.CargoSellOp;
import spacetrader.game.enums.EncounterResult;
import spacetrader.gui.*;

/**
 * Ment to be the connecting link between the game engine and the GUI. Will help later
 * with replacing the GUI layer.
 * <p>
 * It holds things refactored UP from the controller classes. Also see GameController.
 *
 * @author Aviv
 */
public class GuiFacade {
    /**
     * Asks the user for how many items to buy (or steal).
     *
     * @return amount selected by the user to buy; 0 if clicked cancel.
     */
    public static int queryAmountAcquire(int item, int maxAmount, CargoBuyOp op) {
        FormCargoBuy form = new FormCargoBuy(item, maxAmount, op);
        return form.showDialog() == DialogResult.OK ? form.Amount() : 0;
    }

    /**
     * Asks the user for how many items to sell.
     *
     * @return amount selected by the user to sell; 0 if clicked cancel.
     */
    public static int queryAmountRelease(int item, CargoSellOp op, int maxAmount, int price) {
        FormCargoSell form = new FormCargoSell(item, maxAmount, op, price);
        return form.showDialog() == DialogResult.OK ? form.Amount() : 0;
    }

    /**
     * Lets the user Plunder an un-opposing ship. The gui handles the what and the how much of it.
     * <p>
     * todo smells like there's too much game code in that form.
     */
    public static void performPlundering() {
        (new FormPlunder()).showDialog();
    }

    /**
     * Lets the user get rid of items. The gui handles the what and the how much of it.
     * <p>
     * todo smells like there's too much game code in that form.
     */
    public static void performJettison() {
        (new FormJettison()).showDialog();
    }

    /**
     * Starts an encounter - note that the GUI apparently decides which kind.
     * <p>
     * todo smells like there's too much game code in that form.
     */
    public static EncounterResult performEncounter(GuiWindow parentWindow) {
        FormEncounter form = new FormEncounter();
        form.showDialog((WinformPane) parentWindow);
        return form.getResult();
    }

    public static void performMonsterCom() {
        GuiEngine.getCheat().showMonsterForm();
    }

    public static void performTestForm() {
        GuiEngine.getCheat().showTestForm();
    }

    public static DialogResult alert(AlertType type) {
        return FormAlert.alert(type);
    }

    public static DialogResult alert(AlertType type, String var1, String var2) {
        return FormAlert.alert(type, var1, var2);
    }

    public static DialogResult alert(AlertType type, String var1, String var2, String var3) {
        return FormAlert.alert(type, var1, var2, var3);
    }

    public static DialogResult alert(AlertType type, String[] args) {
        return FormAlert.alert(type, args);
    }

    public static DialogResult alert(AlertType type, String var1) {
        return FormAlert.alert(type, var1);
    }

    public static DialogResult alert(AlertType type, WinformPane owner, String var1) {
        return FormAlert.alert(type, owner, var1);
    }
}
