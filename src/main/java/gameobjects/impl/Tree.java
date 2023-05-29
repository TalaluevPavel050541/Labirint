package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;


public class Tree extends AbstractGameObject {
    public Tree(Coordinate coordinate) {
        super.setType(GameObjectType.TREE); // запись типа дерево
        super.setCoordinate(coordinate); // запись координаты
        super.saveIcon("/images/tree.jpg"); // сохранение иконки
    }
}
