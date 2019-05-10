package com.bss.tm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bss.tm.mapper.TaskMapper;
import com.bss.tm.model.Task;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskMapper taskMapper;
	
	@Cacheable(value="tasks", unless = "#result.size() == 0")
	@Override
	public List<Task> getAll(){
		return taskMapper.findAll();
	}
	
	@Cacheable(value = "tasks", key = "#id")
	@Override
	public Task getTaskById(Long id) {
		return taskMapper.selectTaskById(id);
	}
	

//	@Caching(evict = {@CacheEvict(value = "tasks", allEntries = true)}, put = {@CachePut(value="tasks", key = "#task.id")})
	@CachePut(value = "tasks", key = "#task.id")
	@Override
	public Long insertTask(Task task) {
		if (task.getStatus() == null) {
			task.setStatus(0);
		}
		task.setIsDeleted(Boolean.FALSE);
		taskMapper.insertTask(task);
		return task.getId();
	}
	
	@Caching(evict = {@CacheEvict(value = "tasks", allEntries = true)}, put = {@CachePut(value = "tasks")})
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
	
	@CacheEvict(value = "tasks", key = "#id")
	@Override
	public void deleteTaskLogical(Long id) {
		taskMapper.deleteTaskLogical(id);
	}
	
	@Override
	public void deleteTaskPhysical(Long id) {
		taskMapper.deleteTaskPhysical(id);
	}
}
