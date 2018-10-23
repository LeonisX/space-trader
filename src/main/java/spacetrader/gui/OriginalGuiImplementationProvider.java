package spacetrader.gui;

import spacetrader.controls.Image;
import spacetrader.controls.ImageList;
import spacetrader.gui.cheat.FormMonster;
import spacetrader.guifacade.GuiEngine.CheatGui;
import spacetrader.guifacade.GuiEngine.ImageProvider;
import spacetrader.guifacade.GuiEngine.ImplementationProvider;

public class OriginalGuiImplementationProvider implements ImplementationProvider {
    private final SpaceTrader spaceTrader;

    public OriginalGuiImplementationProvider(SpaceTrader spaceTrader) {
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
                (new FormMonster()).showDialog();
            }

            public void showTestForm() {
                (new FormTest()).showDialog();
            }
        };
    }

}
