package objects;
//таблица saved_game в БД
public class SavedMapInfo extends MapInfo{

    private long saveDate; //дата сохранения
    private int totalScore; // общее количество очков
    private int turnsCount; // количество ходов

    public long getSaveDate() {
        return saveDate;
    } // получение даты

    public void setSaveDate(long saveDate) {
        this.saveDate = saveDate;
    } // запись даты

    public int getTotalScore() {
        return totalScore;
    } // получение очков

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    } //запись очков

    public int getTurnsCount() {
        return turnsCount;
    } // получение шагов

    public void setTurnsCount(int turnsCount) {
        this.turnsCount = turnsCount;
    } //запись шагов
}
