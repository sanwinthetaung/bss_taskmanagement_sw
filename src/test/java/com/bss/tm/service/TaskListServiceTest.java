package com.bss.tm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bss.tm.model.Task;
import com.bss.tm.model.TaskList;
import com.bss.tm.service.TaskListService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskListServiceTest {
	
	@Autowired
	private TaskListService taskListService;

	@Test
	public void getTaskListById() {
		String title = "TODO1";
		TaskList list = taskListService.getTaskById(2L);
		assertThat(list.getTitle()).isEqualTo(title);
	}
	
	@Test
	public void getTaskListAll() {
		List<TaskList> list = taskListService.getAll();
		assertThat(list).isNotNull().isNotEmpty();
	}
	
	@Test
	public void insertTaskList() {
		TaskList task = new TaskList();
		task.setTitle("test2");
		task.setDescription("desc2");
		
		long id = taskListService.insertTaskList(task);
		TaskList newTask = taskListService.getTaskById(id);
		
		assertThat(newTask.getTitle()).isNotNull().isNotEmpty()
			.isEqualTo(task.getTitle());
		assertThat(newTask.getDescription()).isNotNull().isNotEmpty()
			.isEqualTo(task.getDescription());
	}
	
	@Test
	public void updateTaskList() {
		Long id = 1L;
		TaskList task = new TaskList();
		task.setId(id);
		task.setTitle("testtest");
		task.setDescription("descdesc");
		
		taskListService.updateTaskList(task, id);
		TaskList newTask = taskListService.getTaskById(id);
		
		assertThat(newTask.getTitle()).isNotNull().isNotEmpty()
			.isEqualTo(task.getTitle());
		assertThat(newTask.getDescription()).isNotNull().isNotEmpty()
			.isEqualTo(task.getDescription());
	}
	
	@Test
	public void deleteTaskList() {
		Long id = 1L;
		
		taskListService.deleteTaskListLogical(id);
		TaskList task = taskListService.getTaskById(id);
		
		assertThat(task).isNull();
	}

}
