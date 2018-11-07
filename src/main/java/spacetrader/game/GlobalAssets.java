package spacetrader.game;

import spacetrader.controls.Image;
import spacetrader.controls.ImageList;
import spacetrader.controls.ImageListStreamer;
import spacetrader.controls.ResourceManager;
import spacetrader.controls.Size;
import spacetrader.game.enums.ShipType;
import spacetrader.gui.SpaceTrader;
import spacetrader.stub.PropertiesLoader;
import spacetrader.stub.StringsBundle;
import spacetrader.stub.ValuesBundle;

import java.awt.*;

public class GlobalAssets {

    private static final String STRINGS_TEMPLATE = "strings/%s.properties";
    private static final String DIMENSIONS_TEMPLATE = "dimensions/%s.properties";
    private static final String VERSION_FILE = "version.properties";

    private static ImageList ilChartImages;
    private static ImageList ilDirectionImages;
    private static ImageList ilEquipmentImages;
    private static ImageList ilShipImages;

    private static StringsBundle strings = new StringsBundle();
    private static ValuesBundle dimensions = new ValuesBundle();
    private static StringsBundle versions = new StringsBundle();

    public static void initializeImages() {
        ResourceManager resources = new ResourceManager(SpaceTrader.class);

        ilChartImages = new ImageList(null);
        ilShipImages = new ImageList(null);
        ilDirectionImages = new ImageList(null);
        ilEquipmentImages = new ImageList(null);

        ilChartImages.setImageSize(new Size(7, 7));
        ilChartImages.setImageStream(((ImageListStreamer) (resources.getObject("ilChartImages.ImageStream"))));
        ilChartImages.setTransparentColor(Color.white);

        ilShipImages.setImageSize(new Size(64, 52));
        ilShipImages.setImageStream(((ImageListStreamer) (resources.getObject("ilShipImages.ImageStream"))));
        ilShipImages.setTransparentColor(Color.white);

        ilDirectionImages.setImageSize(new Size(13, 13));
        ilDirectionImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilDirectionImages.ImageStream"))));
        ilDirectionImages.setTransparentColor(Color.white);

        ilEquipmentImages.setImageSize(new Size(64, 52));
        ilEquipmentImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilEquipmentImages.ImageStream"))));
        ilEquipmentImages.setTransparentColor(Color.white);
    }

    public static void loadStrings(String fileName) {
        strings = PropertiesLoader.getStringsBundle(String.format(STRINGS_TEMPLATE, fileName));
    }

    public static void loadDimensions(String fileName) {
        dimensions = PropertiesLoader.getValuesBundle(String.format(DIMENSIONS_TEMPLATE, fileName));
    }

    public static void loadVersions() {
        versions = PropertiesLoader.getStringsBundle(VERSION_FILE);
    }

    public static StringsBundle getStrings() {
        return strings;
    }

    public static ValuesBundle getDimensions() {
        return dimensions;
    }

    public static StringsBundle getVersions() {
        return versions;
    }

    public static ImageList getChartImages() {
        return ilChartImages;
    }

    public static ImageList getDirectionImages() {
        return ilDirectionImages;
    }

    public static ImageList getEquipmentImages() {
        return ilEquipmentImages;
    }

    public static ImageList getShipImages() {
        return ilShipImages;
    }

    public static Image[] getCustomShipImages() {
        spacetrader.controls.Image[] images = new spacetrader.controls.Image[Consts.ImagesPerShip];
        int baseIndex = ShipType.CUSTOM.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++) {
            images[index] = GlobalAssets.getShipImages().getImages()[baseIndex + index];
        }
        return images;
    }

    public static void setCustomShipImages(Image[] value) {
        int baseIndex = ShipType.CUSTOM.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++) {
            GlobalAssets.getShipImages().getImages()[baseIndex + index] = value[index];
        }
    }

}
