package gamemap.interfaces;

import gameobjects.abstracts.AbstractGameObject;

import java.awt.*;

public interface DrawableMap extends MainMap{

    Component getMapComponent();

    boolean updateMap();

    void updateMapObjects(AbstractGameObject obj1, AbstractGameObject obj2);

}
