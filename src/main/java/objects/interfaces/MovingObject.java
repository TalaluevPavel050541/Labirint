/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.interfaces;

import enums.MovingDirection;
import objects.common.AbstractGameObject;

import javax.swing.*;

/**
 *
 * поведение для всех движущихся объектов: 
 */
public interface MovingObject extends StaticObject{
    
    void move(MovingDirection direction);
    
    void getMoveResult(AbstractGameObject objectInNewCoordinate);
    
    // иконки при движении в разные стороны
    ImageIcon getIconLeft();

    ImageIcon getIconUp();

    ImageIcon getIconDown();

    ImageIcon getIconRight();
   
}
