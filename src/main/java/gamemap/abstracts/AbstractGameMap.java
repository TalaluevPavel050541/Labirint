package gamemap.abstracts;

import gamemap.interfaces.TimeMap;
import gameobjects.abstracts.AbstractGameObject;
import collections.interfaces.GameCollection;
import objects.MapInfo;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * базовая карта без конкретного отображения
 */
public abstract class AbstractGameMap implements TimeMap, Serializable { // Serializable нужен для сериализации (сохранения) объекта карты, чтобы можно было сохранять игру и восстанавливать

    private static final long serialVersionUID = 1L;
    protected GameCollection gameCollection; // определение коллекции (содержит две коллекции с объектами реализованные в MapCollection
    //и коллекцию слушателей listeners
    protected MapInfo mapInfo = new MapInfo(); // объект карты (из БД map)

    public AbstractGameMap(GameCollection gameCollection) {
        this.gameCollection = gameCollection;
    }

    @Override
    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }


    //получение объекта по приоритету
    public AbstractGameObject getPriorityObject(AbstractGameObject firstObject, AbstractGameObject secondObject) {
        // приоритет объекта зависит от номера индекса объекта enum
        return (firstObject.getType().getIndexPriority() > secondObject.getType().getIndexPriority()) ? firstObject : secondObject; // сокращенная запись условия if: если первый объект имеет больший приоритет - вернуть его, иначе вернуть второй объект
    }



    @Override
    public GameCollection getGameCollection() { // получение коллекции с объектами
        if (gameCollection == null) { // проверка на существование GameCollection
            try {
                throw new Exception("Game collection not initialized!");
            } catch (Exception ex) {
                Logger.getLogger(AbstractGameMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return gameCollection;
    }

    public void setGameCollection(GameCollection gameCollection) {
        this.gameCollection = gameCollection;
    } // запись коллекции с объектами и слушателями
}
