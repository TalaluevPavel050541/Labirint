package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;


public class Tree extends AbstractGameObject {
    public Tree(Coordinate coordinate) {
        super.setType(GameObjectType.TREE);
        super.setCoordinate(coordinate);
        super.saveIcon("/images/tree.jpg");
    }
}
