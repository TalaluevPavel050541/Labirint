package objects.listeners;


import enums.ActionResult;
import objects.GoldMan;

public interface MoveResultListener {
    
     public void notifyActionResult(ActionResult actionResult, GoldMan goldMan);

}
