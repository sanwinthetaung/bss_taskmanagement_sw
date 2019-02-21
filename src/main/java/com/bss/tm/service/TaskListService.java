package com.bss.tm.service;

import java.util.List;

import com.bss.tm.model.Task;
import com.bss.tm.model.TaskList;

public interface TaskListService {
	
	public List<TaskList> getAll();
	public TaskList getTaskById(Long id);
	public Long insertTaskList(TaskList task);
    public int updateTaskList(TaskList task, Long id);
    public void deleteTaskListLogical(Long id);
    public void deleteTaskListPhysical(Long id);
}
