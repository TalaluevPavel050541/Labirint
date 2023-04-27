package abstracts;

import enums.GameObjectType;
import enums.MovingDirection;
import objects.common.Coordinate;
import objects.interfaces.gameobjects.GameObjectMoveListener;
import objects.interfaces.gameobjects.MoveListener;
import objects.interfaces.gameobjects.MovingObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * класс, который отвечает за любой движущийся объект. наследуется от класса
 * AbstractGameObject с добавлением функций движения
 */
public abstract class AbstractMovingObject extends AbstractGameObject implements MovingObject, MoveListener {

    public abstract void changeIcon(MovingDirection direction);
    private int step = 1;// по-умолчанию у всех объектов шаг равен 1

    @Override
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public void move(MovingDirection direction, AbstractGameMap gameMap) {

        Coordinate newCoordinate = getNewCoordinate(direction);

        AbstractGameObject objectInNewCoordinate = gameMap.getGameCollection().getObjectByCoordinate(newCoordinate);

        if (objectInNewCoordinate.getType() == GameObjectType.NOTHING) {
            changeIcon(direction);
            setCoordinate(newCoordinate);
            notifyListeners(this, objectInNewCoordinate);
        }
    }

    public Coordinate getNewCoordinate(MovingDirection direction) {

        // берем текущие координаты объекта, которые нужно передвинуть (индексы начинаются с нуля)
        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();

        Coordinate newCoordinate = new Coordinate(x, y);

        switch (direction) {// определяем, в каком направлении нужно двигаться по массиву
            case UP: {
                newCoordinate.setY(y - step);
                break;
            }
            case DOWN: {
                newCoordinate.setY(y + step);
                break;
            }
            case LEFT: {
                newCoordinate.setX(x - step);
                break;
            }
            case RIGHT: {
                newCoordinate.setX(x + step);
                break;
            }
        }

        return newCoordinate;
    }
    private ArrayList<GameObjectMoveListener> listeners = new ArrayList<>();

    @Override
    public List<GameObjectMoveListener> getListeners() {
        return listeners;
    }

    @Override
    public void addListener(GameObjectMoveListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(GameObjectMoveListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }

    @Override
    public void notifyListeners(AbstractGameObject obj1, AbstractGameObject obj2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
