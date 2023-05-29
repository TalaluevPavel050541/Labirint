package listeners.interfaces;

import gameobjects.abstracts.AbstractGameObject;
import gameobjects.abstracts.AbstractMovingObject;
import enums.ActionResult;
// этот интерфейс должны реализовывать все объекты, кто захочет слушать событие после движения игрока
public interface MoveResultListener {
//метод оповещения о событии происходящем на карте
     public void notifyActionResult(ActionResult actionResult, AbstractGameObject sourceObject, AbstractGameObject targetObject);
}
