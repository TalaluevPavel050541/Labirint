package objects.maps;

import abstracts.AbstractGameMap;
import abstracts.AbstractGameObject;
import enums.GameObjectType;
import objects.Coordinate;
import objects.creators.GameObjectCreator;
import interfaces.collections.GameCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// класс по загрузке карты с файловой системы

public class FSGameMap extends AbstractGameMap {


    public FSGameMap(){
        super();
    }

    public FSGameMap(GameCollection gameCollection) {
        super(gameCollection);
    }

    @Override
    public boolean loadMap(Object source) {
        File file = new File(source.toString());
        if (!file.exists()) {
            throw new IllegalArgumentException("filename must not be empty!");
        }

        try {
            setExitExist(false);
            setGoldManExist(false);

            setHeight(getLineCount(file));

            BufferedReader br = new BufferedReader(new FileReader(file));

            String strLine = br.readLine().trim(); // считываем первую строку для определения имени, длины, ширины карты. убираем пробела по краям

            // разбиваем первую строку на токены, разделенные запятой.
            setName(strLine.split(",")[0]);

            setTimeLimit(Integer.valueOf(strLine.split(",")[1]).intValue());
            setWidth(Integer.valueOf(strLine.split(",")[2]).intValue());

            int y = 0; // номер строки в массиве
            int x = 0; // номер столбца в массиве

            while ((strLine = br.readLine()) != null) {
                x = 0; // чтобы каждый раз с первого столбца начинал

                for (String str : strLine.split(",")) {
                    // вытаскивать все значения в строке между запятыми, чтобы заполнить карту элементами

                    createGameObject(str, new Coordinate(x, y));
                    x++;
                }
                y++;
            }

            if (!isValidMap()) {
                throw new Exception("The map is not valid!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;

    }

    private void createGameObject(String str, Coordinate coordinate) {

        GameObjectType type = GameObjectType.valueOf(str.toUpperCase());
        AbstractGameObject newObj = GameObjectCreator.getInstance().createObject(type, coordinate); //реализация Фабричного метода
        getGameCollection().addGameObject(newObj);

        if (newObj.getType() == GameObjectType.EXIT) {
            setExitExist(true);
        } else if (newObj.getType() == GameObjectType.GOLDMAN) {
            setGoldManExist(true);
        }

    }

    @Override
    public boolean saveMap(Object source) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean drawMap() {
        return false;
    }

    private int getLineCount(File file) {
        BufferedReader reader = null;
        int lineCount = 0;
        try {
            reader = new BufferedReader(new FileReader(file));

            while (reader.readLine() != null) {
                lineCount++;
            }
            lineCount = lineCount - 1;// lineNumber-1 потому что первая строка из файла не входит в карту
        } catch (IOException ex) {
            Logger.getLogger(FSGameMap.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(FSGameMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lineCount;

    }
}
