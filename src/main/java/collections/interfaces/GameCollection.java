package collections.interfaces;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import enums.MovingDirection;
import movestrategies.interfaces.MoveStrategy;
import gameobjects.impl.Coordinate;
import listeners.interfaces.MoveResultNotifier;
import java.util.List;
//любая коллекция может уведомлять о своем результате хода наследуется от  MoveResultNotifier
public interface GameCollection extends MoveResultNotifier {

    AbstractGameObject getObjectByCoordinate(Coordinate coordinate); //получить объект по координате

    AbstractGameObject getObjectByCoordinate(int x, int y); // получение объекта по координатам на карте

    void addGameObject(AbstractGameObject gameObject); // добавление объектов в коллекцию ( в листы)

    List<AbstractGameObject> getAllGameObjects();//получение списка объектов игры (коллекция типа List)

    List<AbstractGameObject> getGameObjects(GameObjectType type); //получение списка объектов игры по типу объекта (коллекция типа List)

    void moveObject(MovingDirection direction, GameObjectType gameObjectType); // определение движения объекта на карте по направлению

    //данный метод нужен для обработки коллекцией движения объектов
    void moveObject(MoveStrategy moveStrategy, GameObjectType gameObjectType); // определение движения объекта на карте по стратегии
    void clear(); // метод очистки списков

}
