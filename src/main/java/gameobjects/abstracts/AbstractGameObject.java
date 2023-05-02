package gameobjects.abstracts;


import enums.GameObjectType;
import gameobjects.impl.Coordinate;
import gameobjects.interfaces.StaticObject;

import javax.swing.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * класс, который отвечает за любой объект, созданный в игре. задает все общие
 * характеристики объектов в игре
 */
public abstract class AbstractGameObject implements StaticObject, Serializable {

    private GameObjectType type;// все объекты будут иметь тип
    private Coordinate coordinate;// все объекты будут иметь координаты положения

    private ImageIcon icon = getImageIcon("/images/noicon.png");// изображение по-умолчанию

    protected AbstractGameObject() {
    }

    public void setIcon(ImageIcon currentIcon) {
        this.icon = currentIcon;
    }

    @Override
    public ImageIcon getIcon() {
        return icon;
    }


    protected ImageIcon getImageIcon(String path){
        return new ImageIcon(getClass().getResource(path));
    }

    @Override
    public GameObjectType getType() {
        return type;
    }

    public void setType(GameObjectType type) {
        this.type = type;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.type);
        hash = 43 * hash + Objects.hashCode(this.coordinate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractGameObject other = (AbstractGameObject) obj;
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.coordinate, other.coordinate)) {
            return false;
        }
        return true;
    }
}