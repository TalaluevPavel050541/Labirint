package abstracts;

import enums.ActionResult;
import enums.MovingDirection;
import interfaces.gameobjects.MovingObject;

/**
 * класс, который отвечает за любой движущийся объект. наследуется от класса
 * AbstractGameObject с добавлением функций движения
 */
public abstract class AbstractMovingObject extends AbstractGameObject implements MovingObject {

    public abstract void changeIcon(MovingDirection direction);

    private int step = 1;// по-умолчанию у всех объектов шаг равен 1

    @Override
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    protected void actionBeforeMove(MovingDirection direction) {

        // при движении объект должен сменить иконку и произвести звук
        changeIcon(direction);
//        playSound(); на будушее

    }

    @Override
    public ActionResult moveToObject(MovingDirection direction, AbstractGameObject gameObject) {
        actionBeforeMove(direction);
        return doAction(gameObject);
    }

    public ActionResult doAction(AbstractGameObject gameObject) {

        if (gameObject == null) { // край карты
            return ActionResult.NO_ACTION;
        }

        switch (gameObject.getType()) {

            case NOTHING: {
                return ActionResult.MOVE;
            }
        }

        return ActionResult.NO_ACTION;
    }
}

