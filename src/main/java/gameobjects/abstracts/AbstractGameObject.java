package gameobjects.abstracts;


import enums.GameObjectType;
import gameobjects.impl.Coordinate;
import gameobjects.interfaces.StaticObject;

import javax.swing.*;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Objects;

/**
 * класс, который отвечает за любой объект, созданный в игре. задает все общие
 * характеристики объектов в игре
 */
public abstract class AbstractGameObject implements StaticObject, Serializable {

    protected static EnumMap<GameObjectType, ImageIcon> staticImages = new EnumMap<>(GameObjectType.class);// карта иконок для всех направлений
    private GameObjectType type;// все объекты будут иметь тип
    private Coordinate coordinate;// все объекты будут иметь координаты положения
    private ImageIcon icon = getImageIcon("/images/noicon.png");// изображение по-умолчанию

    protected AbstractGameObject() {//конструктор без параметров для класса
    }

    public void setIcon(ImageIcon currentIcon) {
        this.icon = currentIcon;
    } // меттод сеттер для присвоения иконки

    @Override
    public ImageIcon getIcon() {
        return icon;
    } // меттод геттер для получения иконки

    protected ImageIcon getImageIcon(String path) { // получение иконки по указанному пути
        return new ImageIcon(getClass().getResource(path));
    }

    @Override
    public GameObjectType getType() {
        return type;
    } // метод получения типа объекта

    public void setType(GameObjectType type) {
        this.type = type;
    } // метод присвоения типа объекта

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    } // метод получения координаты

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    } // метод присвоения координаты

    @Override
    public int hashCode() { // это метод для получения уникального целочисленного номера объекта, своего рода его идентификатор. Благодаря хешу (номеру) можно, например, быстро определить местонахождение объекта в коллекции.
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.type);
        hash = 43 * hash + Objects.hashCode(this.coordinate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) { // метод проверки на сравнение объектов Данный метод проверяет два объекта одного происхождения на логическую равность.
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

    protected void saveIcon(String path) { // метод сохранения иконки с указанием пути ее расположения
        if (staticImages.get(type) == null) { // если в коллекции нет данных по типу объекта
            staticImages.put(type, getImageIcon(path)); //то мы добавляем эти данные, указывая тип и путь (смотрите коллекции в Java, в данном случае используются методы коллекции Map, это стандартные методы библиотеки коллекций)
        }
        setIcon(staticImages.get(type)); // получение данных из коллекции по ключу - тип объекта
    }
}
