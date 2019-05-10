package com.bss.tm;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bss.tm.controller.TaskController;
import com.bss.tm.model.Task;
import com.bss.tm.model.TaskList;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TaskController taskController;

	@Test
	public void getAllTask() throws Exception {
		Task task = new Task();
		task.setTitle("title33");
		
		Task task2 = new Task();
		task2.setTitle("title44");

		List<Task> tasks = new ArrayList<>();
		tasks.add(task);
		tasks.add(task2);
		
		ResponseEntity taskEntity = new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);

		// when
		when(taskController.getAllTask()).thenReturn(taskEntity);
		
		// perform
		mvc.perform(get("/task/all")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$[0].title", is("title33")))
				.andExpect(status().isOk());
	}

	@Test
	public void getTaskById() throws Exception {
		Task task = new Task();
		task.setId(1L);
		task.setTitle("title33");
		
		ResponseEntity taskEntity = new ResponseEntity<Task>(task, HttpStatus.OK);
		when(taskController.getTaskById(Matchers.anyLong())).thenReturn(taskEntity);
		
		mvc.perform(get("/task/1")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(jsonPath("$.title", is("title33")))
		.andExpect(status().isOk());
	}
	
	@Test
	public void addTask() throws Exception {
		TaskList task = new TaskList();
		task.setTitle("titleMockito");

		Gson gson = new Gson();
		String json = gson.toJson(task);

		ResponseEntity r=new ResponseEntity<>(task, HttpStatus.CREATED);
		when(taskController.addTask(Matchers.anyLong(), Matchers.any(Task.class))).thenReturn(r);
		mvc.perform(post("/task/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(json))
		.andExpect(status().isCreated())
		.andDo(print())
		.andExpect(jsonPath("$.title", is("titleMockito")));
	}
	
	@Test
	public void updateTask() throws Exception {
		Task task = new Task();
		task.setId(1L);
		task.setTitle("titleMockito");

		Gson gson = new Gson();
		String json = gson.toJson(task);

		ResponseEntity r=new ResponseEntity<>(task, HttpStatus.OK);
		when(taskController.modifyTask(Matchers.any(Task.class), Matchers.anyLong())).thenReturn(r);
		mvc.perform(put("/task/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(json))
		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.title", is("titleMockito")))
		.andDo(print());
	}
	
	@Test
	public void updateTaskBadRequest() throws Exception {
		Task task = new Task();
		task.setTitle("titleMockito");

		Gson gson = new Gson();
		String json = gson.toJson(task);

		mvc.perform(put("/taskList/").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(json)).andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	@Test
	public void deleteTask() throws Exception {
		mvc.perform(delete("/task/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")).andExpect(status().isOk())
				.andDo(print());
	}
}