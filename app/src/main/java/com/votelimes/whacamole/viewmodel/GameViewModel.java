package com.votelimes.whacamole.viewmodel;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.votelimes.whacamole.interfaces.Scorable;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameViewModel extends ViewModel implements Scorable {
    public final int MIN_HOLES_COUNT = 6;
    public final int MAX_HOLES_COUNT = 9;


    public final int ACTIVE_HOLES_COUNT;
    public int currentMolesStack;

    public GameViewModel(){
        ACTIVE_HOLES_COUNT = getRandomNumber(MIN_HOLES_COUNT, MAX_HOLES_COUNT + 1);
    }

    private ObservableInt score = new ObservableInt(0);
    private ObservableInt time = new ObservableInt(30);

    public String getScoreString() {
        return String.valueOf(score.get());
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public void addScorePoint() {
        this.score.set(getScore() + 1);
        this.currentMolesStack --;
    }

    public String getTimeString() {
        return String.valueOf(time.get());
    }

    public void setTime(int time) {
        this.time.set(time);
    }

    public ObservableInt getScoreObs() {
        return score;
    }

    public ObservableInt getTimeObs() {
        return time;
    }

    public int getScore() {
        return score.get();
    }

    @Override
    public void removePlayer() {
        currentMolesStack--;
    }

    public int getTime() {
        return time.get();
    }

    public List<Integer> getActiveMoles(){
        List<Integer> activeMoles = IntStream
                .range(1, MAX_HOLES_COUNT + 1)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(activeMoles);

        return activeMoles.subList(0, ACTIVE_HOLES_COUNT);
    }

    public int getNextShownMoleIndex(){
        return getRandomNumber(0, ACTIVE_HOLES_COUNT);
    }

    public boolean shouldShowNext(){
        int chance = getRandomNumber(MIN_HOLES_COUNT - 1, ACTIVE_HOLES_COUNT);
        if(currentMolesStack <= 0){
            currentMolesStack++;
            return true;
        }
        else if(chance < 50 && currentMolesStack == 1){
            currentMolesStack++;
            return true;
        }
        else if(chance < 25 && currentMolesStack == 2){
            currentMolesStack++;
            return true;
        }
        else if(chance < 10 && currentMolesStack == 3){
            currentMolesStack++;
            return true;
        }
        else{
            currentMolesStack = 0;
            return false;
        }
    }

    private int getRandomNumber(int min, int max) {
        if(min > max || min == max){
            return min;
        }

        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /*private int getRandomNumber(int min, int max) {
        if(min > max || min == max){
            return min;
        }

        Random rand = new Random();
        return rand.nextInt((max - min)) + min;
    }*/
}