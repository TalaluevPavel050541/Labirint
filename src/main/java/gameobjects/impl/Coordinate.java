package gameobjects.impl;

import java.io.Serializable;

/**
 * класс работает с координатами объекта. Каждый объект в игре имеет свои
 * координаты
 */
public class Coordinate implements Serializable {

    private int x; //координата х
    private int y; //координата у

    public Coordinate(int x, int y) { //конструктор с координатами
        this.x = x;
        this.y = y;
    }
//сеттеры и геттеры для полей - запись и получение данных
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) { // стандартные методы для коллекций
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Coordinate)) {
            return false;
        }
        Coordinate objCoord = (Coordinate) obj;
        return (x == objCoord.getX() && y == objCoord.getY());
    }

    @Override
    public int hashCode() { // стандартные методы для коллекций
        int hash = 7;
        hash = 19 * hash + this.x;
        hash = 19 * hash + this.y;
        return hash;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    } // метод для преобразования в строку
}
