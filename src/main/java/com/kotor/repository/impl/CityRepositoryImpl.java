package com.kotor.repository.impl;

import com.kotor.model.City;
import com.kotor.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CityRepositoryImpl implements CityRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public City create(City city) {
        String sql = "INSERT INTO city(name, content) VALUES (:name, :content)";
        Map<String, String> map = new HashMap();
        map.put("name", city.getName());
        map.put("content", city.getContent());
        jdbcTemplate.update(sql, map);
        sql = "SELECT id FROM city WHERE name=:name AND content=:content";
        jdbcTemplate.queryForObject(sql, map, Long.class);
        return city;
    }

    private static final class CityRowMapper implements RowMapper<City> {
        public City mapRow(ResultSet rs, int rowNum) throws SQLException {
            City user = new City();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setContent(rs.getString("content"));
            return user;
        }
    }
}
