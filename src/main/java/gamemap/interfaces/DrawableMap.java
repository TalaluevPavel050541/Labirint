package gamemap.interfaces;

import gamemap.abstracts.AbstractGameMap;

import java.awt.*;

public interface DrawableMap extends MainMap{

    Component getMapComponent();

    boolean drawMap();

}
