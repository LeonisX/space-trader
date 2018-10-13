import spacetrader.gui.OriginalGuiImplementationProvider;
import spacetrader.gui.SpaceTrader;
import spacetrader.guifacade.GuiEngine;
import util.PropertiesLoader;

import javax.swing.*;

public class SpaceTraderApp {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("swing.boldMetal", Boolean.FALSE);
        } catch (Exception e) {
            // TODO: We get here if there's no windows UI; I think no special treatment is needed.
        }
        SpaceTrader spaceTrader = new SpaceTrader(args.length > 0 ? args[0] : null);
        GuiEngine.installImplementation(new OriginalGuiImplementationProvider(spaceTrader));
        spaceTrader.showWindow();
    }

}
