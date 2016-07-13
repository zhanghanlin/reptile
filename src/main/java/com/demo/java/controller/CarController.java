package com.demo.java.controller;

import com.demo.java.model.Car;
import com.demo.java.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("car")
public class CarController {

    @Resource
    CarService carService;

    @RequestMapping("/list")
    public ModelAndView list() {
        List<Car> list = carService.list();
        ModelAndView modelAndView = new ModelAndView("carList");
        modelAndView.addObject("list", list);
        return modelAndView;
    }
}
