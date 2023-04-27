package objects.interfaces.gamemap;

import abstracts.AbstractGameObject;
import enums.GameObjectType;
import objects.common.Coordinate;
import objects.interfaces.collections.GameCollection;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class MapCollection implements GameCollection {// объекты для карты, которые умеют уведомлять всех слушателей о своих ходах

    private HashMap<Coordinate, AbstractGameObject> gameObjects = new HashMap<>();// хранит все объекты с доступом по координатам
    private EnumMap<GameObjectType, ArrayList<AbstractGameObject>> typeObjects = new EnumMap<>(GameObjectType.class); // хранит список объектов для каждого типа

    @Override
    public List<AbstractGameObject> getAllGameObjects() {
        return new ArrayList(gameObjects.values());// ! узкое место - каждый раз создается новая коллекция
    }

    @Override
    public List<AbstractGameObject> getGameObjects(GameObjectType type) {
        return typeObjects.get(type);
    }

    @Override
    public AbstractGameObject getObjectByCoordinate(Coordinate coordinate) {
        return gameObjects.get(coordinate);
    }

    @Override
    public AbstractGameObject getObjectByCoordinate(int x, int y) {
        return gameObjects.get(new Coordinate(x, y));
    }

    @Override
    public void addGameObject(AbstractGameObject gameObject) {

        ArrayList<AbstractGameObject> tmpList = typeObjects.get(gameObject.getType());

        if (tmpList == null) {
            tmpList = new ArrayList<>();
        }

        tmpList.add(gameObject);

        gameObjects.put(gameObject.getCoordinate(), gameObject);
        typeObjects.put(gameObject.getType(), tmpList);
    }
}