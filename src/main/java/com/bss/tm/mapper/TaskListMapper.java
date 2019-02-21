package com.bss.tm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bss.tm.model.Task;
import com.bss.tm.model.TaskList;

@Mapper
@Repository
public interface TaskListMapper {
	
	public List<TaskList> findAll();
	public TaskList selectTaskListById(Long id);
	public Long insertTaskList(TaskList task);
    public int updateTaskList(TaskList task);
    public void deleteTaskListLogical(Long id);
    public void deleteTaskListPhysical(Long id);
}
