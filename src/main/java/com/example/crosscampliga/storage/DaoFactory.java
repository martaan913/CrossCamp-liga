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
            dataSource.setUser("sql11686896");
            dataSource.setPassword("yg2J5B1b8R");
            dataSource.setUrl("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11686896");
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
