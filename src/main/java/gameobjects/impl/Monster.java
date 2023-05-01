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
        super.setIcon(getImageIcon("/images/monster_up.jpg"));
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

    @Override
    public ActionResult doAction(AbstractGameObject gameObject) {

        switch (gameObject.getType()) {


            case TREASURE:
            case MONSTER: { // монстр не может наступать на сокровище и на других монстров
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
