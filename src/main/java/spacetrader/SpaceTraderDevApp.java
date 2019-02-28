package spacetrader;

import spacetrader.controls.BaseComponent;
import spacetrader.game.Game;
import spacetrader.game.GlobalAssets;
import spacetrader.game.enums.*;
import spacetrader.gui.*;
import spacetrader.gui.cheat.FormMonster;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.ReflectionUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spacetrader.game.enums.AlertType.EncounterAttackNoLasers;

public class SpaceTraderDevApp {

    public static void main(String[] args) {
        try {
            //String.format(format, date, source, logger, level, message, thrown);
            //                        1      2      3       4       5        6
            System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT [%4$-7s] %2$s %5$s %6$s%n");
            Logger.getLogger("").setLevel(Level.ALL);
            for (Handler handler : Logger.getLogger("").getHandlers()) {
                handler.setLevel(Level.ALL);
                handler.setFilter(record -> record.getLoggerName().startsWith("spacetrader"));
            }

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("swing.boldMetal", Boolean.FALSE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        GlobalAssets.setDebug(true);
        GlobalAssets.loadVersion();
        GlobalAssets.loadLanguageFromRegistry();
        GlobalAssets.loadStrings();
        //GlobalAssets.loadDimensions("0768");
        GlobalAssets.initializeImages();

        GuiEngine.installImplementation(new OriginalGuiImplementationProvider());

        SpaceTrader spaceTrader = new SpaceTrader(args.length > 0 ? args[0] : null);

        //dumpAll(spaceTrader);

        //ReflectionUtils.loadControlsDimensions(spaceTrader.getFrame(), spaceTrader.getName(), GlobalAssets.getDimensions());
        ReflectionUtils.loadControlsStrings(spaceTrader.getFrame(), spaceTrader.getName(), GlobalAssets.getStrings());
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());

        Game.setCurrentGame(null);

        spaceTrader.showWindow();
        spaceTrader.newGameMenuItemClick();
    }


    private static void dumpAll(SpaceTrader spaceTrader) {
        Game game = new Game("name", Difficulty.BEGINNER, 8, 8, 8, 8, null);
        game.getShip().getCargo()[1] = 12;
        game.setSelectedSystemId(StarSystemId.Aldea);
        game.warpDirect();
        game.getCommander().getCurrentSystem().setShipyardId(ShipyardId.CORELLIAN);

        List<BaseComponent> components = Arrays.asList(
                spaceTrader,
                new FormAbout(),
                FormAlert.makeDialog(EncounterAttackNoLasers, new String[]{}),
                new FormBuyFuel(),
                new FormBuyRepairs(),
                new FormCargoBuy(1, 1, CargoBuyOp.BUY_SYSTEM),
                new FormCargoSell(1, 1, CargoSellOp.JETTISON, 255),
                new FormCosts(),
                new FormEncounter(),
                new FormEquipment(),
                new FormFind(""),
                new FormGetLoan(25000),
                new FormJettison(),
                new FormMonster(),
                new FormNewCommander(),
                new FormOptions(),
                new FormPayBackLoan(),
                new FormPlunder(),
                new FormShipList(),
                new FormShipyard(),
                new FormViewBank(),
                new FormViewCommander(),
                new FormViewHighScores(),
                new FormViewPersonnel(),
                new FormViewQuests(),
                new FormViewShip()
        );
        dumpAllDimensions(components);
        dumpAllStrings(components);
    }

    private static void dumpAllDimensions(List<BaseComponent> components) {
        components.forEach(baseComponent -> {
            Component component = baseComponent.asSwingObject();
            ReflectionUtils.dumpControlsDimensions(component, component.getName());
            System.out.println();
        });
    }

    private static void dumpAllStrings(List<BaseComponent> components) {
        //ReflectionUtils.dumpAllAlertStrings();
        components.forEach(baseComponent -> {
            Component component = baseComponent.asSwingObject();
            ReflectionUtils.dumpControlsStrings(component, component.getName());
            System.out.println();
        });
        ReflectionUtils.dumpStrings();
    }

}
