package spacetrader.guifacade;

import spacetrader.controls.Image;
import spacetrader.controls.ImageList;

/**
 * Install your GUI implementation here. At run-time. todo install default implementation
 *
 * @author Aviv
 */
public class GuiEngine {

    private static ImageProvider imageProvider;
    private static CheatGui cheat;

    // todo assert only called once by the user?
    static public void installImplementation(ImplementationProvider impl) {
        imageProvider = impl.getImageProvider();
        cheat = impl.getCheatGuiProvider();
    }

    public static ImageProvider getImageProvider() {
        return imageProvider;
    }

    public static CheatGui getCheat() {
        return cheat;
    }

    // note - internal interfaces are implicitly static. see section 8.5.2 in the spec.
    public interface ImageProvider {
        ImageList getEquipmentImages();

        ImageList getShipImages();

        Image[] getCustomShipImages();

        void setCustomShipImages(Image[] value);

        Image[] getDirectionImages();
    }

    public interface CheatGui {
        void showMonsterForm();

        void showTestForm();
    }

    public interface ImplementationProvider {
        ImageProvider getImageProvider();

        CheatGui getCheatGuiProvider();
    }
}
