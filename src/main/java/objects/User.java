package objects;

import java.io.Serializable;
//таблица в БД player
public class User implements Serializable{
    protected int id; //id пользователя
    protected String username; // имя пользователя

    public User() {
    }
    public User(String username) {
        this.username = username;
    }
    public int getId() {
        return id;
    } //получение идентификатора пользователя
    public String getUsername() {
        return username;
    } // получение имени пользователя
    public void setId(int id) {
        this.id = id;
    } // запись идентификатора пользователя
    public void setUsername(String username) {
        this.username = username;
    } //запись имени пользователя
}
