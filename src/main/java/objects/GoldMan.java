package objects;

import abstracts.AbstractGameObject;
import abstracts.AbstractMovingObject;
import enums.ActionResult;
import enums.GameObjectType;
import enums.MovingDirection;
import objects.sound.SoundObject;

/**
 * класс отвечает за работу объекта GOLDMAN - главный персонаж игры
 */
public class GoldMan extends AbstractMovingObject implements SoundObject {

    private int totalScore = 0;// кол-во очков, собранных игроком
    private int turnsNumber = 0;// кол-во сделанных ходов

    public GoldMan(Coordinate coordinate) {
        super.setType(GameObjectType.GOLDMAN);
        super.setCoordinate(coordinate);
        super.setIcon(getImageIcon("/images/goldman_up.png"));
    }

    public void addSTotalcore(int score) {
        this.totalScore += score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTurnsNumber() {
        return turnsNumber;
    }

    public void setTurnsNumber(int turnsNumber) {
        this.turnsNumber = turnsNumber;
    }

    @Override
    public void changeIcon(MovingDirection direction) {
        switch (direction) {
            case DOWN:
                super.setIcon(getImageIcon("/images/goldman_down.png"));
                break;
            case LEFT:
                super.setIcon(getImageIcon("/images/goldman_left.png"));
                break;
            case RIGHT:
                super.setIcon(getImageIcon("/images/goldman_right.png"));
                break;
            case UP:
                super.setIcon(getImageIcon("/images/goldman_up.png"));
                break;
        }
    }

    @Override
    public ActionResult doAction(AbstractGameObject gameObject) {

        turnsNumber++;

        switch (gameObject.getType()) {

            case TREASURE: {
                totalScore += ((Treasure) gameObject).getScore();
                return ActionResult.COLLECT_TREASURE;
            }

            case MONSTER:  {
                return ActionResult.DIE;
            }

            case EXIT:{
                totalScore *= 2;
                return ActionResult.WIN;
            }

        }

        return super.doAction(gameObject);
    }

    @Override
    public String getSoundPath(ActionResult actionResult) {
        switch (actionResult) {
            case COLLECT_TREASURE: return "treasure.wav";
            case DIE: return "die.wav";
            case WIN: return "win.wav";
        }

        return null;
    }
}
