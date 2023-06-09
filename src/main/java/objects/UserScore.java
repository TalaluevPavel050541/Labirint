package objects;
//таблица в БД score
public class UserScore {

    private User user; // класс пользователя

    private int score; // количество очков
    private long playDate; // дата
    private int playCount; // сколько раз сыграл пользователь - кол-во игр пользователя
 // геттеры и сеттеры параметров
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getPlayDate() {
        return playDate;
    }

    public void setPlayDate(long playDate) {
        this.playDate = playDate;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
