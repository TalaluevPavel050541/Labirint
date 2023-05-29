package score.interfaces;

import objects.UserScore;

import java.util.ArrayList;

public interface ScoreSaver{

    boolean saveScore(UserScore userScore); // сохранение очков

    ArrayList<UserScore> getScoreList();//получение списка очков пользователя

}
