package movestrategies.interfaces;

import gameobjects.abstracts.AbstractGameObject;
import gameobjects.abstracts.AbstractMovingObject;
import enums.MovingDirection;
import collections.interfaces.GameCollection;

public interface MoveStrategy {

// возвращает направление движения объекта
    /*
    movingObject - объект, который движется - по умолчанию это монстр
    targetObject - объект, к которому движется - по умолчанию это персонаж
    gameCollection - то, что находится на карте, коллекции
     */

    MovingDirection getDirection(AbstractMovingObject movingObject, AbstractGameObject targetObject, GameCollection gameCollection);
}
