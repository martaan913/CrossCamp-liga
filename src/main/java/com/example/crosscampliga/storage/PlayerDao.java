package com.example.crosscampliga.storage;

import java.util.List;

public interface PlayerDao {
    Player getByName(String name);
    void add(Player player);
    List<Player> getAll();
    List<Player> getByNameLike(String nameLike);
}
