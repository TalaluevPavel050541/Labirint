package gamemap.interfaces;

import gamemap.abstracts.AbstractGameMap;

import java.awt.*;

public interface DrawableMap {

    Component getMapComponent();

    boolean drawMap();
}
