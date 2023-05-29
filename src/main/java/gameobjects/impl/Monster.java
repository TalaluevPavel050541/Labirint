package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.ActionResult;
import enums.GameObjectType;
import enums.MovingDirection;
import gameobjects.abstracts.AbstractSoundObject;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.util.EnumMap;

/**
 * класс отвечает за работу объекта MONSTER
 */
public class Monster extends AbstractSoundObject {

    protected static EnumMap<MovingDirection, ImageIcon> monsterImages = new EnumMap<>(MovingDirection.class);// карта иконок для всех направлений монстра

    public Monster(Coordinate coordinate) {
        super.setType(GameObjectType.MONSTER); // запись типа объекта
        super.setCoordinate(coordinate); //запись координаты

        if (monsterImages.isEmpty()) {// для всех монстров будут одинаковые картики, поэтому нет смысла создавать для каждого объекта отдельный map
            monsterImages.put(MovingDirection.UP, getImageIcon("/images/monster_up.jpg"));
            monsterImages.put(MovingDirection.DOWN, getImageIcon("/images/monster_down.jpg"));
            monsterImages.put(MovingDirection.LEFT, getImageIcon("/images/monster_left.jpg"));
            monsterImages.put(MovingDirection.RIGHT, getImageIcon("/images/monster_right.jpg"));
        }

        super.changeIcon(MovingDirection.UP);

    }

    @Override
    public ActionResult doAction(AbstractGameObject gameObject) { // метод возвращает событие

        if (gameObject == null){
            return ActionResult.NO_ACTION;
        }

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
    public Clip getSoundClip(ActionResult actionResult) { // получение музыкального клипа
        switch (actionResult) {
            case DIE:
                return super.getDieClip();

        }

        return null;
    }

    @Override
    public void changeIcon(MovingDirection direction) {
        super.setIcon(monsterImages.get(direction));
    } // смена иконки
}