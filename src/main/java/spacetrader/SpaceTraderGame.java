package spacetrader;

/**
 * My way of moving methods I don't want the GUI to call directly.
 *
 * @author Aviv
 */
public interface SpaceTraderGame {

    Commander Commander();

    int[] PriceCargoBuy();

    int[] PriceCargoSell();

    StarSystem WarpSystem();
}
