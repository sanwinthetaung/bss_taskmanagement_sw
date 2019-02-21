package com.bss.tm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bss.tm.mapper.TaskListMapper;
import com.bss.tm.mapper.TaskMapper;
import com.bss.tm.model.Task;
import com.bss.tm.model.TaskList;

@Service
@Transactional
public class TaskListServiceImpl implements TaskListService {
	
	@Autowired
	private TaskListMapper taskListMapper;
	
	@Autowired
	private TaskMapper taskMapper;

	@Override
	public List<TaskList> getAll(){
		return taskListMapper.findAll();
	}
	
	@Override
	public TaskList getTaskById(Long id) {
		return taskListMapper.selectTaskListById(id);
	}
	
	@Override
	public Long insertTaskList(TaskList bean) {
		bean.setIsDeleted(Boolean.FALSE);
		taskListMapper.insertTaskList(bean);
		return bean.getId();
	}
	
	@Override
    public int updateTaskList(TaskList newTask, Long id) {
		TaskList task = taskListMapper.selectTaskListById(id);
		
		if (newTask.getTitle() != null) {
			task.setTitle(newTask.getTitle());
		}
		
		if (newTask.getDescription() != null) {
			task.setDescription(newTask.getDescription());
		}
		
		return taskListMapper.updateTaskList(task);
	}
	
	@Override
	public void deleteTaskListLogical(Long id) {
		TaskList taskList = taskListMapper.selectTaskListById(id);
		for (Task task : taskList.getTasks()) {
			taskMapper.deleteTaskLogical(task.getId());
		}
		taskListMapper.deleteTaskListLogical(id);
	}
	
	@Override
	public void deleteTaskListPhysical(Long id) {
		taskListMapper.deleteTaskListPhysical(id);
	}
}
