package gamemap.loader.interfaces;

import objects.MapInfo;
import objects.SavedMapInfo;
import objects.User;

import java.util.ArrayList;
//интерфейс для создания карты (таблица в БД Map)
public interface MapLoader {

    boolean saveMap(SavedMapInfo mapInfo); //сохранение карты

    boolean loadMap(MapInfo mapInfo);//загрузка карты

    ArrayList<SavedMapInfo> getSavedMapList(User user); // сохранение листа с данными карт

    boolean deleteSavedMap(MapInfo mapInfo); // удаление сохраненной карты


}
