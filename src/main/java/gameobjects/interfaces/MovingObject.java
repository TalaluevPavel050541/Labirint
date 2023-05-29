/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects.interfaces;

import enums.ActionResult;
import enums.MovingDirection;
import gameobjects.abstracts.AbstractGameObject;


/**
 *
 * поведение для всех движущихся объектов: 
 */
public interface MovingObject extends StaticObject {

    //передаем направление, и объект, в который хотим сделать движение
    ActionResult moveToObject(MovingDirection direction, AbstractGameObject gameObject); // метод возвращающий тип события

    int getStep(); // для каждого объекта прописан шаг, по умолчанию он равен 1 - ход на 1 шаг вперед для движущихся объектов
}
