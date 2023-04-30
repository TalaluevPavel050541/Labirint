package abstracts;

import interfaces.gamemap.collections.GameCollection;
import interfaces.gamemap.GameMap;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * базовая карта без конкретного отображения
 */
public abstract class AbstractGameMap implements GameMap, Serializable { // Serializable нужен для сериализации (сохранения) объекта карты, чтобы можно было сохранять игру и восстанавливать

    private static final long serialVersionUID = 1L;
    private int width;
    private int height;
    private int timeLimit;
    private String name;
    private boolean exitExist;
    private boolean goldManExist;
    private GameCollection gameCollection;

    public AbstractGameMap() {
    }

    public AbstractGameMap(GameCollection gameCollection) {
        this.gameCollection = gameCollection;
    }

    public boolean isExitExist() {
        return exitExist;
    }

    public void setExitExist(boolean isExitExist) {
        this.exitExist = isExitExist;
    }

    public boolean isGoldManExist() {
        return goldManExist;
    }

    public void setGoldManExist(boolean isGoldManExist) {
        this.goldManExist = isGoldManExist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public AbstractGameObject getPriorityObject(AbstractGameObject firstObject, AbstractGameObject secondObject) {
        // приоритет объекта зависит от номера индекса объекта enum
        return (firstObject.getType().getIndexPriority() > secondObject.getType().getIndexPriority()) ? firstObject : secondObject; // сокращенная запись условия if: если первый объект имеет больший приоритет - вернуть его, иначе вернуть второй объект
    }

    public boolean isValidMap() {
        return goldManExist && exitExist; // если есть и вход и выход - карта валидна
    }

    public GameCollection getGameCollection() {
        if (gameCollection == null) {
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
    }
}
