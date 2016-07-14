package com.demo.java.dao;

import com.demo.java.common.quartz.ScheduleJob;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class JobDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    static String insert_sql = "INSERT INTO JOB(id,name,job_group,status,cron,description,bean_class,method_name,method_param)" +
            " values(?,?,?,?,?,?,?,?,?)";

    public ScheduleJob get(String id) {
        String sql = "SELECT * FROM JOB WHERE ID = ?";
        List<ScheduleJob> list = jdbcTemplate.query(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(ScheduleJob.class));
        if (list == null || list.isEmpty())
            return null;
        return list.get(0);
    }

    public List<ScheduleJob> list() {
        String sql = "SELECT * FROM JOB";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ScheduleJob.class));
    }

    public int delete(String id) {
        String sql = "DELETE FROM JOB WHERE ID = ?";
        return jdbcTemplate.update(sql, new Object[]{id});
    }

    /**
     * 保存
     *
     * @param job
     * @return
     */
    public int save(ScheduleJob job) {
        return jdbcTemplate.update(insert_sql, new Object[]{
                job.getId(),
                job.getName(),
                job.getJobGroup(),
                job.getStatus(),
                job.getCronExpression(),
                job.getDescription(),
                job.getBeanClass(),
                job.getMethodName(),
                job.getMethodParam()
        });
    }
}
