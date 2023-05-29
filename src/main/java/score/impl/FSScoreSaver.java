package score.impl;

import objects.UserScore;
import score.interfaces.ScoreSaver;

import java.util.ArrayList;

public class FSScoreSaver implements ScoreSaver{
//методы не реализованы для файловой системы, работаем только с базой данных
    @Override
    public boolean saveScore(UserScore userScore) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<UserScore> getScoreList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }







}
