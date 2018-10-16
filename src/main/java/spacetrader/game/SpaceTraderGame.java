package spacetrader.game;

/**
 * My way of moving methods I don't want the GUI to call directly.
 *
 * @author Aviv
 */
public interface SpaceTraderGame {

    Commander getCommander();

    int[] getPriceCargoBuy();

    int[] getPriceCargoSell();

    StarSystem getWarpSystem();
}
