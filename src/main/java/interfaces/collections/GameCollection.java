package interfaces.collections;

import abstracts.AbstractGameObject;
import enums.ActionResult;
import enums.GameObjectType;
import enums.MovingDirection;
import objects.Coordinate;

import java.util.List;

public interface GameCollection{

    AbstractGameObject getObjectByCoordinate(Coordinate coordinate);

    AbstractGameObject getObjectByCoordinate(int x, int y);

    void addGameObject(AbstractGameObject gameObject);

    List<AbstractGameObject> getAllGameObjects();

    List<AbstractGameObject> getGameObjects(GameObjectType type);

    ActionResult moveObject(MovingDirection direction, GameObjectType gameObjectType);

}

