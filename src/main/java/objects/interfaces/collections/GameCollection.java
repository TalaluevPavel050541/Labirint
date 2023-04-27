package objects.interfaces.collections;

import abstracts.AbstractGameObject;
import enums.GameObjectType;
import objects.common.Coordinate;

import java.util.List;

public interface GameCollection {

    AbstractGameObject getObjectByCoordinate(Coordinate coordinate);

    AbstractGameObject getObjectByCoordinate(int x, int y);

    void addGameObject(AbstractGameObject gameObject);

    List<AbstractGameObject> getAllGameObjects();

    List<AbstractGameObject> getGameObjects(GameObjectType type);
}
