package spacetrader;

import java.awt.Component;
import java.util.Arrays;
import java.util.List;
import spacetrader.controls.BaseComponent;
import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.StarSystemId;
import spacetrader.gui.FormShipList;
import spacetrader.gui.OriginalGuiImplementationProvider;
import spacetrader.gui.SpaceTrader;
import spacetrader.guifacade.GuiEngine;

import javax.swing.*;
import spacetrader.stub.PropertiesLoader;
import spacetrader.stub.StringsMap;
import spacetrader.stub.ValuesMap;
import spacetrader.util.ReflectionUtils;

public class SpaceTraderApp {

    private static StringsMap strings = new StringsMap();
    private static ValuesMap dimensions = new ValuesMap();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("swing.boldMetal", Boolean.FALSE);
        } catch (Exception e) {
            // TODO: We get here if there's no windows UI; I think no special treatment is needed.
        }

        strings = PropertiesLoader.getStringsMap("strings/en.properties");
        dimensions = PropertiesLoader.getValuesMap("dimensions/0768.properties");

        SpaceTrader spaceTrader = new SpaceTrader(args.length > 0 ? args[0] : null);
        GuiEngine.installImplementation(new OriginalGuiImplementationProvider(spaceTrader));

        //dumpAll(spaceTrader);

        ReflectionUtils.loadControlsDimensions(spaceTrader.getFrame(), spaceTrader.getName(), getDimensions());
        ReflectionUtils.loadControlsStrings(spaceTrader.getFrame(), spaceTrader.getName(), getStrings());
        ReflectionUtils.loadStrings(getStrings());

        spaceTrader.showWindow();
    }


    private static void dumpAll(SpaceTrader spaceTrader) {
        Game game = new Game("name", Difficulty.BEGINNER,8,8,8,8, null);
        game.getCommander().getShip().getCargo()[1] = 12;
        game.setSelectedSystemId(StarSystemId.Aldea);
        game.warpDirect();

        List<BaseComponent> components = Arrays.asList(
                /*spaceTrader,
                new FormAbout(),
                FormAlert.makeDialog(EncounterDrinkContents, new String[]{}),
                new FormBuyFuel(),
                new FormBuyRepairs(),
                new FormCargoBuy(1, 1, CargoBuyOp.BUY_SYSTEM),
                new FormCargoSell(1, 1, CargoSellOp.JETTISON, 255),
                new FormCosts(),
                new FormEncounter(),
                new FormEquipment(),
                new FormFind(),
                new FormGetLoan(25000),
                new FormJettison(),
                new FormMonster(),
                new FormNewCommander(),
                new FormOptions(),
                new FormPayBackLoan(),
                new FormPlunder(),*/
                new FormShipList()
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
        //ReflectionUtils.dumpStrings();
    }


    public static StringsMap getStrings() {
        return strings;
    }

    public static ValuesMap getDimensions() {
        return dimensions;
    }
}
