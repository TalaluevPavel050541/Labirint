package database;

import score.impl.DbScoreSaver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//класс для работы с БД
public class SQLiteConnection {

    private SQLiteConnection() { //конструктор класса
    }

    // Используется паттерн Singleton - используем единственный
    // экземпляр класса для того, чтобы создать объекты в любом месте программы
    private static SQLiteConnection instance; //статическая переменная класса SQLiteConnection - Поле для хранения объекта-одиночки

    public static SQLiteConnection getInstance() {   // Основной статический метод одиночки служит альтернативой
        // конструктору и является точкой доступа к экземпляру класса SQLiteConnection.
        if (instance == null) {
            instance = new SQLiteConnection();
        }

        return instance;
    }
    private Connection con;
    private String path = "db/goldman.db"; // переменная строки для указания пути файла

    public Connection getConnection() { // получение соединения
        try {


            if (con == null) {

//                // динамическая регистрация драйвера SQLite
             //  Class.forName("org.sqlite.JDBC").newInstance(); // необязательно для последних версий драйверов

                // создание подключение к базе данных по пути, указанному в урле
                String url = "jdbc:sqlite:" + path;

                con = DriverManager.getConnection(url);
            }

            return con;

        } catch (SQLException ex) { //в случае ошибки соединения выводиться лог об ошибке
            Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void closeConnection() { //закрытие соединения
        try {
            con.close();
            con = null;
        } catch (Exception e) { //в случае ошибки соединения выводиться лог об ошибке
            Logger.getLogger(DbScoreSaver.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
}
