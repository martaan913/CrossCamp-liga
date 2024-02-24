package com.example.crosscampliga.storage;

public class Player {

    private int id;
    private String name;
    private String position;
    private int goals;
    private int assists;
    private int saves;
    private int failedSaves;
    private int teamId;

    public Player() {
    }

    public Player(int id, String name, String position, int saves, int failedSaves) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.saves = saves;
        this.failedSaves = failedSaves;
    }

    public Player(int id, String name, String position, int goals, int assists, int teamId) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.goals = goals;
        this.assists = assists;
        this.teamId = teamId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public int getFailedSaves() {
        return failedSaves;
    }

    public void setFailedSaves(int failedSaves) {
        this.failedSaves = failedSaves;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toStringComplete() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", goals=" + goals +
                ", assists=" + assists +
                ", saves=" + saves +
                ", failedSaves=" + failedSaves +
                ", teamId=" + teamId +
                '}';
    }
}
