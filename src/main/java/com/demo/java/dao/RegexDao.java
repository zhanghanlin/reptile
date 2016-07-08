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

    public void save(Regex regex) {
        String sql = "INSERT INTO REGEX(id,seed,regex,start,thread,data) values(?,?,?,?,?," + regex.getData() + ")";
        jdbcTemplate.update(sql, new Object[]{regex.getId()
                , regex.getSeed()
                , regex.getRegex()
                , regex.getStart()
                , regex.getThread()
        });
    }

    public List<Regex> list() {
        String sql = "SELECT * FROM REGEX";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Regex.class));
    }
}