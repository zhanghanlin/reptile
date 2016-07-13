package com.demo.java.dao;

import com.demo.java.model.Car;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CarDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    static String insert_sql = "INSERT INTO CAR(id,car_name,price,on_time," +
            "mileage,speed_case,inspect_expire,safe_expire,accident,user_name,phone,create_time,url)" +
            " values(?,?,?,?,?,?,?,?,?,?,?,NOW(),?)";
    static String update_sql = "UPDATE CAR SET car_name=?,price=?,on_time=?" +
            ",mileage=?,speed_case=?,inspect_expire=?,safe_expire=?,accident=?,user_name=?,phone=?,create_time=NOW(),url=? WHERE id=?";

    public int saveOrUpdate(Car car) {
        if (get(car.getId()) != null) {
            return update(car);
        } else {
            return save(car);
        }
    }

    public Car get(String id) {
        String sql = "SELECT * FROM CAR WHERE ID = ?";
        List<Car> list = jdbcTemplate.query(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(Car.class));
        if (list == null || list.isEmpty())
            return null;
        return list.get(0);
    }

    /**
     * 更新
     *
     * @param car
     * @return
     */
    public int update(Car car) {
        return jdbcTemplate.update(update_sql, new Object[]{car.getCarName(),
                car.getPrice(),
                car.getOnTime(),
                car.getMileage(),
                car.getSpeedCase(),
                car.getInspectExpire(),
                car.getSafeExpire(),
                car.getAccident(),
                car.getUserName(),
                car.getPhone(),
                car.getUrl(),
                car.getId()
        });
    }

    /**
     * 保存
     *
     * @param car
     * @return
     */
    public int save(Car car) {
        return jdbcTemplate.update(insert_sql, new Object[]{car.getId(),
                car.getCarName(),
                car.getPrice(),
                car.getOnTime(),
                car.getMileage(),
                car.getSpeedCase(),
                car.getInspectExpire(),
                car.getSafeExpire(),
                car.getAccident(),
                car.getUserName(),
                car.getPhone(),
                car.getUrl()
        });
    }
}
