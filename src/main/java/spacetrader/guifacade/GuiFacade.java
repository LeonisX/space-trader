package spacetrader.guifacade;

import jwinforms.DialogResult;
import jwinforms.WinformPane;
import spacetrader.gui.*;
import spacetrader.enums.AlertType;
import spacetrader.enums.CargoBuyOp;
import spacetrader.enums.CargoSellOp;
import spacetrader.enums.EncounterResult;

/**
 * Ment to be the connecting link between the game engien and the GUI. Will help later
 * with replacing the GUI layer.
 *
 * It holds things refactored UP from the controller classes. Also see GameController.
 *
 * @author Aviv
 *
 */
public class GuiFacade
{
	/**
	 * Asks the user for how many items to buy (or steal).
	 *
	 * @return amount selected by the user to buy; 0 if clicked Cancel.
	 */
	public static int queryAmountAcquire(int item, int maxAmount, CargoBuyOp op)
	{
		FormCargoBuy form = new FormCargoBuy(item, maxAmount, op);
		return form.Show() == DialogResult.OK ? form.Amount() : 0;
	}

	/**
	 * Asks the user for how many items to sell.
	 *
	 * @return amount selected by the user to buy; 0 if clicked Cancel.
	 */
	public static int queryAmountRelease(int item, CargoSellOp op, int maxAmount, int price)
	{
		FormCargoSell form = new FormCargoSell(item, maxAmount, op, price);
		return form.Show() == DialogResult.OK ? form.Amount() : 0;
	}

	/**
	 * Lets the user Plunder an un-opposing ship. The gui handles the what and the how
	 * much of it.
	 *
	 * todo smells like there's too much game code in that form.
	 */
	public static void performPlundering()
	{
		(new FormPlunder()).Show();
	}

	/**
	 * Lets the user get rid of items. The gui handles the what and the how much of it.
	 *
	 * todo smells like there's too much game code in that form.
	 */
	public static void performJettison()
	{
		(new FormJettison()).Show();
	}

	/**
	 * Starts an encounter - note that the GUI apperantly decides which kind.
	 *
	 * todo smells like there's too much game code in that form.
	 */
	public static EncounterResult performEncounter(GuiWindow parentWindow)
	{
		FormEncounter form = new FormEncounter();
		form.ShowDialog((WinformPane)parentWindow);
		return form.Result();
	}

	public static void performMonsterCom()
	{
		GuiEngine.cheat.showMonsterForm();
	}

	public static void performTestForm()
	{
		GuiEngine.cheat.showTestForm();
	}


	public static DialogResult alert(AlertType type)
	{
		return FormAlert.Alert(type);
	}

	public static DialogResult alert(AlertType type, String var1, String var2)
	{
		return FormAlert.Alert(type, var1, var2);
	}

	public static DialogResult alert(AlertType type, String var1, String var2, String var3)
	{
		return FormAlert.Alert(type, var1, var2, var3);
	}

	public static DialogResult alert(AlertType type, String[] args)
	{
		return FormAlert.Alert(type, args);
	}
	public static DialogResult alert(AlertType type, String var1)
	{
		return FormAlert.Alert(type, var1);
	}
}
