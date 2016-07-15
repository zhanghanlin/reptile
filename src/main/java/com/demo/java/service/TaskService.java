package com.demo.java.service;

import com.demo.java.model.Task;
import com.demo.java.dao.TaskDao;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TaskService {

    @Resource
    TaskDao taskDao;

    public int save(Task job) {
        job.setId(UUID.randomUUID().toString().replace("-", ""));
        return taskDao.save(job);
    }

    public Task get(String id) {
        return taskDao.get(id);
    }

    public List<Task> list() {
        return taskDao.list();
    }

    public int delete(String id) {
        return taskDao.delete(id);
    }

    public Map<String, Task> map() {
        Map<String, Task> map = new HashedMap();
        for (Task task : list()) {
            map.put(task.getTriggerKey(), task);
        }
        return map;
    }
}
