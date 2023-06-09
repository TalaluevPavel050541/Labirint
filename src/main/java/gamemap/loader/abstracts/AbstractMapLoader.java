package gamemap.loader.abstracts;

import creators.GameObjectCreator;
import database.SQLiteConnection;
import enums.GameObjectType;
import gamemap.abstracts.AbstractGameMap;
import gamemap.loader.interfaces.MapLoader;

import gameobjects.abstracts.AbstractGameObject;
import gameobjects.impl.Coordinate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractMapLoader implements MapLoader {

    protected AbstractGameMap gameMap; // объект базовой карты

    protected AbstractMapLoader(AbstractGameMap gameMap) {
        this.gameMap = gameMap;
    } // конструктор класса

    protected void createGameObject(String str, Coordinate coordinate) { // создание объекта по типу объекта и координатам

        GameObjectType type = GameObjectType.valueOf(str.toUpperCase()); // создаются все объекты из карты (монстры, стена, дерево, пустота, сокровище, персонаж, дерево)

         // получили доступ к экзмепляру класса GameObjectCreator - получили объект по типу и координате
        AbstractGameObject newObj = GameObjectCreator.getInstance().createObject(type, coordinate); // паттерн Одиночка

        gameMap.getGameCollection().addGameObject(newObj); // добавление нового объекта в коллекцию
//карта должна содержать вход и выход
        if (newObj.getType() == GameObjectType.EXIT) {
            gameMap.getMapInfo().setExitExist(true); // проверка на выход на карте
        } else if (newObj.getType() == GameObjectType.GOLDMAN) {
            gameMap.getMapInfo().setGoldManExist(true);//проверка на вход на карте
        }

    }

    public AbstractGameMap getGameMap() {
        return gameMap;
    } // получение карты

    public int getPlayerId(String username) { // получить пользователя по id

        PreparedStatement selectStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;

        try {

            // если есть уже такой пользователь - получить его id
            selectStmt = SQLiteConnection.getInstance().getConnection().prepareStatement("select id from player where username = ?");
            selectStmt.setString(1, username);

            rs = selectStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            selectStmt.close();


            // если нет такого пользователя - создать его и вернуть id
            insertStmt = SQLiteConnection.getInstance().getConnection().prepareStatement("insert into player(username) values(?)");
            insertStmt.setString(1, username);
            insertStmt.executeUpdate();

            // получить id созданного пользователя
            selectStmt = SQLiteConnection.getInstance().getConnection().prepareStatement("select last_insert_rowid()");
            return selectStmt.executeQuery().getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(AbstractMapLoader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                if (selectStmt != null) {
                    selectStmt.close();
                }
                if (insertStmt != null) {
                    insertStmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AbstractMapLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return 0;
    }
}


