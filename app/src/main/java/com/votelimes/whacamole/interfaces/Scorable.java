package com.votelimes.whacamole.interfaces;

public interface Scorable {
    public void setScore(int score);
    public void addScorePoint();
    public int getScore();

    public void removePlayer();
}
