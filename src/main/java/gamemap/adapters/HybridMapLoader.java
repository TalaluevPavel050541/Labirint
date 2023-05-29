package gamemap.adapters;

import enums.LocationType;
import gamemap.abstracts.AbstractGameMap;
import gamemap.loader.impl.DBMapLoader;
import gamemap.loader.impl.FSMapLoader;
import objects.MapInfo;
import objects.SavedMapInfo;
import objects.User;

import java.util.ArrayList;

// Паттерн проектирования «Адаптер» - для возможности одновременной работы из файловой системы и БД
/*
загрузка карты из файла
сохранение результатов в БД
 */
public class HybridMapLoader {
    
    private DBMapLoader dBMapLoader; // объект для работы с БД
    private FSMapLoader fSMapLoader;// объект для работы с файловой системой

    private AbstractGameMap gameMap; //
    
    public HybridMapLoader(AbstractGameMap gameMap) { //инициализуруем объекты в зависимости от карты, с которой будут работать
        dBMapLoader = new DBMapLoader(gameMap);
        fSMapLoader = new FSMapLoader(gameMap);
        this.gameMap = gameMap;
    }

    public boolean saveMap(SavedMapInfo mapInfo, LocationType locationType){ // сохранение карты
        switch (locationType){ //откуда выполнять данную операцию
            case DB:{ // в БД
                return dBMapLoader.saveMap(mapInfo);
            }
                
            case FS:{ // в файловой системе
                return fSMapLoader.saveMap(mapInfo);
            }
        }
        
        return false;
    }
    
    public boolean loadMap(MapInfo mapInfo, LocationType locationType){ // загрузка карты
        switch (locationType){
            case DB:{ // из БД
                gameMap = dBMapLoader.getGameMap();
                return dBMapLoader.loadMap(mapInfo);
            }
                
            case FS:{ // из файловой системы
                gameMap = fSMapLoader.getGameMap();
                return fSMapLoader.loadMap(mapInfo);
            }
        }

        return false;
    }

    public ArrayList<SavedMapInfo> getSavedMapList(User user, LocationType locationType){ // получение списка сохраненных игр
        switch (locationType){
            case DB:{ // из БД
                return dBMapLoader.getSavedMapList(user);
            }
                
            case FS:{ // из файловой системы
                return fSMapLoader.getSavedMapList(user);
            }
        }
        
        return null;
    }
    
    public boolean deleteSavedMap(MapInfo mapInfo, LocationType locationType){ //удаление сохраненной карты
        switch (locationType){
            case DB:{ // из базы данных
                return dBMapLoader.deleteSavedMap(mapInfo);
            }
                
            case FS:{ // из файловой системы
                return fSMapLoader.deleteSavedMap(mapInfo);
            }
        }
        
        return false;
    }

    public AbstractGameMap getGameMap() {
        return gameMap;
    }

    
    public int getPlayerId(String username){
        return dBMapLoader.getPlayerId(username);        
    } // получение id пользователя по имени

}
