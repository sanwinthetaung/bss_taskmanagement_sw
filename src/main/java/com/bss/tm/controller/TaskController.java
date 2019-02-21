package com.bss.tm.controller;

import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bss.tm.model.Task;
import com.bss.tm.model.TaskList;
import com.bss.tm.service.TaskListService;
import com.bss.tm.service.TaskService;

@RestController
public class TaskController {
	
	private static final Logger log = LogManager.getLogger(TaskController.class);
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskListService taskListService;

	/***
	 * get all task
	 * 
	 * @return
	 * when record found 
	 * 	<p>{@link ResponseEntity}<>(result, {@link HttpStatus.OK})</p>
	 * otherwise,
	 * 	<p>{{@link ResponseEntity}<>({@link HttpStatus.NO_CONTENT})}</p>
	 */
	@GetMapping("/task/all")
	public ResponseEntity<?> getAllTask() {
		log.info("*** getAllTask ***");

		List<Task> taskList = taskService.getAll();
		if (taskList == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(taskList, HttpStatus.OK);
	}

	/***
	 * getTaskById
	 * @param id
	 * 
	 * @return
	 * when record found 
	 * 	<p>{@link ResponseEntity}<>(result, {@link HttpStatus.OK})</p>
	 * otherwise,
	 * 	<p>{{@link ResponseEntity}<>({@link HttpStatus.NO_CONTENT})}</p>
	 */
	@GetMapping("/task/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable Long id) {
		log.info("*** getTaskById ***");
		Task task = taskService.getTaskById(id);
		if (task == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(task, HttpStatus.OK);

	}

	/***
	 * getTaskByTaskListIdAndId
	 * 
	 * @param taskListId
	 * @param id
	 * 
	 * @return
	 * when record found 
	 * 	<p>{@link ResponseEntity}<>(result, {@link HttpStatus.OK})</p>
	 * otherwise,
	 * 	<p>{{@link ResponseEntity}<>({@link HttpStatus.NO_CONTENT})}</p>
	 */
	@GetMapping("/task/{taskListId}/{id}")
	public ResponseEntity<?> getTaskByTaskListIdAndId(@PathVariable Long taskListId, @PathVariable Long id) {
		log.info("*** getTaskByTaskListIdAndId ***");
		Task task = taskService.getTaskById(id);
		if (task == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(task, HttpStatus.OK);
	}

	/***
	 * addTask
	 * 
	 * @param taskListId Long
	 * @param task {@link Task}
	 * 
	 * @return
	 * when success
	 * 	<p>{@link ResponseEntity}<>(result, {@link HttpStatus.OK})</p>
	 * otherwise,
	 * 	<p>{{@link ResponseEntity}<>({@link HttpStatus.NO_CONTENT})}</p>
	 */
	@PostMapping("/task/{taskListId}")
	public ResponseEntity<?> addTask(@PathVariable Long taskListId, @RequestBody Task task) {
		log.info("*** addTask ***");
		
		TaskList parent = taskListService.getTaskById(taskListId);
		if (parent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		task.setTaskListId(taskListId);
		Long id = taskService.insertTask(task);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return new ResponseEntity<>(location, HttpStatus.CREATED);
	}

	/***
	 * modifyTask
	 * 
	 * @param taskListId Long
	 * @param task {@link Task}
	 * 
	 * @return
	 * when success 
	 * 	<p>{@link ResponseEntity}<>(uri, {@link HttpStatus.OK})</p>
	 * otherwise,
	 * 	<p>{{@link ResponseEntity}<>({@link HttpStatus.BAD_REQUEST})}</p>
	 */
	@PutMapping("/task/{id}")
	public ResponseEntity<?> modifyTask(@RequestBody Task task, @PathVariable Long id) {
		log.info("*** modifyTask ***");

		Task pre = taskService.getTaskById(id);
		if (pre == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		taskService.updateTask(task, id);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id).toUri();
		
		return new ResponseEntity<>(location, HttpStatus.OK);
	}

	/***
	 * deleteTask
	 * 
	 * @param id Long
	 * 
	 * @return
	 * when delete success
	 * 	<p>{@link ResponseEntity}<>(result, {@link HttpStatus.OK})</p>
	 * otherwise,
	 * 	<p>{{@link ResponseEntity}<>({@link HttpStatus.BAD_REQUEST})}</p>
	 */
	@DeleteMapping("/task/{id}")
	public ResponseEntity<?> deleteTaskLogical(@PathVariable Long id) {
		log.info("*** deleteTaskLogical ***");
		Task pre = taskService.getTaskById(id);
		if (pre == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		taskService.deleteTaskLogical(id);
		// taskListService.deleteTaskListPhysical(id);//delete task physical can use
		return new ResponseEntity<>(HttpStatus.OK);
	}
    
 }
