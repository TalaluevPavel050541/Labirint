/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.interfaces.gameobjects;

import abstracts.AbstractGameMap;
import enums.MovingDirection;
import abstracts.AbstractGameObject;

import javax.swing.*;

/**
 *
 * поведение для всех движущихся объектов: 
 */
public interface MovingObject extends StaticObject {

    void move(MovingDirection direction, AbstractGameMap abstractGameMap);

    int getStep();
   
}
