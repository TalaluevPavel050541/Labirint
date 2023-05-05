package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import gameobjects.impl.Coordinate;


/**
 * объект EXIT
 */
public class Exit extends AbstractGameObject {

    public Exit(Coordinate coordinate) {
        super.setType(GameObjectType.EXIT);
        super.setCoordinate(coordinate);
        super.saveIcon("/images/exit.gif");
    }
}
