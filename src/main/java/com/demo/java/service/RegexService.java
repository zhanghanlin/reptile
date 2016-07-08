package com.demo.java.service;

import com.demo.java.dao.RegexDao;
import com.demo.java.model.Regex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegexService {

    @Resource
    RegexDao regexDao;

    public void save(Regex regex) throws Exception {
        regexDao.save(regex);
    }

    public List<Regex> list() {
        return regexDao.list();
    }
}
