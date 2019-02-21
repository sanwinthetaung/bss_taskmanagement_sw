package com.bss.tm.template;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

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
import com.bss.tm.service.TaskListService;
import com.bss.tm.service.TaskService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TaskTestUsingRestTemplate {
	
	@MockBean
	private TaskService taskService;
	
	@MockBean
	private TaskListService taskListService;
	
	private static final String base = "http://localhost:8080/task";
	
	@Test
	public void deleteTask() {
		RestTemplate template = new RestTemplate();
		String url = base + "/1";
		
		template.delete(url);
		
		ResponseEntity<Task> response = template.getForEntity(url, Task.class);
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
	}
	
	@Test
	public void addTask() throws Exception {
		RestTemplate template = new RestTemplate();
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", "1");
		
		Task task = new Task();
		task.setTitle("todo");
		task.setTaskListId(1L);
		
		String url = base + "/{id}";
		HttpHeaders requestHeaders = new HttpHeaders();
	    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> taskEntity = new HttpEntity<>(task, requestHeaders);
		
		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, taskEntity, 
				String.class, param);
		
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		String res = response.getBody();
		System.err.println(res);
		
		
		/*
		 * Task t = template.postForObject(url, task, Task.class, param);
		 * Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		 * Assert.assertThat(t, notNullValue()); Assert.assertThat(t.getTitle(),
		 * is("haha"));
		 */
	}
	
	@Test
	public void updateTask() throws Exception {
		RestTemplate template = new RestTemplate();
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", "1");
		
		Task task = new Task();
		task.setTitle("TODO1");
		
		String url = base + "/{id}";
		
		HttpHeaders requestHeaders = new HttpHeaders();
	    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	    // attach data and header to entity
		HttpEntity<Task> taskEntity = new HttpEntity<>(task, requestHeaders);
		
		ResponseEntity<URI> response = template.exchange(url, HttpMethod.PUT, taskEntity, 
				URI.class, param);
		//template.put(url, taskEntity, new Object[] {});
		
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
//		URI res = response.getBody();
//		System.err.println(response.getBody());
	}
	
	@Test
	public void getTaskById() throws Exception {
		TestRestTemplate template = new TestRestTemplate();
		String url = base + "/1";
		ResponseEntity<Task> response = template.getForEntity(url, Task.class);
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		Assert.assertThat(response.getBody().getTitle(), is("TODO1"));
	}
	
	@Test
	public void getTaskAll() throws Exception {
		String url = base + "/all";
		/*
		 * MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
		 * .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		 * 
		 * int status = mvcResult.getResponse().getStatus(); Assert.assertEquals(200,
		 * status); String content = mvcResult.getResponse().getContentAsString();
		 * Task[] taskList = super.mapFromJson(content, Task[].class);
		 * Assert.assertTrue(taskList.length > 0);
		 */
		TestRestTemplate template = new TestRestTemplate();
		ResponseEntity<List<Task>> response = template.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Task>>() {}
		);
		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		List<Task> tasks = response.getBody();
		System.err.println(tasks);
	}
}
