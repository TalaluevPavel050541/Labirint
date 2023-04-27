package objects.common;

import abstracts.AbstractGameObject;
import enums.GameObjectType;


/**
 * объект EXIT
 */
public class Exit extends AbstractGameObject {

    public Exit(Coordinate coordinate) {
        super.setType(GameObjectType.EXIT);
        super.setCoordinate(coordinate);
        super.setIcon(getImageIcon("/images/exit.gif"));
    }
}
