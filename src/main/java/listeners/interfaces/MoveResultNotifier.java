package listeners.interfaces;

import java.util.List;
import enums.ActionResult;
import gameobjects.abstracts.AbstractMovingObject;

public interface MoveResultNotifier {

    List<MoveResultListener> getMoveListeners();

    void addMoveListener(MoveResultListener listener);

    public void removeMoveListener(MoveResultListener listener);

    public void removeAllMoveListeners();

    public void notifyMoveListeners(ActionResult actionResult, AbstractMovingObject abstractMovingObject);

}
