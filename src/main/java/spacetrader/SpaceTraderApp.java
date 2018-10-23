package spacetrader;

import spacetrader.game.GlobalAssets;
import spacetrader.gui.OriginalGuiImplementationProvider;
import spacetrader.gui.SpaceTrader;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.ReflectionUtils;

import javax.swing.*;

public class SpaceTraderApp {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("swing.boldMetal", Boolean.FALSE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        GlobalAssets.loadStrings("en");
        GlobalAssets.loadDimensions("0768");

        SpaceTrader spaceTrader = new SpaceTrader(args.length > 0 ? args[0] : null);
        GuiEngine.installImplementation(new OriginalGuiImplementationProvider(spaceTrader));

        ReflectionUtils.loadControlsDimensions(spaceTrader.getFrame(), spaceTrader.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(spaceTrader.getFrame(), spaceTrader.getName(), GlobalAssets.getStrings());
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());

        spaceTrader.showWindow();
    }
}
