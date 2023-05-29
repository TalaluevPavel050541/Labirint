package movestrategies.impl;



import gameobjects.abstracts.AbstractGameObject;
import gameobjects.abstracts.AbstractMovingObject;
import enums.ActionResult;
import enums.MovingDirection;
import collections.interfaces.GameCollection;
import movestrategies.interfaces.MoveStrategy;

import java.util.Random;
//аггресивная стратегия для движения монстра
public class AgressiveMoving implements MoveStrategy {

    private MovingDirection[] directions = MovingDirection.values();//массав направлений дживжений объекта
    private AbstractMovingObject movingObject;
    private GameCollection gameCollection;

    //метод направления движения
    @Override
    public MovingDirection getDirection(AbstractMovingObject movingObject, AbstractGameObject targetObject, GameCollection gameCollection) {

        this.movingObject = movingObject;
        this.gameCollection = gameCollection;

        // сначала ищем вокруг, можно ли съесть игрока
        MovingDirection direction = searchAction(ActionResult.DIE, false); // ищет по событию DIE

        // если рядом персонажа (не съели его) - просто двигаемся в случайном направлении
        if (direction == null) {
            direction = searchAction(ActionResult.MOVE, true); // Ищет направление при движении
        }

        return direction;
    }

    private Random random = new Random();

    // случайное определение направления
    private MovingDirection getRandomDirection() {
        return directions[random.nextInt(directions.length)];
    }

    //метод ищет куда будет двигаться объект
    private MovingDirection searchAction(ActionResult actionResult, boolean random) {

        int c = 0;

        MovingDirection direction = null;

        if (random) {
            do {
                c++; //счетчик
                direction = getRandomDirection(); // случайное определение направления
                if (c>4){
                    return direction;
                }
            } while (!checkActionResult(actionResult, direction));// случайно подбирать сторону пока не найдем нужный ActionResult
        }else{
            for (MovingDirection movingDirection : directions) {// искать по всем 4 направлениям
                if (checkActionResult(actionResult, movingDirection)){ // проверка на действие на нужный ActionResult
                    direction = movingDirection; // присвоение нужного направления движения
                    break;
                }
            }
        }

        return direction;

    }

// ищет искомое действие
    private boolean checkActionResult(ActionResult actionResult, MovingDirection direction){
        AbstractGameObject objectInNewCoordinate = gameCollection.getObjectByCoordinate(movingObject.getDirectionCoordinate(direction));
        if (objectInNewCoordinate == null){
            return false;
        }
        //определяет действие у движущегося объекта и сранивает его с заданным действием (либо DIE, либо MOVE)
        return movingObject.doAction(objectInNewCoordinate).equals(actionResult); // возвращает true или false
    }
}

