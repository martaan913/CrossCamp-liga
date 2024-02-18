package com.example.crosscampliga.storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;

public class MysqlPlayerDao implements PlayerDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlPlayerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Player> playerRM(){
        return new RowMapper<Player>() {
            @Override
            public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
                Player player = new Player();
                player.setId(rs.getInt("idPlayer"));
                player.setName(rs.getString("name"));
                player.setPosition(rs.getString("position"));
                player.setGoals(rs.getInt("goals"));
                player.setAssists(rs.getInt("assists"));
                player.setSaves(rs.getInt("saves"));
                player.setFailedSaves(rs.getInt("failed_saves"));
                player.setTeamId(rs.getInt("Team_idTeam"));
                return player;
            }
        };
    }

    @Override
    public Player getByName(String name) {
        String sql = "select * from player where meno = ?";

        try{
            return jdbcTemplate.queryForObject(sql, playerRM(), name);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void add(Player player) {
        if (player.getId() == 0){
            String query = "insert into player (name, position, goals, assists, saves, failed_saves, Team_idTeam) " +
                    "values (?,?,?,?,?,?,?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, player.getName());
                    statement.setString(2, player.getPosition());
                    statement.setInt(3, player.getGoals());
                    statement.setInt(4, player.getAssists());
                    statement.setInt(5, player.getSaves());
                    statement.setInt(6, player.getFailedSaves());
                    statement.setInt(7, player.getTeamId());
                    return statement;
                }
            });
        }else{
            String query = "update player set name = ?, position = ?, goals = ?, assists = ?, saves = ?, failed_saves = ?, " +
                    "Team_idTeam = ?";

            jdbcTemplate.update(query, player.getName(), player.getPosition(), player.getGoals(), player.getAssists(),
                    player.getSaves(), player.getFailedSaves(), player.getTeamId());
        }
    }
}
