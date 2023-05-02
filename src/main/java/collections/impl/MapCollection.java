package collections.impl;

import gameobjects.abstracts.AbstractGameObject;
import gameobjects.abstracts.AbstractMovingObject;
import enums.ActionResult;
import enums.GameObjectType;
import enums.MovingDirection;
import movestrategies.interfaces.MoveStrategy;
import gameobjects.impl.Coordinate;
import gameobjects.impl.GoldMan;
import gameobjects.impl.Nothing;
import gameobjects.impl.Wall;
import collections.abstracts.MapMoveListenersRegistrator;
import listeners.interfaces.MoveResultListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class MapCollection extends MapMoveListenersRegistrator implements Serializable{// объекты для карты, которые умеют уведомлять всех слушателей о своих ходах

    private HashMap<Coordinate, ArrayList<AbstractGameObject>> gameObjects = new HashMap<>();// хранит все объекты с доступом по координатам
    private EnumMap<GameObjectType, ArrayList<AbstractGameObject>> typeObjects = new EnumMap<>(GameObjectType.class); // хранит список объектов для каждого типа

    public MapCollection() {
    }

    @Override
    public List<AbstractGameObject> getAllGameObjects() {
        //return new ArrayList(gameObjects.values());// ! узкое место - каждый раз создается новая коллекция
        ArrayList<AbstractGameObject> list = new ArrayList<>();// ! узкое место - каждый раз создается новая коллекция

        for (List<AbstractGameObject> tmpList : gameObjects.values()) {
            for (AbstractGameObject obj : tmpList) {
                list.add(obj);
            }
        }

        return list;
    }

    @Override
    public List<AbstractGameObject> getGameObjects(GameObjectType type) {
        return typeObjects.get(type);
    }

    @Override
    public AbstractGameObject getObjectByCoordinate(Coordinate coordinate) {

        AbstractGameObject gameObject = null;

        ArrayList<AbstractGameObject> list = gameObjects.get(coordinate);

        if (list == null) {// край карты
            return new Wall(coordinate);
        }

        for (AbstractGameObject obj : list) {
            if (gameObject == null) {
                gameObject = obj;
                continue;
            }
            if (obj.getType().getIndexPriority() > gameObject.getType().getIndexPriority()) {
                gameObject = obj;
            }
        }


        return gameObject;
    }

    @Override
    public AbstractGameObject getObjectByCoordinate(int x, int y) {
        return getObjectByCoordinate(new Coordinate(x, y));
    }

    @Override
    public void addGameObject(AbstractGameObject gameObject) {

        ArrayList<AbstractGameObject> typeList = typeObjects.get(gameObject.getType());
        ArrayList<AbstractGameObject> objList = gameObjects.get(gameObject.getCoordinate());

        if (typeList == null) {
            typeList = new ArrayList<>();
        }

        if (objList == null) {
            objList = new ArrayList<>();
        }

        typeList.add(gameObject);
        objList.add(gameObject);

        gameObjects.put(gameObject.getCoordinate(), objList);
        typeObjects.put(gameObject.getType(), typeList);

    }

    @Override
    public void moveObject(MovingDirection direction, GameObjectType gameObjectType) {
        doMoveAction(direction, gameObjectType, null);// движение по направлению (без стратегии)
    }

    @Override
    public void moveObject(MoveStrategy moveStrategy, GameObjectType gameObjectType) {
        doMoveAction(null, gameObjectType, moveStrategy);// движение по стратегии
    }

    private void doMoveAction(MovingDirection direction, GameObjectType gameObjectType, MoveStrategy moveStrategy) {
        GoldMan goldMan = (GoldMan) getGameObjects(GameObjectType.GOLDMAN).get(0);

        ActionResult actionResult = null;

        if (this.getGameObjects(gameObjectType) == null) {
            return;
        }

        for (AbstractGameObject gameObject : this.getGameObjects(gameObjectType)) {
            if (gameObject instanceof AbstractMovingObject) {// дорогостоящая операция - instanceof
                AbstractMovingObject movingObject = (AbstractMovingObject) gameObject;

                if (moveStrategy != null) {// если указана стратегия движения - то берем наравления оттуда
                    direction = moveStrategy.getDirection(movingObject, goldMan, this);
                }

                Coordinate newCoordinate = movingObject.getDirectionCoordinate(direction);

                AbstractGameObject objectInNewCoordinate = getObjectByCoordinate(newCoordinate);

                actionResult = movingObject.moveToObject(direction, objectInNewCoordinate);

                switch (actionResult) {
                    case MOVE: {
                        swapObjects(movingObject, objectInNewCoordinate);
                        break;
                    }
                    case COLLECT_TREASURE: {
                        swapObjects(movingObject, new Nothing(newCoordinate));
                        removeObject(objectInNewCoordinate);
                        break;
                    }
                    case HIDE_IN_TREE: {
                        swapObjects(movingObject, new Nothing(newCoordinate));
                        break;
                    }

                    case WIN:
                    case DIE: {
                        break;
                    }

                }

                notifyMoveListeners(actionResult, movingObject);

            }


        }
    }

    private void removeObject(AbstractGameObject obj){
        gameObjects.get(obj.getCoordinate()).remove(obj);
        typeObjects.get(obj.getType()).remove(obj);
    }

    private void swapObjects(AbstractGameObject obj1, AbstractGameObject obj2) {

        gameObjects.get(obj1.getCoordinate()).remove(obj1);
        gameObjects.get(obj2.getCoordinate()).remove(obj2);

        Coordinate tmpCoordinate = obj1.getCoordinate();
        obj1.setCoordinate(obj2.getCoordinate());
        obj2.setCoordinate(tmpCoordinate);

        gameObjects.get(obj1.getCoordinate()).add(obj1);
        gameObjects.get(obj2.getCoordinate()).add(obj2);
    }

    @Override
    public void notifyMoveListeners(ActionResult actionResult, AbstractMovingObject movingObject) {
        for (MoveResultListener listener : getMoveListeners()) {
            listener.notifyActionResult(actionResult, movingObject);
        }
    }

    @Override
    public void clear() {
        gameObjects.clear();
        typeObjects.clear();
    }
}