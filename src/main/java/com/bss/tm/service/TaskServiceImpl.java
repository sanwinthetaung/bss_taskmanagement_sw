package com.bss.tm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bss.tm.mapper.TaskMapper;
import com.bss.tm.model.Task;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskMapper taskMapper;
	
	@Override
	public List<Task> getAll(){
		return taskMapper.findAll();
	}
	
	@Override
	public Task getTaskById(Long id) {
		return taskMapper.selectTaskById(id);
	}
	
	@Override
	public Long insertTask(Task task) {
		if (task.getStatus() == null) {
			task.setStatus(0);
		}
		task.setIsDeleted(Boolean.FALSE);
		taskMapper.insertTask(task);
		return task.getId();
	}
	
	@Override
    public int updateTask(Task newTask, Long id) {
		Task task = taskMapper.selectTaskById(id);
		if (newTask.getStatus() != null) {
			task.setStatus(newTask.getStatus());
		}
		
		if (newTask.getTitle() != null) {
			task.setTitle(newTask.getTitle());
		}
		
		return taskMapper.updateTask(task);
	}
	
	@Override
	public void deleteTaskLogical(Long id) {
		taskMapper.deleteTaskLogical(id);
	}
	
	@Override
	public void deleteTaskPhysical(Long id) {
		taskMapper.deleteTaskPhysical(id);
	}
}
