package objects.listeners;


import abstracts.AbstractMovingObject;
import enums.ActionResult;
import objects.GoldMan;

public interface MoveResultListener {

     public void notifyActionResult(ActionResult actionResult, AbstractMovingObject abstractMovingObject);

}
