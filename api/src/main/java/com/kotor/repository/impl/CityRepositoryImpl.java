package com.kotor.repository.impl;

import com.kotor.model.City;
import com.kotor.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CityRepositoryImpl implements CityRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CityRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    public City create(City city) {
        String sql = "INSERT INTO city(name, content) VALUES (:name, :content)";
        Map<String, String> map = new HashMap();
        map.put("name", city.getName());
        map.put("content", city.getContent());
        jdbcTemplate.update(sql, map);
        sql = "SELECT id FROM city WHERE name=:name AND content=:content";
        city.setId(jdbcTemplate.queryForObject(sql, map, Long.class));
        return city;
    }

    public City findById(long id) {
        String sql = "SELECT id, name, content FROM city WHERE id=:id";
        Map<String, String> map = new HashMap();
        map.put("id", String.valueOf(id));
        return jdbcTemplate.queryForObject(sql, map, new CityRowMapper());
    }

    public City findByName(String name) {
        System.out.println(name);
        String sql = "SELECT id, name, content FROM city WHERE name=:name";
        Map<String, String> map = new HashMap();
        map.put("name", name);
        return jdbcTemplate.queryForObject(sql, map, new CityRowMapper());
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
