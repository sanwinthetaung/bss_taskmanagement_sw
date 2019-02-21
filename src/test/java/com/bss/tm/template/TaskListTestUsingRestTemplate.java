package com.bss.tm.template;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.bss.tm.model.Task;
import com.bss.tm.model.TaskList;
import com.bss.tm.service.TaskListService;
import com.bss.tm.service.TaskService;

/***
 * ## require db
 * @author
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class TaskListTestUsingRestTemplate {

	@MockBean
	private TaskService taskService;
	
	@MockBean
	private TaskListService taskListService;
	
	private static final String base = "http://localhost:8080/taskList";
	
	@Test
	public void deleteTask() {
		RestTemplate template = new RestTemplate();
		String url = base + "/1";
		
		template.delete(url);
		
		ResponseEntity<Task> response = template.getForEntity(url, Task.class);
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
	}
	
	@Test
	public void addTaskList() throws Exception {
		RestTemplate template = new RestTemplate();
		
		TaskList task = new TaskList();
		task.setTitle("todo");
		task.setDescription("desc");
		
		String url = base;
		HttpHeaders requestHeaders = new HttpHeaders();
	    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TaskList> taskEntity = new HttpEntity<>(task, requestHeaders);
		
//		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, taskEntity, 
//				String.class);
		
//		template.postForLocation(url, request, uriVariables)
		
//		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
//		String res = response.getBody();
//		System.err.println(res);
	}
	
	@Test
	public void updateTaskList() throws Exception {
		RestTemplate template = new RestTemplate();
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", "1");
		
		TaskList task = new TaskList();
		task.setTitle("TODO1");
		task.setDescription("DESC1");
		
		String url = base + "/{id}";
		
		HttpHeaders requestHeaders = new HttpHeaders();
	    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	    // attach data and header to entity
		HttpEntity<TaskList> taskEntity = new HttpEntity<>(task, requestHeaders);
		
		ResponseEntity<URI> response = template.exchange(url, HttpMethod.PUT, taskEntity, 
				URI.class, param);
		
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	@Test
	public void getTaskListById() throws Exception {
		TestRestTemplate template = new TestRestTemplate();
		String url = base + "/1";
		ResponseEntity<TaskList> response = template.getForEntity(url, TaskList.class);
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		Assert.assertThat(response.getBody().getTitle(), is("TODO1"));
		Assert.assertThat(response.getBody().getDescription(), is("DESC1"));
	}
	
	@Test
	public void getTaskListAll() throws Exception {
		String url = base + "/all";
		TestRestTemplate template = new TestRestTemplate();
		ResponseEntity<List<TaskList>> response = template.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<TaskList>>() {}
		);
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		List<TaskList> tasks = response.getBody();
		System.err.println(tasks);
	}

}
