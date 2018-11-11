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

        GlobalAssets.loadVersion();
        GlobalAssets.loadLanguageFromRegistry();
        GlobalAssets.loadStrings();
        //GlobalAssets.loadDimensions("0768");
        GlobalAssets.initializeImages();

        GuiEngine.installImplementation(new OriginalGuiImplementationProvider());

        SpaceTrader spaceTrader = new SpaceTrader(args.length > 0 ? args[0] : null);

        //ReflectionUtils.loadControlsDimensions(spaceTrader.getFrame(), spaceTrader.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(spaceTrader.getFrame(), spaceTrader.getName(), GlobalAssets.getStrings());
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());

        spaceTrader.showWindow();
    }
}
