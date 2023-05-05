package listeners.interfaces;

import gameobjects.abstracts.AbstractGameObject;
import gameobjects.abstracts.AbstractMovingObject;
import enums.ActionResult;

public interface MoveResultListener {

     public void notifyActionResult(ActionResult actionResult, AbstractGameObject sourceObject, AbstractGameObject targetObject);
}
