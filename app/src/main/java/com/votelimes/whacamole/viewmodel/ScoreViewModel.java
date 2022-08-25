package com.votelimes.whacamole.viewmodel;

import android.content.Context;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.votelimes.whacamole.util.HighScore;

public class ScoreViewModel extends ViewModel {

    private final ObservableInt score = new ObservableInt(0);

    public int getHighScore(Context context){
        return HighScore.getLast(context);
    }

    public void updateHighscore(Context context){
        if(getHighScore(context) < score.get()){
            HighScore.put(context, score.get());
        }
    }

    public ObservableInt getScoreObs() {
        return score;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public boolean isANewRecord(Context context){
        return getScore() > getHighScore(context);
    }
}