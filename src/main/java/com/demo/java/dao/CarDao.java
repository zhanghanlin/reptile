package com.demo.java.dao;

import com.demo.java.model.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class CarDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public void save(Car car) {
        String sql = "INSERT INTO CAR(id,title,img,year,kilometre,liter,type,price) values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{car.getId()
                , car.getTitle()
                , car.getImg()
                , car.getYear()
                , car.getKilometre()
                , car.getLiter()
                , car.getType()
                , car.getPrice()
        });
    }
}
