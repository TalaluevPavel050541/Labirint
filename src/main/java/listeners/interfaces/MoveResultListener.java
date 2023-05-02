package listeners.interfaces;

import gameobjects.abstracts.AbstractMovingObject;
import enums.ActionResult;

public interface MoveResultListener {

     public void notifyActionResult(ActionResult actionResult, AbstractMovingObject abstractMovingObject);

}
