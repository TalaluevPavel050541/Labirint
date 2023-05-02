package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.ActionResult;
import enums.GameObjectType;
import enums.MovingDirection;
import gameobjects.abstracts.AbstractSoundObject;

import javax.sound.sampled.Clip;

/**
 * класс отвечает за работу объекта MONSTER
 */
public class Monster extends AbstractSoundObject {

    private transient Clip clip;

    public Monster(Coordinate coordinate) {
        super.setType(GameObjectType.MONSTER);
        super.setCoordinate(coordinate);
        super.setIcon(getImageIcon("/images/monster_up.jpg"));// иконку по-умолчанию (можно сделать реализацию случайного выбора иконки)

        movingImages.put(MovingDirection.UP, getImageIcon("/images/monster_up.jpg"));
        movingImages.put(MovingDirection.DOWN, getImageIcon("/images/monster_down.jpg"));
        movingImages.put(MovingDirection.LEFT, getImageIcon("/images/monster_left.jpg"));
        movingImages.put(MovingDirection.RIGHT, getImageIcon("/images/monster_right.jpg"));

    }

    @Override
    public ActionResult doAction(AbstractGameObject gameObject) {

        switch (gameObject.getType()) {

            case TREASURE:
            case MONSTER:
            case TREE: { // монстр не может наступать на сокровище, дерево и на других монстров
                return ActionResult.NO_ACTION;
            }

            case GOLDMAN: {
                return ActionResult.DIE;
            }

        }

        return super.doAction(gameObject);
    }

    @Override
    public Clip getSoundClip(ActionResult actionResult) {
        switch (actionResult) {
            case DIE:
                return super.getDieClip();

        }

        return null;
    }
}
