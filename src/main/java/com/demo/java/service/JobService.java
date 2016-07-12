package com.demo.java.service;

import com.demo.java.common.quartz.ScheduleJob;
import com.demo.java.dao.JobDao;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class JobService {

    @Resource
    JobDao jobDao;

    public int save(ScheduleJob job) {
        job.setId(UUID.randomUUID().toString().replace("-", ""));
        return jobDao.save(job);
    }

    public ScheduleJob get(String id) {
        return jobDao.get(id);
    }

    public List<ScheduleJob> list() {
        return jobDao.list();
    }

    public int delete(String id) {
        return jobDao.delete(id);
    }

    public Map<String, ScheduleJob> map() {
        Map<String, ScheduleJob> map = new HashedMap();
        for (ScheduleJob job : list()) {
            map.put(job.getTriggerKey(), job);
        }
        return map;
    }
}
