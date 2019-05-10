package com.bss.tm.service;

import java.util.List;

import com.bss.tm.exception.BssException;
import com.bss.tm.model.Task;

public interface TaskService {
	
	public List<Task> getAll() throws BssException;
	public Task getTaskById(Long id);
	public Long insertTask(Task task);
    public int updateTask(Task task, Long id);
    public void deleteTaskLogical(Long id);
    public void deleteTaskPhysical(Long id);
}
