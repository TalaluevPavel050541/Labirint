package gamemap.facades;

import enums.GameObjectType;
import enums.LocationType;
import enums.MovingDirection;
import gamemap.abstracts.AbstractGameMap;
import gamemap.adapters.HybridMapLoader;
import gameobjects.abstracts.AbstractGameObject;
import gameobjects.impl.GoldMan;
import listeners.interfaces.MoveResultListener;
import objects.MapInfo;
import objects.SavedMapInfo;
import objects.UserScore;
import score.interfaces.ScoreSaver;
import sound.impl.WavPlayer;
import sound.interfaces.SoundPlayer;

import java.awt.*;

// используется паттерн Фасад - создание единого интерфейса для многих объектов, этот фасад можно передавать в любой из фреймов
public class GameFacade {

    private HybridMapLoader mapLoader; // объект HybridMapLoader
    private SoundPlayer soundPlayer; // объект SoundPlayer
    private ScoreSaver scoreSaver; // объект ScoreSaver
    private MapInfo mapInfo; // объект MapInfo
    private AbstractGameMap gameMap; // объект AbstractGameMap

    //передаем объекты при создании GameFacade
    public GameFacade(HybridMapLoader mapLoader, SoundPlayer soundPlayer, ScoreSaver scoreSaver) { // конструктор с параметрами
        this.mapLoader = mapLoader;
        this.scoreSaver = scoreSaver;
        this.soundPlayer = soundPlayer;
    }

    public GameFacade() { // конструктор пустой
    }

    public void setSoundPlayer(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    public void setMapLoader(HybridMapLoader mapLoader) { // получение карты
        this.mapLoader = mapLoader;

        // слушатели для звуков должны идти в первую очередь, т.к. они запускаются в отдельном потоке и не мешают выполняться следующим слушателям
        if (soundPlayer instanceof MoveResultListener) {
            mapLoader.getGameMap().getGameCollection().addMoveListener((MoveResultListener) soundPlayer); // реализация Паттерна Наблюдатель
        //добавление в GameCollection списка слушателей
        }

        updateMap(); // обновление карты
    }

    public ScoreSaver getScoreSaver() {
        return scoreSaver;
    } // получение очков

    public void setScoreSaver(ScoreSaver scoreSaver) {
        this.scoreSaver = scoreSaver;
    } //запись очков

    public void stopGame() { //остановка игры
        soundPlayer.stopBackgoundMusic(); // остановка музыки
        mapLoader.getGameMap().stop();
    }
// движение объекта по направлению
    public void moveObject(MovingDirection movingDirection, GameObjectType gameObjectType) {
        gameMap.getGameCollection().moveObject(movingDirection, gameObjectType); // вызов метода moveObject из GameCollection
    }

    public Component getMap() {
        return mapLoader.getGameMap().getMapComponent();
    } // получение компоненты карта

    public void saveScore() { //сохранение очков
        UserScore userScore = new UserScore();
        userScore.setUser(mapInfo.getUser()); //запись пользователя
        userScore.setScore(getGoldMan().getTotalScore()); //запись очков полученных персонажем
        scoreSaver.saveScore(userScore); //сохранение очков
    }

    public void addMoveListener(MoveResultListener listener) { // добавление слушателя
        mapLoader.getGameMap().getGameCollection().addMoveListener(listener);
    }

    public void saveMap() { //сохранение карты в БД
        SavedMapInfo saveMapInfo = new SavedMapInfo();
        saveMapInfo.setId(mapInfo.getId());//записываем id карты
        saveMapInfo.setUser(mapInfo.getUser()); //записываем данные пользователя
        saveMapInfo.setTotalScore(getGoldMan().getTotalScore()); // для полученного персонажа записываем общее количество шагов
        saveMapInfo.setTurnsCount(getGoldMan().getTurnsNumber()); //записываем количество шагов для полученного персонажа
        mapLoader.saveMap(saveMapInfo, LocationType.DB); // сохраняем карту в БД
    }

    public void startGame() { // запуск игры
        soundPlayer.startBackgroundMusic(WavPlayer.SOUND_BACKGROUND); //выбор музыки
        mapLoader.getGameMap().start(); //выбор персонажа
    }

    private GoldMan getGoldMan() { //получение персонажа из коллекции
        return (GoldMan) mapLoader.getGameMap().getGameCollection().getGameObjects(GameObjectType.GOLDMAN).get(0);
    }

    public int getTurnsLeftCount() { // остаток шагов
        return mapInfo.getTurnsLimit() - getGoldMan().getTurnsNumber(); // от лимита шагов отнимается количество шагов сделанных персонажем
    }

    public int getTotalScore() {
        return getGoldMan().getTotalScore();
    } //получение общего количества очков

    public void updateMap() { //обновление карты
        gameMap = mapLoader.getGameMap(); // получаем загруженную карту
        mapInfo = gameMap.getMapInfo();
        gameMap.updateMap();//обновление карты
    }

    public void updateObjects(AbstractGameObject obj1, AbstractGameObject obj2) { //обновление объектов
        gameMap.updateMapObjects(obj1, obj2);
    }
}


