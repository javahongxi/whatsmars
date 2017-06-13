package com.itlong.whatsmars.worker.base;

import java.util.List;

/**
 * Created by shenhongxi on 2016/7/13.
 */
public class TaskDaoImpl extends BaseDao implements TaskDao {

    @Override
    public void insert(Task task) {
        this.sqlSession.insert("Task.insert", task);
    }

    @Override
    public int updateSuccess(Task task) {
        return this.sqlSession.update("Task.updateSuccess", task);
    }

    @Override
    public int updateFailed(Task task) {
        return this.sqlSession.update("Task.updateFailed", task);
    }

    @Override
    public int lock(Task task) {
        return this.sqlSession.update("Task.lock", task);
    }

    @Override
    public int unlock(Task task) {
        return this.sqlSession.update("Task.unlock", task);
    }

    @Override
    public List<Task> findLocked(int splitSeconds, int retries) {
        ParamHashMap<String, Object> params = new ParamHashMap<String, Object>();
        params.put("splitSeconds", splitSeconds);
        params.put("retries", retries);
        return this.sqlSession.selectList("Task.findLocked", params);
    }

    @Override
    public List<Task> findByType(String type) {
        ParamHashMap<String, Object> params = new ParamHashMap<String, Object>();
        params.put("type", type);
        return this.sqlSession.selectList("Task.findByType", params);
    }
}
