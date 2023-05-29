package gamemap.interfaces;

import gameobjects.abstracts.AbstractGameObject;

import java.awt.*;
// интерфейс для отображения карты
public interface DrawableMap extends MainMap{

    Component getMapComponent(); //получение компонентов карты

    boolean updateMap(); // проверка на обновление карты

    void updateMapObjects(AbstractGameObject obj1, AbstractGameObject obj2); // обновление объектов карты

}
