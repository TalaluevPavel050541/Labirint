package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import gameobjects.impl.Coordinate;


/**
 * класс отвечает за работу объекта WALL
 */
public class Wall extends AbstractGameObject {

    public Wall(Coordinate coordinate) {
        super.setType(GameObjectType.WALL);
        super.setCoordinate(coordinate);        
        super.setIcon(getImageIcon("/images/wall.png"));
    }
}
