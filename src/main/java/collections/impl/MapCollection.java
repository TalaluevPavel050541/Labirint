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
// класс для карты игры - наблюдатель для слушателя по паттерну Наблюдатель
public class MapCollection extends MapMoveListenersRegistrator implements Serializable {// объекты для карты, которые умеют уведомлять всех слушателей о своих ходах

    //GameCollection содержит эти две коллекции объектов и коллекцию слушателей listeners
    private HashMap<Coordinate, ArrayList<AbstractGameObject>> gameObjects = new HashMap<>();// хранит все объекты в коллекции с доступом по координатам
    private EnumMap<GameObjectType, ArrayList<AbstractGameObject>> typeObjects = new EnumMap<>(GameObjectType.class); // хранит список объектов для каждого типа

    public MapCollection() { //конструктор класса без параметров
    }

    @Override
    public List<AbstractGameObject> getAllGameObjects() { // получение всех объектов игры

        ArrayList<AbstractGameObject> list = new ArrayList<>();// создается новая коллекция

        for (List<AbstractGameObject> tmpList : gameObjects.values()) { //извлечение из коллекции объектов через цикл
            for (AbstractGameObject obj : tmpList) {
                list.add(obj);//добавление в новую коллекцию объекта
            }
        }

        return list;
    }

    @Override
    public List<AbstractGameObject> getGameObjects(GameObjectType type) { // получение объекта из списка по типу
        return typeObjects.get(type);
    }

    @Override
    public AbstractGameObject getObjectByCoordinate(Coordinate coordinate) {// получение объекта из списка по объекту координата

        AbstractGameObject gameObject = null;

        ArrayList<AbstractGameObject> list = gameObjects.get(coordinate); // получение объекта из списка по координате

        if (list == null) {// край карты
            return null;
        }

        for (AbstractGameObject obj : list) {
            if (gameObject == null) {
                gameObject = obj;
                continue;
            }
            if (obj.getType().getIndexPriority() > gameObject.getType().getIndexPriority()) { //определение объекта по приоритету из коллекции
                gameObject = obj;
            }
        }


        return gameObject;
    }

    @Override
    public AbstractGameObject getObjectByCoordinate(int x, int y) { //получение объекта по координатам на карте
        return getObjectByCoordinate(new Coordinate(x, y));
    }

    @Override
    public void addGameObject(AbstractGameObject gameObject) { // метод добавления объектов в листы

        ArrayList<AbstractGameObject> typeList = typeObjects.get(gameObject.getType()); // по типу
        ArrayList<AbstractGameObject> objList = gameObjects.get(gameObject.getCoordinate()); // по координате

        if (typeList == null) { // проверка на Null
            typeList = new ArrayList<>(); // создание нового листа
        }

        if (objList == null) { // проверка на Null
            objList = new ArrayList<>();// создание нового листа
        }

        typeList.add(gameObject); //добвление в новый лист объектов игры
        objList.add(gameObject); //добвление в новый лист объектов игры

        gameObjects.put(gameObject.getCoordinate(), objList); // добавление в лист объектов по координатам
        typeObjects.put(gameObject.getType(), typeList); // добавление в лист объектов по типу

    }

    @Override
    public void moveObject(MovingDirection direction, GameObjectType gameObjectType) {
        doMoveAction(direction, gameObjectType, null);// движение по направлению (без стратегии)
    }

    @Override
    public void moveObject(MoveStrategy moveStrategy, GameObjectType gameObjectType) {
        doMoveAction(null, gameObjectType, moveStrategy);// движение по стратегии
    }
//метод реализует движение объекта
    private void doMoveAction(MovingDirection direction, GameObjectType gameObjectType, MoveStrategy moveStrategy) {
        GoldMan goldMan = (GoldMan) getGameObjects(GameObjectType.GOLDMAN).get(0); // получение персонажа в качестве объекта

        ActionResult actionResult = null;

        if (this.getGameObjects(gameObjectType) == null) {
            return;
        }

        for (AbstractGameObject gameObject : this.getGameObjects(gameObjectType)) { // достаем объект из списка
            if (gameObject instanceof AbstractMovingObject) { // проверка движущийся ли объект
                AbstractMovingObject movingObject = (AbstractMovingObject) gameObject; // определяем движущийся объект

                if (moveStrategy != null) {// если указана стратегия движения - то берем наравления оттуда
                    direction = moveStrategy.getDirection(movingObject, goldMan, this); // выбор метода направления движения из стратегии
                }

                Coordinate newCoordinate = movingObject.getDirectionCoordinate(direction); //определяем новые координаты для движущегося объекта

                AbstractGameObject objectInNewCoordinate = getObjectByCoordinate(newCoordinate); //получение объекта по этим координатам

                actionResult = movingObject.moveToObject(direction, objectInNewCoordinate); //определение типа события по направлению и координатам

                //в зависимости от типа действия, чем закончилось движение персонажа
                switch (actionResult) {
                    case MOVE: { // движение
                        swapObjects(movingObject, objectInNewCoordinate); // объекты меняются местами при движении
                        break;
                    }
                    case COLLECT_TREASURE: { // сбор золота
                        Nothing nothing = new Nothing(newCoordinate);
                        swapObjects(movingObject, nothing); // объекты меняются местами
                        removeObject(objectInNewCoordinate); // удаления объекта золото
                        objectInNewCoordinate = nothing; // объект пустота с новыми координатами
                        break;
                    }
                    case HIDE_IN_TREE: { //прячется в дереве
                        Nothing nothing = new Nothing(newCoordinate);
                        swapObjects(movingObject, nothing); // объекты меняются местами
                        objectInNewCoordinate = nothing; // создание объекта с новыми координатами
                        break;
                    }

                    case WIN:
                    case DIE: { // ничего не делаем
                        break;
                    }

                }

                if (actionResult != ActionResult.NO_ACTION) { // если есть какое-то действие на карте
                     // вызов всех слушателей, заинтересованных в результате движения
                    //мы передаем сведения для слушателей - тип действия, объект и новые координаты объектов
                    //используется для самой карты FrameGame
                    notifyMoveListeners(actionResult, movingObject, objectInNewCoordinate); //паттерн Наблюдатель
                }

            }


        }
    }

    private void removeObject(AbstractGameObject obj) { // удаление объекта
        gameObjects.get(obj.getCoordinate()).remove(obj); // по координатам
        typeObjects.get(obj.getType()).remove(obj); // по типу объекта
    }

    private void swapObjects(AbstractGameObject obj1, AbstractGameObject obj2) { // смена положения объектов - обмен координатами - меняются местами

        gameObjects.get(obj1.getCoordinate()).remove(obj1); // удаление первоначальных координат объекта 1
        gameObjects.get(obj2.getCoordinate()).remove(obj2); // удаление первоначальных координат объекта 2

        Coordinate tmpCoordinate = obj1.getCoordinate(); //получение координат первого объекта
        obj1.setCoordinate(obj2.getCoordinate()); // получение координат 2-го объекта и запись в координаты первого
        obj2.setCoordinate(tmpCoordinate);// запись координат первого объекта в координаты второго объекта

        gameObjects.get(obj1.getCoordinate()).add(obj1); // запись новых координат объекта 1
        gameObjects.get(obj2.getCoordinate()).add(obj2); // запись новых координат объекта 2
    }

    @Override //
    public void notifyMoveListeners(ActionResult actionResult, AbstractGameObject movingObject, AbstractGameObject targetObject) {
        for (MoveResultListener listener : getMoveListeners()) { //получение списка слушателей по событиям
            listener.notifyActionResult(actionResult, movingObject, targetObject);
        }
    }

    @Override //очистка объектов в списках - стандартный метод коллекции, описанный в библиотеке по коллекциям Java
    public void clear() {
        gameObjects.clear(); // по координатами
        typeObjects.clear(); // по типу объекта
    }
}
