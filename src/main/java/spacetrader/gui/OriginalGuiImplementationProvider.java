package spacetrader.gui;

import jwinforms.Image;
import jwinforms.ImageList;
import spacetrader.guifacade.GuiEngine.CheatGui;
import spacetrader.guifacade.GuiEngine.ImageProvider;
import spacetrader.guifacade.GuiEngine.ImplementationProvider;

public class OriginalGuiImplementationProvider implements ImplementationProvider
{
	private final SpaceTrader spaceTrader;

	public OriginalGuiImplementationProvider(SpaceTrader spaceTrader)
	{
		super();
		this.spaceTrader = spaceTrader;
	}

	public ImageProvider getImageProvider()
	{
		return new ImageProvider()
		{
			public ImageList getEquipmentImages()
			{
				return spaceTrader.EquipmentImages();
			}

			public ImageList getShipImages()
			{
				return spaceTrader.ShipImages();
			}

			public Image[] getCustomShipImages()
			{
				return spaceTrader.CustomShipImages();
			}

			public void setCustomShipImages(Image[] value)
			{
				spaceTrader.setCustomShipImages(value);
			}

			public Image[] getDirectionImages()
			{
				return spaceTrader.DirectionImages().getImages();
			}
		};
	}

	public CheatGui getCheatGuiProvider()
	{
		return new CheatGui()
		{
			public void showMonsterForm()
			{
				(new FormMonster()).Show();
			}

			public void showTestForm()
			{
				(new FormTest()).Show();
			}
		};
	}

}
