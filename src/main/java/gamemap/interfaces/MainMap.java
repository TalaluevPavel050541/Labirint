package gamemap.interfaces;

import collections.interfaces.GameCollection;
import objects.MapInfo;

public interface MainMap {

    MapInfo getMapInfo();
    
    GameCollection getGameCollection();// все карты должны хранить коллекцию объектов
   
}
