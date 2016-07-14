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

    public int save(Regex regex) {
        String sql = "INSERT INTO REGEX(id,name,seed,regex,start,thread,ignore_key,data) values(?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{regex.getId(),
                regex.getName(),
                regex.getSeed(),
                regex.getRegex(),
                regex.getStart(),
                regex.getThread(),
                regex.getIgnoreKey(),
                regex.getData()
        });
    }


    public int update(Regex regex) {
        String sql = "UPDATE REGEX SET name=?,seed=?,regex=?,start=?,thread=?,ignore_key=?,data=?,update_time=NOW() WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{
                regex.getName(),
                regex.getSeed(),
                regex.getRegex(),
                regex.getStart(),
                regex.getThread(),
                regex.getIgnoreKey(),
                regex.getData(),
                regex.getId()
        });
    }

    public List<Regex> list() {
        String sql = "SELECT * FROM REGEX";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Regex.class));
    }

    public int remove(String id) {
        String sql = "DELETE FROM REGEX WHERE ID = ?";
        return jdbcTemplate.update(sql, new Object[]{id});
    }
}