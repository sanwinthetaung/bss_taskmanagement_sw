package com.bss.tm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bss.tm.model.Task;

@Mapper
@Repository
public interface TaskMapper {
	
	public List<Task> findAll();
	public Task selectTaskById(Long id);
	public int insertTask(Task task);
    public int updateTask(Task task);
    public void deleteTaskLogical(Long id);
    public void deleteTaskPhysical(Long id);
}
