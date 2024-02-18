package com.example.crosscampliga.storage;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum DaoFactory {
    INSTANCE;

    private PlayerDao playerDao;

    private JdbcTemplate jdbcTemplate;

    private JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("user");
            dataSource.setPassword("CrossCamp-Liga");
            dataSource.setUrl("jdbc:mysql://localhost:3306/crosscamp");
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }

    public PlayerDao getPlayerDao() {
        if(playerDao == null){
            playerDao = new MysqlPlayerDao(getJdbcTemplate());
        }
        return playerDao;
    }
}
