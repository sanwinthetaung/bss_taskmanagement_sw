package com.bss.tm;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bss.tm.controller.TaskListController;
import com.bss.tm.model.TaskList;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskListUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TaskListController taskListController;

	@Test
	public void addTask() throws Exception {
		TaskList task = new TaskList();
		task.setTitle("titleMockito");

		Gson gson = new Gson();
		String json = gson.toJson(task);

		ResponseEntity taskEntity = new ResponseEntity<TaskList>(task, HttpStatus.CREATED);
		when(taskListController.addTaskList(Matchers.any(TaskList.class))).thenReturn(taskEntity);
		mvc.perform(post("/taskList").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(json)).andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(json)).andDo(print())
		.andExpect(jsonPath("$.title", is("titleMockito")));
	}

	@Test
	public void getAllTaskList() throws Exception {
		
		TaskList taskList = new TaskList();
		taskList.setTitle("title33");
		taskList.setDescription("description");
		
		TaskList taskList2 = new TaskList();
		taskList2.setTitle("title44");
		taskList2.setDescription("description");

		List<TaskList> tasks = new ArrayList<>();
		tasks.add(taskList);
		tasks.add(taskList2);
		
		ResponseEntity taskEntity = new ResponseEntity<List<TaskList>>(tasks, HttpStatus.OK);

		when(taskListController.getAllTaskList()).thenReturn(taskEntity);
		mvc.perform(get("/taskList/all")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$[0].title", is("title33")))
				.andExpect(status().isOk());
	}

	@Test
	public void getTaskListById() throws Exception {
		TaskList taskList = new TaskList();
		taskList.setId(1L);
		taskList.setTitle("title33");
		taskList.setDescription("description");
		taskList.setIsDeleted(Boolean.FALSE);
		
		ResponseEntity taskEntity = new ResponseEntity<TaskList>(taskList, HttpStatus.OK);
		when(taskListController.getTaskListById(Matchers.anyLong())).thenReturn(taskEntity);
		mvc.perform(get("/taskList/1")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(jsonPath("$.title", is("title33")))
		.andExpect(status().isOk());
	}
	
	@Test
	public void addTaskList() throws Exception {
		TaskList task = new TaskList();
		task.setTitle("titleMockito");
		task.setDescription("description");

		Gson gson = new Gson();
		String json = gson.toJson(task);

		ResponseEntity taskEntity = new ResponseEntity<TaskList>(task, HttpStatus.OK);
		when(taskListController.addTaskList(Matchers.any(TaskList.class))).thenReturn(taskEntity);
		mvc.perform(post("/taskList").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(json))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(json)).andDo(print())
		.andExpect(jsonPath("$.title", is("titleMockito")))
		.andExpect(jsonPath("$.description", is("description")));
	}
	
	@Test
	public void updateTaskList() throws Exception {
		TaskList task = new TaskList();
		task.setTitle("titleMockito");

		Gson gson = new Gson();
		String json = gson.toJson(task);

		mvc.perform(put("/taskList/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(json))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@Test
	public void updateTaskListBadRequest() throws Exception {
		TaskList task = new TaskList();
		task.setTitle("titleMockito");

		Gson gson = new Gson();
		String json = gson.toJson(task);

		mvc.perform(put("/taskList/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(json))
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
	@Test
	public void deleteTaskList() throws Exception {
		mvc.perform(delete("/taskList/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
}
