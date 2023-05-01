package creators;

import gamemap.abstracts.AbstractGameMap;
import enums.LocationType;
import gamemap.loader.impl.DBMapLoader;
import gamemap.loader.impl.FSMapLoader;
import gamemap.loader.interfaces.MapLoader;

public class MapLoaderCreator {

    private static MapLoaderCreator instance;

    public static MapLoaderCreator getInstance() {
        if (instance == null) {
            instance = new MapLoaderCreator();
        }
        return instance;
    }

    public MapLoader createMapLoader(LocationType type, AbstractGameMap gameMap) {
        MapLoader obj = null;

        switch (type) {
            case FS: {
                obj = new FSMapLoader(gameMap);
                break;
            }
            case DB: {
                obj = new DBMapLoader(gameMap);
                break;
            }
            default:
                throw new IllegalArgumentException("Can't create map type: " + type);

        }

        return obj;
    }
}