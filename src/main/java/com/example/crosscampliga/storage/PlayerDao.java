package com.example.crosscampliga.storage;

public interface PlayerDao {
    Player getByName(String name);
    void add(Player player);
}
