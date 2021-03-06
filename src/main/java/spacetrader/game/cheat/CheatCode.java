package spacetrader.game.cheat;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to mark elements that are only used as part of a cheat, or otherwise exist only for the cheat mechanisms.
 *
 * @author Aviv
 */
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface CheatCode {
    // Todo: Create a annotation tool to ensure @CheatCode is not called from "real" code.
}
