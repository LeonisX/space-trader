package spacetrader.game;

import spacetrader.controls.Image;
import spacetrader.controls.ImageList;
import spacetrader.controls.ImageListStreamer;
import spacetrader.controls.ResourceManager;
import spacetrader.game.enums.Language;
import spacetrader.game.enums.ShipType;
import spacetrader.gui.SpaceTrader;
import spacetrader.stub.ObjectsBundle;
import spacetrader.stub.PropertiesLoader;
import spacetrader.stub.StringsBundle;
import spacetrader.util.IOUtils;

import java.awt.*;

public class GlobalAssets {

    private static final String STRINGS_TEMPLATE = "strings/%s.properties";
    private static final String DIMENSIONS_TEMPLATE = "dimensions/%s.properties";
    private static final String VERSION_FILE = "version.properties";
    private static final String VERSION = "version";

    private static boolean debug;

    private static Language language = Language.ENGLISH;

    private static ImageList ilChartImages;
    private static ImageList ilDirectionImages;
    private static ImageList ilEquipmentImages;
    private static ImageList ilShipImages;

    private static StringsBundle strings = new StringsBundle();
    private static ObjectsBundle dimensions = new ObjectsBundle();
    private static String version;

    public static void initializeImages() {
        ResourceManager resources = new ResourceManager(SpaceTrader.class);

        ilChartImages = new ImageList();
        ilShipImages = new ImageList();
        ilDirectionImages = new ImageList();
        ilEquipmentImages = new ImageList();

        ilChartImages.setImageSize(7, 7);
        ilChartImages.setImageStream(((ImageListStreamer) (resources.getObject("ilChartImages.ImageStream"))));
        ilChartImages.setTransparentColor(Color.white);

        ilShipImages.setImageSize(64, 52);
        ilShipImages.setImageStream(((ImageListStreamer) (resources.getObject("ilShipImages.ImageStream"))));
        ilShipImages.setTransparentColor(Color.white);

        ilDirectionImages.setImageSize(13, 13);
        ilDirectionImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilDirectionImages.ImageStream"))));
        ilDirectionImages.setTransparentColor(Color.white);

        ilEquipmentImages.setImageSize(64, 52);
        ilEquipmentImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilEquipmentImages.ImageStream"))));
        ilEquipmentImages.setTransparentColor(Color.white);
    }

    public static void loadStrings() {
        strings = PropertiesLoader.getStringsBundle(String.format(STRINGS_TEMPLATE, language.toString()));
    }

    public static void loadDimensions(String fileName) {
        dimensions = PropertiesLoader.getValuesBundle(String.format(DIMENSIONS_TEMPLATE, fileName));
    }

    public static void loadVersion() {
        version = PropertiesLoader.getStringsBundle(VERSION_FILE).getString(VERSION);
        if (null == version) {
            throw new IllegalStateException("Can't load version from " + VERSION_FILE);
        }
    }

    public static StringsBundle getStrings() {
        return strings;
    }

    public static ObjectsBundle getDimensions() {
        return dimensions;
    }

    public static String getVersion() {
        return version;
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

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        GlobalAssets.debug = debug;
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

    public static Language getLanguage() {
        return language;
    }

    public static void setLanguage(Language language) {
        GlobalAssets.language = language;
        IOUtils.setRegistrySetting("language", language.toString());
    }

    public static void loadLanguageFromRegistry() {
        String lang = IOUtils.getRegistrySetting("language", Language.ENGLISH.toString());
        try {
            language = Language.valueOf(lang.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal language name from Registry: " + lang);
        }
    }
}
