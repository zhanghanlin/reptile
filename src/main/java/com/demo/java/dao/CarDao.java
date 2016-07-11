package com.demo.java.dao;

import com.demo.java.model.Car;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CarDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    static String insert_sql = "INSERT INTO CAR(id,title,img,year,kilometre,liter,type,price) values(?,?,?,?,?,?,?,?)";
    static String update_sql = "UPDATE CAR SET title=?,img=?,year=?,kilometre=?,liter=?,type=?,price=?";

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
        return jdbcTemplate.update(update_sql, new Object[]{car.getTitle()
                , car.getImg()
                , car.getYear()
                , car.getKilometre()
                , car.getLiter()
                , car.getType()
                , car.getPrice()
        });
    }

    /**
     * 保存
     *
     * @param car
     * @return
     */
    public int save(Car car) {
        return jdbcTemplate.update(insert_sql, new Object[]{car.getId()
                , car.getTitle()
                , car.getImg()
                , car.getYear()
                , car.getKilometre()
                , car.getLiter()
                , car.getType()
                , car.getPrice()
        });
    }

    /**
     * 批量
     *
     * @param list
     * @return
     */
    public int save(List<Car> list) {
        int[] res = jdbcTemplate.batchUpdate(insert_sql, new MyBatchPreparedStatementSetter(list));
        return res.length;
    }

    class MyBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

        final List<Car> list;

        public MyBatchPreparedStatementSetter(List<Car> list) {
            this.list = list;
        }

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            Car car = list.get(i);
            ps.setString(1, car.getId());
            ps.setString(2, car.getTitle());
            ps.setString(3, car.getImg());
            ps.setString(4, car.getYear());
            ps.setString(5, car.getKilometre());
            ps.setString(6, car.getLiter());
            ps.setString(7, car.getType());
            ps.setString(8, car.getPrice());
        }

        @Override
        public int getBatchSize() {
            return list.size();
        }
    }
}
