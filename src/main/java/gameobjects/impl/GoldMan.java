package gameobjects.impl;

import gameobjects.abstracts.AbstractGameObject;
import gameobjects.abstracts.AbstractMovingObject;
import enums.ActionResult;
import enums.GameObjectType;
import enums.MovingDirection;
import gameobjects.abstracts.AbstractSoundObject;
import sound.impl.WavPlayer;
import sound.interfaces.SoundObject;

import javax.sound.sampled.Clip;

/**
 * класс отвечает за работу объекта GOLDMAN - главный персонаж игры
 */
public class GoldMan extends AbstractSoundObject {

    private int totalScore = 0;// кол-во очков, собранных игроком
    private int turnsNumber = 0;// кол-во сделанных ходов
    private transient Clip moveClip; // музыкальный клип для движения
    private transient Clip treasureClip; // музыкальный клип при сборе золота
    private transient Clip winClip;// музыкальный клип выигрыша
    private transient Clip treeClip;//музыкальный клип при встрече с деревом

    public GoldMan(Coordinate coordinate) {
        super.setType(GameObjectType.GOLDMAN); //запись типа персонаж
        super.setCoordinate(coordinate); //запись координаты
        super.setIcon(getImageIcon("/images/goldman_up.png")); //сохранение иконки

        movingImages.put(MovingDirection.UP, getImageIcon("/images/goldman_up.png"));
        movingImages.put(MovingDirection.DOWN, getImageIcon("/images/goldman_down.png"));
        movingImages.put(MovingDirection.LEFT, getImageIcon("/images/goldman_left.png"));
        movingImages.put(MovingDirection.RIGHT, getImageIcon("/images/goldman_right.png"));

    }

    public void addSTotalcore(int score) {
        this.totalScore += score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTurnsNumber() {
        return turnsNumber;
    }

    public void setTurnsNumber(int turnsNumber) {
        this.turnsNumber = turnsNumber;
    }

    @Override
    public ActionResult doAction(AbstractGameObject gameObject) { // метод возвращает событие на карте в зависимости от типа объекта

        if (gameObject == null) {
            return ActionResult.NO_ACTION;
        }


        turnsNumber++; //счетчик ходов

        switch (gameObject.getType()) {  // выбор типа события в зависимости от типа объекта на карте

            case TREASURE: { //встреча с золотом
                totalScore += ((Treasure) gameObject).getScore(); // подсчет очков от сокровища
                return ActionResult.COLLECT_TREASURE; //возвращает событие сбор золота
            }

            case MONSTER: { //встреча с монстром
                return ActionResult.DIE;
            }

            case EXIT: { //выход
                totalScore *= 2; // удвоение очков
                return ActionResult.WIN; // выиграл
            }

            case TREE: {//дерево
                return ActionResult.HIDE_IN_TREE; // прячется за дерево
            }

        }

        return super.doAction(gameObject);
    }

    @Override
    public Clip getSoundClip(ActionResult actionResult) { // получение музыкального клипа

        if (moveClip == null) { // движение персонажа
            moveClip = openClip(WavPlayer.SOUND_MOVE); // метод считывания звукового файла
        }

        if (treasureClip == null) { // сокровище
            treasureClip = openClip(WavPlayer.SOUND_TREASURE);
        }

        if (winClip == null) { //выигрыш
            winClip = openClip(WavPlayer.SOUND_WIN);
        }

        if (treeClip == null) { // дерево
            treeClip = openClip(WavPlayer.SOUND_TREE);
        }

        switch (actionResult) {
            case MOVE: { // движение
                return moveClip;
            }
            case HIDE_IN_TREE: { // прячется в дереве
                return treeClip;
            }
            case COLLECT_TREASURE: { // сбор сокровищ
                return treasureClip;
            }
            case DIE: { // смерть персонажа
                return super.getDieClip();
            }
            case WIN: { //выигрыш
                return winClip;
            }
        }

        return null;
    }
}
