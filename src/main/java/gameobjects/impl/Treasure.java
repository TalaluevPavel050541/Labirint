package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import gameobjects.impl.Coordinate;


/**
 * класс отвечает за работу объекта TREASURE
 */
public class Treasure extends AbstractGameObject {

    public Treasure(Coordinate coordinate) {
        super.setType(GameObjectType.TREASURE); // запись объекта золото
        super.setCoordinate(coordinate); //запись координаты
        super.saveIcon("/images/gold.png");// сохранение иконки
    }

    private int score = 5;// каждое сокровище будет давать 5 очков игроку

    public int getScore() {
        return score;
    } // получение очков

    public void setScore(int score) {
        this.score = score;
    } // запись очков
}
