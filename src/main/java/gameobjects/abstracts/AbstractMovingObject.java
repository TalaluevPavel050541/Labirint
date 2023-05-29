package gameobjects.abstracts;

import enums.ActionResult;
import enums.MovingDirection;
import gameobjects.interfaces.MovingObject;
import gameobjects.impl.Coordinate;

import javax.swing.*;
import java.io.Serializable;
import java.util.EnumMap;

/**
 * класс, который отвечает за любой движущийся объект. наследуется от класса
 * AbstractGameObject с добавлением функций движения
 */
public abstract class AbstractMovingObject extends AbstractGameObject implements MovingObject {

    private int step = 1;// по-умолчанию у всех объектов шаг равен 1

    protected EnumMap<MovingDirection, ImageIcon> movingImages = new EnumMap<>(MovingDirection.class);// карта иконок для всех направлений

    @Override
    public int getStep() {
        return step;
    } // получение шага

    public void setStep(int step) {
        this.step = step;
    } //запись шага

    protected void actionBeforeMove(MovingDirection direction) { // при каждом движении будет меняться иконка

        // при движении объект должен сменить иконку
        changeIcon(direction); // задано направление движения

    }

    //метод возвращает тип события, потом используется в MapCollection для определения дальнейших дейсвтвий
    @Override
    public ActionResult moveToObject(MovingDirection direction, AbstractGameObject gameObject) {
        actionBeforeMove(direction); // смена иконки при движении
        return doAction(gameObject); // метод возвращает событие
    }

    public ActionResult doAction(AbstractGameObject gameObject) {

        if (gameObject == null) { // край карты
            return ActionResult.NO_ACTION; // нет действий
        }

        switch (gameObject.getType()) {

            case NOTHING: {//если пустота, объект движется
                return ActionResult.MOVE; // объект движется
            }

            case WALL: {// по-умолчанию объект не может ходить через стену
                return ActionResult.NO_ACTION; // нет действий
            }
        }

        return ActionResult.NO_ACTION; // нет действий
    }

    public Coordinate getDirectionCoordinate(MovingDirection direction) { //получение координат при движении в зависимости от направления движения

        // берем текущие координаты объекта, которые нужно передвинуть (индексы начинаются с нуля)
        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();

        Coordinate newCoordinate = new Coordinate(x, y); // создание новых координат

        switch (direction) {// определяем, в каком направлении нужно двигаться
            case UP: { // вверх
                newCoordinate.setY(y - this.getStep());
                break;
            }
            case DOWN: { // вниз
                newCoordinate.setY(y + this.getStep());
                break;
            }
            case LEFT: { //влево
                newCoordinate.setX(x - this.getStep());
                break;
            }
            case RIGHT: { //вправо
                newCoordinate.setX(x + this.getStep()); // задание новых координат
                break;
            }
        }

        return newCoordinate; // возвращаем новые координаты
    }
    public void changeIcon(MovingDirection direction) {
        super.setIcon(movingImages.get(direction));
    } //смена иконки при движении в разные направления - есть у монстра и персонажа
}
