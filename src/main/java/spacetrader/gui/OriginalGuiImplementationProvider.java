package spacetrader.gui;

import jwinforms.Image;
import jwinforms.ImageList;
import spacetrader.guifacade.GuiEngine.CheatGui;
import spacetrader.guifacade.GuiEngine.ImageProvider;
import spacetrader.guifacade.GuiEngine.ImplementationProvider;

public class OriginalGuiImplementationProvider implements ImplementationProvider {
    private final SpaceTrader spaceTrader;

    OriginalGuiImplementationProvider(SpaceTrader spaceTrader) {
        super();
        this.spaceTrader = spaceTrader;
    }

    public ImageProvider getImageProvider() {
        return new ImageProvider() {
            public ImageList getEquipmentImages() {
                return spaceTrader.equipmentImages();
            }

            public ImageList getShipImages() {
                return spaceTrader.shipImages();
            }

            public Image[] getCustomShipImages() {
                return spaceTrader.customShipImages();
            }

            public void setCustomShipImages(Image[] value) {
                spaceTrader.setCustomShipImages(value);
            }

            public Image[] getDirectionImages() {
                return spaceTrader.directionImages().getImages();
            }
        };
    }

    public CheatGui getCheatGuiProvider() {
        return new CheatGui() {
            public void showMonsterForm() {
                (new FormMonster()).Show();
            }

            public void showTestForm() {
                (new FormTest()).Show();
            }
        };
    }

}
