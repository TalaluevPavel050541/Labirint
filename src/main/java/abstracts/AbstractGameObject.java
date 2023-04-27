package abstracts;


import enums.GameObjectType;
import objects.common.Coordinate;
import objects.interfaces.gameobjects.StaticObject;

import javax.swing.*;
import java.util.Objects;

/**
 * класс, который отвечает за любой объект, созданный в игре. задает все общие
 * характеристики объектов в игре
 */
public abstract class AbstractGameObject implements StaticObject {
    
    private GameObjectType type;// все объекты будут иметь тип
    private Coordinate coordinate;// все объекты будут иметь координаты положения
    
    private ImageIcon icon = getImageIcon("/images/noicon.png");// изображение по-умолчанию

    public AbstractGameObject() {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractGameObject)) return false;
        AbstractGameObject that = (AbstractGameObject) o;
        return type == that.type && coordinate.equals(that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, coordinate);
    }

    @Override
    public String toString() {
        return "AbstractGameObject{" +
                "type=" + type +
                ", coordinate=" + coordinate +
                '}';
    }
}
