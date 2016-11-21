package exception;

import controller.GameController;

/**
 * Created by lifengshuang on 9/26/16.
 */
public class GameWinException extends Exception {

    private final GameController.Side side;
    private final String message;

    public GameWinException(GameController.Side side, String message) {
        super();
        this.side = side;
        this.message = message;
    }

    public GameController.Side getSide() {
        return side;
    }

    public String getMessage() {
        return message;
    }
}
