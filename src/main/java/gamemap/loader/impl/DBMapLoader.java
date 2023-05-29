package gamemap.loader.impl;

import database.SQLiteConnection;
import gamemap.abstracts.AbstractGameMap;
import gamemap.loader.abstracts.AbstractMapLoader;
import collections.interfaces.GameCollection;
import gameobjects.impl.GoldMan;
import enums.GameObjectType;
import gameobjects.impl.Coordinate;
import objects.MapInfo;
import objects.SavedMapInfo;
import objects.User;
import score.impl.DbScoreSaver;
import utils.ObjectByteCreator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//класс загрузки карты из базы данных
public class DBMapLoader extends AbstractMapLoader {

    public DBMapLoader(AbstractGameMap gameMap) {
        super(gameMap);
    }

    @Override
    public ArrayList<SavedMapInfo> getSavedMapList(User user) { // получение списка данных из таблицы saved_game

        ArrayList<SavedMapInfo> list = new ArrayList<>();
        PreparedStatement selectStmt = null;

        ResultSet rs = null;

        SQLiteConnection sqliteDB = SQLiteConnection.getInstance();

        try {

            selectStmt = sqliteDB.getConnection().prepareStatement("select * from saved_game where player_id = ? order by total_score desc");

            selectStmt.setInt(1, user.getId());

            rs = selectStmt.executeQuery();

            while (rs.next()) {
                SavedMapInfo mapInfo = new SavedMapInfo();
                mapInfo.setId(rs.getInt("id"));
                mapInfo.setSaveDate(rs.getLong("save_date"));
                mapInfo.setUser(user);
                mapInfo.setTotalScore(rs.getInt("total_score"));
                mapInfo.setTurnsCount(rs.getInt("turns_count"));
                list.add(mapInfo);
            }




        } catch (SQLException ex) {
            Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {
                if (selectStmt != null) {
                    selectStmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


        return list;
    }

    @Override
    public boolean saveMap(SavedMapInfo saveMapInfo) { // сохранение карты
        PreparedStatement insertStmt = null;
        SQLiteConnection sqliteDB = SQLiteConnection.getInstance();

        try {
          //запись таблицы saved_game в БД - заполнение данными
            insertStmt = sqliteDB.getConnection().prepareStatement("insert into saved_game(player_id, save_date, collection, total_score, map_id, turns_count) values(?,?,?,?,?,?)");
            insertStmt.setInt(1, saveMapInfo.getUser().getId());
            insertStmt.setLong(2, new Date().getTime());
            insertStmt.setBytes(3, ObjectByteCreator.getBytes(getGameMap().getGameCollection()));
            insertStmt.setInt(4, saveMapInfo.getTotalScore());
            insertStmt.setInt(5, saveMapInfo.getId());
            insertStmt.setInt(6, saveMapInfo.getTurnsCount());

            insertStmt.executeUpdate();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(DBMapLoader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (insertStmt != null) {
                try {
                    insertStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBMapLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return false;
    }

    @Override
    public boolean loadMap(MapInfo mapInfo) { //загрузка карты

        if (mapInfo.getLevelId() > 0) {
            return createNewMap(mapInfo.getLevelId()); // выбор карты по идентификатору уровня сложности
        }

        if (mapInfo.getId() > 0) {
            return loadMap(mapInfo.getId());// выбор карты по идентификатору
        }

        return false;

    }

    @Override
    public boolean deleteSavedMap(MapInfo mapInfo) { // удаление сохраненной карты из БД по идентификатору
        PreparedStatement deleteStmt = null;
        SQLiteConnection sqliteDB = SQLiteConnection.getInstance();

        try {

            deleteStmt = sqliteDB.getConnection().prepareStatement("delete from saved_game where id=?");
            deleteStmt.setInt(1, mapInfo.getId());

            int result = deleteStmt.executeUpdate();

            if (result == 1) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBMapLoader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (deleteStmt != null) {
                try {
                    deleteStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBMapLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return false;
    }

    private boolean createNewMap(int levelId) { //создание новой карты с указанием уровня сложности
        PreparedStatement selectStmt = null;

        ResultSet rs = null;

        SQLiteConnection sqliteDB = SQLiteConnection.getInstance();

        try {

            selectStmt = sqliteDB.getConnection().prepareStatement("select * from map where level_id = ? limit 1");

            selectStmt.setInt(1, levelId);

            rs = selectStmt.executeQuery();

            while (rs.next()) {
                 //запись данных
                gameMap.getMapInfo().setId(rs.getInt("id"));
                gameMap.getMapInfo().setMapName(rs.getString("map_name"));
                gameMap.getMapInfo().setTurnsLimit(rs.getInt("turns_limit"));
                gameMap.getMapInfo().setWidth(rs.getInt("width"));
                gameMap.getMapInfo().setHeight(rs.getInt("height"));
                gameMap.getMapInfo().setLevelId(levelId);
                gameMap.getMapInfo().setValue(rs.getString("value"));

            }


            String[] lines = gameMap.getMapInfo().getValue().split("\n");

            gameMap.getMapInfo().setExitExist(false);
            gameMap.getMapInfo().setGoldManExist(false);


            int y = 0; // номер строки в массиве
            int x = 0; // номер столбца в массиве

            for (int i = 0; i < lines.length; i++) {
                x = 0; // чтобы каждый раз с первого столбца начинал

                for (String str : lines[i].split(",")) {
                    // вытаскивать все значения в строке между запятыми, чтобы заполнить карту элементами

                    createGameObject(str, new Coordinate(x, y));
                    x++;
                }
                y++;
            }


            if (!gameMap.getMapInfo().isValidMap()) {
                throw new Exception("The map is not valid!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (selectStmt != null) {
                try {
                    if (selectStmt != null) {
                        selectStmt.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return true;

    }

    private boolean loadMap(int id) { // выбор карты из БД по id
        PreparedStatement selectStmt = null;

        ResultSet rs = null;

        SQLiteConnection sqliteDB = SQLiteConnection.getInstance();

        try {

            selectStmt = sqliteDB.getConnection().prepareStatement("select "
                    + " g.player_id,"
                    + " g.save_date,"
                    + " g.collection,"
                    + " g.total_score,"
                    + " g.turns_count,"
                    + " m.height,"
                    + " m.width,"
                    + " m.turns_limit,"
                    + " m.map_name,"
                    + " m.level_id"
                    + " from saved_game g inner join map m on m.id=g.map_id where g.id = ?");

            selectStmt.setInt(1, id);

            rs = selectStmt.executeQuery();

            while (rs.next()) {
                gameMap.setGameCollection((GameCollection) ObjectByteCreator.getObject(rs.getBytes("collection")));
                gameMap.getMapInfo().setHeight(rs.getInt("height"));
                gameMap.getMapInfo().setWidth(rs.getInt("width"));
                gameMap.getMapInfo().setMapName(rs.getString("map_name"));
                gameMap.getMapInfo().setTurnsLimit(rs.getInt("turns_limit"));

                GoldMan goldMan = (GoldMan)gameMap.getGameCollection().getGameObjects(GameObjectType.GOLDMAN).get(0); // получение персонажа из коллекции
                goldMan.setTurnsNumber(rs.getInt("turns_count"));
                goldMan.setTotalScore(rs.getInt("total_score"));
            }


            gameMap.getMapInfo().setExitExist(false);
            gameMap.getMapInfo().setGoldManExist(false);


        } catch (SQLException ex) {
            Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (selectStmt != null) {
                try {
                    if (selectStmt != null) {
                        selectStmt.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return true;
    }
}


