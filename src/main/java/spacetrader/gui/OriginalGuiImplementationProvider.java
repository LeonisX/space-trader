package spacetrader.gui;

import spacetrader.controls.Image;
import spacetrader.controls.ImageList;
import spacetrader.game.GlobalAssets;
import spacetrader.gui.cheat.FormMonster;
import spacetrader.gui.debug.FormsTest;
import spacetrader.guifacade.GuiEngine.CheatGui;
import spacetrader.guifacade.GuiEngine.ImageProvider;
import spacetrader.guifacade.GuiEngine.ImplementationProvider;

public class OriginalGuiImplementationProvider implements ImplementationProvider {

    public OriginalGuiImplementationProvider() {
        super();
    }

    public ImageProvider getImageProvider() {
        return new ImageProvider() {
            public ImageList getEquipmentImages() {
                return GlobalAssets.getEquipmentImages();
            }

            public ImageList getShipImages() {
                return GlobalAssets.getShipImages();
            }

            public Image[] getCustomShipImages() {
                return GlobalAssets.getCustomShipImages();
            }

            public void setCustomShipImages(Image[] value) {
                GlobalAssets.setCustomShipImages(value);
            }

            public Image[] getDirectionImages() {
                return GlobalAssets.getDirectionImages().getImages();
            }
        };
    }

    public CheatGui getCheatGuiProvider() {
        return new CheatGui() {
            public void showMonsterForm() {
                (new FormMonster()).showDialog();
            }

            public void showTestForm() {
                (new FormsTest()).showDialog();
            }
        };
    }

}
