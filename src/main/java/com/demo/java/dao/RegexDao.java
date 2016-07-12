package com.demo.java.dao;

import com.demo.java.model.Regex;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class RegexDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public Regex get(String id) {
        String sql = "SELECT * FROM REGEX WHERE ID = ?";
        List<Regex> list = jdbcTemplate.query(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(Regex.class));
        if (list == null || list.isEmpty())
            return null;
        return list.get(0);
    }

    public void save(Regex regex) {
        String sql = "INSERT INTO REGEX(id,seed,regex,start,thread,data) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{regex.getId()
                , regex.getSeed()
                , regex.getRegex()
                , regex.getStart()
                , regex.getThread()
                , regex.getData()
        });
    }

    public List<Regex> list() {
        String sql = "SELECT * FROM REGEX";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Regex.class));
    }
}