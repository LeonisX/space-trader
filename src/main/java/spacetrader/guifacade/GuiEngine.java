package spacetrader.guifacade;

import jwinforms.Image;
import jwinforms.ImageList;

/**
 * Install your GUI implementation here. At run-time. todo install default implementation
 *
 * @author Aviv
 */
public class GuiEngine
{
	// todo assert only called once by the user?
	static public void installImplementation(ImplementationProvider impl)
	{
		imageProvider = impl.getImageProvider();
		cheat = impl.getCheatGuiProvider();
	}

	public static ImageProvider imageProvider;
	public static CheatGui cheat;

	// note - internal interfaces are implicitlystatic. see section 8.5.2 in the spec.
	public interface ImageProvider
	{
		ImageList getEquipmentImages();

		ImageList getShipImages();

		void setCustomShipImages(Image[] value);

		Image[] getCustomShipImages();

		Image[] getDirectionImages();
	}

	public interface CheatGui
	{
		void showMonsterForm();

		void showTestForm();
	}

	public interface ImplementationProvider
	{
		ImageProvider getImageProvider();

		CheatGui getCheatGuiProvider();
	}
}
