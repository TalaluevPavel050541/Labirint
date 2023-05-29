package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import gameobjects.impl.Coordinate;


/**
 * класс отвечает за работу объекта WALL
 */
public class Wall extends AbstractGameObject {

    public Wall(Coordinate coordinate) {
        super.setType(GameObjectType.WALL); // запись объекта стена
        super.setCoordinate(coordinate); // запись координаты
        super.saveIcon("/images/wall.png"); // сохранение иконки для дерева
    }
}
