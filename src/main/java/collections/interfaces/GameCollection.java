package collections.interfaces;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import enums.MovingDirection;
import movestrategies.interfaces.MoveStrategy;
import gameobjects.impl.Coordinate;
import listeners.interfaces.MoveResultNotifier;
import java.util.List;

public interface GameCollection extends MoveResultNotifier {

    AbstractGameObject getObjectByCoordinate(Coordinate coordinate);

    AbstractGameObject getObjectByCoordinate(int x, int y);

    void addGameObject(AbstractGameObject gameObject);

    List<AbstractGameObject> getAllGameObjects();

    List<AbstractGameObject> getGameObjects(GameObjectType type);

    void moveObject(MovingDirection direction, GameObjectType gameObjectType);

    void moveObject(MoveStrategy moveStrategy, GameObjectType gameObjectType);
    void clear();

}
