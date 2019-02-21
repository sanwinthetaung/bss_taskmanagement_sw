package com.bss.tm.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bss.tm.model.Task;
import com.bss.tm.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceUnitTest {

	@Autowired
	private TaskService taskService;
	
	@Test
	public void getTaskAll() {
		List<Task> list = taskService.getAll();
		assertThat(list).isNotNull().isNotEmpty();
	}
	
	@Test
	public void insertTask() {
		Task task = new Task();
		task.setTitle("test2");
		task.setTaskListId(1L);
		task.setStatus(0);
		
		long id = taskService.insertTask(task);
		Task newTask = taskService.getTaskById(id);
		
		assertThat(newTask.getTitle()).isNotNull().isNotEmpty().isEqualTo(task.getTitle());
	}
	
	@Test
	public void updateTask() {
		Long id = 1L;
		Task task = new Task();
		task.setId(id);
		task.setTitle("testtest");
		
		taskService.updateTask(task, id);
		Task newTask = taskService.getTaskById(id);
		
		assertThat(newTask.getTitle()).isNotNull().isNotEmpty().isEqualTo(task.getTitle());
	}
	
	@Test
	public void deleteTask() {
		Long id = 1L;
		
		taskService.deleteTaskLogical(id);
		Task task = taskService.getTaskById(id);
		
		assertThat(task).isNull();
	}
	
}
