/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.gameobjects;

import enums.ActionResult;
import enums.MovingDirection;
import abstracts.AbstractGameObject;


/**
 *
 * поведение для всех движущихся объектов: 
 */
public interface MovingObject extends StaticObject {

    ActionResult moveToObject(MovingDirection direction, AbstractGameObject gameObject);

    int getStep();
   
}
