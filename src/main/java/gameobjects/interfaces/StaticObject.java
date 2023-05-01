/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects.interfaces;

import enums.GameObjectType;
import gameobjects.impl.Coordinate;

import javax.swing.*;

public interface StaticObject {


    // объект должен иметь иконку
    ImageIcon getIcon();

    // координаты
    Coordinate getCoordinate();

    // тип объекта
    GameObjectType getType();
    
            
}
