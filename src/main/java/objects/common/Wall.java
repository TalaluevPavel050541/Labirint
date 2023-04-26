package objects.common;

import enums.GameObjectType;


/**
 * класс отвечает за работу объекта WALL
 */
public class Wall extends AbstractGameObject{

    public Wall(Coordinate coordinate) {
        super.setType(GameObjectType.WALL);
        super.setCoordinate(coordinate);        
        super.setIcon(getImageIcon("/images/wall.png"));
    }
}
