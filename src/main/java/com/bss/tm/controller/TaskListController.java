package com.bss.tm.controller;

import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@RestController
public class TaskListController {
	
	private static final Logger log = LogManager.getLogger(TaskListController.class);
	
	@Autowired
	private TaskListService taskListService;

	/***
	 * getAllTaskList
	 * @param id
	 * 
	 * @return
	 * when record found 
	 * 	<p>{@link ResponseEntity}<>(result, {@link HttpStatus.OK})</p>
	 * otherwise,
	 * 	<p>{{@link ResponseEntity}<>({@link HttpStatus.NO_CONTENT})}</p>
	 */
	@GetMapping(path="/taskList/all", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> getAllTaskList() {
    	log.info("*** getAllTaskList ***");
    	List<TaskList> taskList = taskListService.getAll();
    	if (taskList == null) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	else {
    		return new ResponseEntity<>(taskList, HttpStatus.OK);
    	}
    }
    
	/***
	 * getTaskListById
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
    @GetMapping(path="/taskList/{id}")
    public ResponseEntity<?> getTaskListById(@PathVariable Long id) {
    	log.info("*** getTaskListById ***");
    	TaskList taskList = taskListService.getTaskById(id);
    	if (taskList == null) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<TaskList>(taskList, HttpStatus.OK);
    }
    
    /***
	 * addTask
	 * 
	 * @param taskListId Long
	 * @param task {@link Task}
	 * 
	 * @return
	 * when success
	 * 	<p>{@link ResponseEntity}<>(uri, {@link HttpStatus.OK})</p>
	 * otherwise,
	 * 	<p>{{@link ResponseEntity}<>({@link HttpStatus.NO_CONTENT})}</p>
	 */
    @PostMapping("/taskList")
    public ResponseEntity<?> addTaskList(@RequestBody TaskList task) {
    	log.info("*** addTaskList ***");
    	Long id = taskListService.insertTaskList(task);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    			.buildAndExpand(id).toUri();
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
    @PutMapping("/taskList/{id}")
    public ResponseEntity<?> modifyTaskList(@RequestBody TaskList task, @PathVariable Long id) {
    	log.info("*** modifyTaskList ***");
    	
    	TaskList pre = taskListService.getTaskById(id);
    	if (pre == null) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	taskListService.updateTaskList(task, id);
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
    @DeleteMapping("/taskList/{id}")
    public ResponseEntity<?> deleteTaskList(@PathVariable Long id) {
    	log.info("*** deleteTaskLogical ***");
    	TaskList pre = taskListService.getTaskById(id);
    	
    	if (pre == null) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	taskListService.deleteTaskListLogical(id);
    	// taskListService.deleteTaskListPhysical(id); // delete task physical can be use.
    	return new ResponseEntity<>(HttpStatus.OK);
    }
 }
