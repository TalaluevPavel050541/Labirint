package interfaces.gamemap;

import abstracts.AbstractGameMap;

import java.awt.*;

public interface DrawableMap {

    Component getMapComponent();

    AbstractGameMap getGameMap();

    boolean drawMap();
}
