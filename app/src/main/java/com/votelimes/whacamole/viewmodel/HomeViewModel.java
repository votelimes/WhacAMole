package com.votelimes.whacamole.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.votelimes.whacamole.util.HighScore;

public class HomeViewModel extends ViewModel {

    public int getHighScore(Context context){
        return HighScore.getLast(context);
    }
}