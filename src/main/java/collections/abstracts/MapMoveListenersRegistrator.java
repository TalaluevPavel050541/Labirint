package collections.abstracts;

import collections.interfaces.GameCollection;
import listeners.interfaces.MoveResultListener;
import listeners.interfaces.MoveResultNotifier;

import java.util.ArrayList;
import java.util.List;
 // базовый функционал для класса MapCollection (стандартная реализация полиморфизма в Java)
//реализует внутри себя работу со слушателями
public abstract class MapMoveListenersRegistrator implements GameCollection, MoveResultNotifier{

    //GameCollection содержит список слушателей listeners
    private ArrayList<MoveResultListener> listeners = new ArrayList<>(); // создание списка слушателей

    @Override
    public List<MoveResultListener> getMoveListeners() {
        return listeners;
    } //получение слушателей из списка

    @Override
    public void addMoveListener(MoveResultListener listener) { // добавление слушателя
        listeners.add(listener);
    } //добавление слушателя в список

    @Override
    public void removeMoveListener(MoveResultListener listener) { // удаление слушателя
        listeners.remove(listener);
    }

    @Override
    public void removeAllMoveListeners() {
        listeners.clear();
    } //удаление всех слушателей из списка (метод коллекции)

}