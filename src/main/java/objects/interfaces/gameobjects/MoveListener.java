package objects.interfaces.gameobjects;

import abstracts.AbstractGameObject;

import java.util.List;

public interface MoveListener {

    List<GameObjectMoveListener> getListeners();

    void addListener(GameObjectMoveListener listener);

    public void removeListener(GameObjectMoveListener listener);

    public void removeAllListeners();

    public void notifyListeners(AbstractGameObject obj1, AbstractGameObject obj2);
}
