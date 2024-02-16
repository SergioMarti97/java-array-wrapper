package org.array.wrapper.showcase.app;

import org.array.wrapper.showcase.main.spline.SplineGame;
import org.javafx.game.GameApplication;

public class GameApp extends GameApplication {

    @Override
    public void init() throws Exception {
        super.init();
        setAppName("Spline Game");
        setGame(new SplineGame());
    }

}
