package listeners.interfaces;

import java.util.List;
import enums.ActionResult;
import gameobjects.abstracts.AbstractGameObject;
import gameobjects.abstracts.AbstractMovingObject;
//интерфейс слушателя для паттерна Наблюдатель - этот интерфейс для издателя
//тот кто будет уведомлять о том, что произошел ход и нужно отреагировать на него
public interface MoveResultNotifier {

    List<MoveResultListener> getMoveListeners(); //получение списка со всеми слушателями, которые реализовали интерфейс MoveResultListener

    void addMoveListener(MoveResultListener listener); //добавление слушателя, которые реализовали интерфейс MoveResultListener

    public void removeMoveListener(MoveResultListener listener); //удаление слушателя, которые реализовали интерфейс MoveResultListener

    public void removeAllMoveListeners(); //удаление всех слушателей, которые реализовали интерфейс MoveResultListener

    //метод оповещения о событии
    public void notifyMoveListeners(ActionResult actionResult, AbstractGameObject movingObject, AbstractGameObject targetObject);

}
