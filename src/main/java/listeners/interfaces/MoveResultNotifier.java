package listeners.interfaces;

import enums.ActionResult;
import gameobjects.abstracts.AbstractMovingObject;
import gameobjects.impl.GoldMan;
import listeners.interfaces.MoveResultListener;

import java.util.List;

public interface MoveResultNotifier {

    List<MoveResultListener> getMoveListeners();

    void addMoveListener(MoveResultListener listener);

    public void removeMoveListener(MoveResultListener listener);

    public void removeAllMoveListeners();

    public void notifyMoveListeners(ActionResult actionResult, AbstractMovingObject abstractMovingObject);

}
