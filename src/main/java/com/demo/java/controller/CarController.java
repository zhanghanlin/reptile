package com.demo.java.controller;

import com.demo.java.model.Car;
import com.demo.java.service.CarService;
import com.demo.java.vo.TablePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("car")
public class CarController {

    final static Logger LOG = LoggerFactory.getLogger(CarController.class);

    @Resource
    CarService carService;

    @RequestMapping("/list")
    public ModelAndView list() {
        List<Car> list = carService.list();
        ModelAndView modelAndView = new ModelAndView("carList");
        modelAndView.addObject("list", list);
        return modelAndView;
    }

    @RequestMapping("/api/list")
    @ResponseBody
    public TablePage<Car> apiList(@RequestParam(defaultValue = "1", required = false) int start,
                                  @RequestParam(defaultValue = "10", required = false) int length,
                                  @RequestParam(defaultValue = "1", required = false) int draw) {
        try {
            TablePage<Car> page = carService.listByPage(start, length);
            page.setDraw(draw);
            return page;
        } catch (Exception e) {
            LOG.error("search error : {}", e.getMessage(), e);
            return new TablePage<>(draw, 0, 0, null);
        }
    }
}
