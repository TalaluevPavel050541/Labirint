package objects;

import abstracts.AbstractMovingObject;
import enums.GameObjectType;
import enums.MovingDirection;
import objects.Coordinate;

/**
 * класс отвечает за работу объекта MONSTER
 */
public class Monster extends AbstractMovingObject {

    public Monster(Coordinate coordinate) {
        super.setType(GameObjectType.MONSTER);
        super.setCoordinate(coordinate);

        super.setIcon(getImageIcon("/images/monster_up.jpg"));// иконку по-умолчанию (можно сделать реализацию случайного выбора иконки)

    }

    @Override
    public void changeIcon(MovingDirection direction) {
        switch (direction) {
            case DOWN:
                super.setIcon(getImageIcon("/images/monster_down.jpg"));
                break;
            case LEFT:
                super.setIcon(getImageIcon("/images/monster_right.jpg"));
                break;
            case RIGHT:
                super.setIcon(getImageIcon("/images/monster_right.jpg"));
                break;
            case UP:
                super.setIcon(getImageIcon("/images/monster_up.jpg"));
                break;
        }
    }





}
