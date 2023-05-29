package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import gameobjects.impl.Coordinate;


/**
 * объект EXIT
 */
public class Exit extends AbstractGameObject {

    public Exit(Coordinate coordinate) {
        super.setType(GameObjectType.EXIT); // запись типа
        super.setCoordinate(coordinate); //запись координаты для объекта
        super.saveIcon("/images/exit.gif"); // сохранение иконки для объекта
    }
}
