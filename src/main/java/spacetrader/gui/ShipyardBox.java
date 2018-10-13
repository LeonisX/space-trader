package spacetrader.gui;

import java.awt.Point;

import jwinforms.DialogResult;
import jwinforms.EventArgs;
import jwinforms.EventHandler;
import spacetrader.Commander;
import spacetrader.Consts;
import spacetrader.Strings;
import spacetrader.enums.AlertType;
import spacetrader.enums.ShipType;
import spacetrader.guifacade.GuiFacade;

public class ShipyardBox extends jwinforms.GroupBox
{
	private Commander commander;

	void setGame(Commander commander)
	{
		this.commander = commander;
	}

	private final SpaceTrader mainWindow;

	public ShipyardBox(SpaceTrader mainWindow)
	{
		this.mainWindow = mainWindow;
	}


	private jwinforms.Button btnDesign;
	private jwinforms.Button btnPod;
	private jwinforms.Label lblEquipForSale;
	private jwinforms.Label lblEscapePod;
	private jwinforms.Button btnEquip;
	private jwinforms.Button btnBuyShip;
	private jwinforms.Label lblShipsForSale;

	void InitializeComponent()
	{
		btnDesign = new jwinforms.Button();
		btnPod = new jwinforms.Button();
		lblEscapePod = new jwinforms.Label();
		btnEquip = new jwinforms.Button();
		btnBuyShip = new jwinforms.Button();
		lblEquipForSale = new jwinforms.Label();
		lblShipsForSale = new jwinforms.Label();

		SuspendLayout();

		Controls.add(btnDesign);
		Controls.add(btnPod);
		Controls.add(lblEscapePod);
		Controls.add(btnEquip);
		Controls.add(btnBuyShip);
		Controls.add(lblEquipForSale);
		Controls.add(lblShipsForSale);
		setName("boxShipYard");
		setSize(new jwinforms.Size(168, 168));
		setTabIndex(4);
		setTabStop(false);
		setText("Shipyard");
		//
		// btnDesign
		//
		btnDesign.setFlatStyle(jwinforms.FlatStyle.Flat);
		btnDesign.setLocation(new Point(8, 32));
		btnDesign.setName("btnDesign");
		btnDesign.setSize(new jwinforms.Size(54, 22));
		btnDesign.setTabIndex(55);
		btnDesign.setText("Design");
		btnDesign.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				btnDesign_Click(sender, e);
			}
		});
		//
		// btnPod
		//
		btnPod.setFlatStyle(jwinforms.FlatStyle.Flat);
		btnPod.setLocation(new Point(98, 138));
		btnPod.setName("btnPod");
		btnPod.setSize(new jwinforms.Size(58, 22));
		btnPod.setTabIndex(54);
		btnPod.setText("Buy Pod");
		btnPod.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				btnPod_Click(sender, e);
			}
		});
		//
		// lblEscapePod
		//
		lblEscapePod.setLocation(new Point(8, 122));
		lblEscapePod.setName("lblEscapePod");
		lblEscapePod.setSize(new jwinforms.Size(152, 26));
		lblEscapePod.setTabIndex(27);
		lblEscapePod.setText("You can buy an escape pod for  2,000 cr.");
		//
		// btnEquip
		//
		btnEquip.setFlatStyle(jwinforms.FlatStyle.Flat);
		btnEquip.setLocation(new Point(43, 85));
		btnEquip.setName("btnEquip");
		btnEquip.setSize(new jwinforms.Size(113, 22));
		btnEquip.setTabIndex(53);
		btnEquip.setText("Buy/Sell Equipment");
		btnEquip.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				btnEquip_Click(sender, e);
			}
		});
		//
		// btnBuyShip
		//
		btnBuyShip.setFlatStyle(jwinforms.FlatStyle.Flat);
		btnBuyShip.setLocation(new Point(70, 32));
		btnBuyShip.setName("btnBuyShip");
		btnBuyShip.setSize(new jwinforms.Size(86, 22));
		btnBuyShip.setTabIndex(52);
		btnBuyShip.setText("View Ship Info");
		btnBuyShip.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				btnBuyShip_Click(sender, e);
			}
		});
		//
		// lblEquipForSale
		//
		lblEquipForSale.setLocation(new Point(8, 69));
		lblEquipForSale.setName("lblEquipForSale");
		lblEquipForSale.setSize(new jwinforms.Size(152, 13));
		lblEquipForSale.setTabIndex(21);
		lblEquipForSale.setText("There is equipment for sale.");
		//
		// lblShipsForSale
		//
		lblShipsForSale.setLocation(new Point(8, 16));
		lblShipsForSale.setName("lblShipsForSale");
		lblShipsForSale.setSize(new jwinforms.Size(152, 13));
		lblShipsForSale.setTabIndex(20);
		lblShipsForSale.setText("There are new ships for sale.");
	}

	public void Update()
	{
		if (commander == null)
		{
			lblShipsForSale.setText("");
			lblEquipForSale.setText("");
			lblEscapePod.setText("");
			btnPod.setVisible(false);
			btnBuyShip.setVisible(false);
			btnDesign.setVisible(false);
			btnEquip.setVisible(false);
		} else
		{
			boolean noTech = (commander.getCurrentSystem().TechLevel().CastToInt() < Consts.ShipSpecs[ShipType.Flea
					.CastToInt()].MinimumTechLevel().CastToInt());

			lblShipsForSale.setText(noTech ? Strings.ShipyardShipNoSale : Strings.ShipyardShipForSale);
			btnBuyShip.setVisible(true);
			btnDesign.setVisible(commander.getCurrentSystem().Shipyard() != null);

			lblEquipForSale.setText(noTech ? Strings.ShipyardEquipNoSale : Strings.ShipyardEquipForSale);
			btnEquip.setVisible(true);

			btnPod.setVisible(false);
			if (commander.getShip().getEscapePod())
				lblEscapePod.setText(Strings.ShipyardPodInstalled);
			else if (noTech)
				lblEscapePod.setText(Strings.ShipyardPodNoSale);
			else if (commander.getCash() < 2000)
				lblEscapePod.setText(Strings.ShipyardPodIF);
			else
			{
				lblEscapePod.setText(Strings.ShipyardPodCost);
				btnPod.setVisible(true);
			}
		}
	}

	private void btnBuyShip_Click(Object sender, EventArgs e)
	{
		new FormShipList().ShowDialog(mainWindow);
		mainWindow.UpdateAll();
	}

	private void btnDesign_Click(Object sender, EventArgs e)
	{
		new FormShipyard().ShowDialog(mainWindow);
		mainWindow.UpdateAll();
	}

	private void btnEquip_Click(Object sender, EventArgs e)
	{
		new FormEquipment().ShowDialog(mainWindow);
		mainWindow.UpdateAll();
	}

	private void btnPod_Click(Object sender, EventArgs e)
	{
		if (GuiFacade.alert(AlertType.EquipmentEscapePod) == DialogResult.Yes)
		{
			commander.setCash(commander.getCash() - 2000);
			commander.getShip().setEscapePod(true);
			mainWindow.UpdateAll();
		}
	}
}
