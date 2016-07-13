package com.demo.java.service;

import com.demo.java.dao.CarDao;
import com.demo.java.model.Car;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarService {

    @Resource
    CarDao carDao;

    public Car get(String id) {
        return carDao.get(id);
    }

    public List<Car> list() {
        return carDao.list();
    }

    public int saveOrUpdate(Car car) {
        if (carDao.get(car.getId()) != null) {
            return carDao.update(car);
        } else {
            return carDao.save(car);
        }
    }

    public int save(Car car) {
        return carDao.save(car);
    }

    public int update(Car car) {
        return carDao.update(car);
    }
}
