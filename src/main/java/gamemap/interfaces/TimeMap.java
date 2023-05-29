package gamemap.interfaces;
//карта, которая будет реализовывать интерфейс DrawableMap будет иметь визуальный вид и уметь работать со временем
public interface TimeMap extends DrawableMap{
    
    void start(); // старт
    
    void stop(); // остановка

}
